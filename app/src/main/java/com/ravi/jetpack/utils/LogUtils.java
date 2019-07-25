package com.ravi.jetpack.utils;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.ravi.jetpack.JetPackApplication;
import com.ravi.jetpack.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

public class LogUtils {

    private static final String TAG = LogUtils.class.getSimpleName();
    private static final long MAX_SIZE = 20 * 1024 * 1024; //20 MB LIMIT

    public static void v(String iTag, String iMessage) {
        if (Constants.DEBUG)
            Log.v(iTag, iMessage);
    }

    public static void d(String iTag, String iMessage) {
        if (Constants.DEBUG)
            Log.d(iTag, iMessage);
    }

    public static void e(String iTag, String iMessage) {
        if (Constants.DEBUG)
            Log.e(iTag, iMessage);
    }

    public static void i(String iTag, String iMessage) {
        if (Constants.DEBUG)
            Log.i(iTag, iMessage);
    }

    public static void appendLog(Bundle dataToLog) {

        if (dataToLog == null || dataToLog.keySet() == null) {
            return;
        }

        StringBuilder stringBuilder = new StringBuilder(1000);
        stringBuilder.append(Constants.NEWLINE)
                .append("=>START<=")
                .append(Constants.NEWLINE)
                .append("TIMESTAMP=>")
                .append(new Date())
                .append(Constants.NEWLINE);


        Set<String> keySet = dataToLog.keySet();

        for (String key : keySet) {
            Object value = dataToLog.get(key);

            if (value != null) {
                stringBuilder.append(key).append("=>")
                        .append(String.valueOf(value))
                        .append(Constants.NEWLINE);
            }
        }

        stringBuilder.append("=>END<=");
        stringBuilder.append(Constants.NEWLINE);
        writeDataToLogFile(stringBuilder.toString(), true);
    }

    public static void appendLog(String dataToLog) {
        try {
            if (TextUtils.isEmpty(dataToLog)) {
                return;
            }

            String stringBuilder = Constants.NEWLINE +
                    "=>START<=" +
                    Constants.NEWLINE +
                    "TIMESTAMP=>" +
                    new Date() +
                    Constants.NEWLINE +
                    "DataToLog" + "=>" +
                    dataToLog +
                    Constants.NEWLINE +
                    "=>END<=" +
                    Constants.NEWLINE;

            writeDataToLogFile(stringBuilder, true);
        } catch (Exception ignored) {
        }
    }

    /**
     *
     * @param iData String to be logged in the file
     * @param iAppend boolean to append the data
     */
    private static void writeDataToLogFile(String iData, boolean iAppend) {

        Context iContext = JetPackApplication.getInstance();

        if (iContext == null || iData == null) {
            return;
        }

        String fileName = iContext.getResources()
                .getString(R.string.app_name)
                .replace(Constants.SPACE, Constants.EMPTY)
                .concat("Logs.txt");

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                fileName);

        d(TAG, "Saving log here==>" + file.getAbsolutePath());

        long fileSize = file.length();

        d(TAG, "File size here ==>" + fileSize);

        FileOutputStream fileOutputStream = null;

        try {
            long dataLength = iData.getBytes().length;

            // Check for data and clear accordingly
            if ((fileSize + dataLength) > MAX_SIZE) {
                boolean deletedFile = file.delete();

                LogUtils.e(TAG, "File deleted: " + deletedFile);
                file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                        fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add new data
        try {
            fileOutputStream = new FileOutputStream(file, iAppend);
            fileOutputStream.write(iData.getBytes());

        } catch (IOException e) {
            //do nothing
            e.printStackTrace();
        } finally {

            if (fileOutputStream != null) {

                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    //do nothing
                }
            }
        }
    }


    /**
     * Writes known exception to the log files
     *
     * @param exception exception to be logged
     */
    public static void logException(Exception exception) {

        try {
            StackTraceElement[] temp = exception.getStackTrace();
            StringBuilder sb = new StringBuilder();
            sb.append("\nTime : " + new Date().toString());
            sb.append("\nException Message: " + exception.getMessage());

            for (int i = 0; i < temp.length; i++) {
                sb.append("\n\nFile Name : " + temp[i].getFileName());
                sb.append("\nClass Name: " + temp[i].getClassName());
                sb.append("\nMethodName: " + temp[i].getMethodName());
                sb.append("\nLineNumber: " + temp[i].getLineNumber());
            }
            sb.append("\n===============================================================================\n");

            // Write data to the log file
            writeDataToLogFile(sb.toString(), true);
        } catch (Exception e) {
            // do nothing
        }
    }

    /**
     * Device details as string
     *
     * @return device details
     */
    public static String getModelDetails() {
        return "Brand: " + Build.BRAND + Constants.NEWLINE +
                "Manufacturer: " + Build.MANUFACTURER + Constants.NEWLINE +
                "Model: " + Build.MODEL + Constants.NEWLINE +
                "Product: " + Build.PRODUCT;
    }

}
