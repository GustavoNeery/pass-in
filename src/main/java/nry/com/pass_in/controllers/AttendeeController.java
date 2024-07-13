package nry.com.pass_in.controllers;

import lombok.RequiredArgsConstructor;
import nry.com.pass_in.domain.attendee.Attendee;
import nry.com.pass_in.dto.attendee.AttendeeBadgeResponseDTO;
import nry.com.pass_in.dto.attendee.AttendeeIdDTO;
import nry.com.pass_in.services.AttendeeService;
import nry.com.pass_in.services.CheckInService;
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

    @PostMapping
    public ResponseEntity<AttendeeIdDTO> createAttendee(@RequestBody Attendee attendee, UriComponentsBuilder uriComponentsBuilder) {
        AttendeeIdDTO attendeeIdDTO =  this.service.createAttendee(attendee);
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();
        return ResponseEntity.created(uri).body(attendeeIdDTO);
    }

    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        AttendeeBadgeResponseDTO attendeeBadgeResponseDTO = this.service.getAttendeeeBadge(attendeeId, uriComponentsBuilder);
        return ResponseEntity.ok().body(attendeeBadgeResponseDTO);
    }

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity registerCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        this.service.checkInAttendee(attendeeId);
        var uri = uriComponentsBuilder.path("/attendees/{atttendeeId}/badge").buildAndExpand(attendeeId).toUri();
        return ResponseEntity.created(uri).build();
    }

}
