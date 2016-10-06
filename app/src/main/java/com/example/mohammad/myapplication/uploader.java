package com.example.mohammad.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class uploader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploader);
        WebView webView= (WebView) findViewById(R.id.wv);
        webView.loadUrl("http://192.168.1.50:55000/upload");
    }
}
