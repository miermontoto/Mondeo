package com.mier.mondeo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.Contract;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // logic variables
    private boolean running = false;
    private boolean partialRunning = true;
    private long seconds = 0;
    private long currentSeconds = 0;

    // ui elements
    private Button mainButton;
    private Button resetButton;
    private TextView partial;
    private TextView total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            running = savedInstanceState.getBoolean("running");
            seconds = savedInstanceState.getLong("seconds");
        }

        mainButton = findViewById(R.id.buttonStart);
        resetButton = findViewById(R.id.buttonStop);
        partial = (TextView) findViewById(R.id.currentTime);
        total = (TextView) findViewById(R.id.totalTime);
        runTimer();

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
        partialRunning = false;
        updateTimers();
        currentSeconds = 0;
    }

    private void onClickSave() {
        clear();
        // TODO: save
        // TODO: save notification
    }

    private void clear() {
        seconds = 0;
        currentSeconds = 0;
        updateTimers();
        stopSaveTransition(true);
        resetButton.setEnabled(false);
        mainButtonTransition("start");
    }


    private void onClickStart() {
        if(resetButton.getText() == getResources().getText(R.string.stop)) seconds = 0;
        running = true;
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
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(running) {
                    seconds++;
                    currentSeconds++;
                }

                updateTimers();
                handler.postDelayed(this, 1000);
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
        int[] currentTime = formatSeconds(currentSeconds);

        total.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", totalTime[0], totalTime[1], totalTime[2]));
        if(partialRunning) partial.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", currentTime[0], currentTime[1], currentTime[2]));

    }
    
    private void leftToggle() {
        if(mainButton.getText().equals(getResources().getString(R.string.start))) mainButton.setText(getResources().getString(R.string.lap));
        else mainButton.setText(getResources().getString(R.string.start));
    }
    
    private void stopSaveTransition(boolean stop) {
        resetButton.setText(getResources().getString(stop ? R.string.stop : R.string.save));
        resetButton.setBackgroundColor(getResources().getColor(stop ? R.color.red : R.color.green));
    }

    private void mainButtonTransition(String mode) {
        // append variable mode to R.string and find in resources
        mainButton.setText(getResources().getString(getResources().getIdentifier(mode, "string", getPackageName())));
    }
}