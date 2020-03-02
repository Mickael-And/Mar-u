package com.example.maru.Service;

import com.example.maru.R;
import com.example.maru.model.Meeting;
import com.example.maru.model.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Permet la génération des données utilisées pour notre application.
 */
public abstract class DummyMeetingGenerator {

    /**
     * Liste factice de calendars.
     */
    private static final Calendar[] DUMMY_CALENDARS = generateCalendars();

    /**
     * Génère 10 dates.
     *
     * @return 10 dates
     */
    private static Calendar[] generateCalendars() {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2019, Calendar.DECEMBER, 28, 12, 0);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2019, Calendar.DECEMBER, 28, 13, 0);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2019, Calendar.DECEMBER, 28, 14, 0);
        Calendar calendar4 = Calendar.getInstance();
        calendar4.set(2019, Calendar.DECEMBER, 28, 15, 0);
        Calendar calendar5 = Calendar.getInstance();
        calendar5.set(2019, Calendar.DECEMBER, 28, 16, 0);
        Calendar calendar6 = Calendar.getInstance();
        calendar6.set(2019, Calendar.DECEMBER, 28, 17, 0);
        Calendar calendar7 = Calendar.getInstance();
        calendar7.set(2019, Calendar.DECEMBER, 28, 18, 0);
        Calendar calendar8 = Calendar.getInstance();
        calendar8.set(2019, Calendar.DECEMBER, 28, 19, 0);
        Calendar calendar9 = Calendar.getInstance();
        calendar9.set(2019, Calendar.DECEMBER, 28, 20, 0);
        Calendar calendar10 = Calendar.getInstance();
        calendar10.set(2019, Calendar.DECEMBER, 28, 21, 0);

        return new Calendar[]{calendar1, calendar2, calendar3, calendar4, calendar5, calendar6, calendar7, calendar8, calendar9, calendar10};
    }

    /**
     * Génère la liste des salles.
     *
     * @return les salles
     */
    public static List<Room> generateRooms() {
        return new ArrayList<>(DUMMY_ROOMS);
    }

    /**
     * Liste factice des salles de réunions.
     */
    public static List<Room> DUMMY_ROOMS = Arrays.asList(new Room(UUID.randomUUID(), 1, "Salle 1", R.color.color_meeting_1),
            new Room(UUID.randomUUID(), 2, "Salle 2", R.color.color_meeting_2),
            new Room(UUID.randomUUID(), 3, "Salle 3", R.color.color_meeting_3),
            new Room(UUID.randomUUID(), 4, "Salle 4", R.color.color_meeting_4),
            new Room(UUID.randomUUID(), 5, "Salle 5", R.color.color_meeting_5),
            new Room(UUID.randomUUID(), 6, "Salle 6", R.color.color_meeting_6),
            new Room(UUID.randomUUID(), 7, "Salle 7", R.color.color_meeting_7),
            new Room(UUID.randomUUID(), 8, "Salle 8", R.color.color_meeting_8),
            new Room(UUID.randomUUID(), 9, "Salle 9", R.color.color_meeting_9),
            new Room(UUID.randomUUID(), 10, "Salle 10", R.color.color_meeting_10));

    /**
     * Liste factice de réunions.
     */
    public static List<Meeting> DUMMY_MEETINGS = Arrays.asList(

            new Meeting(UUID.randomUUID(), DUMMY_CALENDARS[0], DUMMY_ROOMS.get(0), "Achats",
                    Arrays.asList("abc@gmail.com", "def@gmail.com", "ghi@gmail.com", "jkl@gmail.com", "mno@gmail.com", "pqr@gmail.com", "stu@gmail.com", "vwx@gmail.com", "yz@gmail.com")),
            new Meeting(UUID.randomUUID(), DUMMY_CALENDARS[1], DUMMY_ROOMS.get(1), "Ventes",
                    Arrays.asList("abc@gmail.com", "def@gmail.com", "ghi@gmail.com")),
            new Meeting(UUID.randomUUID(), DUMMY_CALENDARS[2], DUMMY_ROOMS.get(2), "Situations",
                    Arrays.asList("abc@gmail.com", "def@gmail.com", "ghi@gmail.com", "jkl@gmail.com", "mno@gmail.com")),
            new Meeting(UUID.randomUUID(), DUMMY_CALENDARS[3], DUMMY_ROOMS.get(3), "Gestion",
                    Arrays.asList("abc@gmail.com", "def@gmail.com", "ghi@gmail.com", "jkl@gmail.com")),
            new Meeting(UUID.randomUUID(), DUMMY_CALENDARS[4], DUMMY_ROOMS.get(4), "Personnel",
                    Arrays.asList("abc@gmail.com", "def@gmail.com", "ghi@gmail.com", "jkl@gmail.com", "mno@gmail.com", "pqr@gmail.com")),
            new Meeting(UUID.randomUUID(), DUMMY_CALENDARS[5], DUMMY_ROOMS.get(5), "Management",
                    Arrays.asList("abc@gmail.com", "def@gmail.com", "ghi@gmail.com", "jkl@gmail.com", "mno@gmail.com", "pqr@gmail.com", "stu@gmail.com")),
            new Meeting(UUID.randomUUID(), DUMMY_CALENDARS[6], DUMMY_ROOMS.get(6), "Vacances",
                    Arrays.asList("abc@gmail.com", "def@gmail.com", "ghi@gmail.com", "jkl@gmail.com", "mno@gmail.com", "pqr@gmail.com", "stu@gmail.com", "vwx@gmail.com")),
            new Meeting(UUID.randomUUID(), DUMMY_CALENDARS[7], DUMMY_ROOMS.get(7), "Finances",
                    Arrays.asList("abc@gmail.com", "def@gmail.com")),
            new Meeting(UUID.randomUUID(), DUMMY_CALENDARS[8], DUMMY_ROOMS.get(8), "Budget",
                    Arrays.asList("abc@gmail.com", "def@gmail.com", "ghi@gmail.com", "jkl@gmail.com")),
            new Meeting(UUID.randomUUID(), DUMMY_CALENDARS[9], DUMMY_ROOMS.get(9), "Contraintes",
                    Arrays.asList("abc@gmail.com", "def@gmail.com", "ghi@gmail.com")));

    /**
     * Génère la liste des réunions.
     *
     * @return les réunions
     */
    public static List<Meeting> generateMeetings() {
        return new ArrayList<>(DUMMY_MEETINGS);
    }
}
