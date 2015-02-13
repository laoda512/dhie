package com.tavx.C9Alarm.Form;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.AlarmApplication;
import com.tavx.C9Alarm.Media;
import com.tavx.C9Alarm.R;
import com.tavx.C9Alarm.SleepText;
import com.tavx.C9Alarm.R.drawable;
import com.tavx.C9Alarm.R.raw;

import util.BitmapWorker;
import util.SDResReadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class Form {

	private int btnAnim;
	//private int alarmMusic;
	private int btnBeep;

	static Form[] mForm;

	private int titleImg;
	private String titleName;

	private int alarmSuccessBackTitle;
	private int alarmFailBackTitle;
	private int alarmSuccessBackground;
	private int alarmFailBackground;

	private int settingPageBg;
	private int settingPageHolePadBg;
	private int settingPageTopBtnBg;
	private int settingPageTagBg;

	private int settingPageHolePadColorFilter;
	private int settingPageTopBtnColorFilter;
	private int settingPageTagColorFilter;

	

	private int cirtlResOn;// 首页圆圈状态按钮资源，点亮状态
	private int cirtlResOff;// 首页圆圈状态按钮资源，未点亮状态
	private int cirblResOn;// 首页圆圈状态按钮资源，点亮状态
	private int cirblResOff;// 首页圆圈状态按钮资源，未点亮状态
	private int cirtrResOn;// 首页圆圈状态按钮资源，点亮状态
	private int cirtrResOff;// 首页圆圈状态按钮资源，未点亮状态
	private int cirbrResOn;// 首页圆圈状态按钮资源，点亮状态
	private int cirbrResOff;// 首页圆圈状态按钮资源，未点亮状态

	SleepText mSleepText;
	private int headIcon;

	public SleepText getmSleepText() {
		return mSleepText;
	}
	
	public void setSleepText(SleepText s){
		mSleepText = s;
	}

	public int getSettingPageBg() {
		return settingPageBg;
	}

	public void setSettingPageBg(int settingPageBg) {
		if(settingPageBg==-1) return;
		this.settingPageBg = settingPageBg;
	}
	
	public InputStream getSettingPageBgStream() {
		return getResInputStream(settingPageBg);
	}

	public int getSettingPageHolePadBg() {
		return settingPageHolePadBg;
	}

	public InputStream getSettingPageHolePadBgStream() {
		return getResInputStream(settingPageHolePadBg);
	}

	
	public void setSettingPageHolePadBg(int settingPagePad) {
		if(settingPagePad==-1) return;
		this.settingPageHolePadBg = settingPagePad;
	}

//	public int getSettingPageTopBtnBg() {
//		return settingPageTopBtnBg;
//	}
	
	public InputStream getSettingPageTopBtnBgStream() {
		return getResInputStream(settingPageTopBtnBg);
	}
	
	public void setSettingPageTopBtnBg(int settingPagePadTop) {
		if(settingPagePadTop==-1) return;
		this.settingPageTopBtnBg = settingPagePadTop;
	}

//	public int getSettingPageTagBg() {
//		return settingPageTagBg;
//	}
	
	public InputStream getSettingPageTagBgStream() {
		return getResInputStream(settingPageTagBg);
	}
/**
 * @param
 * what {@link android.R.attr.state_checked}  {@link android.R.attr.state_pressed}   
 * 
 * */
	public Drawable getSettingPageTagDrawable(View v, int what) {
		switch (what) {
		case android.R.attr.state_pressed:
			return getSelectorImage(R.drawable.style_common_choosebutton,
					R.drawable.style_common_choosebutton_pressd, v);
		case android.R.attr.state_checked:
			return getCheckedImage(R.drawable.style_common_choosebutton,
					R.drawable.style_common_choosebutton_pressd, v);
		default:
			return getSelectorImage(R.drawable.style_common_choosebutton,
					R.drawable.style_common_choosebutton_pressd, v);
		}

	}
	
	public Drawable getSettingPageTopBtnBgDrawable(View v, int what) {
		switch (what) {
		case android.R.attr.state_pressed:
			return getSelectorImage(R.drawable.btn_alarmset_on_click,
					R.drawable.btn_alarmset_normal , v);
		
		default:
			return getSelectorImage(R.drawable.btn_alarmset_on_click,
					R.drawable.btn_alarmset_normal , v);
		}
	}

	public Drawable getSettingPageTopBtnBgDrawable(int width,int height, int what) {
		switch (what) {
		case android.R.attr.state_pressed:
			return getSelectorImage(R.drawable.btn_alarmset_normal,
					R.drawable.btn_alarmset_on_click, width,height);
		
		default:
			return getSelectorImage(R.drawable.btn_alarmset_normal,
					R.drawable.btn_alarmset_on_click, width,height);
		}
	}

	
	
	public void setSettingPageTagBg(int settingPageTag) {
		if(settingPageTag==-1) return;
		this.settingPageTagBg = settingPageTag;
	}

	public int getAlarmSuccessBackTitle() {
		return alarmSuccessBackTitle;
	}

	public void setAlarmSuccessBackTitle(int alarmSuccessBackTitle) {
		this.alarmSuccessBackTitle = alarmSuccessBackTitle;
	}

	public int getAlarmFailBackTitle() {
		return alarmFailBackTitle;
	}

	public void setAlarmFailBackTitle(int alarmFailBackTitle) {
		this.alarmFailBackTitle = alarmFailBackTitle;
	}

	public int getAlarmSuccessBackground() {
		return alarmSuccessBackground;
	}

	public void setAlarmSuccessBackground(int alarmSuccessBackground) {
		this.alarmSuccessBackground = alarmSuccessBackground;
	}

	public int getAlarmFailBackground() {
		return alarmFailBackground;
	}

	public void setAlarmFailBackground(int alarmFailBackground) {
		this.alarmFailBackground = alarmFailBackground;
	}

	public int getBtnBeep() {
		return btnBeep;
	}

	public void setBtnBeep(int mBtnBeep) {
		this.btnBeep = mBtnBeep;
		//loadSound();
	}

	//private int alarmAnim;
//	private int backGround;
	/**
	 * 
	 */
	private Object settingPageSetButtonSelector;

	public void setSettingPageSetButtonSelector(int settingPageSetButtonSelector[]) {
		if(settingPageSetButtonSelector==null) return;
		this.settingPageSetButtonSelector = settingPageSetButtonSelector;
	}
	public void setSettingPageSetButtonSelector(int settingPageSetButtonSelector ) {
		if(settingPageSetButtonSelector==-1) return;
		this.settingPageSetButtonSelector = settingPageSetButtonSelector;
	}
	
	

	public StateListDrawable getSettingPageSetButtonSelector() {
		int[] selector;
		if(settingPageSetButtonSelector instanceof int[]){
			selector = (int[]) settingPageSetButtonSelector;
		}else{
			selector = (int[]) SDResReadManager.getInstance().getResObject((Integer) settingPageSetButtonSelector, titleName);
		}
		
		if (selector == null)
			return null;
		return getSelectorColor(selector[0],
				selector[1]);
	}


	public <T> T getBtnAnim() {
		if(isNative) return (T)new Integer(btnAnim);
		return getResObject(btnAnim);
	}

	public void setBtnAnim(int mBtnAnim) {
		this.btnAnim = mBtnAnim;
	}

	

//	public int getAlarmAnim() {
//		return alarmAnim;
//	}
	
	
//
//	public static void inialSound(Context c) {
//		Media m = Media.getInstance(c);
//		// m.loadSoundPoolRes(R.raw.beep, R.raw.beep);
//		m.loadSoundPoolRes(R.raw.lufi, R.raw.lufi);
//		m.loadSoundPoolRes(R.raw.habi_ai, R.raw.habi_ai);
//		m.loadSoundPoolRes(R.raw.ailusha_button_click,
//				R.raw.ailusha_button_click);
//		m.loadSoundPoolRes(R.raw.btn_yindoki, R.raw.btn_yindoki);
//		m.loadSoundPoolRes(R.raw.bilibili, R.raw.bilibili);
//		m.loadSoundPoolRes(R.raw.btn_beep_saber, R.raw.btn_beep_saber);
//		m.loadSoundPoolRes(R.raw.btn_beep_libailu, R.raw.btn_beep_libailu);
//		m.loadSoundPoolRes(R.raw.btn_beep_nazi, R.raw.btn_beep_nazi);
//		m.loadSoundPoolRes(R.raw.btn_beep_chudai, R.raw.btn_beep_chudai);
//		m.loadSoundPoolRes(R.raw.beep_mabi, R.raw.beep_mabi);
//		m.loadSoundPoolRes(R.raw.btn_beep_mianma, R.raw.btn_beep_mianma);
//		m.loadSoundPoolRes(R.raw.btn_beep_pikaqiu, R.raw.btn_beep_pikaqiu);
//	}

	int soundKey;
	public void loadSound(){
		//Media m = Media.getInstance(AlarmApplication.getApp());
		//soundKey = m.loadSoundPoolRes(SDResReadManager.getInstance().getResPathWithName(btnBeep, titleName));
	} 
	public int getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(int titleImg) {
		this.titleImg = titleImg;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	/*
	 * public Form(int mainPic, int alarmMusic, int clickSound, int titleImg,
	 * String titleName, int alarmAnim,int background,int successBackTitle,int
	 * failBackTitle,int successBackground,int failBackground) { super();
	 * this.mainPic = mainPic; this.alarmMusic = alarmMusic; this.clickSound =
	 * clickSound; this.titleImg = titleImg; this.titleName = titleName;
	 * this.alarmAnim = alarmAnim; this.backGround = 0;
	 * 
	 * alarmSuccessBackTitle = R.drawable.success;
	 * alarmFailBackTitle=R.drawable.fail; alarmSuccessBackground=0;
	 * alarmFailBackground=0; }
	 */

	

	/*
	 * public Form(int mainPic, int alarmMusic, int clickSound, int titleImg,
	 * String titleName, int alarmAnim, int background, int settingPageBg, int
	 * settingPagePad, int settingPagePadTop, int settingPageTag) { super();
	 * this.mainPic = mainPic; this.alarmMusic = alarmMusic; this.clickSound =
	 * clickSound; this.titleImg = titleImg; this.titleName = titleName;
	 * this.alarmAnim = alarmAnim; this.backGround = background;
	 * 
	 * this.settingPageBg = settingPageBg; this.settingPagePad = settingPagePad;
	 * this.settingPagePadTop = settingPagePadTop; this.settingPageTag =
	 * settingPageTag;
	 * 
	 * cirtlResOn = R.drawable.pgcirtl;// 首页圆圈状态按钮资源，点亮状态 cirtlResOff =
	 * R.drawable.pgcirtlx;// 首页圆圈状态按钮资源，未点亮状态 cirblResOn =
	 * R.drawable.pgcirbl;// 首页圆圈状态按钮资源，点亮状态 cirblResOff =
	 * R.drawable.pgcirblx;// 首页圆圈状态按钮资源，未点亮状态 cirtrResOn =
	 * R.drawable.pgcirtr;// 首页圆圈状态按钮资源，点亮状态 cirtrResOff =
	 * R.drawable.pgcirtrx;// 首页圆圈状态按钮资源，未点亮状态 cirbrResOn =
	 * R.drawable.pgcirbr;// 首页圆圈状态按钮资源，点亮状态 cirbrResOff =
	 * R.drawable.pgcirbrx;// 首页圆圈状态按钮资源，未点亮状态
	 * 
	 * }
	 */
boolean isNative =false;
	public Form(int btnAnim, int alarmMusic, int mBtnBeep, String titleName,
			int alarmAnim, int settingPageBg, int settingPagePad,
			int settingPagePadTop, int settingPagePadColorFilter,
			int settingPagePadTopColorFilter, int settingPageTag,
			int settingPageTagColorFilter, int[] settingPagButtonSelector,
			SleepText mSleepText, int headIcon) {
		super();
		isNative = true;
		this.btnAnim = btnAnim;
	//	this.alarmMusic = alarmMusic;
		this.btnBeep = mBtnBeep;
		this.titleName = titleName;
	//	this.alarmAnim = alarmAnim;

		this.settingPageBg = settingPageBg;
		this.settingPageHolePadBg = settingPagePad;
		this.settingPageTopBtnBg = settingPagePadTop;
		this.settingPageTagBg = settingPageTag;

		this.settingPageHolePadColorFilter = settingPagePadColorFilter;
		this.settingPageTopBtnColorFilter = settingPagePadTopColorFilter;
		this.settingPageTagColorFilter = settingPageTagColorFilter;
		
		this.settingPageSetButtonSelector = settingPagButtonSelector;

		cirtlResOn = R.drawable.pgcirtl;// 首页圆圈状态按钮资源，点亮状态
		cirblResOn = R.drawable.pgcirbl;// 首页圆圈状态按钮资源，点亮状态
		cirtrResOn = R.drawable.pgcirtr;// 首页圆圈状态按钮资源，点亮状态
		cirbrResOn = R.drawable.pgcirbr;// 首页圆圈状态按钮资源，点亮状态

		
		this.mSleepText = mSleepText;
		this.headIcon = headIcon;
	}
	
	public Form() {
		this.btnAnim = -1;
		//this.alarmMusic = -1;
		this.btnBeep = -1;
		this.titleName = "";
		//this.alarmAnim = -1;

		this.settingPageBg = -1;
		this.settingPageHolePadBg = -R.drawable.bg_holepad_white;
		this.settingPageTopBtnBg = -1;
		this.settingPageTagBg = -1;

		this.settingPageHolePadColorFilter = -1;
		this.settingPageTopBtnColorFilter = -1;
		this.settingPageTagColorFilter = -1;
	}

	public static Object[] getAllForm(){
		return getFormList().values().toArray();
	}

	public static Form getForm(String formName) {
		if(getFormList()==null) inialize(AlarmApplication.getApp());
		if(getFormList().containsKey(formName)){
		return getFormList().get(formName);
		}else{
			AlarmApplication.getApp().showError("找不到主题："+formName);
			return getFormList().get(InialFormCount[0]);
		}
	}
	
	public static boolean hasForm(String formName) {
		if(getFormList()==null) inialize(AlarmApplication.getApp());
		if(getFormList().containsKey(formName)){
		return true;
		}else{
			return false;
		}
	}
	
	
	private static Map<String, Form> getFormList(){
		if(mList==null) inialize(AlarmApplication.getApp()); 
		return mList; 
	}
	

	public void playClickSound() {
		if (btnBeep != 0) {
			MyLogger.e("sound", ""+soundKey);
			Media.getInstance(null).playSound(SDResReadManager.getInstance().getResPathWithName(btnBeep, titleName));
		}
	}
	static Map<String,Form> mList ;
	public static String[] InialFormCount={"兵长","炮姐","Saber","洛奇"};
	
	public static void setForm(String name,Form f){
		getFormList().put(name, f);
	}
	
	public static void removeForm(String name){
		getFormList().remove(name);
	}
	
	public static void inialize(Context c) {
		//inialSound(c);
		mList = new HashMap<String,Form>();
		//mForm = new Form[formCount];
		 
		Form buffForm;
		buffForm = new Form(R.drawable.btn_anim_libailu,
				R.raw.alarm_libailu, R.raw.btn_beep_libailu, "兵长",
				R.drawable.alarm_anim_libailu, R.drawable.bg_libailu,
				R.drawable.bg_holepad_white, R.drawable.btn_alarmset_top,
				0xff9eaf9b, 0xffeffdf0,
				R.drawable.style_common_btn_alarmset_typechoose, 0xff9eaf9b,
				new int[] { 0xff6b390c, 0xffffffff }, new SleepText() {
					@Override
					public void iniString() {
						super.iniString();
						name = "利威尔";

						text_angry = new String[] {
								"怎么回事，[username]你这副连起床的斗志都没有的样子是谁教你的？不想好好努力的话乘早回老家结婚去吧！",
								"可恶，宝贵的时间又被[username]耽误了，再有下次的话你自己也应该清楚会有什么后果吧？我是绝对不会轻易饶了你的~哦！", };
						text_happy = new String[] {
								"哼，准时起床本来就是你的义务，有什么好得意的，赶紧去洗漱吧！",
								"就是这样子，早起早睡身体才会好。。。。什么？‘我是不是以前一直晚上不睡觉才会只有一米六’混蛋你再说一遍。。。揍飞你哦！", };
						text_cry = new String[] { "就算是我[typename]亲自叫你起床都没有效果嘛。。" };
					}
				}, R.drawable.headicon_libailu);

		buffForm.setAlarmPage(R.drawable.selectbar_btn_libailu,
				R.drawable.selectbar_bg_left_libailu,
				R.drawable.selectbar_bg_left_libailu,
				R.drawable.selectbar_bg_left_libailu,
				R.drawable.selectbar_bg_left_libailu,R.raw.alarm_libailu,R.drawable.alarm_anim_libailu);
		
		setForm(buffForm.getTitleName(), buffForm);
		
	//	setForm(FORM_FIRST,buffForm);
		
		buffForm = new Form(R.drawable.btn_anim_paojie,
				R.raw.alarm_paojie, R.raw.bilibili, "炮姐",
				R.drawable.anim_paojie, R.drawable.bg_paojie4,
				R.drawable.bg_holepad_white, R.drawable.btn_alarmset_top,
				0xfffedfe1, 0xfffedfe1,
				R.drawable.style_common_btn_alarmset_typechoose, 0xfffedfe1,
				new int[] { 0xffec97a8, 0xffffffff }, new SleepText() {
					@Override
					public void iniString() {
						super.iniString();
						name = "御坂美琴";

						text_angry = new String[] {
								"怎么，终于知道起床了么，哼，我才没有期待着你起床，只不过这样下去我也要迟到了而已！",
								"既然睡了懒觉，想必也有接受惩罚的觉悟了吧！..电波准备..哔哩哔哩..!", };
						text_happy = new String[] {
								"早~上~好，啊~没拿什么~才不是给你当早点的饼干呢~快点啦~要迟到了哟~",
								"皮卡皮卡~..啊讨厌，人家才不是什么电气老鼠呢~嘻嘻~早上好，今天也是崭新的一天哦~", };
						text_cry = new String[] { "就算是我[typename]亲自叫你起床都没有效果嘛。。" };
					}
				}, R.drawable.headicon_paojie);

		buffForm.setAlarmPage(R.drawable.selectbar_btn_paojie,
				R.drawable.rgroove_copy, R.drawable.rgroove_mask,
				R.drawable.selectbar_bg_left_paojie,
				R.drawable.selectbar_bg_right_paojie,R.raw.alarm_paojie,R.drawable.anim_paojie);
		//setForm(FORM_FIRST+1,buffForm);
		setForm(buffForm.getTitleName(), buffForm);
		
		buffForm = new Form(R.drawable.btn_anim_saber,
				R.raw.alarm_saber, R.raw.btn_beep_saber, "Saber",
				R.drawable.alarm_anim_saber, R.drawable.bg_saber,
				R.drawable.bg_holepad_white, R.drawable.btn_alarmset_top,
				0xfffeed87, 0xfffeed87,
				R.drawable.style_common_btn_alarmset_typechoose, 0xfffeed87,
				new int[] { 0xffffad00, 0xffffffff }, new SleepText() {
					@Override
					public void iniString() {
						super.iniString();
						name = "Saber";

						text_angry = new String[] {
								"Sevant Saber,遵从召唤而来,我问你,你是我的Master吗?..是吗,没想到这次的Master居然是个练起床都不能准时的人...",
								"睡眠也可以为英灵补充魔力,不过比起睡眠,我更喜欢靠吃东西来补充,Master如果魔力不足的话,就请准时起床给我准备早饭！", };
						text_happy = new String[] {
								"准时起床是一个优秀的Master的基本素养，[username]合格了哦~早上好，我的Master",
								"起床了吗？嗯,稍微有一些想起以前的事情,以前我所处的时代没有闹钟，所以我们也有自己的叫床方法，嗯？你问我是什么方法？呵呵，以后告诉你啦。。", };
						text_cry = new String[] { "就算是我[typename]亲自叫你起床都没有效果嘛。。" };
					}
				}, R.drawable.headicon_saber);

		buffForm.setAlarmPage(R.drawable.selectbar_btn_saber,
				R.drawable.selectbar_bg_left_saber,
				R.drawable.selectbar_bg_left_saber,
				R.drawable.selectbar_bg_left_saber,
				R.drawable.selectbar_bg_left_saber,R.raw.alarm_saber,R.drawable.alarm_anim_saber);
		
		setForm(buffForm.getTitleName(), buffForm);
		//setForm(FORM_FIRST+2,buffForm);
		
		buffForm= new Form(R.drawable.btn_anim_mabi,
				R.raw.alarm_mabi, R.raw.beep_mabi, "洛奇",
				R.drawable.alarm_anim_mabi, R.drawable.bg_mabi,
				R.drawable.bg_holepad_white, R.drawable.btn_alarmset_top,
				0xffcde0ff, 0xffcde0ff,
				R.drawable.style_common_btn_alarmset_typechoose, 0xff438ee2,
				new int[] { 0xff99aab6, 0xffffffff }, new SleepText() {
					@Override
					public void iniString() {
						super.iniString();
						name = "洛奇";

						text_angry = new String[] {
								"赖床了吗？[username]今晚早点入睡，明天争取准时起床吧~",
								"米莱西安也是有很多的，[username]连准时起床都做不到，怎么能打败其他同类，追到茉茉女神啊！小心最后追到内文大妈啊！", };
						text_happy = new String[] {
								"起床了吗~愿[username]在新的一天也能健健康康，开开心心",
								"就算是[username]这样的优秀米莱西安也要努力工作啊，准时起床是充满活力的一天的开始，加油！", };
						text_cry = new String[] { "就算是我[typename]亲自叫你起床都没有效果嘛。。" };
					}
				}, R.drawable.headicon_mabi);
		
		buffForm.setAlarmPage(-1,
				-1,
				-1,
				-1,
				-1,R.raw.alarm_mabi,R.drawable.alarm_anim_mabi);
		setForm(buffForm.getTitleName(), buffForm);
		//setForm(FORM_FIRST+3,buffForm);
		// mForm[FORM_FIRST + 3].setSlideValue(R.drawable.selectbar_btn_saber,
		// R.drawable.selectbar_bg_left_saber,
		// R.drawable.selectbar_bg_left_saber,
		// R.drawable.selectbar_bg_left_saber,
		// R.drawable.selectbar_bg_left_saber);

		// mForm[FORM_FIRST + 2].setSlideValue(R.drawable.selectbar_btn_paojie,
		// R.drawable.rgroove_copy, R.drawable.rgroove_mask,
		// R.drawable.selectbar_bg_left_paojie,
		// R.drawable.selectbar_bg_right_paojie);
		try {
			SDResReadManager.test();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(true)
			return;
/*
		buffForm = new Form(R.drawable.button_anim_yinsang,
				R.raw.alarm_yindoki, R.raw.btn_yindoki, "银时",
				R.drawable.yindoki_anim, R.drawable.yintama_bg,
				R.drawable.bg_holepad_white, R.drawable.btn_alarmset_top,
				0xffFCFAF2, 0xffFCFAF2,
				R.drawable.style_common_btn_alarmset_typechoose, 0xffFCFAF2,
				new int[] { 0xffa5dee4, 0xffffffff }, new SleepText() {
					@Override
					public void iniString() {
						super.iniString();
						name = "银时";

						text_angry = new String[] {
								"喂,可恶的小子，自己定下了闹钟就要准时听从闹钟的召唤，磨磨蹭蹭一天又这么荒废过去了，什么？就是多睡了一小会？一天还长着？一日之计在于晨，这么简单的道理都不懂么，好的，面壁10分钟！",
								"喂喂喂,打算睡到几点啊,都怪你,本来和客人约好的事情也泡汤了，这样下去这个月的房租又泡汤了，一想到粑粑的面孔...什么？你说这只是接口？我不好好挣钱？哎，小孩子一点都不懂大人的辛苦啊", };
						text_happy = new String[] {
								"Zzzzzz....啊,[username]今天也很精神啊Zzzzz...好困，让我再睡一会...嗯？嘛,大人晚上总是有很多应酬的嘛...",
								"啊，已经起床了吗，今天意外的很勤奋啊，银桑我还昏昏沉沉呢，一定是低血糖又犯了，红豆布丁，我要吃红豆布丁啊~~", };
						text_cry = new String[] { "就算是我[typename]亲自叫你起床都没有效果嘛。。" };
					}
				}, R.drawable.headicon_gintama);
		buffForm.setSlideValue(R.drawable.selectbar_btn_yindoki,
				R.drawable.selectbar_bg_left_yindoki,
				R.drawable.selectbar_bg_left_yindoki,
				R.drawable.selectbar_bg_left_yindoki,
				R.drawable.selectbar_bg_right_yindoki);
		//setForm(FORM_SECOND,buffForm);
		
		buffForm = new Form(R.drawable.btn_anim_mianma,
				R.raw.alarm_mianma, R.raw.btn_beep_mianma, "芽间",
				R.drawable.alarm_anim_mianma, R.drawable.bg_mianma,
				R.drawable.bg_holepad_white, R.drawable.btn_alarmset_top,
				0xffcde0ff, 0xffcde0ff,
				R.drawable.style_common_btn_alarmset_typechoose, 0xff66327C,
				new int[] { 0xff66327C, 0xffffffff }, new SleepText() {
					@Override
					public void iniString() {
						super.iniString();
						name = "芽间";

						text_angry = new String[] {
								"芽间..芽间呐，不喜欢不准时起床的[username]啦~因为..因为起床晚了妈妈会生气的吧~",
								"[username]今天赖床了呢..没有和人约好要一起玩么？同学..朋友..家人,迟到了，大家会很伤心的吧？就算有芽间陪着[username]，也不能忽略他们哟~", };
						text_happy = new String[] {
								"嘻嘻~现在只有[username]一个人看得到芽间哟,芽间呢~还没有和[username]说早安哟~",
								"讷讷~[username]说，芽间转生以后成为什么好呢？成为一个每天叫醒[username]的闹钟也没关系哟~因为[username]今天也有乖乖的准时起床哦~", };
						text_cry = new String[] { "就算是我[typename]亲自叫你起床都没有效果嘛。。" };
					}
				}, R.drawable.headicon_mianma);
//		buffForm.setSlideValue(R.drawable.selectbar_btn_yindoki,
//				R.drawable.selectbar_bg_left_yindoki,
//				R.drawable.selectbar_bg_left_yindoki,
//				R.drawable.selectbar_bg_left_yindoki,
//				R.drawable.selectbar_bg_right_yindoki);
		//setForm(FORM_SECOND+1,buffForm);

		buffForm = new Form(R.drawable.btn_anim_pikaqiu ,
				R.raw.alarm_pikaqiu, R.raw.btn_beep_pikaqiu, "皮卡丘",
				R.drawable.alarm_anim_pikaqiu, R.drawable.bg_pikaqiu,
				R.drawable.bg_holepad_white, R.drawable.btn_alarmset_top,
				0xffffffff, 0xffffffff,
				R.drawable.style_common_btn_alarmset_typechoose, 0xff62592C,
				new int[] { 0xff62592C, 0xffffffff }, new SleepText() {
					@Override
					public void iniString() {
						super.iniString();
						name = "皮卡丘";

						text_angry = new String[] {
								"皮~~~~~卡~~~~皮~~~~~~~~卡~~~~~Zzzzzz",
								"哎哟少年你再不起床我就点你了哟皮卡~皮卡？为什么我会讲话？啊哈哈哈哈哈，嘛，大概你还没睡醒吧。。", };
						text_happy = new String[] {
								"皮卡皮卡丘~知道为什么我不喜欢进精灵球么~因~为~[typename]想陪在[username]身边皮卡~早上好",
								"皮丘皮卡丘雷丘~[username]喜欢哪一个呢？有些皮卡丘讨厌进化成雷丘，其实是因为汲取雷之石的力量的时候，会觉得痛苦啦", };
						text_cry = new String[] { "就算是我[typename]亲自叫你起床都没有效果嘛。。" };
					}
				}, R.drawable.headicon_pikaqiu);
//		buffForm.setSlideValue(R.drawable.selectbar_btn_yindoki,
//				R.drawable.selectbar_bg_left_yindoki,
//				R.drawable.selectbar_bg_left_yindoki,
//				R.drawable.selectbar_bg_left_yindoki,
//				R.drawable.selectbar_bg_right_yindoki);
		//setForm(FORM_SECOND+2,buffForm);
		
		mForm[FORM_SECOND + 3] = null;

		buffForm = new Form(R.drawable.button_anim_lufi,
				R.raw.lufi_dream, R.raw.lufi, "路飞 ", R.drawable.lufi_anim,
				R.drawable.bg_lufi, R.drawable.bg_holepad_white,
				R.drawable.btn_alarmset_top, 0xff95424a, 0xfffddb94,
				R.drawable.style_common_btn_alarmset_typechoose, 0xffdecbad,
				new int[] { 0xff99336d, 0xffffffff }, new SleepText() {
					@Override
					public void iniString() {
						super.iniString();
						name = "路飞";

						text_angry = new String[] {
								"喂~[username]，你不会和乌索普一样得了一种不能起床的病吧！！ ",
								"啊？嘛人生就是需要好好享受睡眠Zzzzzz", };
						text_happy = new String[] {
								"索隆，乔巴，快起来！去征服新世界！[username]已经起床在等你们了！",
								"一档~二档~三档~好~我要成为[username]的男..啊不对，我要成为海贼王的男人！！！", };
						text_cry = new String[] { "就算是我[typename]亲自叫你起床都没有效果嘛。。" };
					}
				}, R.drawable.headicon_lufi);
		//setForm(FORM_3RD,buffForm);
		mForm[FORM_3RD + 1] = null;
		mForm[FORM_3RD + 2] = null;
		mForm[FORM_3RD + 3] = null;

		
		 * mForm[FORM_4TH] = new Form(R.drawable.button_anim_ailusa,
		 * R.raw.ptest, R.raw.ailusha_button_click,
		 * R.drawable.pgmain_lizi_title, "ailusa",
		 * R.drawable.of_native_loader_progress, 0, R.drawable.bg_ailusa,
		 * R.drawable.bg_holepad_white, R.drawable.btn_alarmset_top, 0xff5fbcdd,
		 * 0xffc8fffe,
		 * R.drawable.style_common_btn_alarmset_typechoose,0xff31ced8,new int[]
		 * { 0xff7c5c5d, 0xffffff }, new
		 * SleepText(),R.drawable.ailusa_headicon);
		 

		// StateListDrawable d = getSelector(0x7f070003,
		// AlarmApplication.getApp().getResources().getColor(R.color.transparentWhite));
		buffForm = new Form(R.drawable.button_anim_firedragon,
				R.raw.bgm_firedragon, R.raw.btn_beep_nazi, "纳兹",
				R.drawable.firedragon_anim, R.drawable.bg_firedragon,
				R.drawable.bg_holepad_white, R.drawable.btn_alarmset_top,
				0xffffa800, 0xffffffff,
				R.drawable.style_common_btn_alarmset_typechoose, 0xffffa800,
				new int[] { 0xff000000, 0xffffffff }, new SleepText() {
					@Override
					public void iniString() {
						super.iniString();
						name = "纳兹";

						text_angry = new String[] {
								"哦！[username]！起来啦！起来啦！起来啦！这下惨了，起得那么晚，等下又要被艾露莎骂了。。",
								"火龙~~的~~瞌睡。。。呼呼呼~~啊~人生嘛~就是需要享受睡眠...哦啊！糟了,才想起来今天和露西约好了..！", };
						text_happy = new String[] {
								"哦~今天也是准时早起~看到[username]这么有活力的样子我也燃起来了啊！",
								"啊哈哈哈哈！[username]已经准时起床了，格雷这家伙肯定还在呼呼大睡，赶紧去嘲笑一下他~", };
						text_cry = new String[] { "就算是我[typename]亲自叫你起床都没有效果嘛。。" };
					}
				}, R.drawable.headicon_nazi);

		buffForm.setSlideValue(R.drawable.selectbar_btn_nazi,
				R.drawable.selectbar_bg_left_nazi,
				R.drawable.selectbar_bg_left_nazi,
				R.drawable.selectbar_bg_left_nazi,
				R.drawable.selectbar_bg_left_nazi);
		//setForm(FORM_4TH,buffForm);
		buffForm = new Form(R.drawable.button_anim_habi, R.raw.habistall,
				R.raw.beep_mabi, "哈比", R.drawable.habi_anim, R.drawable.bg_habi,
				R.drawable.bg_holepad_white, R.drawable.btn_alarmset_top,
				0xff5fbcdd, 0xff92cbde,
				R.drawable.style_common_btn_alarmset_typechoose, 0xffffffff,
				new int[] { 0xff3f7088, 0xffffffff }, new SleepText() {
					@Override
					public void iniString() {
						name = "哈比";
						face_cry = new String[] { "T.T", "(￣﹏￣)", "(￣.￣)",
								"(⊙ˍ⊙)", "╮(╯▽╰)╭", "(＞﹏＜)", "(￣o￣) . z Z",
								"<(￣ ﹌ ￣)> ", "∪ˍ∪", "∪０∪", "∪︿∪", "∪ε ∪",
								"∪ 3∪", "∪ω∪", "∪﹏∪", "∪△∪", "∪▽∪", "○|￣|_" };
						face_happy = new String[] { "(๑￫ܫ￩)", "(｡・`ω´･)",
								"(・(ｪ)・)", "(σ｀・д･)σ", "(oﾟωﾟo)", "(≧3≦)",
								"(≧ω≦)", "o(≧ω≦)o", "o(≧o≦)o ", "( ° ▽、° )" };
						face_angry = new String[] { "╭∩╮_()︿﹀﹀_╭∩╮", "（*－.－）",
								" Σ( ￣□￣；)", "(/≡ _ ≡)/~┴┴ ", "（#－.－）",
								" (╬￣皿￣)凸 ", "(￣ε(#￣)☆╰╮o(￣皿￣///)",

								"(￣ε(#￣)☆╰╮o()︿)///)",

								"( ￣ ▽￣)o╭╯☆#╰ _─﹏─)╯",

								"( ￣ ▽￣)o╭╯☆#╰( ￣﹏￣)╯"

						};
						text_angry = new String[] {
								"[username]放弃了起床挑战~咕~[username]你到底有没有赖床呢",
								"。。哼，人家也没有期待着你准时起床啦" };
						text_happy = new String[] {
								"爱~[username]起床成功了呐~呐呐，[username]早上好，一起来吃鱼吧~",
								"纳兹以前和我说~早起早睡身体好，明天也要和[typename]一起准时起床哦",
								"呐呐，[username]酱轻一点哦，夏露露还在睡觉呢~" };
						text_cry = new String[] { "[username]起床失败了~下次要加油哦" };
					}
				}, R.drawable.habi_headicon);

		buffForm.setSlideValue(R.drawable.selectbar_btn_habi,
				R.drawable.selectbar_bg_left_habi,
				R.drawable.selectbar_bg_left_habi,
				R.drawable.selectbar_bg_left_habi,
				R.drawable.selectbar_bg_left_habi);
		//setForm(FORM_4TH + 1,buffForm);
		buffForm = new Form(R.drawable.btn_anim_chudai,
				R.raw.alarm_chudai, R.raw.btn_beep_chudai, "初代",
				R.drawable.alarm_anim_chudai, R.drawable.bg_chudai,
				R.drawable.bg_holepad_white, R.drawable.btn_alarmset_top,
				0xffffffff, 0xffffffff,
				R.drawable.style_common_btn_alarmset_typechoose, 0xffE87A90,
				new int[] { 0xffE87A90, 0xffffffff }, new SleepText() {
					@Override
					public void iniString() {
						super.iniString();
						name = "梅比斯";
						text_angry = new String[] {
								"[username]有没有好好准时起床嘛，妖精的尾巴的成员从来都不赖床！....Zzzzzz",
								"聚集吧！引导妖精的光之川啊！照亮吧！为了消灭睡懒觉的[username]！ 妖精的光辉！...咦，什么都没有发生，啊呀，借给小卡娜酱了.。。" };
						text_happy = new String[] {
								"呼呼~[username]今天也有准时起床哟，能登阿姨我...哦不是，[typename]大人我很开心哦~",
								"妖精尾巴三大魔法之一，妖精法律的创作灵感就是来源于太阳的光辉普照大地哟~清晨的阳光洒在身上，所有的负面情绪仿佛都被消灭了呢~",
								"虽然是幽灵，可是我并不怕阳光哟~所以[username]酱也要一直坚持准时起床，和我一起迎接朝阳~！" };
						text_cry = new String[] { "就算是我[typename]亲自叫你起床都没有效果嘛。。" };
					}
				}, R.drawable.headicon_chudai);

		buffForm.setSlideValue(R.drawable.selectbar_btn_chudai,
				R.drawable.selectbar_bg_left_chudai,
				R.drawable.selectbar_bg_left_chudai,
				R.drawable.selectbar_bg_left_chudai,
				R.drawable.selectbar_bg_left_chudai);*/
		//setForm(FORM_4TH + 2,buffForm);
	}

	public static StateListDrawable getSelectorColor(int resPressd,
			int resNormal) {

		StateListDrawable states = new StateListDrawable();
		states.addState(new int[] { android.R.attr.state_pressed },
				new ColorDrawable(resPressd));
		states.addState(new int[] { android.R.attr.state_focused },
				new ColorDrawable(resNormal));
		states.addState(new int[] {}, new ColorDrawable(resNormal));
		return states;
	}

	public static StateListDrawable getSelectorImage(int resNormal,
			int resPressd , int width,int height) {
		MyLogger.e("bitmap", "getSelectorImage" ,true);
		Drawable normal = new BitmapDrawable(BitmapWorker.getBitmap(
				AlarmApplication.getApp(), resNormal, width,
				height));
		Drawable pressdd = new BitmapDrawable(BitmapWorker.getBitmap(
				AlarmApplication.getApp(), resPressd, width,
				height));
		StateListDrawable states = new StateListDrawable();
		states.addState(new int[] { android.R.attr.state_pressed }, pressdd);
		states.addState(new int[] { android.R.attr.state_focused }, normal);
		states.addState(new int[] {}, normal);
		return states;
		
	}
	
	
	public static StateListDrawable getSelectorImage(int resPressd,
			int resNormal, View v) {
		MyLogger.e("bitmap", "getSelectorImage" ,true);
		int width = v.getWidth();
		int height = v.getHeight();
		if(width==0){
			LayoutParams l= v.getLayoutParams();
			if(l!=null){
				width=l.width;
				height = l.height;
			}
			
		}
		return getSelectorImage(resPressd, resNormal, width, height);
		
	}

	public static StateListDrawable getCheckedImage(int resPressd,
			int resNormal, View v) {
		MyLogger.e("bitmap", "getCheckedImage" ,true);
		int width = v.getWidth();
		int height = v.getHeight();
		if(width==0){
			LayoutParams l= v.getLayoutParams();
			if(l!=null){
				width=l.width;
				height = l.height;
			}
		}
		Drawable normal = new BitmapDrawable(BitmapWorker.getBitmap(
				AlarmApplication.getApp(), resNormal,width,
				height));
		Drawable pressdd = new BitmapDrawable(BitmapWorker.getBitmap(
				AlarmApplication.getApp(), resPressd, width,
				height));
		StateListDrawable states = new StateListDrawable();
		states.addState(new int[] { android.R.attr.state_checked }, pressdd);
		states.addState(new int[] { android.R.attr.state_focused }, normal);
		states.addState(new int[] {}, normal);
		return states;
	}

	public int getCirRes(int location, boolean isOn) {
		if (isOn) {
			switch (location) {
			case TOP_LEFT:
				return cirtlResOn;
			case TOP_RIGHT:
				return cirtrResOn;
			case BOTTOM_LEFT:
				return cirblResOn;
			case BOTTOM_RIGHT:
				return cirbrResOn;
			}
		} else {
			switch (location) {
			case TOP_LEFT:
				return cirtlResOff;
			case TOP_RIGHT:
				return cirtrResOff;
			case BOTTOM_LEFT:
				return cirblResOff;
			case BOTTOM_RIGHT:
				return cirbrResOff;
			}
		}
		return 0;
	}

	static final int formCount = 4 + 3 * 4;
	public static final int FORM_NONE = -1;
	public static final int FORM_FIRST = 0;
	public static final int FORM_SECOND = 4;
	public static final int FORM_3RD = 8;
	public static final int FORM_4TH = 12;

	public static final int TOP_LEFT = 1;
	public static final int TOP_RIGHT = 2;
	public static final int BOTTOM_LEFT = 3;
	public static final int BOTTOM_RIGHT = 4;



	public static int getGetMoreTagBg() {
		return R.drawable.style_getmore_choosebutton;
	}

	public int getAlarmBgAnim() {
		return R.drawable.alarmborder_anim;// new
											// int[]{R.drawable.alarm_border};
	}

	public void setAlarmPage(int slideThumb, int slideTrack, int slideMask,
			int slideLeft, int slideRight,int alarmMusic,int alarmAnim) {

		this.setAlarmTypeBean(new AlarmTypeAnim(slideThumb, slideTrack, slideMask, slideLeft, slideRight, alarmMusic, alarmAnim));
	}



	public int getHeadIcon() {
		return headIcon;
	}
	
	public void setHeadIcon(int headicon) {
		this.headIcon= headicon;
	}
	
	
//	public int getSettingPageHolePadColorFilter() {
//		return settingPageHolePadColorFilter;
//	}
	
	public int getSettingPageHolePadColorFilter() {
		return getResInt(settingPageHolePadColorFilter);
	}
	
	
	
	public void setSettingPageHolePadColorFilter(int mSettingPageHolePadColorFilter) {
		if(mSettingPageHolePadColorFilter==-1) return;
		this. settingPageHolePadColorFilter = mSettingPageHolePadColorFilter;
	}

	public int getSettingPageTopBtnColorFilter() {
		return getResInt(settingPageTopBtnColorFilter);//settingPageTopBtnColorFilter;
	}

	public void setSettingPageTopBtnColorFilter(int settingPagePadColorFilter) {
		if(settingPagePadColorFilter==-1) return;
		this.settingPageTopBtnColorFilter = settingPagePadColorFilter;
	}

	public int getSettingPageTagColorFilter() {
		return getResInt(settingPageTagColorFilter);
	}

	public void setSettingPageTagColorFilter(int settingPageTagColorFilter) {
		if(settingPageTagColorFilter==-1) return;
		this.settingPageTagColorFilter = settingPageTagColorFilter;
	}
	
	private InputStream getResInputStream(int  res){
		return SDResReadManager.getInstance().getResInputStream(res, getTitleName());
	}
	
	private int getResInt(int  res){
		return SDResReadManager.getInstance().getResInt(res, getTitleName());
	}
	
	private XmlPullParser getResXml(int  res){
		return SDResReadManager.getInstance().getResXml(res, getTitleName());
	}
	
	<T> T getResObject(int  res){
		return SDResReadManager.getInstance().getResObject(res, titleName);
	}
	
	public static Bitmap getBitmapByRes(int res,String themeName,int width,int height){
		if(res==-1){return null;}
		if(res<0) return BitmapWorker.getBitmap(AlarmApplication.getApp(), -res, width, height);
		return SDResReadManager.getInstance().getResBitmap(res, themeName, width, height);
	}
	
	AlarmType mAlarmType;
	/**
	 * @param parse
	 */
	public void setAlarmTypeBean(AlarmType parse) {
		mAlarmType = parse;
		if(mAlarmType!=null)
		mAlarmType.setForm(this);
	}
	
	/**
	 * @param parse
	 */
	public AlarmType getAlarmTypeBean() {
		return mAlarmType ;
	}
	
}
