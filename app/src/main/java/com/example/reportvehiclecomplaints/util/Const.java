package com.example.reportvehiclecomplaints.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Const {

    public static final class FilesName {
        public static final String TEMP_FILE_NAME = "temp.fleetify";
    }

    public static String createDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return simpleDateFormat.format(calendar.getTime());
    }
}
