package com.example.maru.adapter;

import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.maru.R;
import com.example.maru.event.DeleteMeetingEvent;
import com.example.maru.model.Meeting;
import com.example.maru.model.Room;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;

/**
 * Adapter de la liste des réunions.
 */
public class MeetingsListAdapter extends RecyclerView.Adapter<MeetingsListAdapter.MeetingViewHolder> {

    // Constantes représentant les filtres disponibles pour l'affichage de la liste.
    public static final int NO_FILTER = 0;
    public static final int ROOM_FILTER = 1;
    public static final int DATE_FILTER = 2;

    @Setter
    private Calendar startDateFilter;
    @Setter
    private Calendar endDateFilter;
    @Setter
    private Room roomFilter;
    @Setter
    private int currentFilter = NO_FILTER;

    /**
     * Liste des réunions à afficher.
     */
    private List<Meeting> meetings;

    /**
     * Constructeur.
     *
     * @param pMeetings liste à afficher
     */
    public MeetingsListAdapter(List<Meeting> pMeetings) {
        this.meetings = pMeetings;
    }

    @NonNull
    @Override
    public MeetingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MeetingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meeting, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingViewHolder holder, int position) {
        // Récupération de la réunion
        Meeting meeting = this.meetings.get(position);
        // Affichage de la couleur de la réunion
        Glide.with(holder.imgColor.getContext())
                .load(new ColorDrawable(holder.imgColor.getContext().getResources().getColor(meeting.getRoom().getColor())))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imgColor);
        // Affichage du lieu, l'heure et le sujet
        String time = String.format(Locale.getDefault(), "%02dh%02d", meeting.getDateTime().get(Calendar.HOUR_OF_DAY), meeting.getDateTime().get(Calendar.MINUTE));
        holder.tvDetails.setText(String.format(Locale.getDefault(), "%s - %s - %s", meeting.getRoom(), time, meeting.getTopic()));

        // Construction de la liste des participants à afficher
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < meeting.getParticipants().size(); i++) {
            sb.append(meeting.getParticipants().get(i));
            if (i != (meeting.getParticipants().size() - 1)) {
                sb.append(", ");
            }
        }
        holder.tvParticipants.setText(sb);

        holder.btnDelete.setOnClickListener(v -> EventBus.getDefault().post(new DeleteMeetingEvent(meeting)));
    }

    @Override
    public int getItemCount() {
        return this.meetings.size();
    }

    /**
     * Met à jour la liste des réunions à afficher.
     *
     * @param pMeetins liste de réunions à afficher
     */
    public void updateList(List<Meeting> pMeetins) {
        Collections.sort(pMeetins, (o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()));
        Log.d("MeetingsListAdatper", String.valueOf(this.currentFilter));
        switch (this.currentFilter) {
            case NO_FILTER:
                this.meetings = pMeetins;
                notifyDataSetChanged();
                break;
            case ROOM_FILTER:
                List<Meeting> meetingsByRoom = new ArrayList<>();
                for (Meeting meeting : pMeetins) {
                    if (meeting.getRoom().equals(this.roomFilter)) {
                        meetingsByRoom.add(meeting);
                    }
                }
                this.meetings = meetingsByRoom;
                notifyDataSetChanged();
                break;
            case DATE_FILTER:
                List<Meeting> meetingsByDate = new ArrayList<>();
                for (Meeting meeting : pMeetins) {
                    if (meeting.getDateTime().after(this.startDateFilter) && meeting.getDateTime().before(this.endDateFilter)) {
                        meetingsByDate.add(meeting);
                    }
                }
                this.meetings = meetingsByDate;
                notifyDataSetChanged();
                break;
            default:
                Log.d("MeetingsListAdapter", "Filtre inconnue");
                break;
        }
    }

    /**
     * {@link androidx.recyclerview.widget.RecyclerView.ViewHolder} représentant une ligne de la liste des réunions.
     */
    public static class MeetingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_item_list_color)
        ImageView imgColor;
        @BindView(R.id.tv_item_list_meeting_details)
        TextView tvDetails;
        @BindView(R.id.tv_item_list_meeting_participants)
        TextView tvParticipants;
        @BindView(R.id.btn_item_list_delete)
        ImageButton btnDelete;

        public MeetingViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
