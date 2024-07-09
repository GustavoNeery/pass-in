package nry.com.pass_in.controllers;

import lombok.RequiredArgsConstructor;
import nry.com.pass_in.dto.attendee.AttendeeListResponseDTO;
import nry.com.pass_in.dto.event.EventIdDTO;
import nry.com.pass_in.dto.event.EventRequestDTO;
import nry.com.pass_in.dto.event.EventResponseDTO;
import nry.com.pass_in.services.AttendeeService;
import nry.com.pass_in.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    @Autowired
    private final EventService eventService;

    @Autowired
    private final AttendeeService attendeeService;

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String eventId){
        EventResponseDTO event = this.eventService.getEventDetail(eventId);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        EventIdDTO eventIdDTO = this.eventService.createEvent(body);

        var uri = uriComponentsBuilder.path("/events/{eventId}").buildAndExpand(eventIdDTO.eventId()).toUri();

        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @GetMapping("/attendees/{id}")
    public ResponseEntity<AttendeeListResponseDTO> getEventAttendees(@PathVariable String id){
        AttendeeListResponseDTO attendeeListResponse = this.attendeeService.getEventsAttendee(id);
        return ResponseEntity.ok(attendeeListResponse);
    }
}
