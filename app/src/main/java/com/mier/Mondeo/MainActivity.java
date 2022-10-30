package com.mier.Mondeo;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.mier.Mondeo.databinding.ActivityMainBinding;
import com.mier.Mondeo.obj.Travel;
import com.mier.Mondeo.ui.Util;
import com.mier.Mondeo.util.LoadSave;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    // timer status
    private static final byte STOPPED = 0;
    private static final byte RUNNING = 1;
    private static final byte PAUSED = 2;

    // logic variables
    private long seconds;
    private long partialSeconds;
    private byte status;
    private Travel currentTravel;

    // ui elements
    private Button leftButton;
    private Button rightButton;
    private TextView partial;
    private TextView total;
    private Spinner travelList;

    // other variables
    private static final int SPLIT_ON_SCREEN_CYCLES = 5;
    private int partialSuspendCycles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        partial = findViewById(R.id.currentTime);
        travelList = findViewById(R.id.travels);
        total = findViewById(R.id.totalTime);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, LoadSave.getTravelList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        travelList.setAdapter(adapter);

        partialSuspendCycles = 0;
        setStatus(STOPPED);

        rightButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                setStatus(STOPPED);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /* --- status methods --- */

    public void onClickLeft(View view) {
        if(status == RUNNING) {
            partialSuspendCycles = SPLIT_ON_SCREEN_CYCLES;
            partialSeconds = 0;
        }
        else setStatus(RUNNING);
    }

    public void onClickRight(View view) {
        if(status == RUNNING) setStatus(PAUSED);
        else {
            setStatus(STOPPED);
            // TODO: save travel
            Util.setSnackBar(view, "Successfully saved the travel.");
        }
    }

    /* --- stopwatch logic --- */

    private void runTimer() {
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                if(getStatus() != RUNNING) return;

                updateTimers();
                seconds++;
                partialSeconds++;
                handler.postDelayed(this, 999);
            }
        });
    }

    private void updateTimers() {
        int[] totalTime = formatSeconds(seconds);
        int[] currentTime = formatSeconds(partialSeconds);

        total.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", totalTime[0], totalTime[1], totalTime[2]));
        if(partialSuspendCycles == 0 || status != RUNNING) partial.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", currentTime[0], currentTime[1], currentTime[2]));
        else partialSuspendCycles--;
    }

    public static int[] formatSeconds(long seconds) {
        return new int[] {
                (int) (seconds / 3600),
                (int) (seconds % 3600 / 60),
                (int) (seconds % 60)
        };
    }

    /* --- buttons update --- */

    private void setStatus(byte status) {
        this.status = status;
        rightButton.setEnabled(status != STOPPED);

        int leftId;
        int rightId = status == PAUSED ? R.string.save : R.string.stop;
        int rightColor = status == PAUSED ? R.color.green : R.color.red;

        switch(status) {
            case RUNNING:
                leftId = R.string.lap;
                runTimer();
                break;
            case PAUSED:
                leftId = R.string.unpause;
                break;
            case STOPPED:
                leftId = R.string.start;
                seconds = 0;
                partialSeconds = 0;
                updateTimers();
                break;
            default:
                throw new UnsupportedOperationException("Invalid status");
        }

        leftButton.setText(getResources().getString(leftId));
        rightButton.setText(getResources().getString(rightId));
        rightButton.setBackgroundColor(getResources().getColor(rightColor, getTheme()));
    }

    public byte getStatus() {
        return status;
    }
}