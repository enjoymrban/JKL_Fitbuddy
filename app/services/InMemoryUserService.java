/**
 * Copyright 2012-2014 Jorge Aliss (jaliss at gmail dot com) - twitter: @jaliss
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
package services;


import models.User;
import play.Logger;
import play.libs.F;
import securesocial.core.BasicProfile;
import securesocial.core.PasswordInfo;
import securesocial.core.services.SaveMode;
import securesocial.core.java.BaseUserService;
import securesocial.core.java.Token;
import securesocial.core.providers.UsernamePasswordProvider;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * A Sample In Memory user service in Java
 *
 * Note: This is NOT suitable for a production environment and is provided only as a guide.
 * A real implementation would persist things in a database
 */
public class InMemoryUserService extends BaseUserService<User> {
    public Logger.ALogger logger = Logger.of("application.service.InMemoryUserService");

    private HashMap<String, User> users = new HashMap<String, User>();
    private HashMap<String, Token> tokens = new HashMap<String, Token>();

    @Override
    public CompletionStage<User> doSave(BasicProfile profile, SaveMode mode) {
        User result = null;
        if (mode == SaveMode.SignUp()) {
            result = new User(profile);
            users.put(profile.providerId() + profile.userId(), result);
        } else if (mode == SaveMode.LoggedIn()) { /*
            for (Iterator<User> it =  users.values().iterator() ; it.hasNext() && result == null ; ) {
                User user = it.next();
                if ( user.getAuthUserId().equals(profile.userId()) ) {
                    user.remove(p);
                    user.identities.add(profile);
                    result = user;
                    break;
                }
                }
            } */
        } else {
            throw new RuntimeException("Unknown mode");
        }
        return new CompletableFuture().completedFuture(result);
    }

    @Override
    public CompletionStage<User> doLink(User current, BasicProfile to) {
        return null;
    }

    @Override
    public CompletionStage<Token> doSaveToken(Token token) {
        return null;
    }

    @Override
    public CompletionStage<BasicProfile> doFind(String providerId, String userId) {
        return null;
    }

    @Override
    public CompletionStage<PasswordInfo> doPasswordInfoFor(User user) {
        throw new RuntimeException("doPasswordInfoFor is not implemented yet in sample app");
    }

    @Override
    public CompletionStage<BasicProfile> doUpdatePasswordInfo(User user, PasswordInfo info) {
        throw new RuntimeException("doUpdatePasswordInfo is not implemented yet in sample app");
    }

    @Override
    public CompletionStage<Token> doFindToken(String tokenId) {
        return new CompletableFuture().completedFuture(tokens.get(tokenId));
    }


    @Override
    public CompletionStage<BasicProfile> doFindByEmailAndProvider(String email, String providerId) {
        return null;
    }

    @Override
    public CompletionStage<Token> doDeleteToken(String uuid) {
        return new CompletableFuture().completedFuture(tokens.remove(uuid));
    }

    @Override
    public void doDeleteExpiredTokens() {
        Iterator<Map.Entry<String,Token>> iterator = tokens.entrySet().iterator();
        while ( iterator.hasNext() ) {
            Map.Entry<String, Token> entry = iterator.next();
            if ( entry.getValue().isExpired() ) {
                iterator.remove();
            }
        }
    }
}