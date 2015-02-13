package com.my.a;



import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SmsActivity extends TestActivity {
    /** Called when the activity is first created. */
	EditText ed;
	 TextView tv_title;
	TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         ed =(EditText) findViewById(R.id.editText1);
        Button bt = (Button) findViewById(R.id.button1);
        
         tv_title =(TextView) findViewById(R.id.title);
         ed.setText("13564582033");
        tv =(TextView) findViewById(R.id.res);
        bt.setText("模拟发送短信");
        bt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				
				
				//tv.setText("");
				sms();
				Toast.makeText(SmsActivity.this, "success", 1000).show();
				smsRefresh();
			}
        	
        });
        smsRefresh();
        
		
    }
   
    
    private void sms(){
        ContentResolver cr = getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put("address",ed.getText().toString());
        cv.put("body", "sorry...");
        cr.insert(Uri.parse("content://sms/inbox"), cv);
    	

    }
    
    private void smsRefresh(){
    	String strUriInbox = "content://sms";
    	Uri uriSms = Uri.parse(strUriInbox);
    	Cursor c_groups = managedQuery( uriSms , new String[] { "body","address" }, "address=\""+ed.getText()+"\"OR address = \"+86"+ed.getText().toString()+"\"", null, "date DESC");
    	 while(c_groups.moveToNext()) {
            int phoneColumn = c_groups.getColumnIndex("address");  
            int smsColumn = c_groups.getColumnIndex("body");  
           String msg = c_groups.getString(phoneColumn) + ":" + c_groups.getString(smsColumn) + "\n";
            Log.e("aaaa", msg);
        }
    	tv.setText("可见短信数量："+ c_groups.getCount());

    	
    }
}