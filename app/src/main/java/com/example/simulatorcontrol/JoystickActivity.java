package com.example.simulatorcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class JoystickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        Intent intent = getIntent();
        CommandClient client = (CommandClient)intent.getSerializableExtra("CommandClient");
        client.connect();

        CirclesDrawingView v = new CirclesDrawingView(this,client);
        setContentView(v);


        int a =5;
    }
}
