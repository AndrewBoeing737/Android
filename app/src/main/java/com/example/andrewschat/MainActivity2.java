package com.example.andrewschat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MainActivity2 extends AppCompatActivity implements Runnable {
LinearLayout line;
EditText message_up;
Button send;
String message;
BufferedWriter out;
BufferedReader in;
Socket soc;
public String host;
public int port;
InputStream inputs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle bundle=getIntent().getExtras();
        line=findViewById(R.id.linear);
        message=bundle.getSerializable("mode")+"><"+bundle.getSerializable("login").toString()+"><"+bundle.getSerializable("password").toString();
        host=bundle.getSerializable("host1").toString();
        System.out.println(bundle.getSerializable("port1"));
        port= (int) bundle.getSerializable("port1");
        new Thread(this::run).start();
        message_up=findViewById(R.id.editTextTextPersonName2);
        send=findViewById(R.id.button2);
        send.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new Thread(this::Send).start();
        }

            private void Send() {
                try {
                    message=message_up.getText().toString();
                    out.write(message);
                    out.flush();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            message_up.setText("");
                            New_message_received("Me : "+message);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public void run() {
        soc = new Socket();
        InetSocketAddress inetAddress = new InetSocketAddress(host ,port);
        try {
            soc.connect(inetAddress, 25000);

        if (soc.isConnected()) {
            System.out.println("1");
        }
            out = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            out.write(message);
            out.flush();
        inputs = soc.getInputStream();
        while(true){
        byte buf_out[] = new byte[100];
        int size = 0;
        size = inputs.read(buf_out);
        message = (new String(buf_out, 0, buf_out.length));
            System.out.println(message);
        if (message.contains("YOURAPPLE")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("host1",(Serializable) host);
            intent.putExtra("port1",(Serializable)port);
            startActivity(intent);
        }
        if(message.contains("loginzanyat")){
            Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
            intent.putExtra("host1",(Serializable) host);
            intent.putExtra("port1",(Serializable)port);
            intent.putExtra("reg_rez",false);
            startActivity(intent);
        }
        runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    New_message_received( message);
                }
            });

        }
    }catch (IOException e) {
        e.printStackTrace();
    }
    }
    public void New_message_received(String message){
        ScrollView sc=findViewById(R.id.Scroll);
        TextView textView=new TextView(this);
        textView.setText(message);
        line.addView(textView);
        sc.fullScroll(View.FOCUS_DOWN);
    }

   }