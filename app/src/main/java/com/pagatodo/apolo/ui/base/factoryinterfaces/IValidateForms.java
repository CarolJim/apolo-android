package com.pagatodo.apolo.ui.base.factoryinterfaces;

/**
 * Created by jvazquez on 23/05/2017.
 */

public interface IValidateForms {

    void setValuesDefaultForm();
    void validateForm();
    void showError(String message);
    void onValidationSuccess();
    void getDataForm();
}
