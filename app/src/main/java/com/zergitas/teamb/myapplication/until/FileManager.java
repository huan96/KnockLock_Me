package com.zergitas.teamb.myapplication.until;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Toast;

import com.zergitas.teamb.myapplication.R;

/**
 * Created by huand on 8/10/2017.
 */

public class FileManager {
    public static final String FILE_NAME = "KnockLock";
    public static final String PASSWORD_KEY = "passwordKnock";
    public static final String BACKGROUND_LOCK_SCREEN = "background";
    public static final String IS_LOCK_KEY = "isLock";
    public static final String IS_24H_FORMAT_KEY = "is24hFormat";
    public static final String IS_SHOW_GRID_KEY = "isShowGrid";

    public static void savePassword(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD_KEY, key);
        if (editor.commit()) {
            Toast.makeText(context, "Lưu thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Lưu thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void saveImageBackground(String uri, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(BACKGROUND_LOCK_SCREEN, uri);
        if (editor.commit()) {
            Toast.makeText(context, "Lưu thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Lưu thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void saveLockState(Boolean isLock, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOCK_KEY, isLock);
        editor.commit();

    }

    public static void save24hFormatTimeState(Boolean is24hFormat, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_24H_FORMAT_KEY, is24hFormat);
        editor.commit();

    }

    public static void saveShowGridState(Boolean isShow, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_SHOW_GRID_KEY, isShow);
        editor.commit();

    }

    public static String getPassword(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PASSWORD_KEY, "");
    }

    public static String getUriImageBackground(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(BACKGROUND_LOCK_SCREEN, Uri.parse("android.resource://com.zergitas.teamb.myapplication/" + R.drawable.bg_3).toString());
    }

    public static boolean getLockState(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_LOCK_KEY, false);
    }

    public static boolean get24hFormatTimeState(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_24H_FORMAT_KEY, false);
    }

    public static boolean getShowGrid(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_SHOW_GRID_KEY, false);
    }
}
