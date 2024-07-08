package nry.com.pass_in.dto.attendee;

import nry.com.pass_in.domain.event.Event;

import java.time.LocalDateTime;

public record AttendeeDetailDTO(String id, String name, String email, LocalDateTime createdAt, LocalDateTime checkedInAt) {
}
