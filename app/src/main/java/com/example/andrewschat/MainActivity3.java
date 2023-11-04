package com.example.andrewschat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.net.Socket;

public class MainActivity3 extends AppCompatActivity {
Button reg;
EditText editTextname;
EditText password;
TextView regrez;
Button hosts;
String host;
int port;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Bundle bundle=getIntent().getExtras();
        regrez=findViewById(R.id.textView3);

          host = bundle.getSerializable("host1").toString();
        System.out.println(bundle.getSerializable("port1"));
         port = (int) bundle.getSerializable("port1");

        if(!(boolean) bundle.getSerializable("reg_rez")){
            regrez.setText("этот логин уже занят");
        }

        editTextname=findViewById(R.id.editTextTextEmailAddress);
        password=findViewById(R.id.editTextTextPassword2);

        reg=findViewById(R.id.button3);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),MainActivity2.class);
                intent.putExtra("mode","r");
                intent.putExtra("login",editTextname.getText().toString());
                intent.putExtra("password",password.getText().toString());
                intent.putExtra("host1",(Serializable) host);
                intent.putExtra("port1",(Serializable)port);
                startActivity(intent);
            }

        });
    }
}