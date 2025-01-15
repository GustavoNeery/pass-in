package nry.com.pass_in.domain.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setMaximumAttendees(Integer maximumAttendees) {
        this.maximumAttendees = maximumAttendees;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }

    public Integer getMaximumAttendees() {
        return maximumAttendees;
    }

    public String getDetails() {
        return details;
    }

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String details;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = false, name = "maximum_attendees")
    private Integer maximumAttendees;

}
