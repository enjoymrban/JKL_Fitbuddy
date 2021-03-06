/**
 * Copyright 2012-214 Jorge Aliss (jaliss at gmail dot com) - twitter: @jaliss
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package controllers;

import com.google.inject.Inject;
import models.User;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import securesocial.core.BasicProfile;
import securesocial.core.RuntimeEnvironment;
import securesocial.core.java.SecureSocial;
import securesocial.core.java.SecuredAction;
import securesocial.core.java.UserAwareAction;


import java.util.concurrent.CompletionStage;
import java.util.function.Function;


/**
 * A sample controller
 */
public class Application extends Controller {
    public static Logger.ALogger logger = Logger.of("application.controllers.Application");
    private RuntimeEnvironment env;

    /**
     * A constructor needed to get a hold of the environment instance.
     * This could be injected using a DI framework instead too.
     *
     * @param env
     */
    @Inject()
    public Application (RuntimeEnvironment env) {
        this.env = env;
    }
    /**
     * This action only gets called if the user is logged in.
     *
     * @return
     */


    @UserAwareAction
    public Result userAware() {
        User demoUser = (User) ctx().args.get(SecureSocial.USER_KEY);
        String userName;
        if ( demoUser != null ) {
            BasicProfile user = demoUser.getProfile();
            if ( user.firstName().isDefined() ) {
                userName = user.firstName().get();
            } else if ( user.fullName().isDefined()) {
                userName = user.fullName().get();
            } else {
                userName = "authenticated user";

            }
            return ok(Json.toJson(demoUser));

        } else {
            userName = "guest";
            return ok("not authenticated");
        }
//        return ok("Hello " + userName + ", you are seeing a public page");

    }

    @SecuredAction(authorization = WithProvider.class, params = {"facebook"})
    public Result onlyFacebook() {
        return ok("You are seeing this because you logged in using Facebook");
    }


    /**
     * Sample use of SecureSocial.currentUser. Access the /current-user to test it
     */
    public CompletionStage<Result> currentUser() {
        return SecureSocial.currentUser(env).thenApply( new Function<Object, Result>() {
            public Result apply(Object maybeUser) {
                String id;

                if ( maybeUser != null ) {
                    User user = (User) maybeUser;
                    id = user.getAuthUserId();
                } else {
                    id = "not available. Please log in.";
                }
                return ok("your id is " + id);
            }
        });
    }
}