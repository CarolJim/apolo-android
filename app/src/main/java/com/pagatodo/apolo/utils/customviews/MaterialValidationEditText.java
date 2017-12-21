package com.pagatodo.apolo.utils.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.utils.ValidateForm;

/**
 * Created by jvazquez on 22/05/2017.
 */

public class MaterialValidationEditText extends LinearLayout implements View.OnClickListener {
    private EditText         edtMain    = null;
    private MaterialTextView tvMessage  = null;
    private AppCompatImageView ivStatus = null;
    private boolean isValidField        = false;
    private String  type = null;
    private OnClickIcon iconListener = null;
    private int mGravity = Gravity.START;
    private int minSizeRequired = -23;
    private int minSize = 6;

    private MaterialValidationEditText edtCompare = null;
    @DrawableRes
    private int pinnedIcon = -1;

    public MaterialValidationEditText(Context context) {
        super(context);
        init(context, null);
    }

    public MaterialValidationEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public MaterialValidationEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.validation_edittext, this);
        edtMain = (EditText) findViewById(R.id.edtMain);
        edtMain.setId(getId());
        tvMessage = (MaterialTextView) findViewById(R.id.tvMessage);
        ivStatus  = (AppCompatImageView) findViewById(R.id.ivStatus);
        int inputType = EditorInfo.TYPE_NULL;
        int textSize = 15;
        String hint = null;
        int maxLength = 0;
        int maxLines = 1;
        boolean isSingleLine = false;
        boolean isTextEnabled = true;
        boolean focusable = true;

        if (attrs != null) {
            TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.MaterialValidationEditText,
                    0, 0);
            try {
                hint = typedArray.getString(R.styleable.MaterialValidationEditText_hintText);
                type = typedArray.getString(R.styleable.MaterialValidationEditText_formato);
                maxLength = typedArray.getInteger(R.styleable.MaterialValidationEditText_maxLength, 0);
                maxLines = typedArray.getInt(R.styleable.MaterialValidationEditText_maxLength, 0);
                isSingleLine = typedArray.getBoolean(R.styleable.MaterialValidationEditText_isSingleLine, false);
                isTextEnabled = typedArray.getBoolean(R.styleable.MaterialValidationEditText_isTextEnabled, true);

                edtMain.setHintTextColor(typedArray.getColor(R.styleable.MaterialValidationEditText_hintColor,
                        ContextCompat.getColor(App.getInstance().getApplicationContext(), R.color.colorText)));

                pinnedIcon = typedArray.getResourceId(R.styleable.MaterialValidationEditText_defaultIcon, -1);
                inputType = typedArray.getInt(R.styleable.MaterialValidationEditText_android_inputType, EditorInfo.TYPE_NULL);
                edtMain.setInputType(inputType);
                inputType = typedArray.getInt(R.styleable.MaterialValidationEditText_android_inputType, EditorInfo.TYPE_NULL);
                //textSize = typedArray.getInt(R.styleable.MaterialValidationEditText_android_textSize, EditorInfo.TYPE_NULL);
                focusable = typedArray.getBoolean(R.styleable.MaterialValidationEditText_focusableInTouchMode, true);
                mGravity = typedArray.getInteger(R.styleable.MaterialValidationEditText_android_gravity, Gravity.START);
                minSizeRequired = typedArray.getInteger(R.styleable.MaterialValidationEditText_minSizeRequired, -23);

            } catch (Exception e) {
                //Log.e(context.getPackageName(), "Error loading attributes:" + e.getMessage());
            } finally {
                typedArray.recycle();
            }

            if (inputType != EditorInfo.TYPE_NULL) {
                edtMain.setInputType(inputType);
            } else {
                edtMain.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            }
            if (edtMain  != null) {
                edtMain.setGravity(mGravity);
            }

            if (textSize != EditorInfo.TYPE_NULL) {
                edtMain.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            }

            if (type != null && !type.isEmpty()) {
                setCustomFormatAttr(type);
            }

            if (hint != null && !hint.isEmpty()) {
                setHintText(hint);
            }

            if (maxLength > 1) {
                setMaxLength(maxLength);
            }

            if (maxLines > 0) {
                setMaxLines(maxLines);
            }
            setEnabled(isTextEnabled);
            setSingleLine(isSingleLine);
            setEdtFocusable(focusable);
            if (pinnedIcon != -1) {
                setIconPinned();
            }else{
                clearIcon();
            }
            setMessageText("");
        }
    }
    private void clearIcon(){
        ivStatus.setImageDrawable(null);
        ivStatus.setVisibility(GONE);
    }
    public void setSingleLine(boolean singleLine) {
        if (singleLine) {
            edtMain.setLines(1);
        }
    }
    public void setHintText(String txt) {
        edtMain.setHint(txt);
    }
    public String getHint(){
        return edtMain.getHint().toString();
    }
    public String getText(){
        return edtMain.getText().toString();
    }
    public void setMaxLines(int n) {
        edtMain.setLines(n);
    }
    public void setMaxLength(Integer i) {
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(i);
        edtMain.setFilters(fArray);
    }
    private void setCustomFormatAttr(String txt) {
        if (txt != null && !txt.isEmpty()) {
            switch (txt) {
                case "0"://email
                    edtMain.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    setValidationListener(txt);
                    break;
                case "1"://password
                    edtMain.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    setValidationListener(txt);
                    break;
                case "2"://phone
                    edtMain.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
                    edtMain.setKeyListener(DigitsKeyListener.getInstance(getContext().getString(R.string.input_int_unsigned)));
                    setValidationListener(txt);
                    break;
                case "3"://zipcode
                    edtMain.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edtMain.setKeyListener(DigitsKeyListener.getInstance(getContext().getString(R.string.input_int_unsigned)));
                    setValidationListener(txt);
                    break;
                case "4"://text
                    edtMain.setInputType(InputType.TYPE_CLASS_TEXT);
                    setValidationListener(txt);
                    break;
                case "5"://number
                    edtMain.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edtMain.setKeyListener(DigitsKeyListener.getInstance(getContext().getString(R.string.input_int_unsigned)));
                    setValidationListener(txt);
                    break;
                case "6"://cellPhone
                    edtMain.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
                    edtMain.setKeyListener(DigitsKeyListener.getInstance(getContext().getString(R.string.input_int_unsigned)));
                    setValidationListener(txt);
                    break;
                case "7"://pwtext
                    edtMain.setInputType(InputType.TYPE_CLASS_TEXT);
                    setValidationListener(txt);
                    break;
            }

        }
    }
    private void setValidationListener(final String type) {
        edtMain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String txt = edtMain.getEditableText().toString().trim();
                boolean result;
                if (txt.isEmpty()) {
                    setMessageText("");
                    if(pinnedIcon != -1){
                        setIconPinned();
                    }else {
                        clearIcon();
                    }
                    isValidField = false;
                } else {
                    String message;
                    switch (type) {
                        case "0"://email
                            result = ValidateForm.isValidEmailAddress(txt);
                            message = getMessage(type, result);
                            if(edtCompare != null && result && edtCompare.isValidField()){
                                if(edtCompare.isValidField()){
                                    result =  edtCompare.getText().equals(txt);
                                    message = getMessageCompare(type, result);
                                }
                            }
                            break;
                        case "1"://password
                            result = ValidateForm.isValidPassword(txt);
                            message = result ? getString(R.string.valid_pass) : getString(R.string.invalid_pass);
                            if(edtCompare != null && result && edtCompare.isValidField()){
                                result = edtCompare.getText().equals(txt);
                                message = result ? getString(R.string.msg_valid_repass): getString(R.string.msg_invalid_repass);
                            }
                            break;
                        case "2"://phone
                            result = ValidateForm.isValidPhone(txt);
                            message = result ? getString(R.string.valid_phonenumber): getString(R.string.invalid_phonenumber);
                            if(edtCompare != null && result && edtCompare.isValidField()){
                                result = edtCompare.getText().equals(txt);
                                message = result ? getString(R.string.msg_valid_repass): getString(R.string.msg_invalid_repass);

                            }
                            break;
                        case "3"://zipcode
                            result = ValidateForm.isValidZipCode(txt);
                            message = result ? getString(R.string.valid_field) : getString(R.string.invalid_field);
                            if(edtCompare != null && result && edtCompare.isValidField()){
                                result = edtCompare.getText().equals(txt);
                                message = result ? getString(R.string.msg_valid_refield): getString(R.string.msg_invalid_refield);
                            }
                            break;
                        case "4"://text
                            result = !txt.isEmpty() && txt.length() >= minSizeRequired;
                            message = result ? getString(R.string.valid_field) : getString(R.string.invalid_field);
                            if(edtCompare != null && result && edtCompare.isValidField()){
                                result = edtCompare.getText().equals(txt);
                                message = result ? getString(R.string.msg_valid_refield): getString(R.string.msg_invalid_refield);
                            }
                            break;
                        case "5"://number
                            result = !txt.isEmpty();
                            message = result ? getString(R.string.valid_field) : getString(R.string.invalid_field);
                            if(edtCompare != null && result && edtCompare.isValidField()){
                                result = edtCompare.getText().equals(txt);
                                message = result ? getString(R.string.msg_valid_refield): getString(R.string.msg_invalid_refield);
                            }
                            break;
                        case "6"://cellPhone
                            result = ValidateForm.isValidCellPhone(txt.trim());
                            message = result ? getString(R.string.valid_phonenumber): getString(R.string.invalid_phonenumber);
                            if(edtCompare != null && result && edtCompare.isValidField()){
                                result = edtCompare.getText().equals(txt);
                                message = result ? getString(R.string.msg_valid_refield): getString(R.string.msg_invalid_refield);
                            }
                            break;
                        case "7"://pwtext
                            result = !txt.isEmpty() && txt.length() == minSize;
                            message = result ? getString(R.string.valid_field) : getString(R.string.error_min_id_afiliador);
                            if(edtCompare != null && result && edtCompare.isValidField()){
                                result = edtCompare.getText().equals(txt);
                                message = result ? getString(R.string.msg_valid_refield): getString(R.string.msg_invalid_refield);
                            }
                            break;
                        default:
                            result = false;
                            message = getString(R.string.invalid_field);
                            break;
                    }
                    if (result) {
                        isValidField = true;
                        setValidView(message);
                    } else {
                        isValidField = false;
                        setNonValidView(message);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void setAddTextChangedListener(TextWatcher watcher){
        edtMain.addTextChangedListener(watcher);
    }

    private void setValidView(String message) {
        setMessageText(message);
        ivStatus.setVisibility(VISIBLE);
        setIconResource(R.drawable.done);
    }

    private void setNonValidView(String message) {
        setMessageText(message);
        ivStatus.setVisibility(VISIBLE);
        setIconResource(R.drawable.warning);
    }

    public void setColorMessageText(@ColorInt int color){
        tvMessage.setTextColor(color);
    }
    public void setIconResource(@DrawableRes int drawable){
       ivStatus.setImageResource(drawable);
    }

    private void setIconPinned() {
        AppCompatImageView icon = (AppCompatImageView) findViewById(R.id.ivStatus);
        if (icon != null && pinnedIcon != -1) {
            Drawable drawable = VectorDrawableCompat.create(App.getInstance().getResources(), pinnedIcon, null);//ContextCompat.getDrawable(App.getInstance(), pinnedIcon);
            if(drawable != null){
                icon.setImageDrawable(drawable);
                icon.setVisibility(VISIBLE);
            }
        }
    }
    public void setMessageText(String messageText) {
        if(tvMessage != null){
            tvMessage.setText(messageText);
        }
    }
    private String getString(@StringRes int string){
        return App.getInstance().getString(string);
    }
    public void setEditTextToCompare(MaterialValidationEditText edtCompare){
        this.edtCompare = edtCompare;
    }
    public boolean isValidField(){
        return isValidField;
    }

    public void setIsValidField(boolean isValidField){
        this.isValidField = isValidField;
    }

    @Override
    public void setOnKeyListener(OnKeyListener l) {
//        super.setOnKeyListener(l);
        if(edtMain != null){
            edtMain.setId(getId());
            edtMain.setOnKeyListener(l);
        }

    }

    public EditText getEdtMain(){
        return edtMain;
    }
    private String getMessageCompare(String type, boolean result){
        String message = "";
        switch (type) {
            case "0"://email
                return result ? getString(R.string.msg_valid_remail): getString(R.string.msg_invalid_remail);
            case "1"://password
                return result ? getString(R.string.msg_valid_repass): getString(R.string.msg_invalid_repass);
            case "2"://phone
                return result ? getString(R.string.msg_valid_repass): getString(R.string.msg_invalid_repass);
            case "3"://zipcode
                return result ? getString(R.string.msg_valid_refield): getString(R.string.msg_invalid_refield);
            case "4"://text
                return result ? getString(R.string.msg_valid_refield): getString(R.string.msg_invalid_refield);
            case "5"://number
                return result ? getString(R.string.msg_valid_refield): getString(R.string.msg_invalid_refield);
            case "6"://cellPhone
                return result ? getString(R.string.msg_valid_refield): getString(R.string.msg_invalid_refield);
            case "7"://pwtext
                return result ? getString(R.string.msg_valid_refield): getString(R.string.msg_invalid_refield);
            default:
                return getString(R.string.invalid_field);
        }
    }
    private String getMessage(String type, boolean result){
        switch (type) {
            case "0"://email
                return result ? getString(R.string.valid_mail) : getString(R.string.invalid_mail);
            case "1"://password
                return result ? getString(R.string.valid_pass) : getString(R.string.invalid_pass);
            case "2"://phone
                return  result ? getString(R.string.valid_phonenumber): getString(R.string.invalid_phonenumber);
            case "3"://zipcode
                return result ? getString(R.string.valid_field) : getString(R.string.invalid_field);
            case "4"://text
                return result ? getString(R.string.valid_field) : getString(R.string.invalid_field);
            case "5"://number
                return result ? getString(R.string.valid_field) : getString(R.string.invalid_field);
            case "6"://cellPhone
                return result ? getString(R.string.valid_phonenumber): getString(R.string.invalid_phonenumber);
            case "7"://pwtext
                return result ? getString(R.string.valid_field) : getString(R.string.invalid_field);
            default:
                return getString(R.string.invalid_field);
        }
    }

    public String getType() {
        return type;
    }

    public void setText(String text){
        edtMain.setText(text);
    }
    public boolean validateField(){
        boolean result = false;
        String txt = edtMain.getText().toString();
        switch (type) {
            case "0"://email
                result = ValidateForm.isValidEmailAddress(txt);
                if(edtCompare != null && result && edtCompare.isValidField()){
                    if(edtCompare.isValidField()){
                        result =  edtCompare.getText().equals(txt);
                    }
                }
                break;
            case "1"://password
                result = ValidateForm.isValidPassword(txt);
                if(edtCompare != null && result && edtCompare.isValidField()){
                    result = edtCompare.getText().equals(txt);
                }
                break;
            case "2"://phone
                result = ValidateForm.isValidPhone(txt);
                if(edtCompare != null && result && edtCompare.isValidField()){
                    result = edtCompare.getText().equals(txt);
                }
                break;
            case "3"://zipcode
                result = ValidateForm.isValidZipCode(txt);
                if(edtCompare != null && result && edtCompare.isValidField()){
                    result = edtCompare.getText().equals(txt);
                }
                break;
            case "4"://text
                result = !txt.isEmpty();
                if(edtCompare != null && result && edtCompare.isValidField()){
                    result = edtCompare.getText().equals(txt);
                }
                break;
            case "5"://number
                result = !txt.isEmpty();
                if(edtCompare != null && result && edtCompare.isValidField()){
                    result = edtCompare.getText().equals(txt);
                }
                break;
            case "6"://cellPhone
                result = ValidateForm.isValidCellPhone(txt.trim());
                if(edtCompare != null && result && edtCompare.isValidField()){
                    result = edtCompare.getText().equals(txt);
                }
                break;
            case "7"://pwtext
                result = !txt.isEmpty();
                if(edtCompare != null && result && edtCompare.isValidField()){
                    result = edtCompare.getText().equals(txt);
                }
                break;
            default:
                result = false;
                break;
        }
        return result;
    }
    public void setEditTextEnable(boolean enable){
        edtMain.setEnabled(enable);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        //super.setOnClickListener(l);
        edtMain.setId(getId());
        edtMain.setOnClickListener(l);
    }

    public void validThisFieldToContinue(final MaterialValidationEditText validationEditText){
        edtMain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(validationEditText.isValidField()){
                    if(!edtMain.getText().toString().equals(validationEditText.getText())){
                        validationEditText.setIsValidField(false);
                        validationEditText.setMessageText(getMessageCompare(type, false));
                        validationEditText.setIconResource(R.drawable.warning);
                    }else{
                        validationEditText.setIsValidField(true);
                        validationEditText.setMessageText(getMessageCompare(type, true));
                        validationEditText.setIconResource(R.drawable.done);
                    }
                }else{
                    if(validationEditText.validateField() && validateField()){
                        validationEditText.setIsValidField(true);
                        validationEditText.setMessageText(getMessageCompare(type, true));
                        validationEditText.setIconResource(R.drawable.done);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setEdtFocusable(boolean edtFocusable) {
        edtMain.setFocusable(edtFocusable);
        edtMain.setFocusableInTouchMode(edtFocusable);
    }
    public void requestFocusEdt(){
        edtMain.requestFocus();
    }

    @Override
    public boolean performClick() {
        //return super.performClick();
        return edtMain.performClick();
    }
    @Override
    public void setEnabled(boolean enabled) {
        //super.setEnabled(enabled);
        if(edtMain != null){
            edtMain.setEnabled(enabled);
            edtMain.setAlpha(enabled ? 1f : 0.6f);
            edtMain.setTextColor(enabled ? ContextCompat.getColor(getContext(), R.color.colorText) : ContextCompat.getColor(getContext(), R.color.colorHint));
        }
    }

    public void setOnClickIcon(OnClickIcon iconListener){
        this.iconListener = iconListener;
        if(ivStatus != null){
            ivStatus.setOnClickListener(this);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivStatus:
                if(iconListener != null){
                    iconListener.onClickIcon(this, edtMain, ivStatus);
                }
                break;
        }
    }

    public interface OnClickIcon{
        void onClickIcon(MaterialValidationEditText view, EditText edtMain, ImageView icon);
    }
    public void clearIconImage(){
        setPinnedIcon(-1);
        clearIcon();
    }
    public void setPinnedIcon(int pinnedIcon) {
        this.pinnedIcon = pinnedIcon;
    }

}
