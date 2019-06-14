package com.example.simulatorcontrol;

import java.io.Serializable;

public class CommandClient implements Serializable {

    String ip;
    int port;

    CommandClient(String ip, int port){

        this.ip = ip;
        this.port = port;

    }

    public void sendMessage(){

        System.out.println("ip: "+ ip + " ,port: " + port);


    }


}
