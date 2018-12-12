package controllers.SecureSocialTests;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;


import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

public class EventControllerTest extends WithApplication {

    String url = "http://localhost:9000";

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }


    // Tests SinglePageControler
    @Test
    public void testGetAllEvents() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(url+"/api/event");

        Result result = route(app, request);
        assertEquals(200, result.status());
    }
    @Test
    public void testGetOneEvent() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(url+"/api/event/1");

        Result result = route(app, request);
        assertEquals(303, result.status());
    }
    @Test
    public void testAddEvent() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .uri(url+"/api/event")
                .header("content-type", "application/json")
                .bodyJson(Json.toJson("{'name': 'test'}"));

        Result result = route(app, request);
        assertEquals(303, result.status());
    }
    @Test
    public void testChangeEvent() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(PUT)
                .uri(url+"/api/event/1");

        Result result = route(app, request);
        assertEquals(303, result.status());
    }
    @Test
    public void testDeleteEvent() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(DELETE)
                .uri(url+"/api/event/1");

        Result result = route(app, request);
        assertEquals(303, result.status());
    }

    @Test
    public void testJoinEvent() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(url+"/api/event/1");

        Result result = route(app, request);
        assertEquals(303, result.status());
    }

    @Test
    public void testLeaveEvent() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(url+"/api/event/1");

        Result result = route(app, request);
        assertEquals(303, result.status());
    }







}
