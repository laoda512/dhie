package util;

 
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
 
public class ScalingImageView extends ImageView {
    private boolean mAdjustViewBounds;
    private int mMaxWidth;
    private int mMaxHeight;
 
    public ScalingImageView(Context context) {
        super(context);
    }
    public ScalingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    @Override
    //  getAdjustViewBounds() was added in api level 16, so for backwards compatibility sake...
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        super.setAdjustViewBounds(adjustViewBounds);
        mAdjustViewBounds = adjustViewBounds;
    }
 
    @Override
    public void setMaxWidth(int maxWidth) {
        super.setMaxWidth(maxWidth);
        mMaxWidth = maxWidth;
    }
 
 
    @Override
    public void setMaxHeight(int maxHeight) {
        super.setMaxHeight(maxHeight);
        mMaxHeight = maxHeight;
    }
 
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable mDrawable = getDrawable();
        int mDrawableWidth = mDrawable.getIntrinsicWidth();
        int mDrawableHeight = mDrawable.getIntrinsicHeight();
 
        //------------
        int w;
        int h;
 
        // Desired aspect ratio of the view's contents (not including padding)
        float desiredAspect = 0.0f;
 
        // We are allowed to change the view's width
        boolean resizeWidth = false;
 
        // We are allowed to change the view's height
        boolean resizeHeight = false;
 
        final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        final int actualWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int actualHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (mDrawable == null) {
            // If no drawable, its intrinsic size is 0.
            mDrawableWidth = -1;
            mDrawableHeight = -1;
            w = h = 0;
        } else {
            w = mDrawableWidth;
            h = mDrawableHeight;
            if (w <= 0) w = 1;
            if (h <= 0) h = 1;
 
            // We are supposed to adjust view bounds to match the aspect
            // ratio of our drawable. See if that is possible.
            desiredAspect = (float) w / (float) h;
 
            if (mAdjustViewBounds) {
                // Original Android code setting whether to resizeHeight / width.
                 resizeWidth = widthSpecMode != MeasureSpec.EXACTLY;
                   resizeHeight = heightSpecMode != MeasureSpec.EXACTLY; 
                // Modified code which resizes no matter what MeasureSpec is set to. Works better with fill_parent/match_parent.
//                float actualAspect = (float) actualWidth/ (float) actualHeight;
//                if (actualAspect > desiredAspect) resizeWidth = true;
//                else if (actualAspect < desiredAspect) resizeHeight = true;
                
                
                
            }
        }
 
 
 
        int pleft = getPaddingLeft();
        int pright = getPaddingRight();
        int ptop = getPaddingTop();
        int pbottom = getPaddingBottom();
 
        int widthSize;
        int heightSize;
 
        if (resizeWidth || resizeHeight) {
            /* If we get here, it means we want to resize to match the
                drawables aspect ratio, and we have the freedom to change at
                least one dimension.
            */
 
            // Get the max possible width given our constraints
            widthSize = resolveAdjustedSize(w + pleft + pright, mMaxWidth, widthMeasureSpec);
 
            // Get the max possible height given our constraints
            heightSize = resolveAdjustedSize(h + ptop + pbottom, mMaxHeight, heightMeasureSpec);
 
            if (desiredAspect != 0.0f) {
                // See what our actual aspect ratio is
                float actualAspect = (float)(widthSize - pleft - pright) /
                        (heightSize - ptop - pbottom);
 
                if (Math.abs(actualAspect - desiredAspect) > 0.0000001) {
 
                    boolean done = false;
 
                    // Try adjusting width to be proportional to height
                    if (resizeWidth) {
                        int newWidth = (int)(desiredAspect * (heightSize - ptop - pbottom)) +
                                pleft + pright;
                        if (newWidth <= widthSize) {
                            widthSize = newWidth;
                            done = true;
                        }
                    }
 
                    // Try adjusting height to be proportional to width
                    if (!done && resizeHeight) {
                        int newHeight = (int)((widthSize - pleft - pright) / desiredAspect) +
                                ptop + pbottom;
                     //   if (newHeight <= heightSize) {
                            heightSize = newHeight;
                     //   }
                    }
                }
            }
        } else {
            /* We are either don't want to preserve the drawables aspect ratio,
               or we are not allowed to change view dimensions. Just measure in
               the normal way.
            */
            w += pleft + pright;
            h += ptop + pbottom;
 
            w = Math.max(w, getSuggestedMinimumWidth());
            h = Math.max(h, getSuggestedMinimumHeight());
            if (Build.VERSION.SDK_INT >= 11) {
            widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);
            heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);
            }
            else{
            	  widthSize = resolveSizeAndState2(w, widthMeasureSpec, 0);
                  heightSize = resolveSizeAndState2(h, heightMeasureSpec, 0);
            }
        }
  
   //     Log.i("F&C", "Setting dimen " + widthSize + " x " + heightSize + " for " +getId() + " Adjusted w/h:" + resizeWidth + "/" + resizeHeight);
        setMeasuredDimension(widthSize, heightSize);
    }
    /**
     * Utility to reconcile a desired size and state, with constraints imposed
     * by a MeasureSpec.  Will take the desired size, unless a different size
     * is imposed by the constraints.  The returned value is a compound integer,
     * with the resolved size in the {@link #MEASURED_SIZE_MASK} bits and
     * optionally the bit {@link #MEASURED_STATE_TOO_SMALL} set if the resulting
     * size is smaller than the size the view wants to be.
     *
     * @param size How big the view wants to be
     * @param measureSpec Constraints imposed by the parent
     * @return Size information bit mask as defined by
     * {@link #MEASURED_SIZE_MASK} and {@link #MEASURED_STATE_TOO_SMALL}.
     */
    public static int resolveSizeAndState2(int size, int measureSpec, int childMeasuredState) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize =  MeasureSpec.getSize(measureSpec);
        switch (specMode) {
        case MeasureSpec.UNSPECIFIED:
            result = size;
            break;
        case MeasureSpec.AT_MOST:
            if (specSize < size) {
                result = specSize | 0x01000000;
            } else {
                result = size;
            }
            break;
        case MeasureSpec.EXACTLY:
            result = specSize;
            break;
        }
        return result | (childMeasuredState&0xff000000);
    }
 
    private int resolveAdjustedSize(int desiredSize, int maxSize,
                                    int measureSpec) {
        int result = desiredSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize =  MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                /* Parent says we can be as big as we want. Just don't be larger
                   than max size imposed on ourselves.
                */
                result = Math.min(desiredSize, maxSize);
                break;
            case MeasureSpec.AT_MOST:
                // Parent says we can be as big as we want, up to specSize.
                // Don't be larger than specSize, and don't be larger than
                // the max size imposed on ourselves.
                result = Math.min(Math.min(desiredSize, specSize), maxSize);
                break;
            case MeasureSpec.EXACTLY:
                // No choice. Do what we are told.
                result = specSize;
                break;
        }
        return result;
    }
}