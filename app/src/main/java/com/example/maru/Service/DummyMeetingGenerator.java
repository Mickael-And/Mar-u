package com.example.maru.Service;

import android.graphics.Color;

import com.example.maru.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Permet la génération des données utilisées pour notre application.
 */
abstract class DummyMeetingGenerator {

    /**
     * Liste factice de réunions.
     */
    static List<Meeting> DUMMY_MEETINGS = Arrays.asList(

            new Meeting(UUID.randomUUID(), Color.GRAY, 9, 30, "Salle 1", "Achats",
                    Arrays.asList("abc@gmail.com", "def@gmail.com", "ghi@gmail.com", "jkl@gmail.com", "mno@gmail.com", "pqr@gmail.com", "stu@gmail.com", "vwx@gmail.com", "yz@gmail.com")),
            new Meeting(UUID.randomUUID(), Color.RED, 10, 00, "Salle 2", "Ventes",
                    Arrays.asList("abc@gmail.com", "def@gmail.com", "ghi@gmail.com")),
            new Meeting(UUID.randomUUID(), Color.BLUE, 14, 15, "Salle 3", "Situation",
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
