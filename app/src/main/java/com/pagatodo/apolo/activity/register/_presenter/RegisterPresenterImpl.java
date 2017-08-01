package com.pagatodo.apolo.activity.register._presenter;

import android.os.Handler;

import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterInteractor;
import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterPresenter;
import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterView;
import com.pagatodo.apolo.data.model.Cards;
import com.pagatodo.apolo.data.model.Documento;
import com.pagatodo.apolo.data.model.FormularioAfiliacion;
import com.pagatodo.apolo.data.model.webservice.request.CreditRequestRegisterRequest;
import com.pagatodo.apolo.data.model.webservice.request.DocumentUploadRequest;
import com.pagatodo.apolo.data.model.webservice.response.CreditRequestRegisterResponse;
import com.pagatodo.apolo.data.model.webservice.response.GeneralServiceResponse;
import com.pagatodo.apolo.data.remote.BuildRequest;
import com.pagatodo.apolo.data.remote.RequestContract;
import com.pagatodo.apolo.ui.base.factorypresenters.BasePresenter;
import com.pagatodo.networkframework.DataManager;
import com.pagatodo.networkframework.interfaces.IRequestResult;
import com.pagatodo.apolo.utils.Constants;

import java.util.HashMap;
import java.util.List;

import static com.pagatodo.apolo.data.remote.RequestContract.DOCUMENT_UPLOAD;
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
        if(mFormularioAfiliacion == null){
            this.mFormularioAfiliacion = request;
        }
        if(mFormularioAfiliacion.getFolio().isEmpty()){
            CreditRequestRegisterRequest requestRegisterRequest = new CreditRequestRegisterRequest(request.getTelefonoCasa(), request.getTelefonoMovil());
            BuildRequest.doCreditRequestRegister(this, requestRegisterRequest, pref.getHeaders());
        }else{
            uploadDocuments();
        }

    }

    private void uploadDocuments() {
        int timeRequest = 1000;
        for(Documento documento: mFormularioAfiliacion.getDocumentos()){
            if(!documento.isUploaded()){
                doDocumentUpload(this, generateRequestDocument(documento), pref.getHeaders(), timeRequest);
                timeRequest = timeRequest + 1000;
            }
        }
    }

    @Override
    public void onSuccess(DataManager dataManager) {
        super.onSuccess(dataManager);
        switch (dataManager.getMethod()){
            case DO_CREDIT_REQUEST_REGISTER:
                processCreditResponse((CreditRequestRegisterResponse) dataManager.getData());
                break;
            case DOCUMENT_UPLOAD:
                processDocumentUploadResponse((DocumentUploadRequest) dataManager.getRequest(), (GeneralServiceResponse) dataManager.getData());
                break;
        }
    }

    @Override
    public void onFailed(DataManager dataManager) {
        super.onFailed(dataManager);
        switch (dataManager.getMethod()){
            case DO_CREDIT_REQUEST_REGISTER:
                view.errorRegister((String) dataManager.getData());
                view.showMessage((String) dataManager.getData());
                break;
            case DOCUMENT_UPLOAD:
                DocumentUploadRequest uploadRequest = (DocumentUploadRequest) dataManager.getRequest();
                view.errorUploadDocument(new Documento(
                        uploadRequest.getRequest().getIdTipoDocumento(),
                        uploadRequest.getRequest().getNombre(),
                        uploadRequest.getRequest().getDocumentoBase64(),
                        uploadRequest.getRequest().getLongitud(),
                        uploadRequest.getRequest().getFolio(),
                        uploadRequest.getRequest().getIdCliente()), (String) dataManager.getData());
                break;
        }
    }

    private void processDocumentUploadResponse(DocumentUploadRequest request, GeneralServiceResponse response) {
        switch (response.getRespuesta().getCodigo()){
            case RESPONSE_CODE_OK:
                //Documento(int idTipoDocumento, String nombre, String documentoBase64, int longitud, String folio, String idCliente)
                view.successUploadDocument(new Documento(
                        request.getRequest().getIdTipoDocumento(),
                        request.getRequest().getNombre(),
                        request.getRequest().getDocumentoBase64(),
                        request.getRequest().getLongitud(),
                        request.getRequest().getFolio(),
                        request.getRequest().getIdCliente()));
                break;
            default:

                break;
        }
    }

    private void processCreditResponse(CreditRequestRegisterResponse response) {
        switch (response.getRespuesta().getCodigo()){
            case RESPONSE_CODE_OK:
                mFormularioAfiliacion.setFolio(response.getSolicitud().getSolicitud());
                view.successRegister();
                int mTimeRequest = 1000;
                for(Documento documento: mFormularioAfiliacion.getDocumentos()){
                    documento.setFolio(response.getSolicitud().getSolicitud());
                    documento.setIdCliente(String.valueOf(response.getSolicitud().getID_Cliente()));

                    doDocumentUpload(this, generateRequestDocument(documento), pref.getHeaders(), mTimeRequest);
                    mTimeRequest = mTimeRequest + 1000;
                }
                break;
            default:
                view.errorRegister(response.getRespuesta().getMensaje());
                view.showMessage(response.getRespuesta().getMensaje());
                break;
        }
    }

    @Override
    public String getFolio() {
        if(mFormularioAfiliacion != null && !mFormularioAfiliacion.getFolio().isEmpty()){
            return mFormularioAfiliacion.getFolio();
        }
        return "";
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

    private DocumentUploadRequest generateRequestDocument(Documento documento){
        return new DocumentUploadRequest(
                documento.getNombre(),
                documento.getDocumentoBase64(),
                documento.getLongitud(),
                documento.getIdTipoDocumento(),
                documento.getFolio(),
                documento.getIdCliente());
    }
    private void doDocumentUpload(final IRequestResult result, final DocumentUploadRequest request, HashMap<String, String> headers, int time){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BuildRequest.doDocumentUpload(result, request, pref.getHeaders());

            }
        }, time);
    }
    public boolean doesDocumentExist(Documento currentDocument, FormularioAfiliacion mFormularioAfiliacion)
    {
        for (Documento document : mFormularioAfiliacion.getDocumentos()){
            if(document.getIdTipoDocumento() == currentDocument.getIdTipoDocumento())
            {
                if(document.getLongitud() != 0)
                    return true;
                else
                    return false;
            }
        }
        return false;
    }

    public int getDocumentPosition(Documento currentDocument, FormularioAfiliacion mFormularioAfiliacion)
    {
        int index = 0;
        for(Documento document : mFormularioAfiliacion.getDocumentos())
        {
            if(document.getIdTipoDocumento() == currentDocument.getIdTipoDocumento())
                break;
            index++;
        }
        return index;
    }

    public int getListPosition(Documento currentDocument)
    {
        switch (currentDocument.getIdTipoDocumento())
        {
            case Constants.SOLICITUD_ADULTO_MAYOR:
                return Constants.SOLICITUD_ADULTO_MAYOR_INDEX;
            case Constants.SOLICITUD_IFE_INE_FRENTE:
                return  Constants.SOLICITUD_IFE_INE_FRENTE_INDEX;
            case Constants.SOLICITUD_IFE_INE_REVERSO:
                return Constants.SOLICITUD_IFE_INE_REVERSO_INDEX;
        }
        return 0;
    }
}
