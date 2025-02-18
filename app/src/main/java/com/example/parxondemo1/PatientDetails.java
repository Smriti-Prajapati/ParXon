package com.example.parxondemo1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class PatientDetails extends AppCompatActivity {

    private EditText patientnameEditText, patientemailEditText, patientstageEditText;
    private Button SaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientdetails);

        // Initialize UI components
        patientnameEditText = findViewById(R.id.patientname);
        patientemailEditText = findViewById(R.id.patientemail);
        patientstageEditText = findViewById(R.id.patientstage);
        SaveButton = findViewById(R.id.SaveButton);

        // Button click listener
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientDetails.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}