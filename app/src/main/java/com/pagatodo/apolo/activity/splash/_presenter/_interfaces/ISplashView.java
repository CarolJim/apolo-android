package com.pagatodo.apolo.activity.splash._presenter._interfaces;

import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnView;

/**
 * Created by jvazquez on 26/07/2017.
 */

public interface ISplashView extends IEventOnView{

    void updatePromotorsSuccess();
    void updatePromotorsFailed(String title, String message);
}
