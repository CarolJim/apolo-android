package com.pagatodo.apolo.data.remote;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.data.model.webservice.remoteconfig.ResponseRemoteConfig;
import com.pagatodo.apolo.data.pojo.factory.ModelPattern;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.mega.GetPushStatus;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.mega.MegaBaseModel;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.requestinbox.RequestGetInbox;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.requestregistrodispositivo.RequestRegistroDispositivo;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.requestregistrousuario.RequestRegistroUsuario;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.response.ResponseInbox;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.response.ResponseRegistroDispositivo;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.response.ResponseRegistroUsuario;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.updateState.RequestUpdateStatus;
import com.pagatodo.networkframework.RequestBuilder;
import com.pagatodo.networkframework.interfaces.IRequestResult;

import java.util.HashMap;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.pagatodo.apolo.data.remote.RequestContract.GET_REMOTE_CONFIG;
import static com.pagatodo.apolo.data.remote.RequestContract.MEGA_ACTIVATE_PUSH;
import static com.pagatodo.apolo.data.remote.RequestContract.MEGA_GET_PUSH_STATUS;
import static com.pagatodo.apolo.data.remote.RequestContract.MEGA_OBTENER_MSJ_INBOX;
import static com.pagatodo.apolo.data.remote.RequestContract.MEGA_REGISTER_DEVICE;
import static com.pagatodo.apolo.data.remote.RequestContract.MEGA_REGISTER_USER;
import static com.pagatodo.apolo.utils.Constants.DEBUG;
import static com.pagatodo.apolo.utils.Constants.TIMEOUT;

/**
 * Created by jvazquez on 26/07/2017.
 */

public class BuildRequest {

    //  REMOTE CONFIG
    public static void updateRemoteConfig(IRequestResult result) {
        new RequestBuilder().request(App.getInstance(),
                DEBUG,
                GET_REMOTE_CONFIG,
                GET,
                null,
                RemoteConfig.getUrlRemoteConfig(),
                null,
                result,
                TIMEOUT,
                ResponseRemoteConfig.class);
    }
    //    REQUEST MEGA
    public static void registerDeviceMega(HashMap<String, String> headers, RequestRegistroDispositivo request, IRequestResult result){
        new RequestBuilder().request(App.getInstance(),
                DEBUG,
                MEGA_REGISTER_DEVICE,
                POST,
                headers,
                RemoteConfig.getUrlMega() + MEGA_REGISTER_DEVICE,
                request,
                result,
                TIMEOUT,
                ResponseRegistroDispositivo.class);
    }
    public static void registerUserMega(HashMap<String, String> headers, RequestRegistroUsuario request, IRequestResult result){
        new RequestBuilder().request(App.getInstance(),
                DEBUG,
                MEGA_REGISTER_USER,
                POST,
                headers,
                RemoteConfig.getUrlMega() + MEGA_REGISTER_USER,
                request,
                result,
                TIMEOUT,
                ResponseRegistroUsuario.class);
    }
    public static void requestGetInboxs(HashMap<String, String> headers, RequestGetInbox request, IRequestResult result){
        new RequestBuilder().request(App.getInstance(),
                DEBUG,
                MEGA_OBTENER_MSJ_INBOX,
                POST,
                headers,
                RemoteConfig.getUrlMega() + MEGA_OBTENER_MSJ_INBOX,
                request,
                result,
                TIMEOUT,
                ResponseInbox.class);
    }

    public static void getPushNotificationStatus(HashMap<String, String> headers,IRequestResult result) {
        new RequestBuilder().request(App.getInstance(),
                DEBUG,
                MEGA_GET_PUSH_STATUS,
                POST,
                headers,
                RemoteConfig.getUrlMega() + MEGA_GET_PUSH_STATUS,
                "",
                result,
                TIMEOUT,
                GetPushStatus.class);
    }

    public static void requestUpdatePushIndicator(HashMap<String, String> headers, RequestUpdateStatus request, IRequestResult result){
        new RequestBuilder().request(App.getInstance(),
                DEBUG,
                MEGA_ACTIVATE_PUSH,
                POST,
                headers,
                RemoteConfig.getUrlMega() + MEGA_ACTIVATE_PUSH,
                request,
                result,
                TIMEOUT,
                MegaBaseModel.class);
    }
}
