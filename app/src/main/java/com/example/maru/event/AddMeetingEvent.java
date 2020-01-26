package com.example.maru.event;

import com.example.maru.model.Meeting;

/**
 * Evenement lancé lors d'une demande d'ajout de réunion.
 */
public class AddMeetingEvent {

    /**
     * Réunion à ajouter.
     */
    public Meeting meeting;


    public AddMeetingEvent(Meeting pMeeting) {
        this.meeting = pMeeting;
    }
}
