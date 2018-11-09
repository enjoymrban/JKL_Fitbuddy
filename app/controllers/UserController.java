package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.User;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;




public class UserController extends Controller {

    private final UserService userService;
    private final UserIdHandler userIdHandler;
    private final HttpExecutionContext ec;


    @Inject
    public UserController(UserService userService, HttpExecutionContext ec) {
        this.userService = userService;
        this.ec = ec;
        this.userIdHandler = new UserIdHandler(userService);
    }



    public CompletionStage<Result> getOneUser(Long id) {
        return userService.get(id).thenApplyAsync(user -> {
            return ok(userIdHandler.getCustomJsonFromUser(user));
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
        final JsonNode jsonRequest = request().body().asJson();
        return userService.change(userIdHandler.getCustomUserFromJson(jsonRequest, id)).thenApplyAsync(user -> {
            return ok("User updated");
        }, ec.current());

    }

    public CompletionStage<Result> deleteUser(Long id) {
        return userService.delete(id).thenApplyAsync(removed -> {
            return removed ? ok() : internalServerError();
        }, ec.current());
    }
}
