package com.example.smarthomedemo;

public class SensorData {

    private String id;
    private String ip;
    private double humi;
    private double temp;

    public SensorData(String id, String ip, double humi, double temp){
        this.id = id;
        this.ip = ip;
        this.humi = humi;
        this.temp = temp;
    }

    public String getId(){
        return id;
    }

    public String getIp(){
        return ip;
    }

    public double getHumi(){
        return humi;
    }

    public double getTemp(){
        return temp;
    }
}
