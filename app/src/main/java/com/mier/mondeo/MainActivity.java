package com.mier.mondeo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mier.mondeo.ui.Util;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // timer status
    private static final byte STOPPED = 0;
    private static final byte RUNNING = 1;
    private static final byte PAUSED = 2;

    // logic variables
    private long seconds;
    private long partialSeconds;
    private byte status;

    // ui elements
    private Button leftButton;
    private Button rightButton;
    private TextView partial;
    private TextView total;

    // other variables
    private static final int SPLIT_ON_SCREEN_CYCLES = 5;
    private int partialSuspendCycles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        partial = findViewById(R.id.currentTime);
        total = findViewById(R.id.totalTime);

        partialSuspendCycles = 0;
        setStatus(STOPPED);
    }

    /* --- status methods --- */

    public void onClickLeft(View view) {
        if(status == RUNNING) partialSuspendCycles = SPLIT_ON_SCREEN_CYCLES;
        else setStatus(RUNNING);
    }

    public void onClickRight(View view) {
        if(status == RUNNING) setStatus(PAUSED);
        else {
            setStatus(STOPPED);
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