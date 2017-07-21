package com.pagatodo.apolo.utils.customviews;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by jvazquez on 24/05/2017.
 */

public class ScrollViewFixed extends ScrollView {
    public ScrollViewFixed(Context context) {
        super(context);
    }

    public ScrollViewFixed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewFixed(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScrollViewFixed(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
