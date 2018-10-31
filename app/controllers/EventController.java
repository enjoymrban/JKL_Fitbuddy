package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Event;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.EventService;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;


public class EventController extends Controller {

    private final EventService eventService;
    private final HttpExecutionContext ec;

    @Inject
    public EventController(EventService eventService, HttpExecutionContext ec) {
        this.eventService = eventService;
        this.ec = ec;
    }

    public CompletionStage<Result> getOneEvent(Long id) {
        return eventService.get(id).thenApplyAsync(event -> {
            return ok(Json.toJson(event));
        }, ec.current());
    }
    public CompletionStage<Result> getAllEvents() {
        return eventService.getAll().thenApplyAsync(personStream -> {
            return ok(Json.toJson(personStream.collect(Collectors.toList())));
        }, ec.current());
    }

    @BodyParser.Of(BodyParser.Json.class)
    public CompletionStage<Result> addEvent() {
        final JsonNode jsonRequest = request().body().asJson();
        final Event eventToAdd = Json.fromJson(jsonRequest, Event.class);

        return eventService.add(eventToAdd).thenApplyAsync(event -> {
            return ok(Json.toJson(event));
        }, ec.current());
    }

    public CompletionStage<Result> changeEvent(Long id) {
        final JsonNode jsonRequest = request().body().asJson();
        final Event eventToChange = Json.fromJson(jsonRequest, Event.class);
        eventToChange.setId(id);
        return eventService.change(eventToChange).thenApplyAsync(event -> {
            return ok(Json.toJson(event));
        }, ec.current());

    }

    public CompletionStage<Result> deleteEvent(Long id) {
        return eventService.delete(id).thenApplyAsync(removed -> {
            return removed ? ok() : internalServerError();
        }, ec.current());
    }

}
