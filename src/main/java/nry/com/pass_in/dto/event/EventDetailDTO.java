package nry.com.pass_in.dto.event;

public record EventDetailDTO(String id,
                             String title,
                             String detail,
                             String slug,
                             Integer maximumAttendees,
                             Integer attendeesAmount
) {
}
