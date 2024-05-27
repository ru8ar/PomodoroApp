package com.example.pomodoroapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class SelectScreen extends AppCompatActivity {

    NumberPicker npBreakTime,npRoundTime,npGoal,npLongBreak;
    Button btnGo;
    int goal, roundTime, breakTime, longBreak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selcet_screen);


        npGoal = (NumberPicker) findViewById(R.id.npGoal);
        npBreakTime = (NumberPicker) findViewById(R.id.npBreakTime);
        npRoundTime = (NumberPicker) findViewById(R.id.npRoundTime);
        npLongBreak = (NumberPicker) findViewById(R.id.npLongBreak);
        btnGo = (Button) findViewById(R.id.btnGo);

        npGoal.setMinValue(1);
        npGoal.setMaxValue(100);
        npGoal.setValue(npGoal.getMinValue());

        npRoundTime.setMinValue(1);   //25
        npRoundTime.setMaxValue(45);
        npRoundTime.setValue(npRoundTime.getMinValue());


        npBreakTime.setMinValue(1);  //3
        npBreakTime.setMaxValue(7);
        npBreakTime.setValue(npBreakTime.getMinValue());

        npLongBreak.setMinValue(1); //30
        npLongBreak.setMaxValue(45);
        npLongBreak.setValue(npLongBreak.getMinValue());



        goal = npGoal.getValue();
        roundTime =npRoundTime.getValue();
        breakTime = npBreakTime.getValue();
        longBreak = npLongBreak.getValue();



        npGoal.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                goal = newVal;
            }
        });
        npRoundTime.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                roundTime = newVal;
            }
        });
        npBreakTime.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                breakTime = newVal;
            }
        });
        npLongBreak.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                longBreak = newVal;
            }
        });

    }
    public void onClick(View v) {
        Intent myInt = new Intent(SelectScreen.this, MainActivity.class);
        myInt.putExtra("goal", goal);
        myInt.putExtra("roundTime",roundTime);
        myInt.putExtra("breakTime", breakTime);
        myInt.putExtra("longBreak", longBreak);
        startActivity(myInt);

    }


}
