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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class ThirdActivity2 extends AppCompatActivity {

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
        setContentView(R.layout.activity_third);

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported");
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonvalue = getIntent().getStringExtra("value");
        int intvalue = Integer.parseInt(buttonvalue);

        if (intvalue >= 16 && intvalue <= 25) {
            setContentView(getResources().getIdentifier("activity_exercise_stage2_" + (intvalue - 15), "layout", getPackageName()));
        } else if (intvalue >= 26 && intvalue <= 30) {
            setContentView(getResources().getIdentifier("activity_exercise_stage3_" + (intvalue - 25), "layout", getPackageName()));
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
            case "16": instructions = getString(R.string.pose16); break;
            case "17": instructions = getString(R.string.pose17); break;
            case "18": instructions = getString(R.string.pose18); break;
            case "19": instructions = getString(R.string.pose19); break;
            case "20": instructions = getString(R.string.pose20); break;
            case "21": instructions = getString(R.string.pose21); break;
            case "22": instructions = getString(R.string.pose22); break;
            case "23": instructions = getString(R.string.pose23); break;
            case "24": instructions = getString(R.string.pose24); break;
            case "25": instructions = getString(R.string.pose25); break;
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
        final String[] splitTime = mtextview.getText().toString().split(":");
        int minutes = Integer.parseInt(splitTime[0]);
        int seconds = Integer.parseInt(splitTime[1]);
        MTimeLeftmills = (minutes * 60 + seconds) * 1000L;

        countDownTimer = new CountDownTimer(MTimeLeftmills, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                MTimeLeftmills = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                int nextExercise = Integer.parseInt(buttonvalue) + 1;
                if (nextExercise <= 30) {
                    Intent intent = new Intent(ThirdActivity2.this, ThirdActivity2.class);
                    intent.putExtra("value", String.valueOf(nextExercise));
                    startActivity(intent);
                    finish();
                } else {
                    finish(); // finish after 30
                }
            }
        }.start();
        startBtn.setText("Pause");
        MTimeRunning = true;
    }

    private void updateTimer() {
        int minutes = (int) MTimeLeftmills / 60000;
        int seconds = (int) MTimeLeftmills % 60000 / 1000;
        String timeLeft = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mtextview.setText(timeLeft);
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
