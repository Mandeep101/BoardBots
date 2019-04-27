package com.teddingtonsch.boardbots;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class StatsActivity extends AppCompatActivity {

    TextView STATS;
    Socket sock;
    SharedPreferences SP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        STATS = findViewById(R.id.Statistics);
        SP = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        GetStats GS = new GetStats();
        GS.execute();
    }



    class GetStats extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                sock = new Socket(SP.getString("IP","boardbots.zapto.org"),Integer.parseInt(SP.getString("Port","8081")));
                BufferedReader din = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                DataOutputStream dout = new DataOutputStream(sock.getOutputStream());
                dout.writeUTF("Stats" + ":" + SP.getString("PlayerID",""));
                dout.flush();
                String Message = din.readLine();
                STATS.setText(Message);

            }catch (Exception e){}
            try{sock.close();}catch (Exception e){}
            return null;
        }
    }


}
