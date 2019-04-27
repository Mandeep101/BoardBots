package com.teddingtonsch.boardbots;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;

public class FirstLauncher extends AppCompatActivity {

    EditText PlayerID;
    SharedPreferences SP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_launcher);
        SP = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        PlayerID = findViewById(R.id.PlayerID);

        RotateAnimation animation1 = new RotateAnimation(-270,90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation1.setRepeatCount(Animation.INFINITE);
        animation1.setDuration(4000);
        animation1.setRepeatMode(animation1.REVERSE);
        RotateAnimation animation2 = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation2.setRepeatCount(Animation.INFINITE);
        animation2.setDuration(4000);
        animation2.setRepeatMode(animation2.RESTART);

        ImageView e1 = findViewById(R.id.Eye1);
        ImageView e2 = findViewById(R.id.Eye2);

        e1.setAnimation(animation1);
        e1.startAnimation(animation1);
        e2.setAnimation(animation2);
        e2.startAnimation(animation2);
    }

    public void SetPlayerID(View V){
        SharedPreferences.Editor SPE = SP.edit();
        SPE.putString("PlayerID", PlayerID.getText().toString());
        SPE.commit();
        startActivity(new Intent(FirstLauncher.this, SplashActivity.class));
        finish();
    }

}
