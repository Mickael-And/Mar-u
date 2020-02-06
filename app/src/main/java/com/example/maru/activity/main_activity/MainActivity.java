package com.example.maru.activity.main_activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.example.maru.R;
import com.example.maru.activity.dialog_fragment.DateFilterDialogFragment;
import com.example.maru.activity.dialog_fragment.RoomFilterDialogFragment;
import com.example.maru.activity.meeting_activity.CreateMeetingActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Activité de démarrage.
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(this.toolbar);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryVariant));

        fab.setOnClickListener(v -> startActivity(new Intent(this, CreateMeetingActivity.class)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.date_filter:
                FragmentManager fragmentManager = getSupportFragmentManager();
                DateFilterDialogFragment dateFilterDialogFragment = DateFilterDialogFragment.newInstance();
                dateFilterDialogFragment.show(fragmentManager, "dialog_fragment_date_filter");
                break;
            case R.id.place_filter:
                fragmentManager = getSupportFragmentManager();
                RoomFilterDialogFragment roomFilterDialogFragment = RoomFilterDialogFragment.newInstance();
                roomFilterDialogFragment.show(fragmentManager, "dialog_fragment_room_filter");
                break;
            default:
                Log.i("MainActivity", "Filtre inconnue");
        }

        return super.onOptionsItemSelected(item);
    }
}
