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

        CommandClient client = new CommandClient("10.0.2.2", 1234);

        //test client

        Intent intent = new Intent(this,JoystickActivity.class);

        intent.putExtra("CommandClient",client);//temp
        startActivity(intent);
    }


}
