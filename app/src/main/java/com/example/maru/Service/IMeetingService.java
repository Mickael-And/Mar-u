package com.example.maru.Service;

import com.example.maru.model.Meeting;

import java.util.List;
import java.util.UUID;

/**
 * API cliente des réunions.
 */
public interface IMeetingService {

    /**
     * Récupère la liste des réunions.
     *
     * @return {@link List} des réunions
     */
    List<Meeting> getMeetings();

    /**
     * Récupère une réunion en particulier.
     *
     * @return id de la réunion que l'on souhaite récupérer
     */
    Meeting getMeeting(UUID id);

    /**
     * Supprimer une réunion.
     *
     * @param meeting que l'on souhaite supprimer
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Ajoute une réunion.
     *
     * @param meeting réunion que l'on souhaite ajouter
     */
    void addMeeting(Meeting meeting);
}