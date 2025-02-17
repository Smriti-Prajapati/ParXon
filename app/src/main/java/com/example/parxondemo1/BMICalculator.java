package com.example.parxondemo1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BMICalculator extends AppCompatActivity {

    EditText weightInput, heightInput;
    Button calculateButton;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_bmi);

        weightInput = findViewById(R.id.weightInput);
        heightInput = findViewById(R.id.heightInput);
        calculateButton = findViewById(R.id.calculateButton);
        resultText = findViewById(R.id.resultText);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
    }

    private void calculateBMI() {
        String weightStr = weightInput.getText().toString();
        String heightStr = heightInput.getText().toString();

        if (!weightStr.isEmpty() && !heightStr.isEmpty()) {
            float weight = Float.parseFloat(weightStr);
            float height = Float.parseFloat(heightStr) / 100; // Convert cm to meters

            float bmi = weight / (height * height);

            String bmiResult = "Your BMI: " + String.format("%.2f", bmi) + "\n";

            if (bmi < 18.5) {
                bmiResult += "⚠ Underweight - Monitor your diet!";
            } else if (bmi >= 18.5 && bmi < 24.9) {
                bmiResult += "✅ Normal weight - Keep maintaining!";
            } else if (bmi >= 25 && bmi < 29.9) {
                bmiResult += "⚠ Overweight - Consider healthy eating & exercise.";
            } else {
                bmiResult += "❗ Obese - Consult a doctor for weight management.";
            }

            resultText.setText(bmiResult);
        } else {
            resultText.setText("❌ Please enter valid weight and height.");
        }
    }
}