package nry.com.pass_in.services;

import lombok.RequiredArgsConstructor;
import nry.com.pass_in.domain.attendee.Attendee;
import nry.com.pass_in.domain.checkIn.CheckIn;
import nry.com.pass_in.dto.attendee.AttendeeDetailDTO;
import nry.com.pass_in.dto.attendee.AttendeeIdDTO;
import nry.com.pass_in.dto.attendee.AttendeeListResponseDTO;
import nry.com.pass_in.repositories.AttendeeRepository;
import nry.com.pass_in.repositories.CheckInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    @Autowired
    private final AttendeeRepository attendeeRepository;
    @Autowired
    private CheckInRepository checkInRepository;
    public List<Attendee> getAllAttendeesFromEvent(String eventId){
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeeListResponseDTO getEventsAttendee(String eventId){
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetailDTO> attendeeDetailList = attendeeList.stream().map(attendee -> {
            Optional<CheckIn> checkIn = this.checkInRepository.findByAttendeeId(attendee.getId());
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
}
