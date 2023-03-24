package com.example.reportvehiclecomplaints.util;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
public class Tools {
    public static File getTempFile(Context context, String name) {
        return new File(context.getCacheDir(), TextUtils.isEmpty(name) ? Const.FilesName.TEMP_FILE_NAME : name);
    }
}