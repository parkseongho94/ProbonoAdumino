package com.example.yesun.myapplication;


import android.content.Context;
import android.os.PowerManager;
import android.app.KeyguardManager;
import android.util.Log;
import service.BTCTemplateService;

/**
 * Created by JUNYONG on 2017-07-15.
 */

public class AlarmWakeLock {
    private static PowerManager.WakeLock mWakeLock;
    private static final String TAG = "AlarmWakeLock";

    public static void wakeLock(Context context){
        if(mWakeLock != null){
            return;
        }

        PowerManager powerManager = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, TAG);
        mWakeLock.acquire();
    }

    public static void releaseWakeLock(){
        if (mWakeLock != null){
            mWakeLock.release();
            mWakeLock = null;
        }
    }
}
