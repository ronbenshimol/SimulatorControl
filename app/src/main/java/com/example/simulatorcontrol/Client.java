package com.example.simulatorcontrol;

import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static final String TAG = Client.class.getSimpleName();

    // used to send messages
    private PrintWriter mBufferOut;
    private InetAddress serverIP;
    private int port;
    private Socket socket = null;


    public Client(String IP, int port) {
        try {
            this.serverIP = InetAddress.getByName(IP);
        } catch (UnknownHostException e) {
            e.getCause();
        }
      this.port = port;
    }
    public void openConnection(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket == null) {
                    try {
                        socket = new Socket(serverIP, port);
                    } catch (IOException e) {
                        e.getMessage();
                    }
                }
            }
        });
        thread.start();
    }
    public void send(final String message) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mBufferOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                            socket.getOutputStream())), true);
                    Log.d(TAG, "Sending: " + message);
                    mBufferOut.println(message);
                    mBufferOut.flush();

                }catch (Exception e){
                    Log.e("TCP", "S: Error", e);
                }
            }
        });
        thread.start();
    }
    public void stopClient() {

        if (mBufferOut != null) {
            mBufferOut.flush();
            mBufferOut.close();
        }
        try {
            socket.close();
        }catch (Exception e){
            e.getMessage();
        }
        mBufferOut = null;
    }

}