package services;

import models.User;
import models.UserRepository;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public class DefaultFitUserService implements FitUserService {
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


}
