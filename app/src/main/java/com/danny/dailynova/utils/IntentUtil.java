package com.danny.dailynova.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtil {
    public static Intent getOpenFacebookIntent(Context context) {
         Intent intent=null;
        try {
            intent = context.getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
            //intent.setComponent(new ComponentName("com.instagram.android", "com.instagram.android.activity.UrlHandlerActivity"));
            intent.setData(Uri.parse("https://www.facebook.com"));
            return intent;
        } catch (Exception e) {
           intent= new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com"));
           return intent;
        }
    }
    public static Intent getOpenInstagramIntent(Context context) {
        Intent intent = null;
        try {
            intent = context.getPackageManager().getLaunchIntentForPackage("com.instagram.android");
            //intent.setComponent(new ComponentName("com.instagram.android", "com.instagram.android.activity.UrlHandlerActivity"));
            intent.setData(Uri.parse("http://instagram.com"));
            return intent;
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com"));
            return intent;
        }
    }
}
