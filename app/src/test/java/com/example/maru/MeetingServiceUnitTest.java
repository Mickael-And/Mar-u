package com.example.maru;

import com.example.maru.DI.DI;
import com.example.maru.Service.DummyMeetingGenerator;
import com.example.maru.Service.IMeetingService;
import com.example.maru.model.Meeting;
import com.example.maru.model.Room;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
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
        List<Meeting> expectedMeetings = new ArrayList<>();
        Meeting meeting1 = new Meeting();
        Meeting meeting2 = new Meeting();
        Meeting meeting3 = new Meeting();
        expectedMeetings.add(meeting1);
        expectedMeetings.add(meeting2);
        expectedMeetings.add(meeting3);

        this.meetingService.addMeeting(meeting1);
        this.meetingService.addMeeting(meeting2);
        this.meetingService.addMeeting(meeting3);

        List<Meeting> meetings = this.meetingService.getMeetings();
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

    @Test
    public void sortByDate() {
        for (Meeting meeting : DummyMeetingGenerator.generateMeetings()) {
            this.meetingService.addMeeting(meeting);
        }

        Calendar startDate = Calendar.getInstance();
        startDate.set(2019, Calendar.DECEMBER, 28, 12, 30);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, Calendar.DECEMBER, 28, 15, 30);

        List<Meeting> sortedList = this.meetingService.sortByDate(startDate, endDate);
        assertEquals(3, sortedList.size());

        List<Meeting> expectedList = new ArrayList<>();
        expectedList.add(DummyMeetingGenerator.DUMMY_MEETINGS.get(1));
        expectedList.add(DummyMeetingGenerator.DUMMY_MEETINGS.get(2));
        expectedList.add(DummyMeetingGenerator.DUMMY_MEETINGS.get(3));
        assertThat(sortedList, containsInAnyOrder(expectedList.toArray()));
    }

    @Test
    public void sortByRoom() {
        for (Meeting meeting : DummyMeetingGenerator.generateMeetings()) {
            this.meetingService.addMeeting(meeting);
        }
        List<Meeting> sortedList = this.meetingService.sortByRoom(DummyMeetingGenerator.DUMMY_ROOMS.get(4));
        assertEquals(1, sortedList.size());

        List<Meeting> expectedList = new ArrayList<>();
        expectedList.add(DummyMeetingGenerator.DUMMY_MEETINGS.get(4));

        assertThat(sortedList, containsInAnyOrder(expectedList.toArray()));
    }
}