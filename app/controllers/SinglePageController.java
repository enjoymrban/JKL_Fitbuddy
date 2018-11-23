package controllers;

import play.mvc.*;
import securesocial.core.java.SecuredAction;

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
    public Result index() {
        return ok(views.html.index.render());
    }

    public Result fitbuddies() {
        return ok(views.html.fitbuddies.render());
    }

    @SecuredAction
    public Result myprofile() {
        return ok(views.html.myprofile.render());
    }

    public Result myevents() {
        return ok(views.html.myevents.render());
    }

}
