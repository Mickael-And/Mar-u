package com.example.maru.Service;

import com.example.maru.model.Meeting;

import java.util.List;
import java.util.UUID;

public class MeetingService implements IMeetingService {

    private List<Meeting> meetings = DummyMeetingGenerator.generateMeetings();

    @Override
    public List<Meeting> getMeetings() {
        return this.meetings;
    }

    @Override
    public Meeting getMeeting(UUID id) {
        for (Meeting meeting : this.meetings) {
            if (meeting.getId().equals(id)) {
                return meeting;
            }
        }
        return new Meeting();
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
