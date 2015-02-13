package com.tavx.C9Alarm;

import java.util.HashSet;

import android.test.InstrumentationTestCase;
import android.text.TextUtils;

public class SleepText extends InstrumentationTestCase {
	private String content = null;

	public String getText(int time, boolean islaichuang) {
		return getText(time, islaichuang, AlarmApplication.getApp()
				.getUserNickName());
	}

	public String getText(int time, boolean islaichuang, String userName) {
		if (face_cry == null) {
			iniString();
		}
		if (TextUtils.isEmpty(userName)) {
			userName = nickName;
		}

		String sweetText;
		String timeText;
		if (time == Integer.MAX_VALUE) {
			timeText = "[username]没有准时起床";
			sweetText = text_angry[(int) (Math.random() * text_angry.length)]
					+ Symbol.SPLIT
					+ face_angry[(int) (Math.random() * face_angry.length)];

		} else {
			String buff = Utils.getFormatTimeWithSeconds(time);
			Utils.submitScore(time, buff);
			timeText = "本次起床花费了" + buff;

			if (islaichuang == false) {
				sweetText = "\n"
						+ text_happy[(int) (Math.random() * text_happy.length)]
						+ Symbol.SPLIT
						+ face_happy[(int) (Math.random() * face_happy.length)];
			} else {
				sweetText = "\n"
						+ text_angry[(int) (Math.random() * text_angry.length)]
						+ Symbol.SPLIT
						+ face_cry[(int) (Math.random() * face_cry.length)];
			}
		}
		return parseString(timeText + sweetText, userName);
	}

	private String parseString(String content, String username) {
		return content.replaceAll("\\[username\\]", username).replaceAll(
				"\\[typename\\]", name);
	}

	public void iniString() {
		name = "小闹";
		nickName = "主人";
		addFace();

		text_angry = new String[] { "主人放弃了起床挑战~咕~主人你到底有没有赖床呢",
				/*"唔" + name + "的游戏不好玩么，还是说太难了。。哼，人家也没有期待着你来玩啦"*/ };
		text_happy = new String[] { "主人起床成功了呐~呐呐，主人早上好",
				/*"早起早睡身体好，明天也要和" + name + "一起准时起床哦"*/ };
		text_cry = new String[] { "主人起床失败了~下次要加油哦" }; 
	}  
 
	public SleepText() {
		super();
		iniString();
		addFace();
	}
	public HashSet<String> happyText = new HashSet<String>();
	public HashSet<String> angryText = new HashSet<String>();
	public void addItem(String content,String tag){
		if(tag.equals("name")){
			name = content;
		}else if(tag.equals("angry_text")){
			angryText.add(content);
		}else if(tag.equals("happy_text")){
			happyText.add(content);
		}else if(tag.equals("happy_icon")){
			happy_icon = content;
		}else if(tag.equals("angry_icon")){
			angry_icon = content;
		}
		
	}
	
	private String angry_icon  ;
	private String happy_icon  ;
	
	public String getAngry_icon() {
		return angry_icon;
	}

	public String getHappy_icon() {
		return happy_icon;
	}

	public void setAngry_icon(String angry_icon) {
		this.angry_icon = angry_icon;
	}

	public void setHappy_icon(String happy_icon) {
		this.happy_icon = happy_icon;
	}

	public void addover(){
		
		addFace();
		text_angry = angryText.toArray(text_angry);
		text_happy = happyText.toArray(text_happy);
	}
	
	
	public void addFace(){
		face_cry = new String[] { "T.T", "(￣﹏￣)", "(￣.￣)", "(⊙ˍ⊙)", "╮(╯▽╰)╭",
				"(＞﹏＜)", "(￣o￣) . z Z", "<(￣ ﹌ ￣)> ", "∪ˍ∪", "∪０∪", "∪︿∪",
				"∪ε ∪", "∪ 3∪", "∪ω∪", "∪﹏∪", "∪△∪", "∪▽∪", "○|￣|_" };
		face_happy = new String[] { "(๑￫ܫ￩)", "(｡・`ω´･)", "(・(ｪ)・)",
				"(σ｀・д･)σ", "(oﾟωﾟo)", "(≧3≦)", "(≧ω≦)", "o(≧ω≦)o", "o(≧o≦)o ",
				"( ° ▽、° )" };
		face_angry = new String[] { "╭∩╮_()︿﹀﹀_╭∩╮", "（*－.－）", " Σ( ￣□￣；)",
				"(/≡ _ ≡)/~┴┴ ", "（#－.－）", " (╬￣皿￣)凸 ", "(￣ε(#￣)☆╰╮o(￣皿￣///)",

				"(￣ε(#￣)☆╰╮o()︿)///)",

				"( ￣ ▽￣)o╭╯☆#╰ _─﹏─)╯",

				"( ￣ ▽￣)o╭╯☆#╰( ￣﹏￣)╯"

		};
	}
	public String[] face_cry;

	protected String[] face_angry;
	protected String[] face_happy;
	protected String[] text_cry;// = new String[] {"主人放弃了起床挑战~咕~主人你到底有没有赖床呢","唔"+name };
	protected String[] text_happy;// = new String[] {"主人起床成功了呐~呐呐，主人早上好" };
	protected String[] text_angry;// = new String[] {"主人起床失败了~下次要加油哦" };

	protected String name = "小闹";// = "小闹";
	protected String nickName = "主人";// = "小闹";

}
