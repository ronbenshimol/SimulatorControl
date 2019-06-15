package com.example.simulatorcontrol;

import java.io.Serializable;

public class CommandClient implements Serializable {

    private Client tcp;
    private final static String elevatorPath = "set controls/flight/elevator ";
    private final static String aileronPath = "set controls/flight/aileron ";
    private final static String lineEnding = "\r\n";

    CommandClient(String ip, int port){
        tcp = new Client(ip, port);
    }

    public void connect() {
        tcp.openConnection();
    }

    public void setElevator(float elevator) {
        StringBuilder s = new StringBuilder();
        s.append(elevatorPath).append(elevator).append(lineEnding);
        String message = s.toString();
        tcp.send(message);
    }

    public void setAileron(float aileron) {
        StringBuilder s = new StringBuilder();
        s.append(aileronPath).append(aileron).append(lineEnding);
        String message = s.toString();
        tcp.send(message);
    }

    public void close() {
        tcp.stopClient();
    }

}

