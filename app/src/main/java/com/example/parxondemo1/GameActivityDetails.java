package com.example.parxondemo1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivityDetails extends AppCompatActivity {

    String gameUrl;
    TextView textView;
    Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_details);

        textView = findViewById(R.id.txt);
        playButton = findViewById(R.id.play_game_button);

        // Get the game link from the intent
        gameUrl = getIntent().getStringExtra("game_link");

        if (gameUrl != null) {
            textView.setText("Click below to play the game.");
        } else {
            textView.setText("Game link not found.");
            playButton.setVisibility(View.GONE);
        }

        playButton.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(gameUrl));
            startActivity(browserIntent);
        });
    }

    public void goback(View view) {
        Intent intent = new Intent(GameActivityDetails.this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(GameActivityDetails.this, GameActivity.class);
        startActivity(intent);
        finish();
    }
}
