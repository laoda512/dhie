package com.my.a;

import android.app.Activity;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;

public class TestActivity extends Activity{
    public void onCreate(Bundle savedInstanceState) {
   	
       super.onCreate(savedInstanceState);
       ComponentName c =getCallingActivity();
       
       Log.e("TestActivity", ""+getClass().getName()+" called by"+ (c==null?c:(c.getClassName()+" "+getPackageName())));
       
       }

}
