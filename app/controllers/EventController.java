package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Event;
import models.User;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import securesocial.core.java.SecureSocial;
import securesocial.core.java.SecuredAction;
import securesocial.core.java.UserAwareAction;
import services.EventService;
import services.FitUserService;

import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;


public class EventController extends Controller {

    private final EventService eventService;
    private final HttpExecutionContext ec;
    private final FitUserService fitUserService;
    private final UserIdHandler userIdHandler;

    @Inject
    public EventController(EventService eventService, HttpExecutionContext ec, FitUserService fitUserService) {
        this.eventService = eventService;
        this.ec = ec;
        this.fitUserService = fitUserService;
        this.userIdHandler = new UserIdHandler(fitUserService);
    }

    @SecuredAction
    public CompletionStage<Result> getOneEvent(Long id) {
        return eventService.get(id).thenApplyAsync(event -> {
            return ok(userIdHandler.getCustomJsonFromEvent(event));
        }, ec.current());
    }
    //@UserAwareAction
    public CompletionStage<Result> getAllEvents() {
        return eventService.getAll().thenApplyAsync(personStream -> {
            return ok(Json.toJson(personStream.collect(Collectors.toList())));
        }, ec.current());
    }

    @SecuredAction
    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> addEvent() {
        final JsonNode jsonRequest = request().body().asJson();
        final Event eventToAdd = Json.fromJson(jsonRequest, Event.class);

        return eventService.add(eventToAdd).thenApplyAsync(event -> {
            return ok(Json.toJson(event));
        }, ec.current());
    }
    @SecuredAction
    public CompletionStage<Result> changeEvent(Long id) throws IOException {
        final JsonNode jsonRequest = request().body().asJson();

        //Check if the user is the creater of the event
        User actualUser = (User) ctx().args.get(SecureSocial.USER_KEY);
        Long requestedEventId = jsonRequest.get("id").asLong();
        Event eventToChange = null;
        Long creatorId = jsonRequest.get("creator.id").asLong();
        if(actualUser.getId()==creatorId) {
            return eventService.change(userIdHandler.getCustomEventFromJson(jsonRequest, id)).thenApplyAsync(event -> {
                return ok("Event updated!");
            }, ec.current());
        }else{
            return null;
        }
    }
    @SecuredAction
    public CompletionStage<Result> deleteEvent(Long id) {
        return eventService.delete(id).thenApplyAsync(removed -> {
            return removed ? ok() : internalServerError();
        }, ec.current());
    }

}
