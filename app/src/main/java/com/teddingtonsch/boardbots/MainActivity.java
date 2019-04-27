package com.teddingtonsch.boardbots;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RotateAnimation animation = new RotateAnimation(-90,0, Animation.RELATIVE_TO_SELF, 0.2f, Animation.RELATIVE_TO_SELF, 0.4f);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(4000);
        animation.setRepeatMode(animation.REVERSE);
        ImageView ARM = findViewById(R.id.MainARM);
        ARM.setAnimation(animation);
        ARM.startAnimation(animation);

        Button MedalBtn = findViewById(R.id.statsBtn);
        MedalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StatsActivity.class));
            }
        });

        Button SettingBtn = findViewById(R.id.optionsBtn);
        SettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OptionsActivity.class));
            }
        });

        Button PlayBtn = findViewById(R.id.playBtn);
        PlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, QueueActivity.class));
            }
        });
    }
}
