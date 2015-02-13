package com.my.a;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ExitDialog {
	private Activity mContext;
	
	public ExitDialog(Activity context){
		mContext = context;
	}
	
	public void show(){
		
			new AlertDialog.Builder(mContext)
					.setTitle(Math.random()>0.9?b:a)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dlg,
										int sumthin) {
									
									mContext.finish();
									System.exit(0);

								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dlg,
										int sumthin) {

								}
							}).show();
	}
	
	String a = "是否决定退出？";
	String b = "是否决定做我女朋友？";
}
