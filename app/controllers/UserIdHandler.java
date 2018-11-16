package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Event;
import models.User;
import play.libs.Json;
import services.FitUserService;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public  class UserIdHandler{

    private ObjectMapper mapper = new ObjectMapper();
    private final FitUserService fitUserService;


    @Inject
    public UserIdHandler(FitUserService fitUserService) { this.fitUserService = fitUserService; }


    public JsonNode getCustomJsonFromUser(User user){
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

    public JsonNode getCustomJsonFromEvent(Event event){
        ArrayList<Long> in = new ArrayList<>();
        ArrayList<Long> pa = new ArrayList<>();
        JsonNode json = Json.toJson(event);
        List<User> interested = event.getInterested();
        List<User> participants = event.getParticipants();
        //Add the interested
        for (User u : interested) {
            in.add(u.getId());
        }
        //Add the participants
        for (User u : participants) {
            pa.add(u.getId());
        }
        ArrayNode interestedIds = mapper.valueToTree(removeDublicate(in));
        ArrayNode participantsIds = mapper.valueToTree(removeDublicate(pa));
        ((ObjectNode) json).putArray("interested").addAll(interestedIds);
        ((ObjectNode) json).putArray("participants").addAll(participantsIds);
        return json;
    }


    public ArrayList<Long> removeDublicate(ArrayList<Long> ls){
        Set<Long> hs = new HashSet<>();
        hs.addAll(ls);
        ls.clear();
        ls.addAll(hs);
        return ls;
    }

    public User getCustomUserFromJson(JsonNode jsonRequest, Long id) throws IOException {
        final User userToChange = Json.fromJson(jsonRequest, User.class);
        userToChange.setId(id);
        List<User> buddies = new ArrayList<>();
        String jsonString = jsonRequest.toString();
        final JsonNode budJson = mapper.readTree(jsonString).get("buddies");
        if (budJson.isArray()) {
            for (JsonNode u : budJson) {
                //insert User to Arraylist
                User buddie = fitUserService.getUserFromId(u.asLong());
                buddies.add(buddie);
                System.out.println(buddie.getFullName());
            }
        }
        //final user to change
        userToChange.setBuddies(buddies);
        return userToChange;
    }

    public Event getCustomEventFromJson(JsonNode jsonRequest, Long id) throws IOException {
        final Event eventToChange = Json.fromJson(jsonRequest, Event.class);
        eventToChange.setId(id);
        List<User> in = new ArrayList<>();
        List<User> pa = new ArrayList<>();
        String jsonString = jsonRequest.toString();
        final JsonNode inJson = mapper.readTree(jsonString).get("interested");
        final JsonNode paJson = mapper.readTree(jsonString).get("participants");
        if (inJson.isArray()) {
            for (JsonNode u : inJson) {
                User interested = fitUserService.getUserFromId(u.asLong());
                in.add(interested);
            }
        }
        if (paJson.isArray()) {
            for (JsonNode u : paJson) {
                User participant = fitUserService.getUserFromId(u.asLong());
                pa.add(participant);
            }
        }
        //final Event to change
        eventToChange.setInterested(in);
        eventToChange.setParticipants(pa);
        return eventToChange;
    }
}
