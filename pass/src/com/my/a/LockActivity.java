package com.my.a;



import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
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

public class LockActivity extends TestActivity {
    /** Called when the activity is first created. */
	EditText ed;
	TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         ed =(EditText) findViewById(R.id.editText1);
        Button bt = (Button) findViewById(R.id.button1);
        
        TextView tv_title =(TextView) findViewById(R.id.title);
         tv =(TextView) findViewById(R.id.res);
        bt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
		           imm.hideSoftInputFromWindow(ed.getWindowToken(),0); 
				tv.setText("");
				StringBuffer sb;
				String a=ed.getText().toString();
				for(int ii=-10;ii<10;ii++){
				sb=new StringBuffer();
				
				for(int i=0;i<a.length();i++){
					sb.append((char)(a.charAt(i)+ii));
				} 
				tv.append("每一位加上"+ii+"后得到："+sb.toString());
				tv.append("\n\r");
				
				}
				
				
			}
        	
        });
        
      
        
        int seed = (int) (Math.random()*6)-3;
        if(seed==0)seed=4;
        StringBuffer sb2=new StringBuffer();
		String str = "TomydearCholeIloveu";
		for(int i=0;i<str.length();i++){
			sb2.append((char)(str.charAt(i)+seed));
		} 
		tv_title.setText(sb2.toString());
		
		
		
		
    }
	@Override
	protected void onPause() {
		 ActivityManager mActivityManager =(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		  List<RunningTaskInfo> a= mActivityManager.getRunningTasks(3);
	    if( getClass().getPackage().getName().equals(a.get(0).baseActivity.getPackageName())){
	    	Log.e("aaa","same");
	    }else{
	    	Log.e("aaa","diff");
	    }
	      
		super.onPause();
	}
    
  
    
    
}