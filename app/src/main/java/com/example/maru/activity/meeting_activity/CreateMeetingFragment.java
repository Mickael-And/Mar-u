package com.example.maru.activity.meeting_activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.maru.DI.DI;
import com.example.maru.R;
import com.example.maru.Service.DummyMeetingGenerator;
import com.example.maru.Service.IMeetingService;
import com.example.maru.event.AddMeetingEvent;
import com.example.maru.model.Meeting;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.maru.Service.DummyMeetingGenerator.ROOM_1;
import static com.example.maru.Service.DummyMeetingGenerator.ROOM_10;
import static com.example.maru.Service.DummyMeetingGenerator.ROOM_2;
import static com.example.maru.Service.DummyMeetingGenerator.ROOM_3;
import static com.example.maru.Service.DummyMeetingGenerator.ROOM_4;
import static com.example.maru.Service.DummyMeetingGenerator.ROOM_5;
import static com.example.maru.Service.DummyMeetingGenerator.ROOM_6;
import static com.example.maru.Service.DummyMeetingGenerator.ROOM_7;
import static com.example.maru.Service.DummyMeetingGenerator.ROOM_8;
import static com.example.maru.Service.DummyMeetingGenerator.ROOM_9;

/**
 * Fragment de création de réunion.
 */
public class CreateMeetingFragment extends Fragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.meeting_place_container)
    TextInputLayout meetingPlaceContainer;
    @BindView(R.id.spn_meeting_place)
    AutoCompleteTextView spnMeetingPlace;

    @BindView(R.id.meeting_topic_container)
    TextInputLayout meetingTopicContainer;
    @BindView(R.id.meeting_topic)
    TextInputEditText edtMeetingTopic;

    @BindView(R.id.meeting_date_time_container)
    TextInputLayout meetingDateTimeContainer;
    @BindView(R.id.meeting_date_time)
    TextInputEditText edtMeetingDateTime;


    @BindView(R.id.meeting_participant_container)
    TextInputLayout meetingParticipantContainer;
    @BindView(R.id.meeting_participant)
    TextInputEditText edtMeetingParticipant;

    @BindView(R.id.tv_participants)
    TextView tvParticipants;

    /**
     * Liste des participants à la nouvelle réunion.
     */
    private List<String> participants = new ArrayList<>();

    /**
     * Couleur de la salle de réunion.
     */
    private int meetingColorId = Integer.MIN_VALUE;

    /**
     * Heure et date de la réunion.
     */
    private Calendar meetingDateTime;

    /**
     * Service de gestion des réunions.
     */
    private IMeetingService meetingService = DI.getMeetingService();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_meeting, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.initPlaceSpinner();
        // Désactivation du clavier pour le choix de la date
        this.edtMeetingDateTime.setRawInputType(InputType.TYPE_NULL);
    }

    /**
     * Initialisation du spinner permettant le choix de la salle.
     */
    private void initPlaceSpinner() {
        if (getContext() != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_popup_item, DummyMeetingGenerator.DUMMY_ROOMS);
            this.spnMeetingPlace.setAdapter(adapter);
            this.spnMeetingPlace.setOnItemClickListener(this);
            this.spnMeetingPlace.setKeyListener(null);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        this.edtMeetingTopic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > meetingTopicContainer.getCounterMaxLength()) {
                    meetingTopicContainer.setError(String.format(Locale.getDefault(), "%d caractères maximum", meetingTopicContainer.getCounterMaxLength()));
                } else {
                    meetingTopicContainer.setError(null);
                }
            }
        });

        this.edtMeetingDateTime.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                this.datepicker();
            }
        });

        this.edtMeetingDateTime.setOnClickListener(v -> this.datepicker());

        this.edtMeetingParticipant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEmailValid(s.toString())) {
                    meetingParticipantContainer.setError(null);
                } else {
                    meetingParticipantContainer.setError("Entrer une adresse mail valide");
                }
            }
        });
    }

    /**
     * Affiche le {@link DatePicker}.
     */
    private void datepicker() {
        this.meetingDateTime = Calendar.getInstance();
        this.meetingDateTimeContainer.setError(null);

        if (getContext() != null) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                this.meetingDateTime.set(year, month, dayOfMonth);
                this.timePicker();
            }, this.meetingDateTime.get(Calendar.YEAR), this.meetingDateTime.get(Calendar.MONTH), this.meetingDateTime.get(Calendar.DAY_OF_MONTH));
            // Empêche la saisie d'une date antérieure à la date actuelle
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        }
    }

    /**
     * Affiche le {@link android.widget.TimePicker}
     */
    private void timePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), (view, hourOfDay, minute) -> {

            this.meetingDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            this.meetingDateTime.set(Calendar.MINUTE, minute);

            Calendar dateNow = Calendar.getInstance();

            // Test si l'heure saisie est avant l'heure actuelle
            if (this.meetingDateTime.before(dateNow)) {
                this.meetingDateTimeContainer.setError("Heure de réunion incorrect");
            }

            // Recherche de disponibilité de la salle choisie à l'heure saisie
            for (Meeting meeting : this.meetingService.getMeetings()) {
                if (meeting.getPlace().contentEquals(this.spnMeetingPlace.getText())) {
                    Calendar meetingBegin = meeting.getDateTime();
                    Calendar meetingEnd = (Calendar) meeting.getDateTime().clone();
                    meetingEnd.add(Calendar.MINUTE, 45);
                    if (this.meetingDateTime.after(meetingBegin) && this.meetingDateTime.before(meetingEnd)) {
                        this.meetingDateTimeContainer.setError(String.format(Locale.getDefault(), "Salle occupée de %02dh%02d à %02dh%02d",
                                meetingBegin.get(Calendar.HOUR_OF_DAY), meetingBegin.get(Calendar.MINUTE), meetingEnd.get(Calendar.HOUR_OF_DAY), meetingEnd.get(Calendar.MINUTE)));
                    }
                }
            }

            // Mise en forme de la date pour l'EditText
            this.edtMeetingDateTime.setText(String.format(Locale.getDefault(), "%02d/%02d/%d %02dh%02d", this.meetingDateTime.get(Calendar.DAY_OF_MONTH),
                    this.meetingDateTime.get(Calendar.MONTH) + 1, this.meetingDateTime.get(Calendar.YEAR), this.meetingDateTime.get(Calendar.HOUR_OF_DAY), this.meetingDateTime.get(Calendar.MINUTE)));

            // Focus sur la prochaine saisie si le choix de la date et heure est bonne
            if (this.meetingDateTimeContainer.getError() == null) {
                this.edtMeetingParticipant.requestFocus();
            }

        }, this.meetingDateTime.get(Calendar.HOUR_OF_DAY), this.meetingDateTime.get(Calendar.HOUR_OF_DAY), true);
        timePickerDialog.show();
    }

    /**
     * Teste la validité d'une adresse mail saisie.
     *
     * @param email adresse mail à tester
     * @return true si valide - false sinon
     */
    private static boolean isEmailValid(String email) {
        boolean valid = true;
        if (email != null && !TextUtils.isEmpty(email)) {
            valid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return valid;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch ((String) parent.getItemAtPosition(position)) {
            case ROOM_1:
                this.meetingColorId = R.color.color_meeting_1;
                break;
            case ROOM_2:
                this.meetingColorId = R.color.color_meeting_2;
                break;
            case ROOM_3:
                this.meetingColorId = R.color.color_meeting_3;
                break;
            case ROOM_4:
                this.meetingColorId = R.color.color_meeting_4;
                break;
            case ROOM_5:
                this.meetingColorId = R.color.color_meeting_5;
                break;
            case ROOM_6:
                this.meetingColorId = R.color.color_meeting_6;
                break;
            case ROOM_7:
                this.meetingColorId = R.color.color_meeting_7;
                break;
            case ROOM_8:
                this.meetingColorId = R.color.color_meeting_8;
                break;
            case ROOM_9:
                this.meetingColorId = R.color.color_meeting_9;
                break;
            case ROOM_10:
                this.meetingColorId = R.color.color_meeting_10;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + parent.getItemAtPosition(position));
        }

        // Remise à zéro de la date lors d'un changement de salle si une date a déjà été saisie
        if (!TextUtils.isEmpty(this.edtMeetingDateTime.getText())) {
            this.edtMeetingDateTime.getText().clear();
            this.meetingDateTimeContainer.setError(null);
        }

        this.meetingPlaceContainer.setError(null);
        this.edtMeetingTopic.requestFocus();
    }

    /**
     * Ajoute un participant.
     */
    @OnClick(R.id.btn_add_participant)
    public void addParticipant() {
        if (isEmailValid(String.valueOf(this.edtMeetingParticipant.getText()))) {
            this.participants.add(String.valueOf(this.edtMeetingParticipant.getText()));
            this.tvParticipants.setText(String.format(Locale.getDefault(), "%s %s", this.tvParticipants.getText(), String.valueOf(this.edtMeetingParticipant.getText())));
            this.edtMeetingParticipant.setText(null);
        }
    }

    /**
     * Créer la réunion.
     */
    @OnClick(R.id.btn_save_meeting)
    public void createMeeting() {
        if (this.meetingPlaceIsValid() && this.meetingTopicIsValid() && this.meetingDateTimeIsValid() && this.meetingParticipantsIsValid()) {
            sendSaveMeetingNotification();
            Objects.requireNonNull(getActivity()).finish();
            Toast.makeText(getContext(), "Réunion créée", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Création impossible", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Vérifie que la liste des participants n'est pas vide.
     *
     * @return true si minimum un participant
     */
    private boolean meetingParticipantsIsValid() {
        boolean isValid;

        if (this.participants.size() < 1) {
            this.meetingParticipantContainer.setError("Ajouter un participant");
            isValid = false;
        } else {
            isValid = true;
        }

        return isValid;
    }

    /**
     * Vérifie la validité de la date et heure de la réunion.
     *
     * @return true si valide
     */
    private boolean meetingDateTimeIsValid() {
        boolean isValid;

        if (this.meetingDateTimeContainer.getError() != null) {
            isValid = false;
        } else if (TextUtils.isEmpty(this.edtMeetingDateTime.getText())) {
            isValid = false;
            this.meetingDateTimeContainer.setError("Choisir date et heure");
        } else {
            isValid = true;
        }

        return isValid;
    }

    /**
     * Vérifie la validité du sujet de la réunion.
     *
     * @return true si valide
     */
    private boolean meetingTopicIsValid() {
        boolean isValid;
        if (this.meetingTopicContainer.getError() != null) {
            isValid = false;
        } else if (TextUtils.isEmpty(this.edtMeetingTopic.getText())) {
            this.meetingTopicContainer.setError("Choisir un sujet");
            isValid = false;
        } else {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Vérifie la validité du lieu de réunion.
     *
     * @return true si valide
     */
    private boolean meetingPlaceIsValid() {
        boolean isValid;
        if (this.meetingPlaceContainer.getError() != null) {
            isValid = false;
        } else if (this.meetingColorId == Integer.MIN_VALUE) {
            this.meetingPlaceContainer.setError("Choisir une salle");
            isValid = false;
        } else {
            isValid = true;
        }

        return isValid;
    }

    /**
     * Envoi la notitfication de sauvegarde de la réunion.
     */
    private void sendSaveMeetingNotification() {
        Meeting meeting = new Meeting();
        meeting.setColor(this.meetingColorId);
        meeting.setParticipants(this.participants);
        meeting.setDateTime(this.meetingDateTime);
        meeting.setPlace(String.valueOf(this.spnMeetingPlace.getText()));
        meeting.setTopic(String.valueOf(this.edtMeetingTopic.getText()));

        EventBus.getDefault().post(new AddMeetingEvent(meeting));
    }
}
