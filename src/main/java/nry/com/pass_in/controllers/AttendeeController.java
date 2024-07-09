package nry.com.pass_in.controllers;

import lombok.RequiredArgsConstructor;
import nry.com.pass_in.domain.attendee.Attendee;
import nry.com.pass_in.dto.attendee.AttendeeIdDTO;
import nry.com.pass_in.services.AttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/attendees")
@RequiredArgsConstructor
public class AttendeeController {

    @Autowired
    private final AttendeeService service;

    @GetMapping("/attendee/{id}")
    public ResponseEntity<String> getEventAttendees(@PathVariable String attendeeId){
        this.service.getAllAttendeesFromEvent(attendeeId);
        return ResponseEntity.ok("Sucesso!");
    }

    @PostMapping
    public ResponseEntity<AttendeeIdDTO> createAttendee(@RequestBody Attendee attendee, UriComponentsBuilder uriComponentsBuilder) {
        AttendeeIdDTO attendeeIdDTO =  this.service.createAttendee(attendee);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}").buildAndExpand(attendeeIdDTO.id()).toUri();

        return ResponseEntity.created(uri).body(attendeeIdDTO);
    }

}
