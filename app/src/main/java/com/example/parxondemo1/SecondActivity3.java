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

        newArray=new int[]{
                R.id.Exercise_1,R.id.Exercise_2,R.id.Exercise_3,R.id.Exercise_4,R.id.Exercise_5,
                R.id.Exercise_6,R.id.Exercise_7,R.id.Exercise_8,R.id.Exercise_9,R.id.Exercise_10,
                R.id.Exercise_11,R.id.Exercise_12,R.id.Exercise_13,
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.id_privacy){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://parxondemo1.blogspot.com/2024/12/parxondemo1-privacy-policy-page.html "));
            startActivity(intent);


            return true;
        }
        if (id == R.id.id_term){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://parxondemo1.blogspot.com/2024/12/parxondemo1-terms-conditions.html "));
            startActivity(intent);



            return true;
        }
        if (id == R.id.id_rate){

            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));


            }catch(Exception ex){

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));

            }


            return true;
        }
        if (id == R.id.id_more){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Leap+Fitness+Group&hl=en"));
            startActivity(intent);



            return true;
        }
        if (id == R.id.id_share){


            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String sharebody = "The best app for Parkinson's Patients." + "https://play.google.com/store/apps/details?id=com.example.parxondemo1";
            String sharehub = "ParXon";
            myIntent.putExtra(Intent.EXTRA_SUBJECT,sharehub);
            myIntent.putExtra(Intent.EXTRA_TEXT,sharebody);
            startActivity(Intent.createChooser(myIntent, "share using"));



            return true;
        }
        return true;
    }
    public void Imagebuttonclick(View view) {


        for (int i=0;i< newArray.length;i++){

            if(view.getId() == newArray[i]){
                int value = i+1;
                Log.i("FIRST", String.valueOf(value));
                Intent intent = new Intent(SecondActivity3.this,ThirdActivity3.class);
                intent.putExtra("value",String.valueOf(value));
                startActivity(intent);
            }
        }
    }
}