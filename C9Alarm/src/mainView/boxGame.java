
package mainView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.tavx.C9Alarm.R;
import com.tavx.C9Alarm.Symbol;
import com.tavx.C9Alarm.Utils;





public class boxGame extends Activity implements NewRoundCallback {
    private final static String TAG                     = "alarm";

    RelativeLayout m_layoutMain;

   // ListView m_alarmList;

    Button m_btStopSleepAlarm;
    RelativeLayout tl,tr,br,bl;
   
    ImageButton blm,tlm,trm,brm;
    boxGameView  bgv;
    
    Handler hd;
   // ImageView imageView_cirtl,imageView_cirtr,imageView_cirbl,imageView_cirbr;
   
    

    
//    Button m_btChangeBg;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maingame);
        
     //   HolidayReceiver.setAlarm(this);
        initUI();
        setListener();
        setUI();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("起床大挑战");
		builder.setMessage("选择中心文字或图案对应的颜色");
		
		builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						
					}
				});
		builder.show();
    }

    @Override
    protected void onResume() {
        refreshUI();
        super.onResume();
    }
    private void initUI(){
    	 Looper looper = Looper.myLooper();

         //此处甚至可以不需要设置Looper，因为 Handler默认就使用当前线程的Looper

    	 hd = new MessageHandler(looper);


    	
    	bgv = (boxGameView) findViewById(R.id.boxgame);
    	bgv.setNewRoundCallBack(this);
    	  m_layoutMain = (RelativeLayout) findViewById(R.id.layoutMain);
    	  
    	  m_btStopSleepAlarm= (Button) findViewById(R.id.btStop);
    	  
    	  tl =(RelativeLayout) findViewById(R.id.include1);
    	 
          tlm = (ImageButton) findViewById(R.id.imageBttl);
     //     imageView_cirtl = (ImageView) findViewById(R.id.imageView_cirtl);
          
          bl =(RelativeLayout) findViewById(R.id.include3);
    	
          blm = (ImageButton) findViewById(R.id.imageBtbl);
      //    imageView_cirtr = (ImageView) findViewById(R.id.imageView_cirtr);
          
          tr =(RelativeLayout) findViewById(R.id.include2);
    	 
          trm = (ImageButton) findViewById(R.id.imageBttr);
      //    imageView_cirbl = (ImageView) findViewById(R.id.imageView_cirbl);
          
          br =(RelativeLayout) findViewById(R.id.include4);
    	
          brm = (ImageButton) findViewById(R.id.imageBtbr);
    //      imageView_cirbr = (ImageView) findViewById(R.id.imageView_cirbr);
    }
    private void setListener(){
    	 m_btStopSleepAlarm.setOnClickListener(new OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				 issend =true;
 				 Utils.forceStopSleepAlarm(boxGame.this);
 			}
         	
         });
  //       m_alarmList = (ListView) findViewById(R.id.alarmList);
         
         
         tlm.setOnClickListener(new OnClickListener(){
 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				bgv.onKeyCLick(a[0]);
 			}
         	
         });
         
     
         
         trm.setOnClickListener(new OnClickListener(){
 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				bgv.onKeyCLick(a[1]);
 			}
         	
         });
     
  
     
     blm.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bgv.onKeyCLick(a[2]);
			}
     	
     });
    
     
     
     brm.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bgv.onKeyCLick(a[3]);
			}
     	
     });
  
     bgv.setNewRoundCallBack(this);
    
    }

    private void setUI() {
    	
    }
    int a[];
    public void newRound(){
    	a = new int[]{0,1,2,3};
    	int b = 0;
    	int buf=0;
    	for(int i=0;i<4;i++){
    		buf = (int) (Math.random()*(4-i));
    		
    		b=a[buf];
    		a[buf]=a[3-i];
    		a[3-i]=b;
    	}
    	
    	tlm.setColorFilter(getColor(a[0]));
    	trm.setColorFilter(getColor(a[1]));
    	blm.setColorFilter(getColor(a[2]));
    	brm.setColorFilter(getColor(a[3]));
    }
    
    private int getColor(int currentColor){
    	switch (currentColor) {
		case boxGameView.COLOR_BLUE:
			return Color.BLUE;
			
		case boxGameView.COLOR_RED:
			return  Color.RED;
			
		case boxGameView.COLOR_YELLOW:
			return  Color.YELLOW;
			
		case boxGameView.COLOR_DARK:
			return  Color.DKGRAY;
			
		}
    	return -1;
    }
   

    public void refreshData() {
       
    }
    public void refreshData(int i) {
    	
       
    }

     public void refreshUI() {
       
    
    }

	@Override
	public void onNewRound() {
		// TODO Auto-generated method stub
		Message m =new Message();
		m.arg1=1;
		hd.sendMessage(m);
	//	newRound();
	}
	
	class MessageHandler extends Handler {

        public MessageHandler(Looper looper) {

            super(looper);

        }

        @Override

        public void handleMessage(Message msg) {



        	newRound();

        }

    }
	boolean issend =false;
	
	boolean gamewin = false;
		/**
		 * Called when the activity get in background.
		 */
		@Override
		protected void onPause() {

			System.out.println("APPLICATION PAUSED!");
			if(issend==false){
			if(gamewin==false){
				Intent intent = new Intent(Symbol.ACTION_LOSE);    
	            sendBroadcast(intent);
			}else{
				Intent intent = new Intent(Symbol.ACTION_WIN);    
	            sendBroadcast(intent);
			}
			issend =true;
			}
			
			super.onPause();
			
			this.finish();
		}

		@Override
		public void gameOver(Boolean b) {
			// TODO Auto-generated method stub
			gamewin = b;
			this.finish();
		}
	



}
