package com.example.simulatorcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connect(View view){

        EditText ipText = (EditText)findViewById(R.id.ipText);
        EditText portText = (EditText)findViewById(R.id.portText);

        String ip = ipText.getText().toString();
        int port = 0;
        try {
            port = Integer.parseInt(portText.getText().toString());
        }catch (Exception e){
            e.getMessage();
        }


        CommandClient client = new CommandClient(ip, port);

        //test client

        Intent intent = new Intent(this,JoystickActivity.class);

        intent.putExtra("CommandClient",client);//temp
        startActivity(intent);
    }


}
