package dmax.words.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Class that able to animate own position. Used to implement showing from (hiding to) screen edge.
 *
 * <br/><br/>
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 22.12.14 at 18:23
 */
public class AnimationLayout extends FrameLayout {

    public AnimationLayout(Context context) {
        super(context);
    }

    public AnimationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AnimationLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //~

    public float getYRatio() {
        int height = getHeight();
        return height != 0 ? getY() / height : -1;
    }

    /**
     * Set yRatio To change view's y position.
     * Use yRatio values from 0 to 1 in animations to move view from initial y to it's height.
     */
    public void setYRatio(float yRatio) {
        int height = getHeight();
        setY(height > 0 ? yRatio * height : -9999);
    }

    public float getXRatio() {
        int width = getWidth();
        return width != 0 ? getX() / width : -1;
    }

    /**
     * Set xRatio To change view's x position.
     * Use xRatio values from 0 to 1 in animations to move view from initial x to it's width.
     */
    public void setXRatio(float xRatio) {
        int width = getWidth();
        setX(width > 0 ? xRatio * width : -9999);
    }
}
