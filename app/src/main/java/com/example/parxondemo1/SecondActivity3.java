package com.example.parxondemo1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Locale;

public class SecondActivity3 extends AppCompatActivity {

    private static final int SPEECH_REQUEST_CODE = 100;
    int[] newArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second3); // your XML file name

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        newArray = new int[]{
                R.id.Exercise_26, R.id.Exercise_27, R.id.Exercise_28, R.id.Exercise_29, R.id.Exercise_30
        };
    }

    public void Imagebuttonclick(View view) {
        for (int i = 0; i < newArray.length; i++) {
            if (view.getId() == newArray[i]) {
                int value = i + 26;
                Log.i("EXERCISE_CLICKED", "Exercise: " + value);
                Intent intent = new Intent(SecondActivity3.this, ThirdActivity3.class);
                intent.putExtra("value", String.valueOf(value));
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_privacy) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://parxondemo1.blogspot.com/2024/12/parxondemo1-privacy-policy-page.html")));
            return true;
        } else if (id == R.id.id_term) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://parxondemo1.blogspot.com/2024/12/parxondemo1-terms-conditions.html")));
            return true;
        } else if (id == R.id.id_rate) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            }
            return true;
        } else if (id == R.id.id_more) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Leap+Fitness+Group&hl=en")));
            return true;
        } else if (id == R.id.id_share) {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String shareBody = "The best app for Parkinson's Patients." + " https://play.google.com/store/apps/details?id=com.example.parxondemo1";
            myIntent.putExtra(Intent.EXTRA_SUBJECT, "ParXon");
            myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(myIntent, "share using"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void startVoiceRecognition(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a command like 'Stage 3 Exercise 28'");
        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "Speech recognition not supported on your device.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String command = result.get(0).toLowerCase();
                handleVoiceCommand(command);
            }
        }
    }

    private void handleVoiceCommand(String command) {
        if (command.contains("exercise")) {
            int exerciseNumber = extractNumberFromCommand(command, "exercise");
            if (exerciseNumber >= 26 && exerciseNumber <= 30) {
                Intent intent = new Intent(SecondActivity3.this, ThirdActivity3.class);
                intent.putExtra("value", String.valueOf(exerciseNumber));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Exercise number out of range (26–30).", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Command not recognized: " + command, Toast.LENGTH_SHORT).show();
        }
    }

    private int extractNumberFromCommand(String command, String keyword) {
        if (command.contains(keyword)) {
            String[] words = command.split("\\s+");
            for (int i = 0; i < words.length - 1; i++) {
                if (words[i].equals(keyword)) {
                    try {
                        return Integer.parseInt(words[i + 1]);
                    } catch (NumberFormatException e) {
                        return -1;
                    }
                }
            }
        }
        return -1;
    }
}
