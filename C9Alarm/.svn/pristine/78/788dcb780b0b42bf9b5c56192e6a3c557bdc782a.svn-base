/**
 * 
 */
package com.tavx.C9Alarm.View;

import m.framework.utils.Utils;

import com.tavx.C9Alarm.AlarmApplication;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * @author Administrator
 * 
 */
public abstract class Rectangle extends Shape {

	int width, height;

	public Rectangle() {
		ini();
		iniPaint();

	}

	@Override
	public void iniPaint() {
		super.iniPaint();
		BorderPaint.setStrokeWidth(Utils.dipToPx(AlarmApplication.getApp(), 1));
		MiddleTextPaint.setColor(0xff8f8f8f);
		LargeTextPaint.setColor(0xff8f8f8f);
		SmallTextPaint.setColor(0xff8f8f8f);
	}

	public abstract void ini();

	public abstract void draw(Canvas canvas);

	public boolean checkClick(float x, float y) {
		Rect rect = new Rect(getX() - width / 2, getY() - height / 2, getX()
				+ width / 2, getY() + height / 2);
		boolean isContain = rect.contains((int) x, (int) y);
		return isContain;
	}

	public int getTop() {
		return getY() - height / 2;
	}

	public int getBottom() {
		return getY() + height / 2;
	}

	public int getLeft() {
		return getX() - width / 2;
	}

	public int getRight() {
		return getX() + width / 2;
	}

	public Rect getBounds() {
		return new Rect(getLeft(), getTop(), getRight(), getBottom());
	}

}
