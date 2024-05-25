package com.example.pomodoroapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    Button btnStart;
    TextView textViewTime, textViewRound, txtMessage;
    int goal, roundTime, breakTime, longBreakTime;
    int defaultValue = 0;
    int currentRound=0;
    MediaPlayer finishAudio;
    CountDownTimer countDownTimer;
    private volatile long timeLeftInMillis;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnStart = (Button) findViewById(R.id.btnStart);
        textViewRound = (TextView) findViewById(R.id.txtRound);
        textViewTime = (TextView) findViewById(R.id.txtTime);
        txtMessage = (TextView) findViewById(R.id.txtMessage);
        finishAudio = MediaPlayer.create(this, R.raw.donebell);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        roundTime = getIntent().getIntExtra("roundTime",defaultValue);
        goal = getIntent().getIntExtra("goal",defaultValue);
        breakTime = getIntent().getIntExtra("breakTime",defaultValue);
        longBreakTime = getIntent().getIntExtra("longBreak",defaultValue);

        updateRound();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.back){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickStart(View view) {
        btnStart.setEnabled(false);
        startRoundTime();
    }

    public void updateRound(){
        if (currentRound!=goal){
            currentRound++;
            textViewRound.setText(currentRound+"/"+goal);
        }
        else{
            txtMessage.setText("Congrats!!! Mission Completed!");
            finishAudio.start();
        }
    }

    private void updateTime(long kalan){
        int seconds = (int) (kalan / 1000) % 60;
        int minutes = (int) (kalan / (1000 * 60)) % 60;
        int hours = (int) (kalan / (1000 * 60 * 60));

        String formattedTime;
        if (hours > 0) {
            formattedTime = String.format("%02d:%02d:%02d", hours, minutes,seconds);
        } else {
            formattedTime = String.format("%02d:%02d", minutes, seconds);
        }

        textViewTime.setText(formattedTime);
    }
    
    public void startRoundTime(){
        txtMessage.setText("It's time to study...");
        countDownTimer = new CountDownTimer(roundTime*60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                int remainingSeconds = (int) (millisUntilFinished / 1000);
                int totalSeconds = roundTime * 60;
                int progress = (int) ((remainingSeconds / (float) totalSeconds) * 100);
                progressBar.setProgress(progress);

                updateTime(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                if (currentRound % 4 == 0 && currentRound!=goal) {
                    startLongBreak();
                } else if(currentRound!=goal) {
                    startBreak();
                } else{
                    updateRound();
                }

            }
        };
        countDownTimer.start();

    }
    
    public void startBreak(){
        txtMessage.setText("It's time to break");
        countDownTimer = new CountDownTimer(breakTime*60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                int remainingSeconds = (int) (millisUntilFinished / 1000);
                int totalSeconds = breakTime * 60;
                int progress = (int) ((remainingSeconds / (float) totalSeconds) * 100);
                progressBar.setProgress(progress);
                updateTime(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                updateRound();
                startRoundTime();
            }
        };
        countDownTimer.start();
    }
    
    public void startLongBreak(){
        txtMessage.setText("It's time to long break");
        countDownTimer = new CountDownTimer(longBreakTime*60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                int remainingSeconds = (int) (millisUntilFinished / 1000);
                int totalSeconds = longBreakTime * 60;
                int progress = (int) ((remainingSeconds / (float) totalSeconds) * 100);
                progressBar.setProgress(progress);
                updateTime(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                updateRound();
                startRoundTime();
            }
        };
        countDownTimer.start();

    }
}
