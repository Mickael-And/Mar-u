package com.example.maru.event;

import com.example.maru.model.Meeting;

/**
 * Evenement lancé lors d'une demande de suppression de réunion.
 */
public class DeleteMeetingEvent {

    /**
     * Réunion à supprimer.
     */
    public Meeting meeting;


    public DeleteMeetingEvent(Meeting pMeeting) {
        this.meeting = pMeeting;
    }
}
