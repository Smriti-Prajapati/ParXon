package com.example.parxondemo1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;

public class GameActivity extends AppCompatActivity {

    ListView listView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String[] tstory = getResources().getStringArray(R.array.title1_story);
        final String[] dstory = getResources().getStringArray(R.array.details_story1_links);



        listView=findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.row,R.id.rowtxt,tstory);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String t = dstory[position];
                Intent intent =new Intent(GameActivity.this,GameActivityDetails.class);
                intent.putExtra("story",t);
                startActivity(intent);
            }
        });

    }

    public void gamegoback(View view) {

        Intent intent = new Intent(GameActivity.this,MainActivity.class);
        startActivity(intent);
        finish();




    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(GameActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

    }
}