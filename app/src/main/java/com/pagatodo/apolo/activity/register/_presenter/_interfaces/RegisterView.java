package com.pagatodo.apolo.activity.register._presenter._interfaces;

import android.provider.DocumentsContract;

import com.pagatodo.apolo.data.model.Documento;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnView;

/**
 * Created by rvargas on 21-07-17.
 */

public interface RegisterView extends IEventOnView{
    void logoutActivity();
    void setNavigation();
    void returnData();
    void showMessage(String message);
    void errorRegister(String message);
    void successRegister();
    void errorUploadDocument(Documento documento, String message);
    void successUploadDocument(Documento documento);
}