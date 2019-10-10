package com.example.dailynova.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserKeyUtil {
    public static String getUserKeyFromEmail(String email)
    {
        String userId="";
        char a='@';
        for (int i=0;i<email.length();i++)
        {
           if (email.charAt(i)==a)
           {
               userId=email.substring(0,i);
           }
        }
        return userId;
    }

}
