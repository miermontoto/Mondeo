package com.mier.mondeo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // logic variables
    private boolean running;
    private long seconds;
    private long partialSeconds;

    // ui elements
    private Button mainButton;
    private Button resetButton;
    private TextView partial;
    private TextView total;

    // other variables
    private static final int SPLIT_ON_SCREEN_CYCLES = 5;
    private int splitOnScreenCycles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            running = savedInstanceState.getBoolean("running");
            seconds = savedInstanceState.getLong("seconds");
            partialSeconds = savedInstanceState.getLong("partialSeconds");
            if(running) runTimer();
        }

        mainButton = findViewById(R.id.buttonStart);
        resetButton = findViewById(R.id.buttonStop);
        partial = findViewById(R.id.currentTime);
        total = findViewById(R.id.totalTime);

        splitOnScreenCycles = 0;
        seconds = 0;
        partialSeconds = 0;
        running = false;

        resetButton.setEnabled(false);
    }

    public void onClickLeft(View view) {
        if(!running) onClickStart();
        else onClickLap();
    }

    public void onClickRight(View view) {
        if(running) onClickStop();
        else onClickSave();
    }

    private void onClickLap() {
        splitOnScreenCycles = SPLIT_ON_SCREEN_CYCLES;
        partialSeconds = 0;
    }

    private void onClickSave() {
        clear();
        // TODO: save

        setSnackBar(findViewById(R.id.mainLayout), "Successfully saved the travel.");
    }

    private void clear() {
        seconds = 0;
        partialSeconds = 0;
        updateTimers();
        stopSaveTransition(true);
        resetButton.setEnabled(false);
        mainButtonTransition("start");
    }


    private void onClickStart() {
        runTimer();
        if(resetButton.getText() == getResources().getText(R.string.stop)) seconds = 0;
        resetButton.setEnabled(true);
        stopSaveTransition(true);
        mainButtonTransition("lap");
    }

    private void onClickStop() {
        mainButtonTransition("unpause");
        stopSaveTransition(false);
        running = false;
    }

    private void runTimer() {
        final Handler handler = new Handler();
        running = true;
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(running) {
                    updateTimers();
                    seconds++;
                    partialSeconds++;
                    handler.postDelayed(this, 1000);
                } else {
                    seconds--;
                    partialSeconds--;
                }
            }
        });
    }

    @NonNull
    public static int[] formatSeconds(long seconds) {
        return new int[] {
                (int) seconds / 3600,
                ((int) seconds % 3600) / 60,
                (int) seconds % 60
        };
    }

    private void updateTimers() {
        int[] totalTime = formatSeconds(seconds);
        int[] currentTime = formatSeconds(partialSeconds);

        total.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", totalTime[0], totalTime[1], totalTime[2]));
        if(splitOnScreenCycles == 0) partial.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", currentTime[0], currentTime[1], currentTime[2]));
        else splitOnScreenCycles--;
    }
    
    private void stopSaveTransition(boolean stop) {
        resetButton.setText(getResources().getString(stop ? R.string.stop : R.string.save));
        resetButton.setBackgroundColor(getResources().getColor(stop ? R.color.red : R.color.green, getTheme()));
    }

    private void mainButtonTransition(String mode) {
        // append variable mode to R.string and find in resources
        mainButton.setText(getResources().getString(getResources().getIdentifier(mode, "string", getPackageName())));
    }

    /**
     * Set a snackbar to the bottom of the screen
     * @param root the root view of the activity
     * @param snackTitle the title of the snackbar
     */
    public static void setSnackBar(View root, String snackTitle) {
        Snackbar snackbar = Snackbar.make(root, snackTitle, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}