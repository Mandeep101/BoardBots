package com.teddingtonsch.boardbots;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SplashActivity extends AppCompatActivity {

    Socket sock;
    TextView Status;
    SharedPreferences SP;
    ImageView Logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Logo = findViewById(R.id.Arm);
        RotateAnimation animation = new RotateAnimation(-90,0,Animation.RELATIVE_TO_SELF, 0.2f, Animation.RELATIVE_TO_SELF, 0.4f);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(4000);
        animation.setRepeatMode(animation.REVERSE);
        Logo.setAnimation(animation);
        Logo.startAnimation(animation);

        SP = getSharedPreferences("Pref",MODE_PRIVATE);
        Status = findViewById(R.id.StatusText);

        Status.setText("Initiating, Please Wait...");

        if (SP.getString("PlayerID","") != ""){
            Status.setText("PlayerID found, attempting to connect to server..");
            String IP = SP.getString("IP", "boardbots.zapto.org");
            String Port = SP.getString("Port","8081");
            String ID = SP.getString("PlayerID"," ");
            myTask mt = new myTask();
            mt.execute(IP,Port,ID);
        }else {
            startActivity(new Intent(SplashActivity.this, FirstLauncher.class));
            finish();
        }
    }

    public void ToOptions(View V){
        try{sock.close();}catch(Exception e){}
        startActivity(new Intent(SplashActivity.this, OptionsActivity.class));
        finish();
    }


    class myTask extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... Strings) {
            try{sock.close();}catch(Exception e){}
            int counter = 4;
            while (counter>=0){
                counter-=1;
                try{
                    sock = new Socket(Strings[0],Integer.parseInt(Strings[1]));
                    BufferedReader din = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    DataOutputStream dout = new DataOutputStream(sock.getOutputStream());
                    dout.writeUTF("Echo" + " :" + Strings[2]);
                    dout.flush();
                    String Message = din.readLine();
                    if (Message.contains("Affirm")){
                        counter = -100;
                        din.close();
                        dout.close();
                        sock.close();
                    }}catch(Exception e){}}
            if (counter == -1){
                Status.setText("Failed To Connect to Server");
                startActivity(new Intent(SplashActivity.this, OptionsActivity.class));
                finish();
            }else{
                Status.setText("Connected to Server");
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();}

            return null;
        }
    }
}
