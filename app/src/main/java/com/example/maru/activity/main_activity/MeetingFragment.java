package com.example.maru.activity.main_activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maru.DI.DI;
import com.example.maru.R;
import com.example.maru.Service.IMeetingService;
import com.example.maru.adapter.MeetingsListAdapter;
import com.example.maru.event.DeleteMeetingEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment contenant la liste des réunions.
 */
public class MeetingFragment extends Fragment {


    @BindView(R.id.tv_no_meetings)
    TextView tvNoMeetings;
    @BindView(R.id.meetings_list)
    RecyclerView meetingsRecyclerView;


    /**
     * Service de gestion des réunions.
     */
    private IMeetingService meetingService;

    /**
     * Adapter de la {@link RecyclerView} contenant les réunions.
     */
    private MeetingsListAdapter meetingsListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.meetingService = DI.getMeetingService();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meetings, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.initList();
        this.checkIfRecyclerViewIsEmpty();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Initialise la liste des réunions.
     */
    private void initList() {
        this.meetingsListAdapter = new MeetingsListAdapter(this.meetingService.getMeetings());
        this.meetingsRecyclerView.setAdapter(this.meetingsListAdapter);
    }

    /**
     * Vérifie si la liste de voisin est vide.
     */
    private void checkIfRecyclerViewIsEmpty() {
        if (this.meetingsListAdapter.getItemCount() == 0) {
            this.meetingsRecyclerView.setVisibility(View.INVISIBLE);
            this.tvNoMeetings.setVisibility(View.VISIBLE);
        } else {
            this.meetingsRecyclerView.setVisibility(View.VISIBLE);
            this.tvNoMeetings.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Déclenché lors de la réception d'un objet de type {@link com.example.maru.event.DeleteMeetingEvent}.
     *
     * @param deleteMeetingEvent évenement de suppression d'une réunion.
     */
    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent deleteMeetingEvent) {
        this.meetingService.deleteMeeting(deleteMeetingEvent.meeting);
        this.meetingsListAdapter.updateList(this.meetingService.getMeetings());
        this.checkIfRecyclerViewIsEmpty();
    }
}
