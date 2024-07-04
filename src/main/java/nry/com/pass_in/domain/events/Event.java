package nry.com.pass_in.domain.events;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "events")
@Getter
public class Event {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String details;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(nullable = false, name = "maximum_attendees")
    private Integer maximumAttendees;

}
