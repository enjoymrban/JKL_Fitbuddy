package services;

import com.google.inject.ImplementedBy;
import models.User;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;


@ImplementedBy(DefaultFitUserService.class)
public interface FitUserService {

    /**
     * Returns
     *
     * @param id User identifier
     * @return User
     */

    CompletionStage<User> get(final Long id);

    /**
     * Return's list of all users.
     *
     * @return list of all users
     */
    CompletionStage<Stream<User>> getAll();

    /**
     * Adds the given user.
     *
     * @param user to add
     * @return added user
     */
    CompletionStage<User> add(final User user);

    /**
     * Removes user with given identifier.
     *
     * @param id user identifier
     * @return {@code true} on success  {@code false} on failure
     */
    CompletionStage<Boolean> delete(final Long id);

    /**
     * Updates user with given identifier.
     *
     * @param changeUser book with updated fields
     * @return updated user
     */
    CompletionStage<User> change(final User changeUser);

    //Get one user from entity manager
    User getUserFromId (final Long id);
}
