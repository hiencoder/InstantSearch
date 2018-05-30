package vn.edu.imic.instanstsearch.utils;

import android.util.Log;

/**
 * Created by MyPC on 30/05/2018.
 */

public class LogUtils {
    public static void d(String tag, String message){
        if (message != null) {
            Log.d(tag, message);
        }
    }
    
    public static void e(String tag, String message){
        if (message != null){
            Log.e(tag,message);
        }
    }
}
