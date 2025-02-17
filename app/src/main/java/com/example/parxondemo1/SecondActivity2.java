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

public class SecondActivity2 extends AppCompatActivity {

    private static final int SPEECH_REQUEST_CODE = 100;
    int[] newArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        newArray = new int[]{
                R.id.Exercise_1, R.id.Exercise_2, R.id.Exercise_3, R.id.Exercise_4, R.id.Exercise_5,
                R.id.Exercise_6, R.id.Exercise_7, R.id.Exercise_8, R.id.Exercise_9, R.id.Exercise_10,
                R.id.Exercise_11, R.id.Exercise_12, R.id.Exercise_13,
        };
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
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://parxondemo1.blogspot.com/2024/12/parxondemo1-privacy-policy-page.html"));
            startActivity(intent);
            return true;
        }
        if (id == R.id.id_term) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://parxondemo1.blogspot.com/2024/12/parxondemo1-terms-conditions.html"));
            startActivity(intent);
            return true;
        }
        if (id == R.id.id_rate) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            } catch (Exception ex) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            }
            return true;
        }
        if (id == R.id.id_more) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Leap+Fitness+Group&hl=en"));
            startActivity(intent);
            return true;
        }
        if (id == R.id.id_share) {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String shareBody = "The best app for Parkinson's Patients." + "https://play.google.com/store/apps/details?id=com.example.parxondemo1";
            String shareHub = "ParXon";
            myIntent.putExtra(Intent.EXTRA_SUBJECT, shareHub);
            myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(myIntent, "share using"));
            return true;
        }
        return true;
    }

    public void Imagebuttonclick(View view) {
        for (int i = 0; i < newArray.length; i++) {
            if (view.getId() == newArray[i]) {
                int value = i + 1;
                Log.i("FIRST", String.valueOf(value));
                Intent intent = new Intent(SecondActivity2.this, ThirdActivity.class);
                intent.putExtra("value", String.valueOf(value));
                startActivity(intent);
            }
        }
    }

    // Method to start speech recognition
    public void startVoiceRecognition(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a command like 'Stage 1 Exercise 2'");
        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "Speech recognition not supported on your device.", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle the results of voice recognition
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

    // Process voice command
    private void handleVoiceCommand(String command) {
        if (command.contains("stage") && command.contains("exercise")) {
            int stageNumber = extractNumberFromCommand(command, "stage");
            int exerciseNumber = extractNumberFromCommand(command, "exercise");

            if (stageNumber > 0 && exerciseNumber > 0 && exerciseNumber <= newArray.length) {
                int value = exerciseNumber; // Use exercise number for your logic
                Log.i("VOICE_COMMAND", "Stage: " + stageNumber + ", Exercise: " + exerciseNumber);

                // Start ThirdActivity for the selected exercise
                Intent intent = new Intent(SecondActivity2.this, ThirdActivity.class);
                intent.putExtra("value", String.valueOf(value));
                startActivity(intent);
            } else {
                Toast.makeText(this, "Invalid stage or exercise number.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Command not recognized: " + command, Toast.LENGTH_SHORT).show();
        }
    }

    // Extract number from command for a specific keyword (e.g., "stage" or "exercise")
    private int extractNumberFromCommand(String command, String keyword) {
        if (command.contains(keyword)) {
            String[] words = command.split("\\s+");
            for (int i = 0; i < words.length - 1; i++) {
                if (words[i].equals(keyword)) {
                    try {
                        return Integer.parseInt(words[i + 1]);
                    } catch (NumberFormatException e) {
                        return -1; // Invalid number
                    }
                }
            }
        }
        return -1; // Keyword not found
    }
}