package com.example.maru.activity.dialog_fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.maru.DI.DI;
import com.example.maru.R;
import com.example.maru.Service.IMeetingService;
import com.example.maru.event.RoomFilterEvent;
import com.example.maru.model.Room;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Fragment sous forme de popup pour le filtre par salle.
 */
public class RoomFilterDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.room_filter_container)
    TextInputLayout roomFilterContainer;
    @BindView(R.id.spn_room_filter)
    AutoCompleteTextView spnRoom;

    /**
     * Salle de la réunion.
     */
    private Room meetingRoom;

    /**
     * Service de gestion des réunions.
     */
    private IMeetingService meetingService = DI.getMeetingService();

    public RoomFilterDialogFragment() {
        // Empty constructor required
    }

    /**
     * Constructeur static.
     *
     * @return instance d'un {@link RoomFilterDialogFragment}
     */
    public static RoomFilterDialogFragment newInstance() {
        return new RoomFilterDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_room_filter, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.initRoomsSpinner();
    }

    @Override
    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 80% of the screen width
        window.setLayout((int) (size.x * 0.80), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }

    /**
     * Initialisation du spinner permettant le choix de la salle.
     */
    private void initRoomsSpinner() {
        if (getContext() != null) {
            ArrayAdapter adapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_popup_item, this.meetingService.getRooms());
            this.spnRoom.setAdapter(adapter);
            this.spnRoom.setOnItemClickListener(this);
            this.spnRoom.setKeyListener(null);
        }
    }

    /**
     * Vérifie la validité du filtre de réunion choisie.
     *
     * @return true si valide
     */
    private boolean roomIsValid() {
        boolean isValid;
        if (this.roomFilterContainer.getError() != null) {
            isValid = false;
        } else if (this.meetingRoom == null) {
            this.roomFilterContainer.setError("Choisir une salle");
            isValid = false;
        } else {
            isValid = true;
        }
        return isValid;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.meetingRoom = (Room) parent.getItemAtPosition(position);
        this.roomFilterContainer.setError(null);
    }

    @OnClick(R.id.btn_room_filter_cancel)
    public void dismissFragment() {
        dismiss();
    }

    @OnClick(R.id.btn_room_filter_ok)
    public void applyFilter() {
        if (this.roomIsValid()) {
            EventBus.getDefault().post(new RoomFilterEvent(this.meetingRoom));
            dismiss();
        }
    }
}

