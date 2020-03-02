package com.example.maru.Service;

import com.example.maru.model.Meeting;
import com.example.maru.model.Room;

import java.util.Calendar;
import java.util.List;

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
     * Récupère la liste des salles.
     *
     * @return {@link List} des salles
     */
    List<Room> getRooms();

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

    /**
     * Permet de trier les réunions pas dates.
     *
     * @param startDate date de début du filtre
     * @param endDate   date de fin du filtre
     * @return la liste triée par date
     */
    List<Meeting> sortByDate(Calendar startDate, Calendar endDate);

    /**
     * Permet de trier les réunions par salle.
     *
     * @param roomFilter salle à trier
     * @return la liste triée par salle
     */
    List<Meeting> sortByRoom(Room roomFilter);

}
