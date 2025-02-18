package com.example.parxondemo1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1;
    Button button1, button2, button3, button4, button5; // Added BMI and Water Intake buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        button1 = findViewById(R.id.startExercise1);
        button2 = findViewById(R.id.startExercise2);
        button3 = findViewById(R.id.startExercice3);
        button4 = findViewById(R.id.bmiButton); // BMI Calculator button
        button5 = findViewById(R.id.waterButton); // Water Intake button

        button1.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        });

        button2.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity2.class);
            startActivity(intent);
        });

        button3.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SecondActivity3.class);
            startActivity(intent);
        });

        button4.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, BMICalculator.class);
            startActivity(intent);
        });

        button5.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, WaterIntakeCalculator.class);
            startActivity(intent);
        });

        Button startVoiceButton = findViewById(R.id.startVoiceButton);
        startVoiceButton.setOnClickListener(view -> startVoiceRecognition());
    }

    private void startVoiceRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak your command...");
        try {
            startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(this, "Speech recognition not supported on this device.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && !results.isEmpty()) {
                String command = results.get(0);
                handleVoiceCommand(command);
            } else {
                Toast.makeText(this, "No voice command detected.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void handleVoiceCommand(String command) {
        command = command.toLowerCase();

        if (command.contains("stage")) {
            int stageNumber = extractNumberFromCommand(command, "stage");
            int exerciseNumber = extractNumberFromCommand(command, "exercise");

            if (stageNumber > 0 && exerciseNumber > 0) {
                redirectToExercise(stageNumber, exerciseNumber);
            } else if (stageNumber > 0) {
                redirectToStage(stageNumber);
            } else {
                Toast.makeText(this, "Invalid stage or exercise command.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Command not recognized: " + command, Toast.LENGTH_LONG).show();
        }
    }

    private void redirectToStage(int stageNumber) {}

    private void redirectToExercise(int stageNumber, int exerciseNumber) {
        Intent intent;

        switch (stageNumber) {
            case 1:
                intent = new Intent(this, SecondActivity.class);
                break;
            case 2:
                intent = new Intent(this, SecondActivity2.class);
                break;
            case 3:
                intent = new Intent(this, SecondActivity3.class);
                break;
            default:
                Toast.makeText(this, "Stage not supported.", Toast.LENGTH_SHORT).show();
                return;
        }

        startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_privacy) {
            openUrl("https://parxondemo1.blogspot.com/2024/12/parxondemo1-privacy-policy-page.html");
            return true;
        } else if (id == R.id.id_term) {
            openUrl("https://parxondemo1.blogspot.com/2024/12/parxondemo1-terms-conditions.html");
            return true;
        } else if (id == R.id.id_rate) {
            openRatePage();
            return true;
        } else if (id == R.id.id_more) {
            openUrl("https://play.google.com/store/apps/developer?id=Leap+Fitness+Group&hl=en");
            return true;
        } else if (id == R.id.id_share) {
            shareApp();
            return true;
        }
        else if (id == R.id.id_logout) {
            logoutUser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    private void openRatePage() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        } catch (Exception ex) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    private void shareApp() {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody = "The best app for Parkinson's Patients." + " https://play.google.com/store/apps/details?id=com.example.parxondemo1";
        String shareSubject = "ParXon";
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
        myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(myIntent, "Share using"));
    }
    private void logoutUser() {
        // Clear login state
        SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        // Redirect to login page
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear backstack
        startActivity(intent);
        finish();
    }

    public void stage1(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }

    public void stage2(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity2.class);
        startActivity(intent);
    }

    public void stage3(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity3.class);
        startActivity(intent);
    }




    public void Games(View view) {
        Intent intent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(intent);
    }
}