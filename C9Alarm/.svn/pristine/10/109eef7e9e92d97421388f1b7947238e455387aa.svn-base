/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package util;

import com.tavx.C9Alam.connector.MyLogger;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

/**
 * Hold a wakelock that can be acquired in the AlarmReceiver and
 * released in the AlarmAlert activity
 */
public class AlarmAlertWakeLock {

    private static PowerManager.WakeLock sWakeLock;
    private static PowerManager.WakeLock sCpuLock;
    static Long time =-1l;

    public static void acquire(Context context) {
        if (sWakeLock != null&&sWakeLock.isHeld()) {
         //     sWakeLock.release();
        	  MyLogger.e("AlarmReceiver", "has lock");
            return ;
        }
        MyLogger.e("AlarmReceiver", "acquire lock");
        PowerManager pm =
                (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        sWakeLock = pm.newWakeLock(
                PowerManager.FULL_WAKE_LOCK |
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.ON_AFTER_RELEASE, "tavx");
        sWakeLock.acquire();
        time = System.currentTimeMillis()+1*60*1000;
    }
    
    public static void acquireCpu(Context context) {
        if (sCpuLock != null&&sCpuLock.isHeld()) {
            //sWakeLock.release();
            return ;
        }
        MyLogger.e("AlarmReceiver", "acquire cpu lock");
        PowerManager pm =
                (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        sCpuLock = pm.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK 
                , "tavx_cpu");
        sCpuLock.acquire();
    }

     public  static void release() {
        if (sWakeLock != null) {
        	if(time<System.currentTimeMillis())
            sWakeLock.release();
            sWakeLock = null;
        }
        if (sCpuLock != null) {
        	sCpuLock.release();
        	sCpuLock = null;
        }
        MyLogger.e("AlarmReceiver", "relase lock");
    }
}
