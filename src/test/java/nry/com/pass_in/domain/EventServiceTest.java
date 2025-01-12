package nry.com.pass_in.domain;

import nry.com.pass_in.controllers.EventController;
import nry.com.pass_in.domain.attendee.Attendee;
import nry.com.pass_in.domain.event.Event;
import nry.com.pass_in.dto.attendee.AttendeeRequestDTO;
import nry.com.pass_in.dto.event.EventIdDTO;
import nry.com.pass_in.dto.event.EventListResponseDTO;
import nry.com.pass_in.dto.event.EventRequestDTO;
import nry.com.pass_in.services.AttendeeService;
import nry.com.pass_in.services.EventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class EventServiceTest {
    private EventRequestDTO event;
    private EventService eventService;
    private AttendeeService attendeeService;
    private EventController eventController;
    private Attendee attendee;
    private EventIdDTO eventIdDto;
    private AttendeeRequestDTO attendeeRequestDTO;
    private AttendeeRequestDTO attendeeRequestDTO2;
    @BeforeEach
    void setup() {
        event = new EventRequestDTO("viagem", "esse é o primeiro evento", 1);
        eventIdDto = eventService.createEvent(event);


        attendeeRequestDTO = new AttendeeRequestDTO("Ivna", "ivnavelezteste@gmail.com");
        attendeeRequestDTO2 = new AttendeeRequestDTO("Luiz", "luiznery@gmail.com");

    }

    @Test
    @DisplayName("teste inicial")
    void shouldIncrementAttendeeNumberWhenAddNewAttendee() {
        eventService.registerAttendeeOnEvent(eventIdDto.eventId(), attendeeRequestDTO);
        eventService.registerAttendeeOnEvent(eventIdDto.eventId(), attendeeRequestDTO2);

       Assertions.assertEquals("Event is full", eventService.registerAttendeeOnEvent(eventIdDto.eventId(), attendeeRequestDTO2));
    }
}
