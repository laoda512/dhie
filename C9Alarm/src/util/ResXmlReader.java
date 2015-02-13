package util;

import java.io.IOException;
import java.math.BigInteger;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.tavx.C9Alam.connector.MyLogger;
import com.tavx.C9Alarm.SleepText;
import com.tavx.C9Alarm.Form.AlarmType;
import com.tavx.C9Alarm.Form.AlarmTypeAnim;
import com.tavx.C9Alarm.Form.AlarmTypeMovie;
import com.tavx.C9Alarm.Form.Form;

import util.AnimationsContainer.AnimContainer;
import android.content.res.XmlResourceParser;
import android.util.Log;

public class ResXmlReader {
	static int index = 0;
	/**
	 * @param args
	 * @throws IOException 
	 * @throws XmlPullParserException 
	 */
	public static ThemeRes parseResXml(XmlPullParser anmiParser,String path) throws XmlPullParserException, IOException {

		//	XmlPullParser anmiParser;
			int count = 0;
			AnimContainer[] anim = null;
		//	try {
				count = 0;
				Form mForm = new Form();
				ThemeRes mThemeRes = null ;
				
				int keybtnAnim = -1;
				int keyAlarmAnim = -1;
				int shareKey=-1;
				
				while (anmiParser.getEventType() != XmlResourceParser.END_DOCUMENT) {
					// 如果是开始标签
					if (anmiParser.getEventType() == XmlResourceParser.START_TAG) {
						String name = anmiParser.getName();
						if(name.equals("theme")){
							String theme_name  =anmiParser.getAttributeValue(null, "name");
							String respath =anmiParser.getAttributeValue(null, "respath");
							
							theme_name = checkIsNull(theme_name);
							respath = checkIsNull(respath);
							
							mForm.setTitleName(theme_name);
							mThemeRes= new ThemeRes(theme_name,path,respath);
							SDResReadManager.getInstance().appendRes(mThemeRes.getThemeName(), mThemeRes);
							
						}
						else if (name.equals("homepage")) {
							String btn_anim  =anmiParser.getAttributeValue(null, "btn_anim");
							String btn_beep  =anmiParser.getAttributeValue(null, "btn_beep");
							
							btn_anim = checkIsNull(btn_anim);
							btn_beep = checkIsNull(btn_beep);
							
							keybtnAnim = mThemeRes.addRes(btn_anim);
						//	mForm.setBtnAnim(mThemeRes.addRes(btn_anim));
							mForm.setBtnBeep(mThemeRes.addRes(btn_beep));
						}else if (name.equals("settingpage")) {
							String bg  =anmiParser.getAttributeValue(null, "bg");
							String hole_pad_bg  =anmiParser.getAttributeValue(null, "hole_pad_bg");
							String hole_pad_color  =anmiParser.getAttributeValue(null,"hole_pad_color");
							String set_btn_color_choosed  =anmiParser.getAttributeValue(null, "set_btn_color_choosed");
							String set_btn_color_normal  =anmiParser.getAttributeValue(null, "set_btn_color_normal");
							String top_btn_bg  =anmiParser.getAttributeValue(null, "top_btn_bg");
							String top_btn_color  =anmiParser.getAttributeValue(null, "top_btn_color");
							String tag_color  =anmiParser.getAttributeValue(null, "tag_color");
							String tag_bg  =anmiParser.getAttributeValue(null, "tag_bg");
							
							bg = checkIsNull(bg);
							hole_pad_bg = checkIsNull(hole_pad_bg);
							hole_pad_color = checkIsNull(hole_pad_color);
							set_btn_color_choosed = checkIsNull(set_btn_color_choosed);
							set_btn_color_normal = checkIsNull(set_btn_color_normal);
							top_btn_bg = checkIsNull(top_btn_bg);
							top_btn_color = checkIsNull(top_btn_color);
							tag_color = checkIsNull(tag_color);
							tag_bg = checkIsNull(tag_bg);
							
							mForm.setSettingPageBg(mThemeRes.addRes(bg));
							mForm.setSettingPageHolePadBg(mThemeRes.addRes(hole_pad_bg));
							mForm.setSettingPageHolePadColorFilter(mThemeRes.addRes(parseInt(hole_pad_color,16)));
							
							
							mForm.setSettingPageSetButtonSelector(mThemeRes.addRes(new int[]{parseInt(set_btn_color_choosed,16),parseInt(set_btn_color_normal,16)}));
							
							mForm.setSettingPageTopBtnBg(mThemeRes.addRes(top_btn_bg));
							mForm.setSettingPageTopBtnColorFilter(mThemeRes.addRes(parseInt(top_btn_color,16)));
							mForm.setSettingPageTagColorFilter(mThemeRes.addRes(parseInt(tag_color,16)));
							mForm.setSettingPageTagBg(mThemeRes.addRes(tag_bg));
							
						}else if (name.equals("alarm")) {
							String type  =anmiParser.getAttributeValue(null, "type");
							if(type==null){
								type = "anim";
							}
							if(type.equals("movie")){
								mForm.setAlarmTypeBean(AlarmTypeMovie.parse(anmiParser, mThemeRes));
							}else{
								mForm.setAlarmTypeBean(AlarmTypeAnim.parse(anmiParser, mThemeRes));
							}
							
							
						}else if (name.equals("share")) {
							String share  =anmiParser.getAttributeValue(null, "share");
							shareKey = mThemeRes.addRes(share);
							//mForm.setAlarmAnim(mThemeRes.addRes(alarm_anim));
							
						}
					} else if (anmiParser.getEventType() == XmlResourceParser.END_TAG) {
					} else if (anmiParser.getEventType() == XmlResourceParser.TEXT) {
					}
					// 下一个标签
					anmiParser.next();
				}
				Form.setForm(mForm.getTitleName(), mForm);
				
				
				AnimContainer[]mbtnAnim =anmiParser(SDResReadManager.getInstance().getResXml(keybtnAnim, mThemeRes.getThemeName()),mThemeRes);
				mForm.setBtnAnim(mThemeRes.addRes(mbtnAnim));
				//AnimContainer[]mAlarmAnim =anmiParser(SDResReadManager.getInstance().getResXml(keyAlarmAnim, mThemeRes.getThemeName()),mThemeRes);
				SleepText mSleepText = shareParser(SDResReadManager.getInstance().getResXml(shareKey, mThemeRes.getThemeName()),mThemeRes);
				if(mSleepText!=null){
					mForm.setSleepText(mSleepText);
					if(mSleepText.getHappy_icon()!=null)
					mForm.setHeadIcon(mThemeRes.addRes(mSleepText.getHappy_icon()));
				}
				
				else{
					mForm.setSleepText(new SleepText());
				}
				
				if(mbtnAnim==null||mForm.getAlarmTypeBean()==null){
					MyLogger.e("parser", "error_"+mbtnAnim+" "+mForm.getAlarmTypeBean());
					SDResReadManager.getInstance().removeRes(mThemeRes.getThemeName());
					Form.removeForm(mThemeRes.getThemeName());
				}
				
				return mThemeRes;
//			} catch (XmlPullParserException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
	}
	
	public static AnimContainer[] anmiParser(XmlPullParser anmiParser,ThemeRes mThemeRes) {
		//	XmlPullParser anmiParser;
		if(anmiParser==null){
			return null;
		} 
			int count = 0;
			AnimContainer[] anim = null;
			try {
				count = 0;
				while (anmiParser.getEventType() != XmlResourceParser.END_DOCUMENT) {
					// 如果是开始标签
					if (anmiParser.getEventType() == XmlResourceParser.START_TAG) {
						// 获取标签名称
						String name = anmiParser.getName();
						if(name.equals("animation-list")){
						 int	total  =Integer.parseInt(anmiParser.getAttributeValue(null, "total"));
							 anim = new AnimContainer[total];
						}
						
						// 判断标签名称是否等于friend
						if (name.equals("item")) {
			 				String res = (anmiParser.getAttributeValue(
									null,
			 						"drawable"));
							int duration = Integer.parseInt(anmiParser.getAttributeValue(
									null,
									"duration"));  
							int resKey = mThemeRes.addRes(res);
							anim[count] = new AnimContainer(resKey, duration,mThemeRes.getThemeName());
		 					count++; 
						}
					} else if (anmiParser.getEventType() == XmlResourceParser.END_TAG) {
					} else if (anmiParser.getEventType() == XmlResourceParser.TEXT) {
					} 
					// 下一个标签
					anmiParser.next();
				}
			} catch (Exception e) {
				e.printStackTrace();
				anim=null;
			}

			anmiParser=null;
			return anim;
		}
	
	public  static String checkIsNull(String res){
		if(res==null||res.equals("")||res.toLowerCase().equals("null")||res.equals("-1")){
			return null;
		}
		return res;
	}
	
	public static SleepText shareParser(XmlPullParser anmiParser,ThemeRes mThemeRes) {
		//	XmlPullParser anmiParser;
		if(anmiParser==null) return null;
			int count = 0;
			SleepText mSleepText = new SleepText();
			try {
				count = 0;
				String tag = null;
				String text = null ;
				while (anmiParser.getEventType() != XmlResourceParser.END_DOCUMENT) {
					
					// 如果是开始标签
					if (anmiParser.getEventType() == XmlResourceParser.START_TAG) {
						// 获取标签名称
						String name = anmiParser.getName();
						if(name.equals("item")){
							 tag = anmiParser.getAttributeValue(null, "tag");
						}
					} else if (anmiParser.getEventType() == XmlResourceParser.END_TAG) {
						 tag = null;
						 text = null ;
					} else if (anmiParser.getEventType() == XmlResourceParser.TEXT) {
						if(tag!=null)
						 text = anmiParser.getText();
					}
					MyLogger.e("aaaaxc", ""+tag+" "+text);
					if(text!=null&&tag!=null){
						
						mSleepText.addItem(text, tag);
					}
					
					// 下一个标签
					anmiParser.next();
				}
				mSleepText.addover();
			} catch (Exception e) {
				mSleepText	= new SleepText();		
				e.printStackTrace();
			}

			return mSleepText;
		}
	
	private static Integer parseInt(String intStr,int jinzhi){
		if(intStr!=null){
			try {
				return new BigInteger(intStr,jinzhi).intValue();
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return -1;
			}
		}
		return -1;
	} 
	private static final int type_string = 0;
}
