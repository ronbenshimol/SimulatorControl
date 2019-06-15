package com.example.simulatorcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class JoystickActivity extends AppCompatActivity {

    private CommandClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        Intent intent = getIntent();
        client = (CommandClient)intent.getSerializableExtra("CommandClient");
        client.connect();

        CirclesDrawingView v = new CirclesDrawingView(this, client);
        setContentView(v);
    }

    protected void onDestroy() {
        super.onDestroy();
        client.close();
    }


}
