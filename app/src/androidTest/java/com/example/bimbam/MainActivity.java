package com.example.bimbam;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.content.Intent;



import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity1);

        // Assuming your ImageView is in the activity_main.xml layout
        ImageView image = findViewById(R.id.image);

        public class ВашаАктивность extends AppCompatActivity {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.ваш_макет); // Установка макета для этой активности

                RelativeLayout relativeLayout = findViewById(R.id.вашИдентификаторRelativeLayout);
                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ВашаАктивность.this, Mainactivity1.class);
                        startActivity(intent);
                    }
                });
            }
        }



        // Now you can work with the 'image' ImageView
    }
}
