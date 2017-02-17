package com.example.mohammad.myapplication.fileUploader_activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mohammad.myapplication.R;
import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;
import java.util.List;


public class mainupload extends AppCompatActivity {

    private final int FILE_SELECT_CODE = 1919;
    private final String TAG = "Uploader";
    private File SelectedFile;
    private UploadTask uploadTask;
    TextView logTxt;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle B = msg.getData();
            ProgressBar pBar = (ProgressBar) findViewById(R.id.progressBar);
            TextView logTxt = (TextView) findViewById(R.id.logText);

            switch (msg.what) {
                case MultiUploader.MSG_PROGRESS_MAX:
                    int max = B.getInt("Value");
                    pBar.setMax(max);
                    pBar.setProgress(0);

                    break;

                case MultiUploader.MSG_PROGRESS_VALUE:
                    int progress = B.getInt("Value");
                    pBar.setProgress(progress);

                    break;

                case MultiUploader.MSG_SEND_ERROR:
                    String eMsg = B.getString("Msg");
                    logTxt.setText(eMsg);
                    break;

                case MultiUploader.MSG_DONE:
                    String[] Msg = B.getStringArray("Msg");
                    for (int i = 0; i < Msg.length; i++) {
                        logTxt.setText(logTxt.getText().toString() + "\n" + Msg[i]);
                    }

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fileuploader);

        logTxt = (TextView) findViewById(R.id.logText);


        Button btn = (Button) findViewById(R.id.fileSelectBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent target = FileUtils.createGetContentIntent();
                Intent intent = Intent.createChooser(target, "Select File");
                try {
                    startActivityForResult(intent, FILE_SELECT_CODE);
                } catch (ActivityNotFoundException e) {
                    logTxt.setText("Error : " + e.getMessage());
                }
            }
        });

        btn = (Button) findViewById(R.id.uploadBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String URL = "http://176.9.194.244:8085/uploader";
                    if (SelectedFile == null) {
                        logTxt.setText("Error : No File Selected");
                    } else {
                        uploadTask = new UploadTask();
                        uploadTask.execute(URL);
                    }
                }

        });

        btn = (Button) findViewById(R.id.cancelBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uploadTask != null) {
                    uploadTask.uploader.canceled = true;
                    uploadTask.cancel(true);

                    uploadTask = null;
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        final Uri uri = data.getData();
                        Log.i(TAG, "Uri = " + uri.toString());
                        try {
                            SelectedFile = FileUtils.getFile(this, uri);

                            final String path = FileUtils.getPath(this, uri);
                            TextView txt = (TextView) findViewById(R.id.fileAddrText);
                            txt.setText("File Selected : " + path);
                        } catch (Exception e) {
                            TextView logTxt = (TextView) findViewById(R.id.logText);
                            logTxt.setText("Error : " + e.getMessage());
                        }
                    }
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private class UploadTask extends AsyncTask<String, Void, String> {
        ProgressBar pBar;

        Button btn;
        public MultiUploader uploader;
        Message Msg;
        Bundle B;

        @Override
        protected String doInBackground(String... params) {
            try {
                uploader = new MultiUploader(params[0], "UTF-8", mHandler);

                uploader.addFile("file1", SelectedFile);

                if (isCancelled()) {
                    return null;
                }

                List<String> Response = uploader.finish();

                B = new Bundle();
                B.clear();
                B.putStringArray("Msg", Response.toArray(new String[0]));

                Message Msg = Message.obtain();
                Msg.what = MultiUploader.MSG_DONE;
                Msg.setData(B);
                mHandler.sendMessage(Msg);

            } catch (Exception e) {
                B = new Bundle();
                B.clear();
                B.putString("Msg", e.getMessage());

                Msg = Message.obtain();
                Msg.what = MultiUploader.MSG_SEND_ERROR;
                Msg.setData(B);
                mHandler.sendMessage(Msg);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            btn = (Button) findViewById(R.id.cancelBtn);
            btn.setEnabled(false);

            btn = (Button) findViewById(R.id.fileSelectBtn);
            btn.setEnabled(true);

            btn = (Button) findViewById(R.id.uploadBtn);
            btn.setEnabled(true);

            uploader = null;
        }

        @Override
        protected void onCancelled() {
            btn = (Button) findViewById(R.id.cancelBtn);
            btn.setEnabled(false);

            btn = (Button) findViewById(R.id.fileSelectBtn);
            btn.setEnabled(true);

            btn = (Button) findViewById(R.id.uploadBtn);
            btn.setEnabled(true);

            uploader = null;
        }

        @Override
        protected void onPreExecute() {
            pBar = (ProgressBar) findViewById(R.id.progressBar);
            pBar.setProgress(0);

            btn = (Button) findViewById(R.id.cancelBtn);
            btn.setEnabled(true);

            btn = (Button) findViewById(R.id.fileSelectBtn);
            btn.setEnabled(false);

            btn = (Button) findViewById(R.id.uploadBtn);
            btn.setEnabled(false);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
