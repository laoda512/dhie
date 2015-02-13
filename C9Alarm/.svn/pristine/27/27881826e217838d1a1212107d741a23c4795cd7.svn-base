package util.seekBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class MySeekBar extends SeekBar {

	private Drawable mThumb;

	public interface OnSeekBarChangeListener {
		void onProgressChanged(MySeekBar VerticalSeekBar, int progress,
				boolean fromUser);

		void onStartTrackingTouch(MySeekBar VerticalSeekBar);

		void onStopTrackingTouch(MySeekBar VerticalSeekBar);
	}

	private OnSeekBarChangeListener mOnSeekBarChangeListener;

	public MySeekBar(Context context) {
		this(context, null);
	}

	public MySeekBar(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.seekBarStyle);
	}

	public MySeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
		mOnSeekBarChangeListener = l;
	}

	void onStartTrackingTouch() {
		if (mOnSeekBarChangeListener != null) {
			mOnSeekBarChangeListener.onStartTrackingTouch(this);
		}
	}

	void onStopTrackingTouch() {
		if (mOnSeekBarChangeListener != null) {
			mOnSeekBarChangeListener.onStopTrackingTouch(this);
		}
	}

	void onProgressRefresh(float scale, boolean fromUser) {
		Drawable thumb = mThumb;
		if (thumb != null) {
			// 这里的端点就是一个具有监听功能的类，类中包括监听，图片显示等等。
			setThumbPos(getHeight(), thumb, scale, Integer.MIN_VALUE);
			// 该方法的作用是重新绘图
			invalidate();
		}
		if (mOnSeekBarChangeListener != null) {
			mOnSeekBarChangeListener.onProgressChanged(this, getProgress(),
					fromUser);
		}
	}

	/**
	 * 设置端点的位置和样式 这里输入的四个参数的含义分别是：进程条的高，进程条的显示，进程条的进程百分比，标记位的值。
	 */
	private void setThumbPos(int w, Drawable thumb, float scale, int gap) {
		// available指的是竖向的可移动的最大距离
		int available = w + getPaddingLeft() - getPaddingRight();
		int thumbWidth = thumb.getIntrinsicWidth();// 获得竖向的高度
		int thumbHeight = thumb.getIntrinsicHeight();// 获得横向的高度
		available -= thumbWidth;
		// The extra space for the thumb to move on the track
		available += getThumbOffset() * 2;

		int thumbPos = (int) (scale * available);// 计算得到竖向的端点的坐标
		int topBound, bottomBound;
		if (gap == Integer.MIN_VALUE) {
			Rect oldBounds = thumb.getBounds();
			topBound = oldBounds.top;// topBound:0
			bottomBound = oldBounds.bottom;// bottomBound:45
		} else {
			topBound = gap;
			bottomBound = gap + thumbHeight;
		}
		// int left, int top, int right, int bottom
		// 设置端点的位置，进程条的左下角为原点，输入的参数分别为端点下坐标，端点左坐标，端点上坐标，端点右坐标
		thumb.setBounds(thumbPos, topBound, thumbPos + thumbWidth, bottomBound);
	}

	@Override
	protected void onDraw(Canvas c) {
		// Seek中的图像逆时针旋转90度
		c.rotate(-90);
		// 设置基准点，原先的基准点应该是左上角，这里改到了SeekBar的左下角。
		c.translate(-getHeight(), 0);
		super.onDraw(c);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		// 申请该控件所占用空间的大小。
		super.onMeasure(heightMeasureSpec, widthMeasureSpec);
		// 向父级元素申请所占用空间的大小。
		setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
	}

	@Override
	public void setThumb(Drawable thumb) {
		mThumb = thumb;
		super.setThumb(thumb);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// 尺寸有变动的时候（包括端点的移动）重绘进度条的大小
		super.onSizeChanged(h, w, oldw, oldh);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isEnabled()) {
			return false;
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setPressed(true);
			onStartTrackingTouch();
			trackTouchEvent(event);
			onSizeChanged(getWidth(), getHeight(), 0, 0);
			break;

		case MotionEvent.ACTION_MOVE:
			trackTouchEvent(event);
			attemptClaimDrag();
			onSizeChanged(getWidth(), getHeight(), 0, 0);
			break;

		case MotionEvent.ACTION_UP:
			trackTouchEvent(event);
			onStopTrackingTouch();
			setPressed(false);
			onSizeChanged(getWidth(), getHeight(), 0, 0);
			break;

		case MotionEvent.ACTION_CANCEL:
			onStopTrackingTouch();
			setPressed(false);
			onSizeChanged(getWidth(), getHeight(), 0, 0);
			break;
		}
		return true;
	}

	private void trackTouchEvent(MotionEvent event) {
		final int Height = getHeight();
		final int available = Height - getPaddingBottom() - getPaddingTop();
		int Y = (int) event.getY();
		float scale;
		float progress = 0;
		if (Y > Height - getPaddingBottom()) {
			scale = 0.0f;
		} else if (Y < getPaddingTop()) {
			scale = 1.0f;
		} else {
			scale = (float) (Height - getPaddingBottom() - Y)
					/ (float) available;
		}

		final int max = getMax();
		progress = scale * max;

		setProgress((int) progress);
	}

	private void attemptClaimDrag() {
		if (getParent() != null) {
			getParent().requestDisallowInterceptTouchEvent(true);
		}
	}
}