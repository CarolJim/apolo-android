package com.pagatodo.apolo.utils.customviews;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Omar on 31/05/2017.
 */

public class CustomNestedScrollView extends NestedScrollView {

    private boolean isScrollingEnabled;

    public boolean isScrollingEnabled() {
        return isScrollingEnabled;
    }

    public void setScrollingEnabled(boolean scrollingEnabled) {
        isScrollingEnabled = scrollingEnabled;
    }

    public CustomNestedScrollView(Context context) {
        super(context);
        isScrollingEnabled = true;
    }

    public CustomNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        isScrollingEnabled = true;
    }

    public CustomNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        isScrollingEnabled = true;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(isScrollingEnabled)
            return super.onInterceptTouchEvent(ev);
        else
            return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(isScrollingEnabled)
            return super.onTouchEvent(ev);
        else
            return false;
    }
}
