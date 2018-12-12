package controllers.SecureSocialTests;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import javax.print.DocFlavor;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

public class SinglePageControllerTest extends WithApplication {

    String url = "http://localhost:9000";

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }



    @Test
    public void testIndex() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(url+"/");

        Result result = route(app, request);
        assertEquals(200, result.status());
    }
    @Test
    public void testFitbuddies() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(url+"/fitbuddies");

        Result result = route(app, request);
        assertEquals(303, result.status());
    }
    @Test
    public void testMyProfile() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(url+"/myprofile");

        Result result = route(app, request);
        assertEquals(303, result.status());
    }
    @Test
    public void testMyEvents() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(url+"/myevents");

        Result result = route(app, request);
        assertEquals(303, result.status());
    }

    @Test
    public void testAuthLogin() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(url+"/auth/login");

        Result result = route(app, request);
        assertEquals(200, result.status());
    }

    @Test
    public void testUserAware() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(url+"/userAware");

        Result result = route(app, request);
        assertEquals(200, result.status());
    }





}
