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

    public CompletionStage<Stream<Event>> list() {
        return supplyAsync(() -> wrap(em -> list(em)));
    }

    public CompletionStage<Event> add(Event event) {
        return supplyAsync(() -> wrap(em -> insert(em, event)));
    }

    public CompletionStage<Event> change(Event event) {
        return supplyAsync(() -> wrap(em -> change(em, event)));
    }

    public CompletionStage<Boolean> remove(Long id) {
        return supplyAsync(() -> wrap(em -> remove(em, id)));
    }



    private Event find(EntityManager em, Long id) {
        return em.find(Event.class, id);
    }



    private Stream<Event> list(EntityManager em) {
        List<Event> events = em.createQuery("select e from event e", Event.class).getResultList();
        return events.stream();
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Event insert(EntityManager em, Event event) {
        em.persist(event);
        return event;
    }

    private Event change(EntityManager em, Event event) {
        Event eventToChange = em.find(Event.class, event.getId());
        eventToChange.setCategory(event.getCategory());
        eventToChange.setDescription(event.getDescription());
        eventToChange.setDate(event.getDate());
        eventToChange.setNrOfPlayers(event.getNrOfPlayers());
        eventToChange.setCoordinateX(event.getCoordinateX());
        eventToChange.setCoordinateY(event.getCoordinateY());
        return eventToChange;
    }

    private Boolean remove(EntityManager em, Long id) {
        Event event = em.find(Event.class, id);
        if(null != event) {
            em.remove(event);
            return true;

        }
        return false;
    }
}