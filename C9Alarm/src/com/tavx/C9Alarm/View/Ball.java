package com.tavx.C9Alarm.View;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Ball extends Shape{

		int r;
	


		public Ball() {
			ini();
			iniPaint();
		}
		
		
		public abstract void ini();

		public abstract void draw(Canvas canvas);
		
		public boolean checkClick(float x,float y){
			Rect rect = new Rect(getX() - r, locY - r, locX + r, locY + r);
			boolean isContain=rect.contains((int)x, (int)y);
			
			return isContain;
		}

		
		

	}