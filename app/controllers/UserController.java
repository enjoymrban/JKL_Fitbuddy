package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.db.jpa.JPAApi;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import scala.util.parsing.json.JSONArray;
import services.UserService;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import play.api.Logger;
import javax.persistence.EntityManager;



public class UserController extends Controller {

    private final UserService userService;
    private final HttpExecutionContext ec;
    private ObjectMapper mapper = new ObjectMapper();

    @Inject
    public UserController(UserService userService, HttpExecutionContext ec) {
        this.userService = userService;
        this.ec = ec;
    }



    public CompletionStage<Result> getOneUser(Long id) {
        return userService.get(id).thenApplyAsync(user -> {
            return ok(getCustomJson(user));
        }, ec.current());
    }
    public CompletionStage<Result> getAllUsers() {
        return userService.getAll().thenApplyAsync(personStream -> {
            return ok(Json.toJson(personStream.collect(Collectors.toList())));
        }, ec.current());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> addUser() {
        final JsonNode jsonRequest = request().body().asJson();
        final User userToAdd = Json.fromJson(jsonRequest, User.class);

        return userService.add(userToAdd).thenApplyAsync(user -> {
            return ok(Json.toJson(user));
        }, ec.current());
    }

    public CompletionStage<Result> changeUser(Long id) throws IOException {

        List<User> buddies = new ArrayList<>();

        final JsonNode jsonRequest = request().body().asJson();
        final User userToChange = Json.fromJson(jsonRequest, User.class);
        userToChange.setId(id);

        String jsonString = jsonRequest.toString();

        final JsonNode budJson = mapper.readTree(jsonString).get("buddies");
        if (budJson.isArray()) {
            for (JsonNode u : budJson) {
                //insert User to Arraylist
                User buddie = userService.getUserFromId(u.asLong());
                buddies.add(buddie);
                System.out.println(buddie.getFullName());
            }
        }
        //final user to change
        userToChange.setBuddies(buddies);
        return userService.change(userToChange).thenApplyAsync(user -> {
            return ok("User changed successfully");
        }, ec.current());

    }

    public CompletionStage<Result> deleteUser(Long id) {
        return userService.delete(id).thenApplyAsync(removed -> {
            return removed ? ok() : internalServerError();
        }, ec.current());
    }

    private JsonNode getCustomJson(User user){
        JsonNode json = Json.toJson(user);
        List<User> buddies = user.getBuddies();
        ArrayList<Long> list = new ArrayList<>();

        for (User u : buddies) {
            list.add(u.getId());
        }
        ArrayNode buddieIds = mapper.valueToTree(removeDublicate(list));
        ((ObjectNode) json).putArray("buddies").addAll(buddieIds);
        return json;
    }

    private ArrayList<Long> removeDublicate(ArrayList<Long> ls){
        Set<Long> hs = new HashSet<>();
        hs.addAll(ls);
        ls.clear();
        ls.addAll(hs);
        return ls;
    }
}
