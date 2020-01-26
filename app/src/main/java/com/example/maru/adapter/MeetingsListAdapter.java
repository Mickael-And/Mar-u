package com.example.maru.adapter;

import android.graphics.drawable.ColorDrawable;
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

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter de la liste des réunions.
 */
public class MeetingsListAdapter extends RecyclerView.Adapter<MeetingsListAdapter.MeetingViewHolder> {

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
                .load(new ColorDrawable(holder.imgColor.getContext().getResources().getColor(meeting.getColor())))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imgColor);
        // Affichage du lieu, l'heure et le sujet
        String details = String.format(Locale.getDefault(), "%02d/%02d/%d %02dh%02d", meeting.getDateTime().get(Calendar.DAY_OF_MONTH), meeting.getDateTime().get(Calendar.MONTH) + 1
                , meeting.getDateTime().get(Calendar.YEAR), meeting.getDateTime().get(Calendar.HOUR_OF_DAY), meeting.getDateTime().get(Calendar.MINUTE));
        holder.tvDetails.setText(String.format(Locale.getDefault(), "%s - %s - %s", meeting.getPlace(), details, meeting.getTopic()));

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
        this.meetings = pMeetins;
        notifyDataSetChanged();
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
