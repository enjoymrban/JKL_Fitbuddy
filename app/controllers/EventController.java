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
    public Result getAllEvents() {return null; }

    public Result addEvent() {
        return ok("it worked");
    }

    public Result changeEvent(long id) {
        return ok("it worked");
    }

    public Result deleteEvent(long id) {
        return ok("it worked");
    }

}
