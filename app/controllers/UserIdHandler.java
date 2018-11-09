package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import play.libs.Json;
import services.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserIdHandler{

    private ObjectMapper mapper = new ObjectMapper();
    private final UserService userService;


    UserIdHandler(UserService userService){
        this.userService = userService;
    }


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
                User buddie = userService.getUserFromId(u.asLong());
                buddies.add(buddie);
                System.out.println(buddie.getFullName());
            }
        }
        //final user to change
        userToChange.setBuddies(buddies);

        return userToChange;
    }
}
