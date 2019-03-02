package com.example.smarthomedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tvTemp;
    private TextView tvHumi;
    private Button btn;
    private boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        tvTemp = (TextView) findViewById(R.id.tvTemp);
        tvHumi = (TextView) findViewById(R.id.tvHumi);
        btn = (Button) findViewById(R.id.btn);

        state = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                while(state){
                    try{
                        RequestBody requestBody = new FormBody.Builder()
                                .add("id", "12345678")
                                .build();
                        Request request = new Request.Builder()
                                .url("http://192.168.1.105:8080/SmartHomeDemo/AppRequestData_servlet")
                                .post(requestBody)
                                .build();
                        Response response = client.newCall(request).execute();
                        String st = response.body().string();
                        if(!st.isEmpty() && st != "")
                            getData(st);
                        Thread.sleep(2000);
                    }catch (IOException e) {
                        Log.d("test", "获取数据失败");
                    }catch (InterruptedException e){

                    }
                }
            }
        }).start();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            OkHttpClient client = new OkHttpClient();
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("id", "12345678")
                                    .add("flag", "1")
                                    .build();
                            Request request = new Request.Builder()
                                    .url("http://192.168.1.105:8080/SmartHomeDemo/AppControl_servlet")
                                    .post(requestBody)
                                    .build();
                            Response response = client.newCall(request).execute();
                            //String st = response.body().string();
                        }catch(IOException e){
                            Log.d("test", "控制设备失败");
                        }
                    }
                }).start();
            }
        });
    }

    private void getData(final String st){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                SensorData sensorData = gson.fromJson(st, SensorData.class);
                tvHumi.setText(String.valueOf(sensorData.getHumi()));
                tvTemp.setText(String.valueOf(sensorData.getTemp()));
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        state = false;
    }
}



