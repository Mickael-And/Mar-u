package com.example.maru;

import android.app.Activity;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.maru.DI.DI;
import com.example.maru.Service.IMeetingService;
import com.example.maru.activity.main_activity.MainActivity;
import com.example.maru.activity.meeting_activity.CreateMeetingActivity;
import com.example.maru.utils.RecyclerViewUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.example.maru.utils.RecyclerViewUtils.atPosition;
import static com.example.maru.utils.RecyclerViewUtils.clickChildView;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Classe de test d'instrumentation pour la MainActivity.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    private int listSize;

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Activity activity = intentsTestRule.getActivity();
        assertThat(activity, notNullValue());
        IMeetingService meetingService = DI.getMeetingService();
        this.listSize = meetingService.getMeetings().size();
    }

    /**
     * Test si l'appui sur le FAB lance l'activité de création d'une réunion.
     */
    @Test
    public void createMeetingActivityIsLaunching() {
        onView(ViewMatchers.withId(R.id.fab)).perform(click());
        Intents.intended(hasComponent(CreateMeetingActivity.class.getName()));
    }

    /**
     * Test si le menu contient les 2 items de filtre par date ou salle.
     */
    @Test
    public void overflowMenuContainTwoItems() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Date")).check(matches(isDisplayed()));
        onView(withText("Salle")).check(matches(isDisplayed()));
        onView(withText("Défaut")).check(matches(isDisplayed()));
    }

    /**
     * Test si la liste de réunions affiche toutes les réunions en BDD.
     */
    @Test
    public void meetingsListContainAllMeetings() {
        onView(withId(R.id.meetings_list)).check(new RecyclerViewUtils.ItemCount(this.listSize));
    }

    /**
     * Test si une réunion est bien supprimée lors d'un clic sur le bouton de suppression.
     */
    @Test
    public void removeAMeeting() {
        onView(withId(R.id.meetings_list)).perform(actionOnItemAtPosition(0, clickChildView(R.id.btn_item_list_delete)));
        onView(withId(R.id.meetings_list)).check(new RecyclerViewUtils.ItemCount(listSize - 1));
    }

    /**
     * Vérifie que le message indiquant une liste vide est bien affiché.
     */
    @Test
    public void noMeetingsMessageIsOk() {
        onView(withId(R.id.tv_no_meetings)).check(matches(not(isDisplayed())));
        for (int i = 0; i < listSize; i++) {
            onView(withId(R.id.meetings_list)).perform(actionOnItemAtPosition(0, clickChildView(R.id.btn_item_list_delete)));
        }
        onView(withId(R.id.tv_no_meetings)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_no_meetings)).check(matches(withText(R.string.txt_empty_meetings_recyclerview)));
    }

    /**
     * Test si la création d'une réunion ajoute bien une réunion dans la liste.
     */
    @Test
    public void addAMeeting() {
        // Taille initiale de la liste
        onView(withId(R.id.meetings_list)).check(new RecyclerViewUtils.ItemCount(listSize));
        // Lancement de l'activité de création de réunion
        onView(withId(R.id.fab)).perform(click());
        // Choix de la salle 1
        onView(withId(R.id.spn_meeting_place)).perform(click());
        onView(withText("Salle 1")).inRoot(isPlatformPopup()).perform(click());
        // Saisie du sujet
        onView(withId(R.id.meeting_topic)).perform(replaceText("blabla"));
        // Saisie de la date et heure
        onView(withId(R.id.meeting_date_time)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName()))).perform(setDate(2020, 3, 24));
        onView(withText("OK")).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName()))).perform(setTime(20, 24));
        onView(withText("OK")).perform(click());
        // Saisie d'un participant
        onView(withId(R.id.meeting_participant)).perform(replaceText("ergerg@erg.erg"));
        // Ajout d'un participant
        onView(withId(R.id.btn_add_participant)).perform(click());
        // Création de la réunion
        onView(withId(R.id.btn_save_meeting)).perform(click());
        // Taille de la liste +1 ?
        onView(withId(R.id.meetings_list)).check(new RecyclerViewUtils.ItemCount(listSize + 1));
    }

    /**
     * Test du filtre par date.
     */
    @Test
    public void meetingsFilteredByDate() {
        // Création de 4 réunions
        for (int i = 1; i <= 4; i++) {
            // Lancement de l'activité de création de réunion
            onView(withId(R.id.fab)).perform(click());
            // Choix de la salle 1
            onView(withId(R.id.spn_meeting_place)).perform(click());
            onView(withText("Salle " + i)).inRoot(isPlatformPopup()).perform(click());
            // Saisie du sujet
            onView(withId(R.id.meeting_topic)).perform(replaceText("blabla" + i));
            // Saisie de la date et heure
            onView(withId(R.id.meeting_date_time)).perform(click());
            onView(withClassName(equalTo(DatePicker.class.getName()))).perform(setDate(2050, 3, 24));
            onView(withText("OK")).perform(click());
            onView(withClassName(equalTo(TimePicker.class.getName()))).perform(setTime(i, 0));
            onView(withText("OK")).perform(click());
            // Saisie d'un participant
            onView(withId(R.id.meeting_participant)).perform(replaceText("ergerg@erg.erg"));
            // Ajout d'un participant
            onView(withId(R.id.btn_add_participant)).perform(click());
            // Création de la réunion
            onView(withId(R.id.btn_save_meeting)).perform(click());
        }

        // Ouverture de la boîte de dialogue pour le filtre par date
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Date")).perform(click());

        // Saisie de la date et heure de début du filtre
        onView(withId(R.id.edt_date_filter_start)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName()))).perform(setDate(2050, 3, 24));
        onView(withText("OK")).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName()))).perform(setTime(1, 30));
        onView(withText("OK")).perform(click());

        // Saisie de la date et heure de fin du filtre
        onView(withId(R.id.edt_date_filter_end)).perform(click());
        onView(withClassName(equalTo(DatePicker.class.getName()))).perform(setDate(2050, 3, 24));
        onView(withText("OK")).perform(click());
        onView(withClassName(equalTo(TimePicker.class.getName()))).perform(setTime(4, 0));
        onView(withText("OK")).perform(click());

        // Validation du filtre
        onView(withId(R.id.btn_date_filter_ok)).perform(click());
        onView(withId(R.id.meetings_list)).check(new RecyclerViewUtils.ItemCount(3));
        onView(withId(R.id.meetings_list)).check((matches(atPosition(0, hasDescendant(withText("Salle 2 - 02h00 - blabla2"))))));
        onView(withId(R.id.meetings_list)).check((matches(atPosition(1, hasDescendant(withText("Salle 3 - 03h00 - blabla3"))))));
        onView(withId(R.id.meetings_list)).check((matches(atPosition(2, hasDescendant(withText("Salle 4 - 04h00 - blabla4"))))));
    }

    /**
     * Test du filtre par Salle.
     */
    @Test
    public void meetingsFilteredByRoom() {
        // Suppression, si il y a, des réunions dans la liste
        if (this.listSize != 0) {
            for (int i = 0; i < listSize; i++) {
                onView(withId(R.id.meetings_list)).perform(actionOnItemAtPosition(0, clickChildView(R.id.btn_item_list_delete)));
            }
        }

        // Création de 4 réunions
        for (int i = 1; i <= 4; i++) {
            // Lancement de l'activité de création de réunion
            onView(withId(R.id.fab)).perform(click());
            // Choix de la salle 1
            onView(withId(R.id.spn_meeting_place)).perform(click());
            onView(withText("Salle " + i)).inRoot(isPlatformPopup()).perform(click());
            // Saisie du sujet
            onView(withId(R.id.meeting_topic)).perform(replaceText("blabla" + i));
            // Saisie de la date et heure
            onView(withId(R.id.meeting_date_time)).perform(click());
            onView(withClassName(equalTo(DatePicker.class.getName()))).perform(setDate(2050, 3, 24));
            onView(withText("OK")).perform(click());
            onView(withClassName(equalTo(TimePicker.class.getName()))).perform(setTime(i, 0));
            onView(withText("OK")).perform(click());
            // Saisie d'un participant
            onView(withId(R.id.meeting_participant)).perform(replaceText("ergerg@erg.erg"));
            // Ajout d'un participant
            onView(withId(R.id.btn_add_participant)).perform(click());
            // Création de la réunion
            onView(withId(R.id.btn_save_meeting)).perform(click());
        }

        // Ouverture de la boîte de dialogue pour le filtre par salle
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Salle")).perform(click());

        // Saisie de la salle à filtrer
        onView(withId(R.id.spn_room_filter)).perform(click());
        onView(withText("Salle 1")).inRoot(isPlatformPopup()).perform(click());

        // Validation du filtre
        onView(withId(R.id.btn_room_filter_ok)).perform(click());
        onView(withId(R.id.meetings_list)).check(new RecyclerViewUtils.ItemCount(1));
        onView(withId(R.id.meetings_list)).check((matches(atPosition(0, hasDescendant(withText("Salle 1 - 01h00 - blabla1"))))));
    }

    // TODO: Ajouter filtre par défaut
}
