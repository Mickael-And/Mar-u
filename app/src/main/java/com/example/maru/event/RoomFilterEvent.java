package com.example.maru.event;

import com.example.maru.model.Room;

/**
 * Evenement lancé lors d'un filtre par salle de réunion.
 */
public class RoomFilterEvent {

    /**
     * Filtre à appliquer.
     */
    public Room choosenRoom;


    public RoomFilterEvent(Room pchoosenRoom) {
        this.choosenRoom = pchoosenRoom;
    }
}
