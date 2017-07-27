package com.pagatodo.apolo.ui.dialogs.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;

import com.pagatodo.apolo.data.model.factory.ModelPattern;
import com.pagatodo.apolo.ui.dialogs.GenericDialog;

/**
 * Clase que define el modelo de datos que manipula el
 * {
 * @see GenericDialog
 * }
 * Created by jvazquez on 22/05/2017.zx
 */

public class DialogContent  extends ModelPattern {
    private String type              = "";
    @LayoutRes
    private int    idLayout          = -1;
    @DrawableRes
    private int    idImageResource   = -1;
    private String title             = "";
    private String content           = "";
    private String btnPrimaryText    = "";
    private String btnSecondaryText  = "";
    private String actionPrimary     = "";
    private String actionSecondary   = "";

    public DialogContent(String type, @LayoutRes int idLayout, @DrawableRes int idImageResource, String title, String content, String btnPrimaryText, String btnSecondaryText, String actionPrimary, String actionSecondary){
        this.type               = type;
        this.idImageResource    = idImageResource;
        this.idLayout           = idLayout;
        this.title              = title;
        this.content            = content;
        this.btnPrimaryText     = btnPrimaryText;
        this.btnSecondaryText   = btnSecondaryText;
        this.actionPrimary      = actionPrimary;
        this.actionSecondary    = actionSecondary;
    }
    public DialogContent(){

    }
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getIdImageResource() {
        return idImageResource;
    }

    public void setIdImageResource(int idImageResource) {
        this.idImageResource = idImageResource;
    }

    public int getIdLayout() {
        return idLayout;
    }

    public void setIdLayout(int idLayout) {
        this.idLayout = idLayout;
    }

    public String getActionPrimary() {
        return actionPrimary;
    }

    public String getActionSecondary() {
        return actionSecondary;
    }

    public String getBtnPrimaryText() {
        return btnPrimaryText;
    }

    public String getBtnSecondaryText() {
        return btnSecondaryText;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setActionPrimary(String actionPrimary) {
        this.actionPrimary = actionPrimary;
    }

    public void setActionSecondary(String actionSecondary) {
        this.actionSecondary = actionSecondary;
    }

    public void setBtnPrimaryText(String btnPrimaryText) {
        this.btnPrimaryText = btnPrimaryText;
    }

    public void setBtnSecondaryText(String btnSecondaryText) {
        this.btnSecondaryText = btnSecondaryText;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
