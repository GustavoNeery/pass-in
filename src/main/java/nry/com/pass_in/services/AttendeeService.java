package nry.com.pass_in.services;

import lombok.RequiredArgsConstructor;
import nry.com.pass_in.domain.attendee.Attendee;
import nry.com.pass_in.domain.attendee.exceptions.AttendeeAlreadyExistException;
import nry.com.pass_in.domain.attendee.exceptions.AttendeeNotFoundException;
import nry.com.pass_in.domain.checkIn.CheckIn;
import nry.com.pass_in.dto.attendee.*;
import nry.com.pass_in.repositories.AttendeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckInService checkInService;
    public List<Attendee> getAllAttendeesFromEvent(String eventId){
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeeListResponseDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetailDTO> attendeeDetailList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());
            LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;
            return new AttendeeDetailDTO(attendee.getId(), attendee.getName(), attendee.getEmail(), attendee.getCreatedAt(), checkedInAt);
        }).toList();

        return new AttendeeListResponseDTO(attendeeDetailList);
    }

    public AttendeeIdDTO createAttendee(Attendee attendee){
        Attendee newAttendee = new Attendee();

        newAttendee.setName(attendee.getName());
        newAttendee.setEmail(attendee.getEmail());
        newAttendee.setEvent(attendee.getEvent());
        newAttendee.setCreatedAt(attendee.getCreatedAt());

        this.attendeeRepository.save(newAttendee);
        return new AttendeeIdDTO(newAttendee.getId());
    }

    public void verifyAttendeeSubscription(String email, String eventId){
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
        if(isAttendeeRegistered.isPresent()) throw new AttendeeAlreadyExistException("Attendee is already registered");
    }

    public Attendee registerAnttedee(Attendee newAttendee){
        this.verifyAttendeeSubscription(newAttendee.getEmail(), newAttendee.getEvent().getId());
        this.attendeeRepository.save(newAttendee);
        return newAttendee;
    }

    public AttendeeBadgeResponseDTO getAttendeeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder){
        Attendee attendee = getAttendee(attendeeId);
        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();

        AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(attendee.getName(), attendee.getEmail(), uri, attendee.getEvent().getTitle());
        return new AttendeeBadgeResponseDTO(attendeeBadgeDTO);
    }

    public void checkInAttendee(String attendeeId){
        Attendee attendee = getAttendee(attendeeId);
        this.checkInService.registerCheckIn(attendee);
    }

    public void deleteAttendee(String attendeeId){
        Attendee attendee = this.getAttendee(attendeeId);
        this.attendeeRepository.delete(attendee);
    }

    private Attendee getAttendee(String attendeeId){
        return this.attendeeRepository.findById(attendeeId).orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with id" + attendeeId));
    }
}