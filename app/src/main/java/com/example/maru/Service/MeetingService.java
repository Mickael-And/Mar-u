package com.example.maru.Service;

import com.example.maru.model.Meeting;
import com.example.maru.model.Room;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Service de gestion des réunions.
 */
public class MeetingService implements IMeetingService {

    private List<Meeting> meetings = new ArrayList<>();
    private List<Room> rooms = DummyMeetingGenerator.generateRooms();

    @Override
    public List<Meeting> getMeetings() {
        return this.meetings;
    }

    @Override
    public List<Room> getRooms() {
        return this.rooms;
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        this.meetings.remove(meeting);
    }

    @Override
    public void addMeeting(Meeting meeting) {
        this.meetings.add(meeting);
    }

    @Override
    public List<Meeting> sortByDate(Calendar startDate, Calendar endDate) {
        List<Meeting> meetingsByDate = new ArrayList<>();
        for (Meeting meeting : this.meetings) {
            if (meeting.getDateTime().after(startDate) && meeting.getDateTime().before(endDate)) {
                meetingsByDate.add(meeting);
            }
        }
        return meetingsByDate;
    }

    @Override
    public List<Meeting> sortByRoom(Room roomFilter) {
        List<Meeting> meetingsByRoom = new ArrayList<>();
        for (Meeting meeting : this.meetings) {
            if (meeting.getRoom().equals(roomFilter)) {
                meetingsByRoom.add(meeting);
            }
        }
        return meetingsByRoom;
    }
}
