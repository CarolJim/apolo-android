package com.pagatodo.apolo.activity.register._presenter;

import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterInteractor;
import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterPresenter;
import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterView;
import com.pagatodo.apolo.data.model.Cards;
import com.pagatodo.apolo.data.model.Documento;
import com.pagatodo.apolo.data.model.FormularioAfiliacion;
import com.pagatodo.apolo.data.model.webservice.request.CreditRequestRegisterRequest;
import com.pagatodo.apolo.data.model.webservice.response.CreditRequestRegisterResponse;
import com.pagatodo.apolo.data.remote.BuildRequest;
import com.pagatodo.apolo.data.remote.RequestContract;
import com.pagatodo.apolo.ui.base.factorypresenters.BasePresenter;
import com.pagatodo.networkframework.DataManager;

import java.util.List;

import static com.pagatodo.apolo.data.remote.RequestContract.DO_CREDIT_REQUEST_REGISTER;
import static com.pagatodo.networkframework.model.ResponseConstants.RESPONSE_CODE_OK;

/**
 * Created by rvargas on 21-07-17.
 */

public class RegisterPresenterImpl extends BasePresenter<RegisterView> implements RegisterPresenter, RegisterInteractor.onRegisterListener, RegisterInteractor.onRequestListener  {

    private RegisterInteractor registerInteractor;
    private FormularioAfiliacion mFormularioAfiliacion = null;

    public RegisterPresenterImpl(RegisterView registerView) {
        super(registerView);
        registerInteractor = new RegisterInteractorImpl();
    }

    @Override
    public void register(String numberCelPhone, String numberPhone, String rutaCard, String rutaINEFront, String rutaINEBack) {
        registerInteractor.onRegisterAfiliado(numberCelPhone, numberPhone, rutaCard, rutaINEFront, rutaINEBack, this);
    }

    @Override
    public void requestRegister(FormularioAfiliacion request) {
        this.mFormularioAfiliacion = request;
        CreditRequestRegisterRequest requestRegisterRequest = new CreditRequestRegisterRequest(request.getTelefonoCasa(), request.getTelefonoMovil());

        BuildRequest.doCreditRequestRegister(this, requestRegisterRequest, pref.getHeaders());
    }

    @Override
    public void onSuccess(DataManager dataManager) {
        super.onSuccess(dataManager);
        switch (dataManager.getMethod()){
            case DO_CREDIT_REQUEST_REGISTER:
                processCreditResponse((CreditRequestRegisterResponse) dataManager.getData());
                break;
        }
    }

    private void processCreditResponse(CreditRequestRegisterResponse response) {
        switch (response.getRespuesta().getCodigo()){
            case RESPONSE_CODE_OK:
                for(Documento documento: mFormularioAfiliacion.getDocumentos()){
                    documento.setFolio(response.getSolicitud().getSolicitud());
                    documento.setIdCliente(String.valueOf(response.getSolicitud().getID_Cliente()));
//                    BuildRequest.doDocumentUpload(this, );
                }
                break;
            default:
                view.showMessage(response.getRespuesta().getMensaje());
                break;
        }
    }

    @Override
    public void request(List<Cards> cardsList) {
        registerInteractor.onRequestData(cardsList, this);
    }

    @Override
    public void onSuccess() {
        if (view!=null)
            view.setNavigation();
    }

    @Override
    public void failure(String message) {
        if (view!=null)
            view.showMessage(message);
    }

    @Override
    public void onSuccessRequest() {
        if (view!=null)
            view.returnData();
    }

    @Override
    public void failureRequest(String message) {
        if (view!=null)
            view.showMessage(message);
    }

    @Override
    public void messagesError(String message) {
        if (view!=null)
            view.setMessageError(message);
    }

}
