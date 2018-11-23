package services;

import models.User;
import models.UserRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import play.Logger;
import securesocial.core.BasicProfile;
import securesocial.core.PasswordInfo;
import securesocial.core.services.SaveMode;
import securesocial.core.java.BaseUserService;
import securesocial.core.java.Token;
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
                // hier muss geprüft werden, ob user schon in db ist. zurzeit wird immer ein neuer user erstellt
                User foundUser = userRepository.findUserByAuthID(profile.userId());
                if(foundUser!=null){
                    //User existiert schon in DB. Nicht neu adden
                    result = foundUser;
                    result.setProfile(profile);
                }else{
                    //Der User existiert noch nicht
                    result = new User(profile);
                    userRepository.add(result);
                }
                users.put(profile.providerId() + profile.userId(), result);
            } else if (mode == SaveMode.LoggedIn()) {
                System.out.println("SaveMode.LoggedIn aufgerufen -- siehe Kommentar");
                if(logger.isDebugEnabled()){ logger.debug("SaveMode.LoggedIn aufgerufen"); }
                // LoggedIn deutet darauf hin, dass es einen User mit dieser authID gibt, testen ohne if else
                result = userRepository.findUserByAuthID(profile.userId());
                result.setProfile(profile);
                /*CompletionStage<Stream<User>> userList = userRepository.list();
                .thenApplyAsync(stream -> {
                    return stream.collect(Collectors.toList());
                });

                        .thenApplyAsync(personStream -> {
                    return personStream.collect(Collectors.toList());
                });

                for (Iterator<User> it =  users.values().iterator() ; it.hasNext() && result == null ; ) {
                    User user = it.next();
                    if ( user.getAuthUserId().equals(profile.userId()) ) {
                        user.remove(p);
                        user.identities.add(profile);
                        result = user;
                        break;
                    }
                }*/
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
            tokens.put(token.uuid, token);
            return new CompletableFuture().completedFuture(token);
        }

        @Override
        public CompletionStage<BasicProfile> doFind(String providerId, String userId) {
            if(logger.isDebugEnabled()){ logger.debug("Finding user " + userId); }
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
