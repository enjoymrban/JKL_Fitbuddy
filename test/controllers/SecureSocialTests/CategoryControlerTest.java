package controllers.SecureSocialTests;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.*;

public class CategoryControlerTest extends WithApplication {

    String url = "http://localhost:9000";

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }


    @Test
    public void testGetAllCategories() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(url+"/api/category");

        Result result = route(app, request);
        assertEquals(200, result.status());
    }
    @Test
    public void testGetOneCategory() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri(url+"/api/category/1");

        Result result = route(app, request);
        assertEquals(200, result.status());
    }

}
