package com.pagatodo.apolo.utils.customviews;

import android.content.Context;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jvazquez on 29/05/2017.
 */

public class ScrollFixedBottomSheetBehavior<V extends View> extends BottomSheetBehavior<V> {

    public ScrollFixedBottomSheetBehavior() {
        super();
    }

    public ScrollFixedBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        //return super.onTouchEvent(parent, child, event);
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
//        return super.onInterceptTouchEvent(parent, child, event);
        return false;
    }
}
