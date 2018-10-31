package services;

import com.google.inject.ImplementedBy;
import models.Event;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;


@ImplementedBy(DefaultEventService.class)
public interface EventService {

    /**
     * Returns
     *
     * @param id Event identifier
     * @return Event
     */

    CompletionStage<Event> get(final Long id);

    /**
     * Return's list of all events.
     *
     * @return list of all events
     */
    CompletionStage<Stream<Event>> getAll();

    /**
     * Adds the given event.
     *
     * @param event to add
     * @return added book
     */
    CompletionStage<Event> add(final Event event);

    /**
     * Removes event with given identifier.
     *
     * @param id event identifier
     * @return {@code true} on success  {@code false} on failure
     */
    CompletionStage<Boolean> delete(final Long id);

    /**
     * Updates event with given identifier.
     *
     * @param changeEvent book with updated fields
     * @return updated event
     */
    CompletionStage<Event> change(final Event changeEvent);
}
