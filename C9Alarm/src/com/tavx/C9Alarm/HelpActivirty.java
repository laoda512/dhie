package com.tavx.C9Alarm;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class HelpActivirty extends Activity {        
    private WebView mWebView;       
    private Handler mHandler = new Handler();       
       
    public void onCreate(Bundle icicle) {       
        super.onCreate(icicle);       
        setContentView(R.layout.help);       
        mWebView = (WebView) findViewById(R.id.web);       
        WebSettings webSettings = mWebView.getSettings();       
        webSettings.setJavaScriptEnabled(true);       
           
        mWebView.loadUrl("http://www.projectalarmx.com/help_cn.html");       
    }       
} 