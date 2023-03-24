package com.example.reportvehiclecomplaints.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

public class BuildTempFileAsync extends AsyncTask<InputStream, Void, File> {

    private final String mFileName;
    private final OnTempFileCreatedListener mListener;
    @SuppressLint("StaticFieldLeak")
    private final Context ctx;

    public BuildTempFileAsync(Context ctx, String fileName, OnTempFileCreatedListener listener) {
        this.mFileName = fileName;
        this.mListener = listener;
        this.ctx = ctx;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected File doInBackground(InputStream... params) {
        try (InputStream in = params[0]) {

            File tempFile = Tools.getTempFile(ctx, mFileName);
            try (OutputStream out = Files.newOutputStream(tempFile.toPath())) {

                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } catch (Exception e) {
                Log.e("ErrorMessage", e.getMessage());
            }

            return tempFile;
        } catch (IOException e) {
            Log.e("ERROR", e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(File s) {
        super.onPostExecute(s);
        if (mListener != null) {
            mListener.onTempFileCreated(s, mFileName);
        }
    }

    public interface OnTempFileCreatedListener {
        void onTempFileCreated(File file, String name);
    }

}