package com.example.parxondemo1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WaterIntakeCalculator extends AppCompatActivity {

    EditText weightInput;
    RadioGroup activityLevelGroup;
    Button calculateButton;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake_calculator);

        weightInput = findViewById(R.id.weightInput);
        activityLevelGroup = findViewById(R.id.activityLevelGroup);
        calculateButton = findViewById(R.id.calculateButton);
        resultText = findViewById(R.id.resultText);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateWaterIntake();
            }
        });
    }

    private void calculateWaterIntake() {
        String weightStr = weightInput.getText().toString();

        if (!weightStr.isEmpty()) {
            float weight = Float.parseFloat(weightStr);

            // Default intake = 35ml per kg
            float waterIntake = weight * 35;

            // Adjust based on activity level
            int selectedId = activityLevelGroup.getCheckedRadioButtonId();
            if (selectedId == R.id.lowActivity) {
                waterIntake *= 1.0;  // No change
            } else if (selectedId == R.id.moderateActivity) {
                waterIntake *= 1.2;  // Increase by 20%
            } else if (selectedId == R.id.highActivity) {
                waterIntake *= 1.4;  // Increase by 40%
            }

            // Convert to liters
            float waterIntakeLiters = waterIntake / 1000;

            String resultMessage = "💧 Recommended Daily Water Intake: " + String.format("%.2f", waterIntakeLiters) + " Liters\n\n";

            if (waterIntakeLiters < 2) {
                resultMessage += "⚠ Drink more water! Staying hydrated helps maintain energy & balance.";
            } else if (waterIntakeLiters >= 2 && waterIntakeLiters < 3) {
                resultMessage += "✅ You're on track! Keep drinking enough water to stay hydrated.";
            } else {
                resultMessage += "🚀 Great hydration! Keep it up for better muscle and brain function!";
            }

            resultText.setText(resultMessage);
        } else {
            resultText.setText("❌ Please enter a valid weight.");
        }
    }
}