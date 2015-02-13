package util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.R;

public class MyToast {
	
	private View displayView;
private TextView toastContent;
private ImageView icon;
private static MyToast mMyToast;
Toast toast;


private MyToast() {
	super();
	init();
}


public static MyToast getInatance(){
	if(mMyToast==null)
		mMyToast = new MyToast();
	return mMyToast;
}

	
public void init(){
	LayoutInflater inflater = (LayoutInflater)AlarmApplication.getApp().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	displayView =inflater.inflate(R.layout.simple_notification_top, null);
	toastContent = (TextView)displayView.findViewById(R.id.of_text);
	icon = (ImageView)displayView.findViewById(R.id.of_icon);
}


public synchronized void  ShowToast(String  content,int gravity){
		if (toast != null) {
			//toast.cancel(); 
			toastContent.setText(content);
			toast.show();
			return;
			
		}
		toastContent.setText(content);
		
	//	app.icon.setImageResource(r.iconRes);
		icon.setImageResource(R.drawable.headicon_chudai);
		toast = new Toast(AlarmApplication.getApp());
		toast.setGravity(Gravity.TOP, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(displayView);
		toast.show();
}

 public synchronized void  ShowToast(String  content){
	 ShowToast(content, Gravity.TOP);
 }

 public void ShowErrorToast(String errorlog){
	 ShowToast(errorlog);
 }
}
