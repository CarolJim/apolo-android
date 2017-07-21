package com.pagatodo.apolo.utils.customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Omar on 27/06/2017.
 */

public class MarqueeTextView extends android.support.v7.widget.AppCompatTextView {

    public MarqueeTextView(Context context) {
        super(context);
        init();
        rotate();
    }

    public MarqueeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        rotate();
    }

    public MarqueeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        rotate();
    }

    private void rotate() {
        setSelected(true);
    }

    private void init() {
        if (!isInEditMode()) {}
    }

}
