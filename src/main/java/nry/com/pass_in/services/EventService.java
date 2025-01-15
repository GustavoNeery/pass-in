package nry.com.pass_in.services;


import lombok.RequiredArgsConstructor;
import nry.com.pass_in.domain.attendee.Attendee;
import nry.com.pass_in.domain.attendee.exceptions.EventFullException;
import nry.com.pass_in.domain.event.Event;
import nry.com.pass_in.domain.event.exceptions.EventNotFoundException;
import nry.com.pass_in.dto.attendee.AttendeeIdDTO;
import nry.com.pass_in.dto.attendee.AttendeeRequestDTO;
import nry.com.pass_in.dto.event.*;
import nry.com.pass_in.repositories.EventRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventService(EventRepository eventRepository, AttendeeService attendeeService) {
        this.eventRepository = eventRepository;
        this.attendeeService = attendeeService;
    }

    public EventResponseDTO getEventDetail(String eventId){
        Event event = getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);
        return new EventResponseDTO(event, attendeeList.size());
    }

    public EventListResponseDTO getAllEvents(){
        List<Event> events = this.eventRepository.findAll();
        return new EventListResponseDTO(events);
    }

    public EventIdDTO createEvent(EventRequestDTO eventDTO){
        Event newEvent = new Event();
        newEvent.setTitle(eventDTO.title());
        newEvent.setDetails(eventDTO.details());
        newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
        newEvent.setSlug(this.createSlug(eventDTO.title()));

        this.eventRepository.save(newEvent);
        return new EventIdDTO(newEvent.getId());
    }

    public AttendeeIdDTO registerAttendeeOnEvent(String eventId, AttendeeRequestDTO attendeeRequestDTO){
        this.attendeeService.verifyAttendeeSubscription(eventId, attendeeRequestDTO.email());
        Event event = this.getEventById(eventId);
        List<Attendee> attendeeList = this.attendeeService.getAllAttendeesFromEvent(eventId);

        if(event.getMaximumAttendees() <= attendeeList.size()) throw new EventFullException("Event is full");

        Attendee newAttendee = new Attendee();
        newAttendee.setName(attendeeRequestDTO.name());
        newAttendee.setEmail(attendeeRequestDTO.email());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());
        this.attendeeService.registerAnttedee(newAttendee);

        return new AttendeeIdDTO(newAttendee.getId());
    }

    public void deleteEventById(String eventId) {
        getEventById(eventId);
        this.eventRepository.deleteById(eventId);
    }


    private Event getEventById(String eventId){
        return this.eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + eventId));
    }

    private String createSlug(String text){
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCOMBINING_DIACRITICAL_MARKS}]", "")
            .replaceAll("[^\\w\\s]", "")
            .replaceAll("[\\s+]", "")
            .toLowerCase();
    }
}