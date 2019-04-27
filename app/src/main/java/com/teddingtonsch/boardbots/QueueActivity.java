package com.teddingtonsch.boardbots;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class QueueActivity extends AppCompatActivity {

    CheckBox SGame;
    CheckBox LGame;
    CheckBox MGame;

    TextView SText;
    TextView MText;
    TextView LText;

    Socket sock;

    SharedPreferences SP;

    TextView QTime;

    int seconds = 0;
    boolean startRun = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        SP = getSharedPreferences("Pref", Context.MODE_PRIVATE);

        SGame = findViewById(R.id.shortGame);
        MGame = findViewById(R.id.medGame);
        LGame = findViewById(R.id.longGame);

        SText = findViewById(R.id.ShortText);
        MText = findViewById(R.id.MedText);
        LText = findViewById(R.id.LongText);

        SText.setText(R.string.ShortDesc);
        MText.setText(R.string.MedDesc);
        LText.setText(R.string.LongDesc);

        seconds = 0;
        startRun = false;
        Timer();
    }

    public void ToMenu(View V){
        try{sock.close();}catch(Exception e){}
        startActivity(new Intent(QueueActivity.this, MainActivity.class));
        finish();
    }

    public void Queue(View V){
        try{sock.close();}catch(Exception e){}
        ServerConn Conn = new ServerConn();
        String[] Strings = new String[3];
        Strings[0] = null;
        Strings[1] = null;
        Strings[2] = null;
        if (SGame.isChecked()){
            Strings[0] = "Queue1";
        }
        if (MGame.isChecked()) {
            Strings[1] = "Queue2";
        }
        if (LGame.isChecked()){
            Strings[2] = "Queue3";
        }
        Conn.execute(Strings[0],Strings[1],Strings[2]);
        startRun = true;
    }

    class ServerConn extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... strings) {
            try{sock.close();}catch(Exception e){}
            try{
                sock = new Socket(SP.getString("IP","boardbots.zapto.org"),Integer.parseInt(SP.getString("Port","8081")));
                BufferedReader din = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                DataOutputStream dout = new DataOutputStream(sock.getOutputStream());
                dout.writeUTF("" + strings[0] + strings[1] + strings[2] + " :" + SP.getString("PlayerID",""));
                dout.flush();
                while(true){
                    String Message = din.readLine();
                    if (Message.contains("Short")){
                        startActivity(new Intent(QueueActivity.this, ShortGame.class));
                        break;
                    }else if(Message.contains("Medium")){
                        break;
                    }else if(Message.contains("Long")) {
                        break;
                    }}

                din.close();
                dout.close();
                sock.close();
                finish();
                }catch(Exception e){}
            return null;
        }
    }

    private void Timer() {
        QTime = findViewById(R.id.QTime);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format("%d:%02d:%02d", hours, minutes, secs);

                QTime.setText(time);

                if (startRun) {
                    seconds++;
                }

                handler.postDelayed(this, 1000);
            }
        });
    }
}
