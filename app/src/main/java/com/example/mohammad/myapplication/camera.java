package com.example.mohammad.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mohammad.myapplication.http_manager.HttpManager;
import com.example.mohammad.myapplication.http_manager.RequestPackage;

public class camera extends AppCompatActivity {
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Button takePicture = (Button) findViewById(R.id.button);
        Button recordVideo = (Button) findViewById(R.id.recordVideo);
        Button StopVideoRecord = (Button) findViewById(R.id.StopVideoRecord);

        assert takePicture != null;
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = "salam";
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://192.168.0.50:55000/poem");
                p.setParam("noun1", name);
                camera.MyTask task = new camera.MyTask();
                task.execute(p);
            }
        });

        assert recordVideo != null;
        recordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request = "start_record";
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://192.168.0.50:55000/poem");
                p.setParam("noun1", request);
                camera.MyTask task = new camera.MyTask();
                task.execute(p);
            }
        });


        assert StopVideoRecord != null;
        StopVideoRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request = "stop_record";
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://192.168.0.50:55000/poem");
                p.setParam("noun1", request);
                camera.MyTask task = new camera.MyTask();
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
            System.err.println(result);
            if (result.contains("unreachable")) {
                Toast.makeText(getApplicationContext(), " خطا در اتصال", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "اتصال را بررسی کنید", Toast.LENGTH_LONG).show();
            }
        }
    }
}


