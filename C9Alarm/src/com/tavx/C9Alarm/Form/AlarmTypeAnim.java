/**
 * 
 */
package com.tavx.C9Alarm.Form;

import org.xmlpull.v1.XmlPullParser;

import util.ResXmlReader;
import util.SDResReadManager;
import util.ThemeRes;
import util.AnimationsContainer.AnimContainer;

/**
 * @author Administrator
 *
 */
public class AlarmTypeAnim extends AlarmType{
	
	int slideThumb = 0;
	int slideTrack = 0;
	int slideMask = 0;
	int slideLeft = 0;
	int slideRight = 0;
	private int alarmMusic;
	private int alarmAnim;
	
	
	public AlarmTypeAnim() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static AlarmType parse(XmlPullParser anmiParser,ThemeRes mThemeRes){
		AlarmTypeAnim mAlarmTypeAnim = new AlarmTypeAnim();
		String alarm_anim  =anmiParser.getAttributeValue(null, "alarm_anim");
		String alarm_music  =anmiParser.getAttributeValue(null, "alarm_music");
		String slide_thumb  =anmiParser.getAttributeValue(null, "slide_thumb");
		String slide_track  =anmiParser.getAttributeValue(null, "slide_track");
		String slide_mask  =anmiParser.getAttributeValue(null, "slide_mask");
		String slide_left  =anmiParser.getAttributeValue(null, "slide_left");
		String slide_right  =anmiParser.getAttributeValue(null, "slide_right");
		
		
		alarm_anim = checkIsNull(alarm_anim);
		alarm_music = checkIsNull(alarm_music);
		slide_thumb = checkIsNull(slide_thumb);
		slide_track = checkIsNull(slide_track);
		slide_mask = checkIsNull(slide_mask);
		slide_left = checkIsNull(slide_left);
		slide_right = checkIsNull(slide_right);

		int keyAlarmAnim = mThemeRes.addRes(alarm_anim);
		//mAlarmTypeAnim.setAlarmAnim(mThemeRes.addRes(alarm_anim));
		mAlarmTypeAnim.setAlarmMusic(mThemeRes.addRes(alarm_music));
		mAlarmTypeAnim.setSlideValue(mThemeRes.addRes(slide_thumb), mThemeRes.addRes(slide_track), mThemeRes.addRes(slide_mask), mThemeRes.addRes(slide_left), mThemeRes.addRes(slide_right));
		
		AnimContainer[]mAlarmAnim =ResXmlReader.anmiParser(SDResReadManager.getInstance().getResXml(keyAlarmAnim, mThemeRes.getThemeName()),mThemeRes);

		mAlarmTypeAnim.setAlarmAnim(mThemeRes.addRes(mAlarmAnim));
		mAlarmTypeAnim.setType(TYPE_ANIM);
		return mAlarmTypeAnim;
	}
	
	public AlarmTypeAnim(int slideThumb, int slideTrack, int slideMask,
			int slideLeft, int slideRight, int alarmMusic, int alarmAnim) {
		super();
		setSlideValue(slideThumb, slideTrack, slideMask, slideLeft, slideRight);
		this.alarmMusic = alarmMusic;
		this.alarmAnim = alarmAnim;
	}

	public  static String checkIsNull(String res){
		if(res==null||res.equals("")||res.toLowerCase().equals("null")||res.equals("-1")){
			return null;
		}
		return res;
	}
	
	
	public void setSlideValue(int slideThumb, int slideTrack, int slideMask,
			int slideLeft, int slideRight) {
		if(slideThumb!=-1)
		this.slideThumb = slideThumb;
		if(slideTrack!=-1)
		this.slideTrack = slideTrack;
		if(slideMask!=-1)
		this.slideMask = slideMask;
		if(slideLeft!=-1)
		this.slideLeft = slideLeft;
		if(slideRight!=-1)
		this.slideRight = slideRight;
	}
	

	public int getSlideThumb() {
		return slideThumb;
	}

	public int getSlideTrack() {
		return slideTrack;
	}

	public int getSlideMask() {
		return slideMask;
	}

	public int getSlideLeft() {
		return slideLeft;
	}

	public int getSlideRight() {
		return slideRight;
	}
	
	public int getAlarmMusic() {
		return alarmMusic;
	}

	public void setAlarmMusic(int alarmMusic) {
		this.alarmMusic = alarmMusic;
	}

//	public int getAlarmAnim() {
//		return alarmAnim;
//	}
	
	public <T> T getAlarmAnim() {
		if(mForm==null) return null;
		return mForm.getResObject(alarmAnim);
	}

	public void setAlarmAnim(int alarmAnim) {
		this.alarmAnim = alarmAnim;
	}

	
}
