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
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;


public class UserController extends Controller {

    private final UserService userService;
    private final HttpExecutionContext ec;

    @Inject
    public UserController(UserService userService, HttpExecutionContext ec) {
        this.UserService = userService;
        this.ec = ec;
    }

    public CompletionStage<Result> getOneUser(Long id) {
        return userService.get(id).thenApplyAsync(user -> {
            return ok(Json.toJson(user));
        }, ec.current());
    }
    public CompletionStage<Result> getAllUsers() {
        return eventService.getAll().thenApplyAsync(personStream -> {
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

    public CompletionStage<Result> changeUser(Long id) {
        final JsonNode jsonRequest = request().body().asJson();
        final User userToChange = Json.fromJson(jsonRequest, User.class);
        userToChange.setId(id);
        return userService.change(userToChange).thenApplyAsync(user -> {
            return ok(Json.toJson(user));
        }, ec.current());

    }

    public CompletionStage<Result> deleteUser(Long id) {
        return userService.delete(id).thenApplyAsync(removed -> {
            return removed ? ok() : internalServerError();
        }, ec.current());
    }

}
