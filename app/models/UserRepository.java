package models;

import play.db.jpa.JPAApi;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;
import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * Provide JPA operations running inside of a thread pool sized to the connection pool
 */


public class UserRepository {

    private final JPAApi jpaApi;

    @Inject
    public UserRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }


    public CompletionStage<User> find(Long id) {
        return supplyAsync(() -> wrap(em -> find(em, id)));
    }

    public CompletionStage<Stream<User>> list() {
        return supplyAsync(() -> wrap(em -> list(em)));
    }

    public CompletionStage<User> add(User user) {
        return supplyAsync(() -> wrap(em -> insert(em, user)));
    }

    public CompletionStage<User> change(User user) {
        return supplyAsync(() -> wrap(em -> change(em, user)));
    }

    public CompletionStage<Boolean> remove(Long id) {
        return supplyAsync(() -> wrap(em -> remove(em, id)));
    }

    //get one user object
    public User findOneUser(Long id) { return wrap(em -> find(em, id)); }

    private User find(EntityManager em, Long id) {
        return em.find(User.class, id);
    }

    //get one user object by AuthID, wird benötigt um bei erneutem einloggen eines users zu überprüfen, ob er bereits in der Datenbank vorhanden ist.
    public User findUserByAuthID(String authUserId){return wrap(em -> findByAuthId(em, authUserId));}

    private User findByAuthId(EntityManager em, String authUserId) {
        User user;
        try {
            user = em.createQuery("select u from fitUser u where u.authUserId=:authId", User.class).setParameter("authId",authUserId).getSingleResult();
            return user;
        } catch (javax.persistence.NoResultException error) {
            return null;
        }
    }



    private Stream<User> list(EntityManager em) {
        List<User> user = em.createQuery("select u from fitUser u", User.class).getResultList();
        return user.stream();
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private User insert(EntityManager em, User user) {
        em.persist(user);
        return user;
    }

    private User change(EntityManager em, User user) {
        User userToChange = em.find(User.class, user.getId());
        userToChange.setDescription(user.getDescription());
        userToChange.setFirstName(user.getFirstName());
        userToChange.setLastName(user.getLastName());
        userToChange.setFullName(user.getFullName());
        userToChange.setEmail(user.getEmail());
        userToChange.setAvatarUrl(user.getAvatarUrl());
        userToChange.setCategories(user.getCategories());
        userToChange.setBuddies(user.getBuddies());
        userToChange.setFollowing(user.getFollowing());
        userToChange.setInterestingEvents(user.getInterestingEvents());
        userToChange.setParticipatingEvent(user.getParticipatingEvent());
        userToChange.setProviderId(user.getProviderId());
        userToChange.setAuthUserId(user.getAuthUserId());
        return userToChange;
    }

    private Boolean remove(EntityManager em, Long id) {
        User user = em.find(User.class, id);
        if(null != user) {
            em.remove(user);
            return true;
        }
        return false;
    }
}