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
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dlg,
										int sumthin) {
									
									mContext.finish();
									System.exit(0);

								}
							})
					.setNegativeButton("ȡ��",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dlg,
										int sumthin) {

								}
							}).show();
	}
	
	String a = "�Ƿ�����˳���";
	String b = "�Ƿ��������Ů���ѣ�";
}
