package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.User;
import play.libs.Json;
import play.mvc.*;
import securesocial.core.BasicProfile;
import securesocial.core.java.SecureSocial;
import securesocial.core.java.SecuredAction;
import securesocial.core.java.UserAwareAction;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class SinglePageController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    @UserAwareAction
    public Result index() {
        User regUser = (User) ctx().args.get(SecureSocial.USER_KEY);
        long userId;
        JsonNode json;
        if ( regUser != null ) {
            userId = regUser.getId();
            json = Json.toJson(regUser);
        } else {
            userId = 0L;
            json = Json.toJson("not logged in");
        }
        //return ok(json);
        // return ok(userId);
        return ok(views.html.index.render());
    }

    @SecuredAction
    public Result fitbuddies() {
        return ok(views.html.fitbuddies.render());
    }

    @SecuredAction
    public Result myprofile() {
        return ok(views.html.myprofile.render());
    }

    @SecuredAction
    public Result myevents() {
        return ok(views.html.myevents.render());
    }

}
