package com.example.maru;

import com.example.maru.DI.DI;
import com.example.maru.Service.DummyMeetingGenerator;
import com.example.maru.Service.IMeetingService;
import com.example.maru.model.Meeting;
import com.example.maru.model.Room;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Classe de tests unitaires pour le service de gestion des réunions ({@link IMeetingService}.
 */
public class MeetingServiceUnitTest {
    /**
     * Service à tester.
     */
    private IMeetingService meetingService;

    @Before
    public void setup() {
        this.meetingService = DI.getNewInstanceMeetingService();
    }

    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = this.meetingService.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETINGS;
        assertThat(meetings, containsInAnyOrder(expectedMeetings.toArray()));
    }

    @Test
    public void getRoomsWithSuccess() {
        List<Room> rooms = this.meetingService.getRooms();
        List<Room> expectedRooms = DummyMeetingGenerator.DUMMY_ROOMS;
        assertThat(rooms, containsInAnyOrder(expectedRooms.toArray()));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = this.meetingService.getMeetings().get(0);
        this.meetingService.deleteMeeting(meetingToDelete);
        assertFalse(this.meetingService.getMeetings().contains(meetingToDelete));
    }

    @Test
    public void addAmeetingWithSuccess() {
        int sizeList = this.meetingService.getMeetings().size();
        Meeting meeting = new Meeting();
        this.meetingService.addMeeting(meeting);
        assertEquals(sizeList + 1, this.meetingService.getMeetings().size());
    }

}