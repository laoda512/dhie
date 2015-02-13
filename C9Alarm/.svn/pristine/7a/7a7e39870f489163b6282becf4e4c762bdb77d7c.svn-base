package util;

import com.tavx.C9Alarm.R;

import android.widget.Toast;

public class ResponseManager {
	public String content;
	public int iconRes;
	
	public static final int INDEX_LAZY_BUTTON = 1;
	
	public ResponseManager(String c,int r){
		this.content=c;
		this.iconRes=r;
	}
	
	public static ResponseManager getResp(int index,int count){
		switch(index){
		default:
		case INDEX_LAZY_BUTTON:return getRespLazy(count);
		}
	}
	
	
	static String []Lazy = {
		"在设定->关于和帮助中可以查看相关问答哦~如果还有不明白的可以在反馈中提问",
		"目前没有贪睡闹钟，按了也不会有用哦~",
		"闹钟响起时选择贪睡模式就会激活贪睡闹钟",
		"贪睡闹钟激活时，按我可以关闭贪睡闹钟哟~",
		"所以说现在你再按我也。。",
		"喵~再按人家就坏了啦~",
		"按吧按吧按吧，反正屏幕坏了人家也不会负责的",
		"(｀・ω・´)找图片找音乐找资源好麻烦",
		"开发应用也很麻烦啊~",
		"定时炸弹已经启动，10分钟后准时爆炸，请把手机设为飞行模式后扔出窗外"
		
	};
	
	static String []Brave = {"已成功使用支付宝支付9.99美元，多谢惠顾",
			"每天看一遍，勇敢一整年",
			"爱情公寓4,不愿说再见",
			"<警告>当前植入广告已经被屏蔽,请联系相关工作人员",
			"话说你到底付了多少9.99美元了",
			"看够了没有，人家才没有特别想让你看",
			"(｀・ω・´)视频剪辑好麻烦",
			"开发应用也很麻烦啊~",
			"看了这么多遍你还是没有变的勇敢吗！\n(╯°口°)╯(┴—┴"
			};
	
	static String []None = {"账户余额不足，无法播放",
		"讨厌就算你再点也不会有的啦",
		"好痒。。",
		"已成功使用支。。(╯°口°)╯(┴—┴ 说了不能播放啦",
		"不行就是不行！",
		"美嘉好萌，子乔也好萌…^ ^",
		"悄悄告诉你一个秘密，其实我就是那台电冰箱哦",
		"愚蠢的人类。。",
		"就算你再点好感也不会增加的啦！",
		"好啦好啦我知道啦，我去催微波炉君早日更新"};
	
	static String []Clam = {"已成功使用支付宝支付99.99美元，多谢惠顾",
		"什么你说为什么标价9.99却收费99.99?",
		"就算你问我我也。。",
		"裙子撕掉撕掉~",
		"林州市励志师离职时拉着手",
		"再次扣除99.99美刀，土豪我们做蓝盆友吧！",
		"悄悄告诉你一个秘密，其实隔壁就是那台电冰箱哦",
		"神马,神马（￣へ￣）",
		};
	
	static int ICON_DRAWABLE[]={R.drawable.logo};
	
	private static int getIcon(int count){
		return ICON_DRAWABLE[(int)(Math.random()*ICON_DRAWABLE.length)];
	}
	
	private static ResponseManager getRespLazy(int count) {
		int index=0;
		if(count>2&&count<10){
			index=((int) (Math.random()*4));
		}else if(count>=10&&count<20){
			index=((int) (Math.random()*5));
		}else if(count>=30&&count<50){
			index=((int) (Math.random()*8));
		}else if(count>=50&&count<80){
			index=((int) (Math.random()*10));
		}
		ResponseManager mResponseManager=new ResponseManager(Lazy[index], getIcon(count));
		return mResponseManager;
	}

	
	
	private static ResponseManager getRespBrave(int count) {
		int index=0;
		if(count>2&&count<10){
			index=((int) (Math.random()*3));
		}else if(count>=10&&count<20){
			index=((int) (Math.random()*5));
		}else if(count>=30&&count<50){
			index=((int) (Math.random()*7));
		}else if(count>=50&&count<80){
			index=((int) (Math.random()*9));
		}
		ResponseManager mResponseManager=new ResponseManager(Brave[index], getIcon(count));
		return mResponseManager;
	}

	private static ResponseManager getRespNone(int count) {
		int index=0;
		if(count>2&&count<10){
			index=((int) (Math.random()*3)+1);
		}else if(count>=10&&count<20){
			index=((int) (Math.random()*5)+1);
		}else if(count>=30&&count<50){
			index=((int) (Math.random()*7)+1);
		}else {
			index=((int) (Math.random()*9)+1);
		}
		ResponseManager mResponseManager=new ResponseManager(None[index], getIcon(count));
		return mResponseManager;
	}
	
	private static ResponseManager getRespClam(int count) {
		int index=0;
		if(count>2&&count<10){
			index=((int) (Math.random()*3));
		}else if(count>=10&&count<20){
			index=((int) (Math.random()*5));
		}else if(count>=30&&count<50){
			index=((int) (Math.random()*7));
		}else if(count>=50&&count<80){
			index=((int) (Math.random()*8));
		}
		ResponseManager mResponseManager=new ResponseManager(Clam[index], getIcon(count));
		return mResponseManager;
	}

	

}
