package com.example.mohammad.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class uploadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        Button VideoUpload = (Button) findViewById(R.id.button3);
        Button SlideUpload = (Button) findViewById(R.id.button4);

        VideoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(uploadActivity.this, uploadVideo.class);
                startActivity(intent);
            }
        });

        SlideUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(uploadActivity.this, uploader.class);
                startActivity(intent);
            }
        });

    }
}
