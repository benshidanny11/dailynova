package com.example.dailynova.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class ScreenSizeUtil {

    static DisplayMetrics displayMetrics = new DisplayMetrics();
    public static int getScreenWidth(Activity activity){
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        return width;
    }
    public static int getScreenHeight(Activity activity){
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        return height;
    }
}
