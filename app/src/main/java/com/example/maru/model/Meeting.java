package com.example.maru.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Représente une réunion.
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Meeting {

    /**
     * Identifiant de la réunion.
     */
    @Getter
    private UUID id = UUID.randomUUID();

    /**
     * Date et heure de la réunion.
     */
    @Getter
    @Setter
    private Calendar dateTime;

    /**
     * Salle de la réunion.
     */
    @Getter
    @Setter
    private Room room;

    /**
     * Sujet de la réunion.
     */
    @Getter
    @Setter
    private String topic;

    /**
     * Liste des participants(adresses mail).
     */
    @Getter
    @Setter
    private List<String> participants = new ArrayList<>();
}
