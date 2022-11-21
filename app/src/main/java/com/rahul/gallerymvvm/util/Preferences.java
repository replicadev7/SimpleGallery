package com.rahul.gallerymvvm.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static Preferences instance;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    private Preferences(Context context) {
        SharedPreferences sharedPreferences2 = context.getSharedPreferences("my_data", 0);
        this.sharedPreferences = sharedPreferences2;
        this.editor = sharedPreferences2.edit();
    }

    public static Preferences getInstance(Context context) {
        if (instance == null) {
            instance = new Preferences(context);
        }
        return instance;
    }

    public static boolean getIsPalayVideo(Context context, String str) {
        SharedPreferences preferanse = getPreferanse(context);
        return preferanse.getBoolean("isplay" + str, false);
    }

    public static SharedPreferences getPreferanse(Context context) {
        return context.getSharedPreferences("VIDEOPLAYERS", 0);
    }

    public void putBoolean(String str, boolean z) {
        this.editor.putBoolean(str, z);
        this.editor.commit();

    }
}
