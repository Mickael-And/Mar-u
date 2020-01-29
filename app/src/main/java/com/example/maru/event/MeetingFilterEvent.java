package com.example.maru.event;

/**
 * Evenement lancé lors d'un changement de filtre de la liste des réunions.
 */
public class MeetingFilterEvent {

    /**
     * Filtre à appliquer.
     */
    public int filter;


    public MeetingFilterEvent(int pFilter) {
        this.filter = pFilter;
    }
}
