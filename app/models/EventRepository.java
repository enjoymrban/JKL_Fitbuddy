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


public class EventRepository {

    private final JPAApi jpaApi;

    @Inject
    public EventRepository(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }


    public CompletionStage<Event> find(Long id) {
        return supplyAsync(() -> wrap(em -> find(em, id)));
    }



    private Event find(EntityManager em, Long id) {
        return em.find(Event.class, id);
    }



    private Stream<Event> list(EntityManager em) {
        List<Event> cards = em.createQuery("select e from event e", Event.class).getResultList();
        return cards.stream();
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Event insert(EntityManager em, Event event) {
        em.persist(event);
        return event;
    }
}