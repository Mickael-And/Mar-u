package com.example.maru.Service;

import com.example.maru.model.Meeting;
import com.example.maru.model.Room;

import java.util.List;

/**
 * Service de gestion des réunions.
 */
public class MeetingService implements IMeetingService {

    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();
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
}
