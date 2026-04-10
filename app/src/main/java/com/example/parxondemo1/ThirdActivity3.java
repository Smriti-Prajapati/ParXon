package com.example.parxondemo1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ThirdActivity3 extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    String buttonvalue;
    Button startBtn;
    private CountDownTimer countDownTimer;
    TextView mtextview;
    private boolean MTimeRunning;
    private long MTimeLeftmills;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.US);
            }
        });

        Intent intent = getIntent();
        buttonvalue = intent.getStringExtra("value");
        int intvalue = Integer.parseInt(buttonvalue);

        // ✅ Set Correct Exercise Layouts
        switch (intvalue) {
            case 26: setContentView(R.layout.activity_exercise_stage3_1); break;
            case 27: setContentView(R.layout.activity_exercise_stage3_2); break;
            case 28: setContentView(R.layout.activity_exercise_stage3_3); break;
            case 29: setContentView(R.layout.activity_exercise_stage3_4); break;
            case 30: setContentView(R.layout.activity_exercise_stage3_5); break;
        }

        startBtn = findViewById(R.id.startbutton);
        mtextview = findViewById(R.id.time);

        startBtn.setOnClickListener(view -> {
            if (MTimeRunning) {
                stoptimer();
            } else {
                startTimer();
            }
        });
    }

    public void speakInstructions(View view) {
        String instructions = "";
        switch (buttonvalue) {
            case "26": instructions = getString(R.string.pose26); break;
            case "27": instructions = getString(R.string.pose27); break;
            case "28": instructions = getString(R.string.pose28); break;
            case "29": instructions = getString(R.string.pose29); break;
            case "30": instructions = getString(R.string.pose30); break;
        }

        if (!instructions.isEmpty()) {
            textToSpeech.speak(instructions, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private void stoptimer() {
        countDownTimer.cancel();
        MTimeRunning = false;
        startBtn.setText("START");
    }

    private void startTimer() {
        String[] time = mtextview.getText().toString().split(":");
        int minutes = Integer.parseInt(time[0]);
        int seconds = Integer.parseInt(time[1]);
        MTimeLeftmills = (minutes * 60 + seconds) * 1000L;

        countDownTimer = new CountDownTimer(MTimeLeftmills, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                MTimeLeftmills = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                int nextValue = Integer.parseInt(buttonvalue) + 1;
                if (nextValue <= 30) {
                    Intent intent = new Intent(ThirdActivity3.this, ThirdActivity3.class);
                    intent.putExtra("value", String.valueOf(nextValue));
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
            }
        }.start();
        startBtn.setText("Pause");
        MTimeRunning = true;
    }

    private void updateTimer() {
        int minutes = (int) MTimeLeftmills / 60000;
        int seconds = (int) (MTimeLeftmills % 60000) / 1000;
        String formatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mtextview.setText(formatted);
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
