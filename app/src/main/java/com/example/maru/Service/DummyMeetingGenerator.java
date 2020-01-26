package com.example.maru.Service;

import com.example.maru.R;
import com.example.maru.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Permet la génération des données utilisées pour notre application.
 */
public abstract class DummyMeetingGenerator {
    // Constantes avec le nom des salles de réunions
    public static final String ROOM_1 = "Salle 1";
    public static final String ROOM_2 = "Salle 2";
    public static final String ROOM_3 = "Salle 3";
    public static final String ROOM_4 = "Salle 4";
    public static final String ROOM_5 = "Salle 5";
    public static final String ROOM_6 = "Salle 6";
    public static final String ROOM_7 = "Salle 7";
    public static final String ROOM_8 = "Salle 8";
    public static final String ROOM_9 = "Salle 9";
    public static final String ROOM_10 = "Salle 10";

    /**
     * Liste factice des salles.
     */
    public static final String[] DUMMY_ROOMS = {ROOM_1, ROOM_2, ROOM_3, ROOM_4, ROOM_5, ROOM_6, ROOM_7, ROOM_8, ROOM_9, ROOM_10};

    /**
     * Liste factice de calendars.
     */
    private static final Calendar[] DUMMY_CALENDARS = generateCalendars();

    /**
     * Génère 3 dates.
     *
     * @return 3 dates
     */
    private static Calendar[] generateCalendars() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2019, Calendar.DECEMBER, 24, 17, 0);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2019, Calendar.DECEMBER, 24, 18, 0);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2019, Calendar.DECEMBER, 25, 14, 0);

        return new Calendar[]{calendar1, calendar2, calendar3};
    }


    /**
     * Liste factice de réunions.
     */
    static List<Meeting> DUMMY_MEETINGS = Arrays.asList(

            new Meeting(UUID.randomUUID(), R.color.color_meeting_1, DUMMY_CALENDARS[0], ROOM_1, "Achats",
                    Arrays.asList("abc@gmail.com", "def@gmail.com", "ghi@gmail.com", "jkl@gmail.com", "mno@gmail.com", "pqr@gmail.com", "stu@gmail.com", "vwx@gmail.com", "yz@gmail.com")),
            new Meeting(UUID.randomUUID(), R.color.color_meeting_2, DUMMY_CALENDARS[1], ROOM_2, "Ventes",
                    Arrays.asList("abc@gmail.com", "def@gmail.com", "ghi@gmail.com")),
            new Meeting(UUID.randomUUID(), R.color.color_meeting_3, DUMMY_CALENDARS[2], ROOM_3, "Situations",
                    Arrays.asList("abc@gmail.com", "def@gmail.com", "ghi@gmail.com", "jkl@gmail.com", "mno@gmail.com")));

    /**
     * Génère la liste des réunions.
     *
     * @return les réunions
     */
    static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
