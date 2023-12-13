package com.example.bimbam;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;


public class Login extends AppCompatActivity {

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
                Intent intent = new Intent(Login.this, Mainactivity1.class);
                startActivity(intent);
            }
        });
    }
}

