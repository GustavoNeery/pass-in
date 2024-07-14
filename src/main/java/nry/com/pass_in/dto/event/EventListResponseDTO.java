package nry.com.pass_in.dto.event;

import nry.com.pass_in.domain.event.Event;

import java.util.List;

public record EventListResponseDTO(List<Event> events) {
}
