package com.example.mohammad.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mohammad.myapplication.http_manager.HttpManager;
import com.example.mohammad.myapplication.http_manager.RequestPackage;

public class management extends AppCompatActivity {
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        Button reboot = (Button) findViewById(R.id.reboot);
//        Button turnOffDisplay= (Button) findViewById(R.id.turndo);
//        Button turnOnDisplay= (Button) findViewById(R.id.displayon);
        Button shutdown = (Button) findViewById(R.id.shutdown);
        Button deleteAll = (Button) findViewById(R.id.button2);


        reboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request = "reboot";
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://192.168.0.50:55000/poem");
                p.setParam("noun1", request);
                management.MyTask task = new management.MyTask();
                task.execute(p);
            }
        });

//        assert turnOffDisplay != null;
//        turnOffDisplay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String request="displayoff";
//                RequestPackage p = new RequestPackage();
//                p.setMethod("POST");
//                p.setUri("http://192.168.1.50:55000/poem");
//                p.setParam("noun1", request);
//                MyTask task = new MyTask();
//                task.execute(p);
//            }
//        });
//
//
//        assert turnOnDisplay != null;
//        turnOnDisplay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String request="displayon";
//                RequestPackage p = new RequestPackage();
//                p.setMethod("POST");
//                p.setUri("http://192.168.1.50:55000/poem");
//                p.setParam("noun1", request);
//                MyTask task = new MyTask();
//                task.execute(p);
//            }
//        });
//

        assert shutdown != null;
        shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request = "shutdown";
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://192.168.0.50:55000/poem");
                p.setParam("noun1", request);
                management.MyTask task = new management.MyTask();
                task.execute(p);
            }
        });

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = "deleteAll";
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://192.168.0.50:55000/poem");
                p.setParam("noun1", name);
                management.MyTask task = new management.MyTask();
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
