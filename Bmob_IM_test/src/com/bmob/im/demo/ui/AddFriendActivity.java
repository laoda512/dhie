package com.bmob.im.demo.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.listener.FindListener;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.AddFriendAdapter;

/** 添加好友
  * @ClassName: AddFriendActivity
  * @Description: TODO
  * @author smile
  * @date 2014-6-5 下午5:26:41
  */
public class AddFriendActivity extends ActivityBase implements OnClickListener{
	
	EditText et_find_name;
	Button btn_search;
	ListView list_search;
	
	List<BmobChatUser> users = new ArrayList<BmobChatUser>();
	
	AddFriendAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		initView();
	}
	
	private void initView(){
		initTopBarForLeft("查找好友");
		et_find_name = (EditText)findViewById(R.id.et_find_name);
		btn_search = (Button)findViewById(R.id.btn_search);
		btn_search.setOnClickListener(this);
		list_search = (ListView)findViewById(R.id.list_search);
		adapter = new AddFriendAdapter(this, users);
		list_search.setAdapter(adapter);
	}

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		if (id == R.id.btn_search) {
			users.clear();
			String searchName = et_find_name.getText().toString();
			if(searchName!=null && !searchName.equals("")){
				final ProgressDialog progress = new ProgressDialog(this);
				progress.setMessage("正在搜索...");
				progress.setCanceledOnTouchOutside(false);
				progress.show();
				userManager.queryUserByName(searchName, new FindListener<BmobChatUser>() {
					
					@Override
					public void onError(int arg0, String arg1) {
						// TODO Auto-generated method stub
						progress.dismiss();
						ShowToast("用户不存在");
						BmobLog.i("onError:"+arg1);
					}
					
					@Override
					public void onSuccess(List<BmobChatUser> arg0) {
						// TODO Auto-generated method stub
						progress.dismiss();
						if(arg0!=null && arg0.size()>0){
							BmobLog.i("onSuccess:"+arg0.size()+",username = "+arg0.get(0).getUsername());
							users.addAll(arg0);
							adapter.notifyDataSetChanged();
						}else{
							BmobLog.i("暂无好友");
							ShowToast("用户不存在");
						}
					}
				});
			}else{
				ShowToast("请输入用户名");
			}
		} else {
		}
	}

}
