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
import java.util.ArrayList;
import java.util.List;
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
    @UserAwareAction
    public CompletionStage<Result> getAllEvents() {
        return eventService.getAll().thenApplyAsync(personStream -> {
            User actualUser = (User) ctx().args.get(SecureSocial.USER_KEY);
            if(actualUser!=null){
                return ok(Json.toJson(personStream.collect(Collectors.toList())));
            }else{}
                //Filter information in events for guests
                List<Event> realEvents = personStream.collect(Collectors.toList());
                List<Event> filterdEvents= new ArrayList<Event>();
                for (Event e : realEvents){
                    Event filterd = new Event();
                    filterd.setId(e.getId());
                    filterd.setNrOfPlayers(e.getNrOfPlayers());
                    filterd.setCategory(e.getCategory());
                    filterd.setCoordinateX(e.getCoordinateX());
                    filterd.setCoordinateY(e.getCoordinateY());
                    filterdEvents.add(filterd);
                }
                return ok(Json.toJson(Json.toJson(filterdEvents)));
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

        //SECURITY FIRST: Check if the user is the real creater of the event
        User actualUser = (User) ctx().args.get(SecureSocial.USER_KEY);
        Long reqEventId = jsonRequest.get("id").asLong();
        Event eventToChange = eventService.getOneEvent(reqEventId);
        User eventOwner = eventToChange.getCreator();

        if(actualUser.getAuthUserId().equals(eventOwner.getAuthUserId())){
            return eventService.change(userIdHandler.getCustomEventFromJson(jsonRequest, id)).thenApplyAsync(event -> {
                System.out.println("Event updatet; Event ID: "+reqEventId);
                return ok("Event updated!");
            }, ec.current());
        }else{
            return eventService.get(reqEventId).thenApplyAsync(event -> {
                System.out.println("Event not updatet; user is not owner");
                return ok("sorry, you are not the creator of this event");
            }, ec.current());
        }
    }
    @SecuredAction
    public CompletionStage<Result> deleteEvent(Long id) {
        //SECURITY FIRST: Check if the user is the real creater of the event
        User actualUser = (User) ctx().args.get(SecureSocial.USER_KEY);
        Event eventToChange = eventService.getOneEvent(id);
        User eventOwner = eventToChange.getCreator();

        if(actualUser.getAuthUserId().equals(eventOwner.getAuthUserId())){
            return eventService.delete(id).thenApplyAsync(removed -> {
                System.out.println("Delete Event with id: "+id);
                return removed ? ok() : internalServerError();
            }, ec.current());
        }else{
            return eventService.get(id).thenApplyAsync(event -> {
                System.out.println("Dont delete event "+id+" User is not the owner");
                return ok("sorry, you are not the creator of this event");
            }, ec.current());
        }
    }

    @SecuredAction
    public Result joinEvent(Long id) {
        //Get the user and the event he wants to change
        User actualUser = (User) ctx().args.get(SecureSocial.USER_KEY);
        Event eventToChange = eventService.getOneEvent(id);

        //Check if the user is already interested, if true: abort!
        List<User> interUsers = eventToChange.getInterested();
        for (User u : interUsers) {
            if (u.getAuthUserId().equals(actualUser.getAuthUserId())) {
                System.out.println("user already interested");
                return ok("you already interested");
            }
        }
        //Add the User to the interested List
        System.out.println("add the user");
        interUsers.add(actualUser);

        //Change the Event on in the repository
        eventToChange.setInterested(interUsers);
        eventService.change(eventToChange);
        return ok("you are now interested in this event");
    }

    @SecuredAction
    public Result leaveEvent(Long id) {
        //Get the user and the event he wants to change
        User actualUser = (User) ctx().args.get(SecureSocial.USER_KEY);
        Event eventToChange = eventService.getOneEvent(id);

        //Check if the user is  interested and delete him from interestedlist
        List<User> interUsers = eventToChange.getInterested();
        List<User> finalInterUsers = null;
        for (User u : interUsers) {
            if (!u.getAuthUserId().equals(actualUser.getAuthUserId())) {
                finalInterUsers.add(u);
            }
        }
        //Change the Event on in the repository
        eventToChange.setInterested(finalInterUsers);
        eventService.change(eventToChange);
        return ok("you are not interested anymore");
    }
}
