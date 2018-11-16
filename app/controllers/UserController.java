package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.User;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.FitUserService;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;


public class UserController extends Controller {

    private final FitUserService fitUserService;
    private final HttpExecutionContext ec;
    private final UserIdHandler userIdHandler;

    @Inject
    public UserController(FitUserService fitUserService, HttpExecutionContext ec) {
        this.fitUserService = fitUserService;
        this.userIdHandler = new UserIdHandler(fitUserService);
        this.ec = ec;
    }

    public CompletionStage<Result> getOneUser(Long id) {
        return fitUserService.get(id).thenApplyAsync(user -> {
            return ok(userIdHandler.getCustomJsonFromUser(user));
        }, ec.current());
    }

    public CompletionStage<Result> getAllUsers() {
        return fitUserService.getAll().thenApplyAsync(personStream -> {
            return ok(Json.toJson(personStream.collect(Collectors.toList())));
        }, ec.current());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> addUser() {
        final JsonNode jsonRequest = request().body().asJson();
        final User userToAdd = Json.fromJson(jsonRequest, User.class);

        return fitUserService.add(userToAdd).thenApplyAsync(user -> {
            return ok(Json.toJson(user));
        }, ec.current());
    }

    public CompletionStage<Result> changeUser(Long id) throws IOException {
        final JsonNode jsonRequest = request().body().asJson();
        return fitUserService.change(userIdHandler.getCustomUserFromJson(jsonRequest, id)).thenApplyAsync(user -> {
            return ok("User updated");
        }, ec.current());
    }

    public CompletionStage<Result> deleteUser(Long id) {
        return fitUserService.delete(id).thenApplyAsync(removed -> {
            return removed ? ok() : internalServerError();
        }, ec.current());
    }
}
