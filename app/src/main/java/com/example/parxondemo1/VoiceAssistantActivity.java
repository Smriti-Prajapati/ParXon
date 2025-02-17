package com.example.parxondemo1;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;
import androidx.appcompat.app.AppCompatActivity;

public class VoiceAssistantActivity extends AppCompatActivity {

    private SpeechRecognizer speechRecognizer;
    private TextToSpeech textToSpeech;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Replace with your layout

        startButton = findViewById(R.id.startbutton);

        // Initialize Text-to-Speech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.US);
            }
        });

        // Initialize SpeechRecognizer
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US"); // Change to desired language

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {}

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String voiceCommand = matches.get(0);
                    handleVoiceCommand(voiceCommand);
                }
            }

            @Override public void onPartialResults(Bundle partialResults) {}
            @Override public void onError(int error) {}
            @Override public void onBeginningOfSpeech() {}
            @Override public void onEndOfSpeech() {}
            @Override public void onEvent(int eventType, Bundle params) {}
            @Override public void onBufferReceived(byte[] buffer) {}
            @Override public void onRmsChanged(float rmsdB) {}
        });

        // Start button functionality
        startButton.setOnClickListener(v -> {
            speak("Start");
            speechRecognizer.startListening(intent);
        });
    }

    private void handleVoiceCommand(String command) {
        if (command.toLowerCase().contains("exercise")) {
            startExercise();
        } else if (command.toLowerCase().contains("reminder")) {
            showReminder();
        } else {
            showUnknownCommandMessage();
        }
    }

    private void speak(String message) {
        textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    private void showReminder() {
        Toast.makeText(getApplicationContext(), "It's time to do your exercises!", Toast.LENGTH_LONG).show();
    }

    private void showUnknownCommandMessage() {
        Toast.makeText(getApplicationContext(), "Sorry, I didn't understand that command.", Toast.LENGTH_LONG).show();
    }

    private void startExercise() {
        // Implement exercise start logic
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer != null) speechRecognizer.destroy();
        if (textToSpeech != null) textToSpeech.shutdown();
    }
}
