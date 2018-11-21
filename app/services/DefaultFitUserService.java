package services;

import models.User;
import models.UserRepository;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

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





public class DefaultFitUserService extends BaseUserService<User> implements FitUserService {
        private UserRepository userRepository;

        @Inject
        public DefaultFitUserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

        @Override
        public CompletionStage<User> get(Long id) {
            return userRepository.find(id);
        }

        @Override
        public CompletionStage<Stream<User>> getAll() {
            return userRepository.list();
        }

        @Override
        public CompletionStage<User> add(User user) {
            return userRepository.add(user);
        }

        @Override
        public CompletionStage<Boolean> delete(Long id) {
            return userRepository.remove(id);
        }

        @Override
        public CompletionStage<User> change(User changeUser) {
            return userRepository.change(changeUser);
        }

        @Override
        public User getUserFromId(Long id){return userRepository.findOneUser(id);}

        // secure social zeug
        public Logger.ALogger logger = Logger.of("application.service.DefaultFitUserService");

        private HashMap<String, User> users = new HashMap<String, User>();
        private HashMap<String, Token> tokens = new HashMap<String, Token>();

        @Override
        public CompletionStage<User> doSave(BasicProfile profile, SaveMode mode) {
            User result = null;
            if (mode == SaveMode.SignUp()) {
                result = new User(profile);
                users.put(profile.providerId() + profile.userId(), result);
                userRepository.add(result);
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
            if(logger.isDebugEnabled()){
                logger.debug("Finding user " + userId);
            }
            BasicProfile found = null;

            for ( User u: users.values() ) {
                if ( u.getProviderId().equals(providerId) && u.getAuthUserId().equals(userId) ) {
                    found = u.getProfile();
                    break;
                }
            }

            return new CompletableFuture().completedFuture(found);
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
