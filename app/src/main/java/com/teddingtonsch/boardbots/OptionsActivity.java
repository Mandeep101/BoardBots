package com.teddingtonsch.boardbots;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class OptionsActivity extends AppCompatActivity {

    SharedPreferences SP;
    EditText PlayerID;
    EditText IP;
    EditText Port;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        PlayerID = findViewById(R.id.EditID);
        Port = findViewById(R.id.EditPort);
        IP = findViewById(R.id.EditIP);
        SP = getSharedPreferences("Pref", Context.MODE_PRIVATE);
        PlayerID.setText(SP.getString("PlayerID",""));

    }

    public void ToMenu(View V){
        startActivity(new Intent(OptionsActivity.this, MainActivity.class));
        finish();
    }

    public void PlayIDChange(View V){
        SP.edit().putString("PlayerID", PlayerID.getText().toString()).commit();
    }

    public void IPChange(View V){
        SP.edit().putString("IP", IP.getText().toString()).commit();//TO CHANGE
    }

    public void PortChange(View V){
        SP.edit().putString("Port",Port.getText().toString()).commit();//TO CHANGE
    }

    public void Clear(View v) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        SP.edit().clear().commit();
                        startActivity(new Intent(OptionsActivity.this, SplashActivity.class));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(OptionsActivity.this);
        builder.setMessage("Are you sure you want to erase all data?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
    }

}
