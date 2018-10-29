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
}
