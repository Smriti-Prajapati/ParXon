package com.example.parxondemo1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CaregiverDashboardActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private ListView reportListView;
    private ProgressBar progressBar;
    private TextView emptyTextView;
    private ArrayAdapter<String> adapter;
    private List<String> reportList;
    private String caregiverUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_dashboard);

        // UI elements
        reportListView = findViewById(R.id.reportListView);
        progressBar = findViewById(R.id.loadingProgressBar);
        emptyTextView = findViewById(R.id.emptyTextView);

        // Adapter setup
        reportList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reportList);
        reportListView.setAdapter(adapter);

        // Firestore
        db = FirebaseFirestore.getInstance();

        // Get caregiver username
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        caregiverUsername = prefs.getString("username", "author");
        Log.d("DEBUG", "Caregiver username: " + caregiverUsername);

        fetchLinkedPatientId();

        // On list item click
        reportListView.setOnItemClickListener((parent, view, position, id) -> {
            String report = reportList.get(position);
            Toast.makeText(this, "Report:\n" + report, Toast.LENGTH_SHORT).show();
        });
    }

    private void fetchLinkedPatientId() {
        progressBar.setVisibility(View.VISIBLE);
        emptyTextView.setVisibility(View.GONE);

        db.collection("users")
                .document(caregiverUsername)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String linkedPatientUid = documentSnapshot.getString("linkedPatientUid");
                        Log.d("DEBUG", "linkedPatientUid: " + linkedPatientUid);

                        if (linkedPatientUid != null && !linkedPatientUid.isEmpty()) {
                            Toast.makeText(this, "Linked Patient: " + linkedPatientUid, Toast.LENGTH_SHORT).show();
                            fetchReports(linkedPatientUid);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            emptyTextView.setText("❗ No patient linked to this caregiver.");
                            emptyTextView.setVisibility(View.VISIBLE);
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        emptyTextView.setText("❗ Caregiver record not found.");
                        emptyTextView.setVisibility(View.VISIBLE);
                    }
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    emptyTextView.setText("❌ Error fetching caregiver info.");
                    emptyTextView.setVisibility(View.VISIBLE);
                    Log.e("Firestore", "Error getting caregiver document", e);
                });
    }

    private void fetchReports(String patientUid) {
        db.collection("users")
                .document(patientUid)
                .collection("reports")
                .get()
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    reportList.clear();

                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Log.d("DEBUG", "Fetched report: " + doc.getData());

                            String date = doc.getString("date");
                            String exercise = doc.getString("exercise");
                            String duration = doc.getString("duration");

                            if (date != null && exercise != null && duration != null) {
                                reportList.add("📅 Date: " + date + "\n🏃 Exercise: " + exercise + "\n⏱️ Duration: " + duration);
                            } else {
                                reportList.add("⚠️ Incomplete report: " + doc.getId());
                            }
                        }

                        adapter.notifyDataSetChanged();

                        if (reportList.isEmpty()) {
                            emptyTextView.setText("ℹ️ No reports found for linked patient.");
                            emptyTextView.setVisibility(View.VISIBLE);
                        } else {
                            emptyTextView.setVisibility(View.GONE);
                        }
                    } else {
                        emptyTextView.setText("❌ Error loading reports.");
                        emptyTextView.setVisibility(View.VISIBLE);
                        Log.e("Firestore", "Error getting reports: ", task.getException());
                    }
                });
    }
}
