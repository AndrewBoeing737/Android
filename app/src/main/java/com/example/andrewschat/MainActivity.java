package com.example.andrewschat;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements Runnable{
Button login;
Button registrate;
Button floatingaction;
String message;
String hostQR;
boolean flagchat;
boolean flaglin;
EditText editTextname;
EditText password;
String host;
int port;
EditText hostname;
EditText pas;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        host="4.tcp.eu.ngrok.io";
        port=11426;
         hostname=findViewById(R.id.editTextTextPersonName3);
         pas=findViewById(R.id.editTextNumber);
         message="";
        floatingaction=findViewById(R.id.button5);
        registrate=findViewById(R.id.button4);
        editTextname=findViewById(R.id.editTextTextPersonName);
        password=findViewById(R.id.editTextTextPassword);
        flagchat=false;
        flaglin=false;
        login=findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),MainActivity2.class);
                intent.putExtra("mode","e");
                intent.putExtra("login",editTextname.getText().toString());
                intent.putExtra("password",password.getText().toString());
                EditText hostname=findViewById(R.id.editTextTextPersonName3);
                host=hostname.getText().toString();
                EditText pas=findViewById(R.id.editTextNumber);
                String value=pas.getText().toString();
                port=Integer.parseInt(value);
                intent.putExtra("host1",(Serializable) host);
                intent.putExtra("port1",(Serializable)port);
                startActivity(intent);
            }
        });
        floatingaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

                    startActivityForResult(intent, 0);



                } catch (Exception e) {

                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                    startActivity(marketIntent);

                }
                if (findViewById(R.id.editTextTextPersonName3).getVisibility()==View.VISIBLE){
                    host=hostname.getText().toString();
                    String value=pas.getText().toString();
                    port=Integer.parseInt(pas.getText().toString());
                    hostname.setVisibility(View.INVISIBLE);
                    pas.setVisibility(View.INVISIBLE);
                    host=hostQR;
                    hostname.setText(host);
                }
                if (findViewById(R.id.editTextTextPersonName3).getVisibility()==View.INVISIBLE){
                    hostname.setVisibility(View.VISIBLE);
                    pas.setVisibility(View.VISIBLE);

                }
                host=hostQR;
                hostname.setText(host);

            }
        });

        registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity3.class);
                intent.putExtra("reg_rez",true);
                intent.putExtra("host1",(Serializable) host);
                intent.putExtra("port1",(Serializable)port);
                startActivity(intent);
            }
        });
    }


    @Override
    public void run() {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                hostQR= data.getStringExtra("SCAN_RESULT");
                System.out.println(hostQR);
                String [] result=hostQR.split(":");
                hostname.setText(result[0]);
                pas.setText(result[1]);
                host=result[0];
                port=Integer.parseInt(result[1]);
            }
            if(resultCode == RESULT_CANCELED){
                hostQR="ошибка";
            }
        }

}
}



