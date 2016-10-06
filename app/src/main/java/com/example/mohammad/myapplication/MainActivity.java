package com.example.mohammad.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohammad.myapplication.connectToServer.activity.CommunicationServiceWear;
import com.example.mohammad.myapplication.connectToServer.activity.ComputerConnectionActivity;
import com.example.mohammad.myapplication.connectToServer.activity.ComputerCreationActivity;
import com.example.mohammad.myapplication.connectToServer.activity.ComputersActivity;
import com.example.mohammad.myapplication.connectToServer.activity.RequirementsActivity;
import com.example.mohammad.myapplication.connectToServer.activity.SlideShowActivity;
import com.example.mohammad.myapplication.http_manager.HttpManager;
import com.example.mohammad.myapplication.http_manager.RequestPackage;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.util.concurrent.Future;


public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    Future<File> downloading;
    ImageView imageView;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        imageView= (ImageView) findViewById(R.id.imageView);
        Ion.getDefault(this).configure().setLogging("گیرنده تصاویر", Log.DEBUG);
        Button button= (Button) findViewById(R.id.button);
        Button button2= (Button) findViewById(R.id.button2);
        Button reboot= (Button) findViewById(R.id.reboot);
        Button turnOffDisplay= (Button) findViewById(R.id.turndo);
        Button turnOnDisplay= (Button) findViewById(R.id.displayon);
        Button shutdown= (Button) findViewById(R.id.shutdown);
        Button slide= (Button) findViewById(R.id.slide);
        assert button2 != null;
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = "deleteAll";
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://192.168.1.50:55000/poem");
                p.setParam("noun1", name);
                MyTask task = new MyTask();
                task.execute(p);
            }
        });

        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText= (EditText) findViewById(R.id.editText);
                name = String.valueOf(editText.getText());
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://192.168.1.50:55000/poem");
                p.setParam("noun1", name);
                getImages task = new getImages();
                task.execute(p);
            }
        });

        assert reboot != null;
        reboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request="reboot";
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://192.168.1.50:55000/poem");
                p.setParam("noun1", request);
                MyTask task = new MyTask();
                task.execute(p);
            }
        });



        assert turnOffDisplay != null;
        turnOffDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request="displayoff";
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://192.168.1.50:55000/poem");
                p.setParam("noun1", request);
                MyTask task = new MyTask();
                task.execute(p);
            }
        });


        assert turnOnDisplay != null;
        turnOnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request="displayon";
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://192.168.1.50:55000/poem");
                p.setParam("noun1", request);
                MyTask task = new MyTask();
                task.execute(p);
            }
        });


        assert shutdown != null;
        shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request="shutdown";
                RequestPackage p = new RequestPackage();
                p.setMethod("POST");
                p.setUri("http://192.168.1.50:55000/poem");
                p.setParam("noun1", request);
                MyTask task = new MyTask();
                task.execute(p);
            }
        });

        slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ComputersActivity.class);
                startActivity(intent);
            }
        });

        final Button upload= (Button) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,uploader.class);
                startActivity(intent);
            }
        });
    }


    protected class getImages extends AsyncTask<RequestPackage, String, String> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
//            tasks.add(this);
        }
        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);
//            if (result.contains("تصویر")) {
//                Toast.makeText(getApplicationContext(), "تصویر از تابلو با موفقیت گرفته شد", Toast.LENGTH_SHORT).show();
//                textView.setText("تصویر از تابلو با موفقیت گرفته شد");
                Downloader("http://192.168.1.50/1.jpg");

                Ion.getDefault(getApplicationContext()).with(getApplicationContext())
                        .load("http://192.168.1.50/"+name+".jpg")
                        .noCache()
                        .withBitmap()
                        .intoImageView(imageView);
            }
//            else
//                Toast.makeText(getApplicationContext(),"امکان گرفتن تصویر از تابلو وجود ندارد",Toast.LENGTH_SHORT).show();
//                textView.setText("ارتباط با سرویس دهنده برقرار نیست");
//        }
    }




    protected class MyTask extends AsyncTask<RequestPackage, String, String> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
//            tasks.add(this);
        }
        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            return content;
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);

        }
    }





//    @Override
//    protected void onPause() {
//        super.onPause();
//        finish();
//        System.exit(1);
//    }

    void Downloader(String uri){
        if (downloading != null && !downloading.isCancelled()) {
            resetDownload();
            return;
        }
        Ion.getDefault(getApplication()).cancelAll();
        downloading = Ion.with(getApplicationContext())
                .load(uri)
                .noCache()
                .write(new File("/storage/emulated/0/"+uri.replace("http://192.168.1.114/","")))
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File result) {
                        resetDownload();
                        if (e != null) {
                            Toast.makeText(getApplicationContext(), "اشکال در ارتباط با سرویس دهنده", Toast.LENGTH_LONG).show();
                            System.err.println(e);
                            return;
                        }
                        else
                            Toast.makeText(getApplicationContext(), "تصاویر با موفقیت دریافت شدند", Toast.LENGTH_LONG).show();
                    }
                });
    }

    void resetDownload() {
        // cancel any pending download
        downloading.cancel(true);
        downloading = null;
        // reset the ui
//        download.setText("Download");
//        downloadCount.setText(null);
//        progressBar.setProgress(0);
    }
}
