package com.example.bimbam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;

public class Registration1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity1);

        // Assuming your ImageView is in the activity_main1.xml layout
        ImageView image = findViewById(R.id.image);

        // Now you can work with the 'image' ImageView

        // Button click event
        RelativeLayout relativeLayout = findViewById(R.id.button);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration1.this, Confirmation.class);
                startActivity(intent);
            }
        });
    }
}