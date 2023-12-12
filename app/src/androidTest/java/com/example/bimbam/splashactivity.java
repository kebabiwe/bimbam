// src/main/java/com/example/yourapp/SplashActivity.java
package com.example.bimbam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class splashactivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 3000; // 3 секунды
    private static final int REPEAT_COUNT = 2; // Количество повторений
    private int repeatCounter = 0; // Счетчик повторений

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repeatCounter++;

        // Выбор макета в зависимости от счетчика повторений
        int layoutResId = (repeatCounter % 2 == 0) ? R.layout.layout : R.layout.layout1;
        setContentView(layoutResId);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Если еще не все повторения выполнены, повторите SplashActivity
                if (repeatCounter < REPEAT_COUNT) {
                    recreate();
                } else {
                    // После завершения повторений, запустите основную активность
                    Intent intent = new Intent(splashactivity.this, MainActivity.class);
                    startActivity(intent);

                    // Закройте SplashActivity, чтобы пользователь не мог вернуться на этот экран
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
