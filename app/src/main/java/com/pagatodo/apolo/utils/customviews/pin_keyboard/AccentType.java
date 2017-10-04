package com.pagatodo.apolo.utils.customviews.pin_keyboard;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef(flag=true, value={PinKeyboardView.ACCENT_NONE, PinKeyboardView.ACCENT_ALL, PinKeyboardView.ACCENT_CHARACTER})
@Retention(RetentionPolicy.SOURCE)
public @interface AccentType {}
