package controllers.SecureSocialTests;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

public class UserControllerTest extends WithApplication {

    String url = "http://localhost:9000";

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }


    @Test
    public void testGetAllUsers() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(url+"/api/user");

        Result result = route(app, request);
        assertEquals(303, result.status());
    }
    @Test
    public void testGetOneUser() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(url+"/api/user/1");

        Result result = route(app, request);
        assertEquals(303, result.status());
    }
    @Test
    public void testChangeUser() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(PUT)
                .uri(url+"/api/user/1");

        Result result = route(app, request);
        assertEquals(303, result.status());
    }

}
