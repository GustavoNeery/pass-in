package nry.com.pass_in.domain;

import nry.com.pass_in.domain.attendee.Attendee;
import nry.com.pass_in.domain.event.Event;
import nry.com.pass_in.services.EventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class EventServiceTest {
    private Event event;
    private EventService eventService;
    private Attendee attendee;

    @BeforeEach
    void setup() {
        event = new Event();
        event.setTitle("primeiro");
        event.setDetails("esse é o primeiro evento");
        event.setSlug("apenas para teste");
        event.setMaximumAttendees(2);

        LocalDateTime dateTime = LocalDateTime.now();
        attendee = new Attendee();
        attendee.setEvent(event);
        attendee.setName("Ivna Velez");
        attendee.setEmail("ivnavelezteste@gmail.com");
        attendee.setCreatedAt(dateTime);
    }

    @Test
    @DisplayName("")
    void shouldIncrementAttendeeNumberWhenAddNewAttendee() {
       eventService.getAllEvents();
    }
}
