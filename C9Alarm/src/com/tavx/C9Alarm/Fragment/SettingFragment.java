package com.tavx.C9Alarm.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.Media;
import com.tavx.C9Alarm.R;
import com.tavx.C9Alarm.Symbol;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;

public class SettingFragment extends BaseFragment {
	
	
	public static SettingFragment newInstance(String formId) {
		SettingFragment details = new SettingFragment();
		Bundle args = new Bundle();
		args.putString("formId", formId);
		details.setArguments(args);
		return details;
	}

	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	List<String> listDataHeader;
	HashMap<String, List<String>> listDataChild;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		// get the listview
		
		// Listview Group expanded listener
//		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {
//
//			@Override
//			public void onGroupExpand(int groupPosition) {
//				Toast.makeText(getActivity().getApplicationContext(),
//						listDataHeader.get(groupPosition) + " Expanded",
//						Toast.LENGTH_SHORT).show();
//			}
//		});

		// Listview Group collasped listener
//		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
//
//			@Override
//			public void onGroupCollapse(int groupPosition) {
//				Toast.makeText(getApplicationContext(),
//						listDataHeader.get(groupPosition) + " Collapsed",
//						Toast.LENGTH_SHORT).show();
//
//			}
//		});

		// Listview on child click listener
//		expListView.setOnChildClickListener(new OnChildClickListener() {
//
//			@Override
//			public boolean onChildClick(ExpandableListView parent, View v,
//					int groupPosition, int childPosition, long id) {
//				// TODO Auto-generated method stub
//				Toast.makeText(
//						getApplicationContext(),
//						listDataHeader.get(groupPosition)
//								+ " : "
//								+ listDataChild.get(
//										listDataHeader.get(groupPosition)).get(
//										childPosition), Toast.LENGTH_SHORT)
//						.show();
//				return false;
//			}
//		});
		
		mode =  AlarmApplication.getApp().getUser().getAlarmRingMode();
		isLowMemory = AlarmApplication.getApp().getUser().getIsLowMemory();
	
	
	}
	
	
	
	/**
	 * @wangcheng
	 * @myfitcode start
	 * */
	
	
	
	int MainViewId;
	private View MainView;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		MainView = inflater.inflate(getContentView(), container, false);
		
		expListView = (ExpandableListView) findViewById(R.id.list);

		// preparing list data
		prepareListData();

		listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader,
				listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);
		// Listview Group click listener
		for (int i = 0; i < listAdapter.getGroupCount(); i++) {
			expListView.expandGroup(i);
		}
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return true;
			}
		});

		
		return MainView;
	}
	
	
	private int getContentView(){
		return MainViewId;
	}
	
	private void setContentView(int guba_stockhome) {
		MainViewId = guba_stockhome;
	}
	
	private View getMainView(){
		return MainView;
	}
	
	private View findViewById(int tvArtiTitle) {
		return getMainView().findViewById(tvArtiTitle);
	}
	/**
	 * @wangcheng
	 * @myfitcode end
	 * */

	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("闹钟设置");
		listDataHeader.add("用户中心");
		listDataHeader.add("关于");

		// Adding child data
		List<String> top250 = new ArrayList<String>();
		top250.add("The Shawshank Redemption");
		top250.add("The Shawshank Redemption");
		top250.add("The Shawshank Redemption");
		top250.add("The Shawshank Redemption");

		List<String> nowShowing = new ArrayList<String>();
		nowShowing.add("The Conjuring");
		nowShowing.add("Despicable Me 2");
	//	nowShowing.add("Despicable Me 3");

		List<String> comingSoon = new ArrayList<String>();
		comingSoon.add("2 Guns");
	//	comingSoon.add("2 Guns");
		

		listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
		listDataChild.put(listDataHeader.get(1), nowShowing);
		listDataChild.put(listDataHeader.get(2), comingSoon);
	}

	class ExpandableListAdapter extends BaseExpandableListAdapter {

		private Context _context;
		private List<String> _listDataHeader; // header titles
		// child data in format of header title, child title
		private HashMap<String, List<String>> _listDataChild;

		public ExpandableListAdapter(Context context,
				List<String> listDataHeader,
				HashMap<String, List<String>> listChildData) {
			this._context = context;
			this._listDataHeader = listDataHeader;
			this._listDataChild = listChildData;
		}

		@Override
		public Object getChild(int groupPosition, int childPosititon) {
			return this._listDataChild.get(
					this._listDataHeader.get(groupPosition))
					.get(childPosititon);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, final int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {

			final String childText = (String) getChild(groupPosition,
					childPosition);

			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.setting_list_item,
						null);
			}

			final TextView txtListChild = (TextView) convertView
					.findViewById(R.id.lblListItem);
			final TextView value = (TextView) convertView
					.findViewById(R.id.value);
			if (groupPosition == 0 && childPosition == 0) {
				txtListChild.setText("贪睡模式及时间");
				int sleepTime = AlarmApplication.getApp().getSleepTime();
				if(sleepTime>0){
					value.setText(AlarmApplication.getApp().getSleepTime() + "分钟");
				}else{
					value.setText("已关闭");
				}
				
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						setIntervalDialog(value);
					}
				});
			} else if (groupPosition == 0 && childPosition == 1) {
				txtListChild.setText("响铃模式");
				if((mode&(Symbol.RINGMODE_MUSIC|Symbol.RINGMODE_VIBRATE))==(Symbol.RINGMODE_MUSIC|Symbol.RINGMODE_VIBRATE)){
					value.setText("铃声+震动");
				}else if((mode&Symbol.RINGMODE_MUSIC)==Symbol.RINGMODE_MUSIC){
					value.setText("仅铃声");
				}else{
					value.setText("仅震动");
				}
				
				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						setRingVibrateModeDialog(value);
					}
				});
				
			}else if (groupPosition == 0 && childPosition == 2) {
				txtListChild.setText("音量及渐响模式");
				
				if ((mode&Symbol.RINGMODE_SLOWLOUD)==Symbol.RINGMODE_SLOWLOUD) {
					value.setText("已开启渐响");
				}else{
					value.setText("未开启渐响");
				}
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						setRingLoudModeDialog(value);
					}
				});
			
			}else if (groupPosition == 0 && childPosition == 3) {
				txtListChild.setText("低内存模式");
				
				if (isLowMemory==true) {
					value.setText("开启");
				}else{
					value.setText("不开启");
				}
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						setIsLowMemory(value);
					}
				});
			
			}/*else if (groupPosition == 1 && childPosition == 0) {
				AlarmApplication
						.getApp()
						.getCommentController()
						.getUserInfo(getActivity(),
								new FetchUserListener() {
									@Override
									public void onStart() {

									}

									@Override
									public void onComplete(int arg0,
											SocializeUser user) {
										if (user != null
												&& user.mLoginAccount != null
												&& !TextUtils
														.isEmpty(user.mLoginAccount
																.getAccountIconUrl())) {
											// 可以从user 中获取用户信息
											String name = user.mLoginAccount
													.getUserName();
											AlarmApplication
													.getApp()
													.getUser()
													.setSnsBindName(
															user.mLoginAccount
																	.getUserName());

											value.setText(name);
										} else {
											value.setText("未绑定");
										}
									}
								});
				txtListChild.setText("平台绑定");
				value.setText(AlarmApplication.getApp().getUser()
						.getSnsBindName());
				// txtListChild.setText("用户昵称");
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// setIntervalDialog(value);
						AlarmApplication
								.getApp()
								.getCommentController()
								.openUserCenter(
										getActivity(),
										SocializeConstants.FLAG_USER_CENTER_LOGIN_VERIFY);
						// SnsAccount s =new SnsAccount(arg0, arg1, arg2, arg3)
					}
				});
			}*/ else if (groupPosition == 1 && childPosition == 0) {

				txtListChild.setText("用户昵称");
				value.setText(AlarmApplication.getApp().getUserNickName());
				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						setManulName(value);
					}
				});
			} else if (groupPosition == 1 && childPosition == 1) {
				txtListChild.setText("留言反馈");
				value.setText("");
				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
//						AlarmApplication.getApp().getCommentController()
//								.openComment(getActivity(), false);
						FeedbackAgent agent = new FeedbackAgent(getActivity());
						agent.startFeedbackActivity();
					}
				});
			}/*else if (groupPosition == 2 && childPosition == 0) {
				txtListChild.setText("关于和帮助");
				value.setText("");
				
				
				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i=new Intent();
						i.setClass(getActivity(), HelpActivirty.class);
						startActivity(i);
					}
				});
			}*/else if (groupPosition == 2 && childPosition == 0) {
				txtListChild.setText("检查更新");
				value.setText(AlarmApplication.getApp().getVersion());
			//	AlarmApplication.myApp.showToast("已提交版本更新请求");
				convertView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						AlarmApplication.myApp.showToast("已提交版本更新请求");
						//AlarmApplication.myApp.showError("已提交版本更新请求");
						UmengUpdateAgent.setUpdateAutoPopup(true);
						UmengUpdateAgent.forceUpdate(getActivity());
						
					}
				});
			}else {
				txtListChild.setText("null");
				value.setText("null");
			}

			// txtListChild.setText(childText);
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return this._listDataChild.get(
					this._listDataHeader.get(groupPosition)).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return this._listDataHeader.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return this._listDataHeader.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			String headerTitle = (String) getGroup(groupPosition);
			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(
						R.layout.setting_list_group, null);
			}

			TextView lblListHeader = (TextView) convertView
					.findViewById(R.id.lblListHeader);
			lblListHeader.setTypeface(null, Typeface.BOLD);
			lblListHeader.setText(headerTitle);
			TextView lefthint = (TextView) convertView
					.findViewById(R.id.lefthint);
			lefthint.setVisibility(View.GONE);

			return convertView;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	private void setIntervalDialog(final TextView tv) {
		View layout = LayoutInflater.from(getActivity()).inflate(
				R.layout.set_alarm_interval, null);

		final RadioGroup rgIntervalMode = (RadioGroup) layout
				.findViewById(R.id.rgIntervalMode);
		final RadioButton rbAfter5min = (RadioButton) layout
				.findViewById(R.id.rbAfter5min);
		final RadioButton rbAfter10min = (RadioButton) layout
				.findViewById(R.id.rbAfter10min);
		final RadioButton rbAfter15min = (RadioButton) layout
				.findViewById(R.id.rbAfter15min);
		final RadioButton rbClose = (RadioButton) layout
				.findViewById(R.id.closeRb);
		
		DialogInterface.OnClickListener dia =new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(which==DialogInterface.BUTTON_POSITIVE){
					int id =  rgIntervalMode.getCheckedRadioButtonId();
					String m_alarmInterval = null;
					if (id == rbAfter5min.getId()) {
						m_alarmInterval = rbAfter5min.getText().toString();
						AlarmApplication.getApp().setSleepTime(5);

					} else if (id == rbAfter10min.getId()) {
						m_alarmInterval = rbAfter10min.getText().toString();
						AlarmApplication.getApp().setSleepTime(10);

					} else if (id == rbAfter15min.getId()) {
						m_alarmInterval = rbAfter15min.getText().toString();
						AlarmApplication.getApp().setSleepTime(15);

					}else if (id == rbClose.getId()) {
						m_alarmInterval = "已关闭";
						AlarmApplication.getApp().setSleepTime(-1);

					}
					
					tv.setText(m_alarmInterval);
					dialog.dismiss();
					
					
				}else if(which==DialogInterface.BUTTON_NEGATIVE){
				
				}
				}
		};;
		
		
		final Dialog dialog = new AlertDialog.Builder(getActivity())
				.setTitle(getString(R.string.alarm_interval_title))
				.setView(layout)
				.setPositiveButton(getString(R.string.set), dia)
				.setNegativeButton(getString(R.string.cancel), dia).create();
		
	
	
		
		

		dialog.show();

		int slp = AlarmApplication.getApp().getSleepTime();
		if (slp == 5) {
			rbAfter5min.setChecked(true);

		} else if (slp == 10) {
			rbAfter10min.setChecked(true);

		} else if (slp == 15) {
			rbAfter15min.setChecked(true);

		}
		 else if (slp <0) {
			 rbClose.setChecked(true);

			}
		/*rgIntervalMode
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup arg0, int checkedId) {
						String m_alarmInterval = null;
						if (checkedId == rbAfter5min.getId()) {
							m_alarmInterval = rbAfter5min.getText().toString();
							AlarmApplication.getApp().setSleepTime(5);

						} else if (checkedId == rbAfter10min.getId()) {
							m_alarmInterval = rbAfter10min.getText().toString();
							AlarmApplication.getApp().setSleepTime(10);

						} else if (checkedId == rbAfter15min.getId()) {
							m_alarmInterval = rbAfter15min.getText().toString();
							AlarmApplication.getApp().setSleepTime(15);

						}

						tv.setText(m_alarmInterval);
						dialog.dismiss();
					}
				});*/
	}
	
	int mode=0;
	boolean isLowMemory =false;
	private void setRingVibrateModeDialog(final TextView tv) {
		View layout = LayoutInflater.from(getActivity()).inflate(
				R.layout.set_alarm_ring, null);
		mode =  AlarmApplication.getApp().getUser().getAlarmRingMode();
		final int curMode = mode;
		
		DialogInterface.OnClickListener dia = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(which==DialogInterface.BUTTON_POSITIVE){
					AlarmApplication.getApp().getUser().setAlarmRingMode(mode);
					
					if((mode&(Symbol.RINGMODE_MUSIC|Symbol.RINGMODE_VIBRATE))==(Symbol.RINGMODE_MUSIC|Symbol.RINGMODE_VIBRATE)){
						tv.setText("铃声+震动");
					}else if((mode&Symbol.RINGMODE_MUSIC)==Symbol.RINGMODE_MUSIC){
						tv.setText("仅铃声");
					}else{
						tv.setText("仅震动");
					}
					
					MyLogger.e("aaaax", "setRingVibrateModeDialog"+mode);
				}else if(which==DialogInterface.BUTTON_NEGATIVE){
					mode = curMode;
				}
				
			}
		};
		
		final Dialog dialog = new AlertDialog.Builder(getActivity())
				.setTitle(getString(R.string.alarm_ring_title)).setView(layout)
				.setIcon(0)
				.setPositiveButton(getString(R.string.set), dia)
				.setNegativeButton(getString(R.string.cancel), dia).create();

		dialog.show();

		RadioGroup rgRingMode = (RadioGroup) layout
				.findViewById(R.id.rgRingMode);

		final RadioButton rbRingSilent = (RadioButton) layout
				.findViewById(R.id.rbSilent);
		final RadioButton rbRingSystem = (RadioButton) layout
				.findViewById(R.id.rbSystemRing);
		 final RadioButton rbRingOptional = (RadioButton)
		 layout.findViewById(R.id.rbVibrateOnly);
		
		
		final CheckBox cbVibrate = (CheckBox) layout
				.findViewById(R.id.cbVibrate);
		cbVibrate.setVisibility(View.GONE);
		
		rbRingSilent.setText("铃声+震动");
		rbRingSystem.setText("仅铃声");
		rbRingOptional.setText("仅震动");
		//cbVibrate.setText("闹铃启动时由轻到响");
		
		// show ring mode status
		  
		if((mode&(Symbol.RINGMODE_MUSIC|Symbol.RINGMODE_VIBRATE))==(Symbol.RINGMODE_MUSIC|Symbol.RINGMODE_VIBRATE)){
			rbRingSilent.setChecked(true);
		}else if((mode&Symbol.RINGMODE_VIBRATE)==Symbol.RINGMODE_VIBRATE){
			rbRingOptional.setChecked(true);
		}else{
			rbRingSystem.setChecked(true);
		}

		// show vibrate status

		rgRingMode.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				if (checkedId == rbRingSilent.getId()) {
					mode=mode|Symbol.RINGMODE_MUSIC|Symbol.RINGMODE_VIBRATE;
				} else if (checkedId == rbRingSystem.getId()) {
					mode=(mode|Symbol.RINGMODE_MUSIC)&(0xf&~Symbol.RINGMODE_VIBRATE);
				}
				  else if ( checkedId == rbRingOptional.getId() ) {
					  mode=(mode|Symbol.RINGMODE_VIBRATE)&(0xf&~(Symbol.RINGMODE_MUSIC));
				  }
				 
				//dialog.dismiss();
			}
		});

//		rbRingSilent.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (rbRingSilent.isChecked()) {
//					dialog.dismiss();
//				}
//				m_ringUri = null;
//			}
//		});
//
//		rbRingSystem.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (rbRingSystem.isChecked()) {
//					dialog.dismiss();
//				}
//				openSystemRingtone(alarm);
//			}
//		});

//		cbVibrate.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (cbVibrate.isChecked()) {
//					m_bVibrate = true;
//					alarm.setVibrate(true);
//				} else {
//					m_bVibrate = false;
//					alarm.setVibrate(false);
//				}
//			}
//		});
	}
	
	private void setRingLoudModeDialog(final TextView tv) {
		View layout = LayoutInflater.from(getActivity()).inflate(
				R.layout.set_alarm_ring_mode, null);
		mode =  AlarmApplication.getApp().getUser().getAlarmRingMode();
		final int curMode = mode;
		
		final AudioManager am = (AudioManager)getActivity(). getSystemService(Context.AUDIO_SERVICE);
		final int currentVol=am.getStreamVolume(AudioManager.STREAM_ALARM);
		
		final CheckBox cbVibrate = (CheckBox) layout
				.findViewById(R.id.cbVibrate);
		final SeekBar mSeekBar = (SeekBar) layout
				.findViewById(R.id.seekBar1);
		DialogInterface.OnClickListener dia =new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(which==DialogInterface.BUTTON_POSITIVE){
					AlarmApplication.getApp().getUser().setAlarmRingMode(mode);
					
					if ((mode&Symbol.RINGMODE_SLOWLOUD)==Symbol.RINGMODE_SLOWLOUD) {
						tv.setText("已开启渐响");
					}else{
						tv.setText("未开启渐响");
					}
					MyLogger.e("aaaax", "setRingLoudModeDialog"+mode);
					am.setStreamVolume(AudioManager.STREAM_ALARM, mSeekBar.getProgress(), 0);
				}else if(which==DialogInterface.BUTTON_NEGATIVE){
					mode = curMode;
					am.setStreamVolume(AudioManager.STREAM_ALARM, currentVol, 0);
				}
				Media.getInstance(getActivity()).Stop();
				MyLogger.e("aaaax", "onClick"+which);
				((Dialog)dialog).setOnDismissListener(null);
				}
		};;
	 Dialog mDialog = new AlertDialog.Builder(getActivity())
		.setTitle(getString(R.string.alarm_ring_title)).setView(layout)
		.setIcon(0)
		.setPositiveButton(getString(R.string.set), dia)
		.setNegativeButton(getString(R.string.cancel), dia).create();
	
		
		

		mDialog.show();
		mDialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				MyLogger.e("aaaax", "dismiss");
				am.setStreamVolume(AudioManager.STREAM_ALARM, currentVol, 0);
				Media.getInstance(getActivity()).Stop();
			}
		});

		
		
		
		cbVibrate.setText("闹铃启动时由轻到响");
		
		mSeekBar.setMax(am.getStreamMaxVolume(AudioManager.STREAM_ALARM));
		mSeekBar.setProgress(currentVol);
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if(fromUser){
					am.setStreamVolume(AudioManager.STREAM_ALARM, mSeekBar.getProgress(), 0);
					Media.getInstance(getActivity()).Stop();
					Media.getInstance(getActivity()).playMusic(R.raw.alarm_paojie, true, false, cbVibrate.isChecked());
				}
			}
		});
		// show ring mode status
	
		// show vibrate status
		if ((mode&Symbol.RINGMODE_SLOWLOUD)==Symbol.RINGMODE_SLOWLOUD) {
			cbVibrate.setChecked(true);
		}
		cbVibrate.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				am.setStreamVolume(AudioManager.STREAM_ALARM, mSeekBar.getProgress(), 0);
				Media.getInstance(getActivity()).Stop();
				Media.getInstance(getActivity()).playMusic(R.raw.alarm_paojie, true, false, isChecked);
				
			}
		});

		cbVibrate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (cbVibrate.isChecked()) {
					mode=mode|Symbol.RINGMODE_SLOWLOUD;
				} else {
					mode=mode&(0xf&~Symbol.RINGMODE_SLOWLOUD);
				}
			}
		});
	}
	
	
	

	public void setManulName(final TextView tv) {
		View layout = LayoutInflater.from(getActivity()).inflate(
				R.layout.set_manul_name, null);
		final EditText etAlarmName = (EditText) layout
				.findViewById(R.id.etAlarmName);
		etAlarmName.setText(AlarmApplication.getApp().getUserNickName());
		new AlertDialog.Builder(getActivity())
				// .setIcon(R.drawable.white_cloud)
				.setTitle(getString(R.string.please_input))
				.setView(layout)
				.setPositiveButton(getString(R.string.set),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String name = etAlarmName.getText().toString();
								tv.setText(name);
								AlarmApplication.getApp().setUserNickName(name);
							}
						}).create().show();
	}
	
	public void setIsLowMemory(final TextView tv) {
		View layout = LayoutInflater.from(getActivity()).inflate(
				R.layout.set_message_dialog, null);
		final TextView etAlarmName = (TextView) layout
				.findViewById(R.id.message);
		etAlarmName.setText("开启低内存模式后，程序会关闭一部分动画效果，加载的图片质量也会降低，如果您的手机经常使用一段时间后崩溃，可以尝试开启低内存模式。低内存模式目前处于测试阶段，无法完全避免内存不足:(");
		new AlertDialog.Builder(getActivity())
				// .setIcon(R.drawable.white_cloud)
				.setTitle("低内存模式")
				.setView(layout)
				.setPositiveButton("开启",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								isLowMemory = true;
								AlarmApplication.getApp().getUser().setIsLowMemory(isLowMemory);
								tv.setText("开启");
							}
						})
						.setNegativeButton("不开启",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								isLowMemory = false;
								AlarmApplication.getApp().getUser().setIsLowMemory(isLowMemory);
								tv.setText("不开启");
							}
						}).create().show();
	}


	
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//	    super.onActivityResult(requestCode, resultCode, data);
//	    /**使用SSO授权必须添加如下代码 */  
//	    UMSsoHandler ssoHandler = AlarmApplication.getApp().getCommentController().getConfig().getSsoHandler(requestCode);
//	    if(ssoHandler != null){
//	       ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//	    }
//	}
	


}