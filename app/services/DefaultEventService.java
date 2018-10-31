package services;

import models.Event;
import models.EventRepository;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public class DefaultEventService implements EventService{
        private EventRepository eventRepository;

        @Inject
        public DefaultEventService(EventRepository eventRepository) {
            this.eventRepository = eventRepository;
        }

        @Override
        public CompletionStage<Event> get(Long id) {
            return eventRepository.find(id);
        }

        @Override
        public CompletionStage<Stream<Event>> getAll() {
            return eventRepository.list();
        }

        @Override
        public CompletionStage<Event> add(Event event) {
            return eventRepository.add(event);
        }

        @Override
        public CompletionStage<Boolean> delete(Long id) {
            return eventRepository.remove(id);
        }

        @Override
        public CompletionStage<Event> change(Event changeEvent) {
            return eventRepository.change(changeEvent);
        }


}
