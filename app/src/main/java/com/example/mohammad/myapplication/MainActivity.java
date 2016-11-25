package com.example.mohammad.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mohammad.myapplication.connectToServer.activity.ComputersActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button slide= (Button) findViewById(R.id.slide);
        Button management = (Button) findViewById(R.id.Management);
        Button ClassManagement = (Button) findViewById(R.id.classManagement);
        Button camera = (Button) findViewById(R.id.camera);
        final Button video = (Button) findViewById(R.id.video);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, camera.class);
                startActivity(intent);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, video.class);
                startActivity(intent);
            }
        });

        management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, management.class);
                startActivity(intent);
            }
        });


        ClassManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, classManagement.class);
                startActivity(intent);
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
                Intent intent = new Intent(MainActivity.this, uploadActivity.class);
                startActivity(intent);
            }
        });
    }
    }
