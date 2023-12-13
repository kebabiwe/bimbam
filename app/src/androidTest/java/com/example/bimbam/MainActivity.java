package com.example.bimbam;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity1);

        // Assuming your ImageView is in the activity_main.xml layout
        ImageView image = findViewById(R.id.image);

        // Now you can work with the 'image' ImageView
    }
}
