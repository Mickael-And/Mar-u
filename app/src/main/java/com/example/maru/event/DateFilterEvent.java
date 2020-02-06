package com.example.maru.event;

import java.util.Calendar;

/**
 * Evenement lancé lors d'un filtre par dates.
 */
public class DateFilterEvent {

    /**
     * Date de début du filtre.
     */
    public Calendar startDateFilter;

    /**
     * Date de fin du filtre.
     */
    public Calendar endDateFilter;


    public DateFilterEvent(Calendar pStartDateFilter, Calendar pEndDateFilter) {
        this.startDateFilter = pStartDateFilter;
        this.endDateFilter = pEndDateFilter;
    }
}
