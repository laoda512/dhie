
package com.tavx.C9Alarm;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainAlarmActivity extends BaseActivity implements RefreshAlarm {
    private final static String TAG                     = "alarm";

    RelativeLayout m_layoutMain;

   // ListView m_alarmList;

    Button m_btStopSleepAlarm;
//    Button m_btChangeBg;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 //       HolidayReceiver.setAlarm(this);
        initUI();
    }

    @Override
    protected void onResume() {
        refreshUI();
        super.onResume();
    }

    private void initUI() {
        m_layoutMain = (RelativeLayout) findViewById(R.id.layoutMain);

        m_btStopSleepAlarm= (Button) findViewById(R.id.btStop);
        m_btStopSleepAlarm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Utils.forceStopSleepAlarm(MainAlarmActivity.this);
			}
        	
        });
//        m_alarmList = (ListView) findViewById(R.id.alarmList);
//        AlarmAdapter adapter = new AlarmAdapter(this, AlarmProvider.geInstance(this).getAlarms(),
//                this);
//        m_alarmList.setAdapter(adapter);
//        m_alarmList.setVerticalScrollBarEnabled(true);
//        m_alarmList.setOnItemClickListener(new OnItemClickListener() {
//        
//        	
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                Bundle bundle = new Bundle();
//                bundle.putInt("alarm_position", position);
//                
//                Intent intent = new Intent(MainAlarmActivity.this, SetAlarmActivity.class);
//                intent.putExtras(bundle);
//                
//                MainAlarmActivity.this.startActivity(intent);
//            }
//        });
        
        /*m_btAddAlarm = (Button) findViewById(R.id.btAddAlarm);
        m_btAddAlarm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startAlarmSettings();
            }
        });

        m_btChangeBg = (Button) findViewById(R.id.btChangeBg);
        m_btChangeBg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                changeBgDialog();
            }
        });*/
    }

    private class AlarmAdapter extends BaseAdapter {
        private Context m_context;

        private List<AlarmBean> m_listBean;

        RefreshAlarm r;
        
        ViewHolder holder = null;

        public AlarmAdapter( Context context, List<AlarmBean> listBean, RefreshAlarm r ) {
            super();
            this.m_context = context;
            this.m_listBean = listBean;
            this.r = r;
        }

        public boolean isAlarmEnabled(int position) {
            return m_listBean.get(position).getEnabled();
        }

        @Override
        public int getCount() {
            return m_listBean.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        
        @Override
        public View getView(final int position, View view, ViewGroup parent) {
          //  if (null == view) {
                holder = new ViewHolder();
                view = LayoutInflater.from(m_context).inflate(R.layout.alarm_info, null);
                Log.i(TAG, "pos: " + position);

                holder.m_alarmTitle = (TextView) view.findViewById(R.id.tvAlarmTitle);
                holder.m_alarmTitle.setText( m_listBean.get(position).getName() );
                
                holder.m_alarmTime = (TextView) view.findViewById(R.id.tvAlarmTime);
                Long time = m_listBean.get(position).getTime();
                holder.m_alarmTime.setText( Utils.getCalendarTime( time ) );
                
                holder.m_alarmDayOfWeek = (TextView) view.findViewById(R.id.tvWeeklyMode);
                int day = m_listBean.get(position).getDay();
                holder.m_alarmDayOfWeek.setText( m_listBean.get(position).getDayText(day) );
                
                holder.m_alarmRing = (TextView) view.findViewById(R.id.tvRing);
                holder.m_alarmRing.setText( Utils.formatRingMode( m_listBean.get(position).getRingMode()) );
                
                holder.m_cbAlarmEnable = (CheckBox) view.findViewById(R.id.cbEnable);
                holder.m_cbAlarmEnable.setChecked( m_listBean.get(position).getEnabled() );
                
                holder.m_cbAlarmEnable.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        m_listBean.get(position).setEnabled(isChecked);
                        r.refreshData();
                    }
                });

                view.setTag(holder);
   //因为listview有半行显示的bug，所以注释掉代码优化段 。            
//            } else {
//                holder = (ViewHolder) view.getTag();
//            }
            
            //If none of days is selected, the alarm should be disable.
            if ( 0 == m_listBean.get(position).getDay() ) {
                holder.m_cbAlarmEnable.setEnabled(false);
            }else {
                holder.m_cbAlarmEnable.setEnabled(true);
            }

            return view;
        }
    }

    public class ViewHolder {
        public TextView m_alarmTitle;
        public TextView m_alarmTime;
        public TextView m_alarmDayOfWeek;
//        public TextView m_alarmOccurs;
        public TextView m_alarmRing;
        public CheckBox m_cbAlarmEnable;
    }

    private void startAlarmSettings() {
        Intent intent = new Intent();
        intent.setClass(MainAlarmActivity.this, SetAlarmActivity.class);
        startActivity(intent);
    }

    private void changeBgDialog() {
        View viewBg = LayoutInflater.from(this).inflate(R.layout.change_bg, null);

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.change_bg_title))
                .setView(viewBg)
                .setPositiveButton(getString(R.string.confirm),
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i(TAG, "bg: " + which);
                                //changeBg(which);
                            }
                        }).setNegativeButton(getString(R.string.cancel), null).create().show();
    }

//    private void changeBg(int id) {
//        switch (id) {
//            case 0:
//                m_layoutMain.setBackgroundResource(R.drawable.bg_xmas);
//                break;
//            case 1:
//                m_layoutMain.setBackgroundResource(R.drawable.bg_totoro);
//                break;
//            case 2:
//                m_layoutMain.setBackgroundResource(R.drawable.bg_jimi);
//                break;
//        }
//    }

    @Override
    public void refreshData() {
    /*    AlarmProvider ap = AlarmProvider.geInstance(this.getApplicationContext());
        for (int i = 0; i < m_alarmList.getAdapter().getCount(); i++) {
            ap.setAlarmEnable(i, ((AlarmAdapter) m_alarmList.getAdapter()).isAlarmEnabled(i));
        }

       
            AlarmSet.setAlarm(this);*/
       
    }

     @Override
    public void refreshUI() {
        // TODO Auto-generated method stub
     /*   m_alarmList.setAdapter(new AlarmAdapter(this, AlarmProvider.geInstance(this).getAlarms(),
                this));*/
        if(AlarmProvider.geInstance(this).isSleepAlarmEnable(0)){
        	m_btStopSleepAlarm.setEnabled(true);
        }else{
        	m_btStopSleepAlarm.setEnabled(false);
        }

    }
}
