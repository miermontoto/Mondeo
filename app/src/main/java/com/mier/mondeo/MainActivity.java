package com.mier.mondeo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private boolean running = false;
    private Button mainButton;
    private Button resetButton;
    private TextView current;
    private TextView total;
    private long seconds = 0;
    private long currentSeconds = 0;

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
        current = (TextView) findViewById(R.id.currentTime);
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
        else onClickReset();
    }

    private void onClickLap() {
        currentSeconds = 0;
        updateTimers();
    }

    private void onClickReset() {
        seconds = 0;
        currentSeconds = 0;
        updateTimers();
        resetButton.setText(getResources().getText(R.string.stop));
        resetButton.setEnabled(false);
    }


    private void onClickStart() {
        mainButton.setText(getResources().getString(R.string.lap));
        if(resetButton.getText() == getResources().getText(R.string.stop)) seconds = 0;
        running = true;
        resetButton.setEnabled(true);
        resetButton.setText(getResources().getText(R.string.stop));
    }

    private void onClickStop() {
        mainButton.setText(getResources().getString(R.string.start));
        resetButton.setText(getResources().getString(R.string.clear));
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

        total.setText(String.format(Locale.UK, "%02d:%02d:%02d", totalTime[0], totalTime[1], totalTime[2]));
        current.setText(String.format(Locale.UK, "%02d:%02d:%02d", currentTime[0], currentTime[1], currentTime[2]));

    }
}