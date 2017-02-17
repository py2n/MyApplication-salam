package com.example.mohammad.myapplication.fileUploader_activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MultiUploader {
    private final String boundary;
    private static final String LINE_FEED = "\r\n";
    private HttpURLConnection httpConn;
    private String charset;
    private OutputStream outputStream;
    private PrintWriter writer;
    private final Handler mHandler;
    public boolean canceled = false;

    public static final int MSG_DONE = 1,
            MSG_SEND_ERROR = 2,
            MSG_PROGRESS_MAX = 3,
            MSG_PROGRESS_VALUE = 4;

    public MultiUploader(String requestURL, String charset, Handler H) throws Exception {
        mHandler = H;
        this.charset = charset;

        boundary = "===" + System.currentTimeMillis() + "===";

        URL url = new URL(requestURL);
        try {
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            httpConn.setChunkedStreamingMode(-1);

            outputStream = httpConn.getOutputStream();

            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public void addField(String name, String value) {
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
                .append(LINE_FEED);
        writer.append("Content-Type: text/plain; charset=" + charset).append(
                LINE_FEED);
        writer.append(LINE_FEED);
        writer.append(value).append(LINE_FEED);
        writer.flush();
    }

    public void addFile(String fieldName, File uploadFile) throws Exception {
        String fileName = uploadFile.getName();
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append(
                "Content-Disposition: form-data; name=\"" + fieldName
                        + "\"; filename=\"" + fileName + "\"")
                .append(LINE_FEED);
        writer.append(
                "Content-Type: "
                        + URLConnection.guessContentTypeFromName(fileName))
                .append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();

        FileInputStream inputStream = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        int max = (int) (uploadFile.length() / 1024);

        Message Msg = Message.obtain();
        Bundle B = new Bundle();

        B.putInt("Value", max);
        Msg.what = MSG_PROGRESS_MAX;
        Msg.setData(B);
        mHandler.sendMessage(Msg);

        canceled = false;
        int progress = 0;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            if (canceled) {
                B.clear();
                B.putString("Msg", "Error : Upload Canceled by user !");
                Msg = Message.obtain();
                Msg.what = MSG_SEND_ERROR;
                Msg.setData(B);
                mHandler.sendMessage(Msg);

                break;
            } else {
                outputStream.write(buffer, 0, bytesRead);
                outputStream.flush();

                progress = progress + (bytesRead / 1024);
                B.clear();
                B.putInt("Value", progress);
                Msg = Message.obtain();
                Msg.what = MSG_PROGRESS_VALUE;
                Msg.setData(B);
                mHandler.sendMessage(Msg);
            }
        }
        outputStream.flush();
        inputStream.close();

        Log.d("Uploading", "OutputStream Flush");

        writer.append(LINE_FEED);
        writer.flush();

        Log.d("Uploading", "Writer Flush");
    }

    public void addHeader(String name, String value) {
        writer.append(name + ": " + value).append(LINE_FEED);
        writer.flush();
    }

    public List<String> finish() {
        List<String> response = new ArrayList<String>();

        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();

        Log.d("Uploading", "Finish");

        try {
            int status = httpConn.getResponseCode();
            Log.d("Uploading", "Get Response");
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        httpConn.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    response.add(line);
                }
                reader.close();
                httpConn.disconnect();
            } else {
                response.add("Server returned non-OK status: " + Integer.toString(status));
            }
        } catch (Exception e) {
            response.add("Error : " + e.getMessage());
        }

        return response;
    }
}
