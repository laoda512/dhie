package mainView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;

public class Graphics {

	private Paint mPaint;
	public Paint getmPaint() {
		return mPaint;
	}

	public void setmPaint(Paint mPaint) {
		this.mPaint = mPaint;
	}

	private Rect mRect;
	private Path mPath;
	private Canvas mCanvas;

	public static final int RIGHT = 1 << 1;
	public static final int HCENTER = 1 << 2;
	public static final int BOTTOM = 1 << 3;
	public static final int VCENTER = 1 << 4;
	public static final int TOP = 1 << 5;
	public static final int LEFT = 1 << 6;

	public static final int TOP_LEFT = (TOP | LEFT);

	// clip

	private int mClipX;
	private int mClipY;
	private int mClipWidth;
	private int mClipHeight;

	private boolean wasClipped;

	public Graphics() {
		mPaint = new Paint();
		mRect = new Rect();
	}

	public Graphics(Canvas c) {
		super();
		mPaint = new Paint();
		mRect = new Rect();
		mCanvas = c;
	}

	public void setCanvas(Canvas c) {
		mCanvas = c;
	}

	public void setClip(int x, int y, int w, int h) {
		if (wasClipped == true) {
			mCanvas.restore();
		}
		mCanvas.save();
		translateToRect(x, y, w, h);
		mCanvas.clipRect(mRect);
		wasClipped = true;
	}

	public void clipRect(int x, int y, int w, int h) {
		if (wasClipped == true) {
			mCanvas.restore();
		}
		mCanvas.save();
		translateToRect(x, y, w, h);
		mCanvas.clipRect(mRect);
		wasClipped = true;
	}

	Path p;

	public void clipCircle(int x, int y, int r) {
		if (wasClipped == true) {
			mCanvas.restore();
		}
		mCanvas.save();
		Path p = new Path();
		p.addCircle(x, y, r, Direction.CCW);
		mCanvas.clipPath(p);
		wasClipped = true;
	}

	public void setFont(Typeface typeface, Style style, int color, float size
			) {
		mPaint.setTypeface(typeface);
		mPaint.setStyle(style);
		mPaint.setColor(color);
		mPaint.setTextSize(size);
		mPaint.setTextAlign(Paint.Align.CENTER);
	}

	public void drawSystemString(String text, int x, int y) {
		mPaint.setAntiAlias(true);
		mCanvas.drawText(text, x, y, mPaint);
		mPaint.setAntiAlias(false);
		performAfterDrawOperations();
	}
	
	public void drawSystemString(String text, int x, int y,int color) {
		mPaint.setAntiAlias(true);
		int cColor = mPaint.getColor();
		mPaint.setColor(color);
		mCanvas.drawText(text, x, y, mPaint);
		mPaint.setAntiAlias(false);
		mPaint.setColor(cColor);
		performAfterDrawOperations();
	}
	
	public void drawSystemString(String text, int x, int y,Paint p) {
		mPaint.setAntiAlias(true);
		mCanvas.drawText(text, x, y, p);
		mPaint.setAntiAlias(false);
		performAfterDrawOperations();
	}
	
	public void drawTextWithBorder(String strMsg, Paint paint, int setx, 
			int sety, int foreground, int background) { 
			paint.setColor(background); 
			mCanvas.drawText(strMsg, setx + 1, sety, paint); 
			mCanvas.drawText(strMsg, setx, sety -1, paint); 
			mCanvas.drawText(strMsg, setx, sety + 1, paint); 
			mCanvas.drawText(strMsg, setx -1, sety, paint); 
			paint.setColor(foreground); 
			mCanvas.drawText(strMsg, setx, sety, paint); 
			performAfterDrawOperations();
			} 

	public void drawRGB(int[] rgbData, int offset, int scanlength, int x,
			int y, int width, int height, boolean processAlpha) {

		mCanvas.drawBitmap(rgbData, offset, width, x, y, width, height,
				processAlpha, null);

		performAfterDrawOperations();
	}

	public void drawARGB(int color, int offset, int scanlength, int x, int y,
			int width, int height, boolean processAlpha) {

		performAfterDrawOperations();
	}

	public void drawRect(int x, int y, int w, int h) {
		mPaint.setStyle(Style.STROKE);
		mRect.set(x, y, x + w, y + h);
		mCanvas.drawRect(mRect, mPaint);
		performAfterDrawOperations();
	}

	public void fillRect(int x, int y, int w, int h,Paint p) {
		if (p == null){
			
		mPaint.setStyle(Style.FILL);
		p = mPaint;
		}
		mRect.set(x, y, x + w, y + h);
		mCanvas.drawRect(mRect, p);
		performAfterDrawOperations();
	}

	public void fillRoundRect(int xPos, int yPos, int width, int height,
			int arcWidth, int arcHeight) {
		// TODO fill if needed
		performAfterDrawOperations();
	}

	public void drawRoundRect(int xPos, int yPos, int width, int height,
			int arcWidth, int arcHeight) {
		// TODO fill if needed
		performAfterDrawOperations();
	}

	public void fillArc(int xPos, int yPos, int width, int height,
			int startAngle, int endAngle) {
		// TODO fill if needed
		performAfterDrawOperations();
	}

	public void drawLine(int xPos1, int yPos1, int xPos2, int yPos2) {
		// TODO fill if needed
		performAfterDrawOperations();
	}
	public void drawArcBySweep(int xPos, int yPos, int width, int height,
			int startAngle, int sweepAngle, Paint p) {
		// TODO fill if needed
		// mCanvas.d
		if (p == null)
			p = mPaint;
		RectF ef = new RectF(xPos - width / 2, yPos - height / 2, xPos + width
				/ 2, yPos + height / 2);
		mCanvas.drawArc(ef, startAngle, sweepAngle, false, p);
		
		
		performAfterDrawOperations();
	}
	/**
	 * 旋转
	 * 
	 * */
	public void drawRotate(float degree,int x,int y, Paint p) {
		// TODO fill if needed
		// mCanvas.d
		if (p == null)
			p = mPaint;
	
		mCanvas.rotate(degree,x,y);
		
		//mCanvas.
		//performAfterDrawOperations();
	}
	
	public void drawArcText(String text,Path path,int hOffset,int vOffset,Paint p) {
		// TODO fill if needed
		// mCanvas.d
		if (p == null)
			p = mPaint;
		
		mCanvas.drawTextOnPath(text, path, hOffset, vOffset, p);
		
		performAfterDrawOperations();
	}
	
	public void drawArc(int xPos, int yPos, int width, int height,
			int startAngle, int endAngle, Paint p) {
		// TODO fill if needed
		// mCanvas.d
		if (p == null)
			p = mPaint;
		RectF ef = new RectF(xPos - width / 2, yPos - height / 2, xPos + width
				/ 2, yPos + height / 2);
		mCanvas.drawArc(ef, startAngle, endAngle - startAngle, false, p);
		performAfterDrawOperations();
	}

	public void drawCircle(float cx, float cy, float radius,Paint p) {
		// TODO fill if needed
		// mCanvas.d
		if (p == null)
			p = mPaint;
		mCanvas.drawCircle(cx, cy, radius, p);

		performAfterDrawOperations();
	}

	public void setColor(int c) {
		mPaint.setColor(c);

	}

	public void drawColor(int color) {
		mCanvas.drawColor(color);
		performAfterDrawOperations();
	}

	public void Clear() {
		//mCanvas.drawColor(color);
		mCanvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
		performAfterDrawOperations();
	}

	public void drawImage(Image img, int x, int y, int anchor) {

		int drawOffsetX = 0;
		int drawOffsetY = 0;

		if ((anchor & HCENTER) != 0) {
			drawOffsetX = (-img.getWidth() / 2);
		} else if ((anchor & RIGHT) != 0) {
			drawOffsetX = -img.getWidth();
		}

		if ((anchor & VCENTER) != 0) {
			drawOffsetY = (-img.getHeight() / 2);
		} else if ((anchor & BOTTOM) != 0) {
			drawOffsetY = -img.getHeight();
		}

		mCanvas.drawBitmap(img.getBitmap(), x + drawOffsetX, y + drawOffsetY,
				null);
		performAfterDrawOperations();
	}

	public void drawBitmap(Bitmap bitmap, int x, int y, int anchor) {

		int drawOffsetX = 0;
		int drawOffsetY = 0;

		if ((anchor & HCENTER) != 0) {
			drawOffsetX = (-bitmap.getWidth() / 2);
		} else if ((anchor & RIGHT) != 0) {
			drawOffsetX = -bitmap.getWidth();
		}

		if ((anchor & VCENTER) != 0) {
			drawOffsetY = (-bitmap.getHeight() / 2);
		} else if ((anchor & BOTTOM) != 0) {
			drawOffsetY = -bitmap.getHeight();
		}
		mPaint.setAntiAlias(true);
		mCanvas.drawBitmap(bitmap, x + drawOffsetX, y + drawOffsetY, mPaint);
		mPaint.setAntiAlias(false);
		performAfterDrawOperations();
	}
	
	public void drawBitmap2(Bitmap bitmap, int x, int y, int width,int height,int anchor) {
		drawBitmap2(bitmap, x, y, width, height, anchor,mPaint);
	}
	
	public void drawBitmap2(Bitmap bitmap, int x, int y, int width,int height,int anchor,Paint p) {

		int drawOffsetX = 0;
		int drawOffsetY = 0;

		if ((anchor & HCENTER) != 0) {
			drawOffsetX = (-width / 2);
		} else if ((anchor & RIGHT) != 0) {
			drawOffsetX = -width;
		}

		if ((anchor & VCENTER) != 0) {
			drawOffsetY = (-height / 2);
		} else if ((anchor & BOTTOM) != 0) {
			drawOffsetY = -height;
		}
		p.setAntiAlias(true);
		Rect r =new Rect ();
		r.set(x + drawOffsetX, y + drawOffsetY, x + drawOffsetX+width, y + drawOffsetY+height);
		mCanvas.drawBitmap(bitmap, null, r, p);
		p.setAntiAlias(false);
		performAfterDrawOperations();
	}

	private void translateToRect(int x, int y, int w, int h) {
		mRect.set(x, y, x + w, y + h);
		mClipX = x;
		mClipY = y;
		mClipWidth = w;
		mClipHeight = h;
	}

	public int getClipX() {
		return mClipX;
	}

	public int getClipY() {
		return mClipY;
	}

	public int getClipWidth() {
		return mClipWidth;
	}

	public int getClipHeight() {
		return mClipHeight;
	}

	private void performAfterDrawOperations() {
		if (wasClipped == true) {
			wasClipped = false;
			mCanvas.restore();
		}
	}

	public Canvas getCanvas() {
		return mCanvas;
	}

}
