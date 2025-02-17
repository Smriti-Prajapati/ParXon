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

        // ✅ Use Stage 2 Exercise IDs
        newArray = new int[]{
                R.id.Exercise_16, R.id.Exercise_17, R.id.Exercise_18, R.id.Exercise_19, R.id.Exercise_20,
                R.id.Exercise_21, R.id.Exercise_22, R.id.Exercise_23, R.id.Exercise_24, R.id.Exercise_25
        };
    }

    public void Imagebuttonclick(View view) {
        for (int i = 0; i < newArray.length; i++) {
            if (view.getId() == newArray[i]) {
                int value = i + 1;
                Log.i("STAGE_2", "Exercise Selected: " + value);
                Intent intent = new Intent(SecondActivity2.this, ThirdActivity2.class);
                intent.putExtra("value", String.valueOf(value));
                startActivity(intent);
            }
        }
    }
}
