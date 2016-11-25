package com.example.mohammad.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mohammad.myapplication.http_manager.HttpManager;
import com.example.mohammad.myapplication.http_manager.RequestPackage;

public class video extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Button playVideo = (Button) findViewById(R.id.playVideo);
        Button stopVideo = (Button) findViewById(R.id.stopVideo);

        assert playVideo != null;
        playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request = "play";
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://192.168.1.50:55000/poem");
                p.setParam("noun1", request);
                video.MyTask task = new video.MyTask();
                task.execute(p);
            }
        });

        assert stopVideo != null;
        stopVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request = "stop";
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://192.168.1.50:55000/poem");
                p.setParam("noun1", request);
                video.MyTask task = new video.MyTask();
                task.execute(p);
            }
        });
    }

    protected class MyTask extends AsyncTask<RequestPackage, String, String> {
        @Override
        protected void onPreExecute() {
//            tasks.add(this);
        }

        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }
}
