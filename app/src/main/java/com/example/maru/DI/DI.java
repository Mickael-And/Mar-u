package com.example.maru.DI;

import com.example.maru.Service.IMeetingService;
import com.example.maru.Service.MeetingService;

/**
 * Injection de dépendances pour récupérer les services de l'application.
 */
public class DI {

    private static final IMeetingService service = new MeetingService();

    /**
     * Récupère l'instance de @{@link IMeetingService}
     *
     * @return service de gestion des réunions
     */
    public static IMeetingService getMeetingService() {
        return service;
    }

    /**
     * Récupère une nouvelle instance de @{@link IMeetingService}. Utilisé pour les tests.
     *
     * @return service de gestion des réunions
     */
    public static IMeetingService getNewInstanceMeetingService() {
        return new MeetingService();
    }
}
