package com.example.maru;

import android.app.Activity;

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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static com.example.maru.utils.RecyclerViewUtils.clickChildView;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Classe de test d'instrumentation pour la MainActivity.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {

    /**
     * Service de gestion des utilisateurs.
     */
    private IMeetingService meetingService;

    private int listSize;

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        Activity activity = intentsTestRule.getActivity();
        assertThat(activity, notNullValue());
        this.meetingService = DI.getNewInstanceMeetingService();
        this.listSize = this.meetingService.getMeetings().size();
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
    }

//    @Test
//    public void meetingsFilteredByDate() {
//
//    }
//
//    @Test
//    public void meetingsFilteredByRoom() {
//
//    }

    /**
     * Test si la liste de réunions affiche toutes les réunions en BDD.
     */
    @Test
    public void meetingsListContainAllMeetings() {
        onView(withId(R.id.meetings_list)).check(new RecyclerViewUtils.ItemCount(listSize));
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

    @Test
    public void addAMeeting() {

    }

}
