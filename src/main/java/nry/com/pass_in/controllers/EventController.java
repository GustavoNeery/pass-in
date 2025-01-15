package nry.com.pass_in.controllers;

import lombok.RequiredArgsConstructor;
import nry.com.pass_in.dto.attendee.AttendeeIdDTO;
import nry.com.pass_in.dto.attendee.AttendeeListResponseDTO;
import nry.com.pass_in.dto.attendee.AttendeeRequestDTO;
import nry.com.pass_in.dto.event.EventIdDTO;
import nry.com.pass_in.dto.event.EventListResponseDTO;
import nry.com.pass_in.dto.event.EventRequestDTO;
import nry.com.pass_in.dto.event.EventResponseDTO;
import nry.com.pass_in.services.AttendeeService;
import nry.com.pass_in.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private final EventService eventService;

    @Autowired
    private final AttendeeService attendeeService;

    public EventController(EventService eventService, AttendeeService attendeeService) {
        this.eventService = eventService;
        this.attendeeService = attendeeService;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String eventId){
        EventResponseDTO event = this.eventService.getEventDetail(eventId);
        return ResponseEntity.ok(event);
    }

    @GetMapping
    public ResponseEntity<EventListResponseDTO> getAllEvents(){
        EventListResponseDTO events = this.eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(@RequestBody EventRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        EventIdDTO eventIdDTO = this.eventService.createEvent(body);
        var uri = uriComponentsBuilder.path("/events/{eventId}").buildAndExpand(eventIdDTO.eventId()).toUri();
        return ResponseEntity.created(uri).body(eventIdDTO);
    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> registerParticipant(@PathVariable String eventId, @RequestBody AttendeeRequestDTO body, UriComponentsBuilder uriComponentsBuilder){
        AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeeOnEvent(eventId, body);
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();
        return ResponseEntity.created(uri).body(attendeeIdDTO);
    }

    @GetMapping("/attendees/{eventId}")
    public ResponseEntity<AttendeeListResponseDTO> getEventAttendees(@PathVariable String eventId){
        AttendeeListResponseDTO attendeeListResponse = this.attendeeService.getEventsAttendee(eventId);
        return ResponseEntity.ok(attendeeListResponse);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEventById(@PathVariable String eventId) {
        this.eventService.deleteEventById(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}