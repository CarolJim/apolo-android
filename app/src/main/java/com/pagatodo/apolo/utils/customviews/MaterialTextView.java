package com.pagatodo.apolo.utils.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import com.pagatodo.apolo.R;
import java.util.HashMap;

/**
 * Created by jvazquez on 17/05/2017.
 */

public class MaterialTextView extends AppCompatTextView {
    private TYPE type;
    private Color color;


    public MaterialTextView(Context context) {
        super(context);
        //applyFont(context);
    }

    public MaterialTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        handlAttrs(context, attrs);
        applyFont(context);
    }

    public MaterialTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handlAttrs(context, attrs);
//        applyFont(context);

    }

    private void applyFont(Context context) {
        Typeface customFont = getTypeface("fonts/Roboto/Roboto-Regular.ttf", context);
        if(type != null){
            switch (type){
                case TITULO:
                    customFont = getTypeface("fonts/Roboto/Roboto-Bold.ttf", context);
                    setTypeface(customFont, Typeface.BOLD);
                    break;
                case SUBTITULO:
                    customFont = getTypeface("fonts/Roboto/Roboto-Bold.ttf", context);
                    setTypeface(customFont, Typeface.BOLD);
                    break;
                case DESCRIPCION:
                    customFont = getTypeface("fonts/Roboto/Roboto-Medium.ttf", context);
                    setTypeface(customFont, Typeface.NORMAL);
                    break;
                case INDICACION:
                    customFont = getTypeface("fonts/Roboto/Roboto-Light.ttf", context);
                    setTypeface(customFont, Typeface.NORMAL);
                    break;
                case TEXTO:
                    customFont = getTypeface("fonts/Roboto/Roboto-Regular.ttf", context);
                    setTypeface(customFont, Typeface.NORMAL);
                    break;
            }
        }else{
            setTypeface(customFont, Typeface.NORMAL);
        }
    }
    private void handlAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaterialTextView);
        String fontName = a.getString(R.styleable.MaterialTextView_tipo) ;
        if (fontName != null) {
            switch (fontName){
                case "0":
                    type = TYPE.TITULO;
                    break;
                case "1":
                    type = TYPE.SUBTITULO;
                    break;
                case "2":
                    type = TYPE.DESCRIPCION;
                    break;
                case "3":
                    type = TYPE.INDICACION;
                    break;
                case "4":
                    type = TYPE.TEXTO;
                    break;
            }
        }
        a.recycle();
    }
    enum TYPE {TITULO, SUBTITULO, DESCRIPCION, INDICACION, TEXTO}

    public static Typeface getTypeface(String fontname, Context context) {
        HashMap<String, Typeface> fontCache = new HashMap<>();
        Typeface typeface = fontCache.get(fontname);
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
            }
            catch (Exception e) {
                return null; }
            fontCache.put(fontname, typeface);
        } return typeface;
    }
}

