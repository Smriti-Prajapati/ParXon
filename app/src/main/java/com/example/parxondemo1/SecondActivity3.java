package com.example.parxondemo1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity3 extends AppCompatActivity {

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

        // ✅ Use Stage 3 Exercise IDs
        newArray = new int[]{
                R.id.Exercise_26, R.id.Exercise_27, R.id.Exercise_28, R.id.Exercise_29, R.id.Exercise_30
        };
    }

    public void Imagebuttonclick(View view) {
        for (int i = 0; i < newArray.length; i++) {
            if (view.getId() == newArray[i]) {
                int value = i + 1;
                Log.i("STAGE_3", "Exercise Selected: " + value);
                Intent intent = new Intent(SecondActivity3.this, ThirdActivity3.class);
                intent.putExtra("value", String.valueOf(value));
                startActivity(intent);
            }
        }
    }
}
