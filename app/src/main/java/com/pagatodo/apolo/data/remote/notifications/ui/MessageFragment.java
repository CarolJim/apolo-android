package com.pagatodo.apolo.data.remote.notifications.ui;

import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.data.model.Mensaje;
import com.pagatodo.apolo.ui.base.factoryfragments.BaseFragment;

/**
 * Created by Omar on 22/06/2017.
 */

public class MessageFragment extends BaseFragment {
    public final static String MESSAGE_KEY = "messageKey";
    private Mensaje message;
//    private SimpleDraweeView dvImageDetail;
//    private UtilsFabEffects fabEffects;
//    ImageLoader loader = new ImageLoader(App.getInstance().getApplicationContext());

    public static MessageFragment newInstance(Mensaje notification)
    {
        MessageFragment messageFragment = new MessageFragment();

        Bundle args = new Bundle();
        args.putSerializable(MESSAGE_KEY, notification);
        messageFragment.setArguments(args);

        return messageFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
        {
            if(getArguments().containsKey(MESSAGE_KEY))
            {
                message = (Mensaje) getArguments().getSerializable(MESSAGE_KEY);
            }
        }

//        fabEffects = new UtilsFabEffects(getActivity());
    }

    public Mensaje getMessage() {
        return message;
    }

    @Override
    protected String setFragmentTag() {
        return "MessageFragment";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        setFabEffectsReferences(getView());
//        configureView(getView());

    }

    @Override
    public void onResume() {
        super.onResume();
//        doAnalyticsTracking(DETALLE_NOTIFICACION_LBL);
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener( new View.OnKeyListener()
//        {
//            @Override
//            public boolean onKey( View v, int keyCode, KeyEvent event )
//            {
//                if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
//                {
//                    if (fabEffects.isFabShown()) {
//                        fabEffects.fabAction();
//                    }
//                }
//                return false;
//            }
//        } );
//
//        getActivity().getIntent().putExtra(MESSAGE_KEY, message);
    }

//    private void setFabEffectsReferences(View rootView)
//    {
//        fabEffects.setFabShare((FloatingActionButton) rootView.findViewById(R.id.fragment_message_fab_share));
//        fabEffects.setTransparentBG(rootView.findViewById(R.id.fragment_message_transparent_bg));
//        fabEffects.setFabFacebookContainer((LinearLayout) rootView.findViewById(R.id.fragment_message_fab_facebook_container));
//        fabEffects.setFabTwitterContainer((LinearLayout) rootView.findViewById(R.id.fragment_message_fab_twitter_container));
//        fabEffects.setFabWhatsappContainer((LinearLayout) rootView.findViewById(R.id.fragment_message_fab_whatsapp_container));
//        fabEffects.setFabEmailContainer((LinearLayout) rootView.findViewById(R.id.fragment_message_fab_email_container));
//        fabEffects.setFabShareFacebook((FloatingActionButton) rootView.findViewById(R.id.fragment_message_fab_share_facebook));
//        fabEffects.setFabShareTwitter((FloatingActionButton) rootView.findViewById(R.id.fragment_message_fab_share_twitter));
//        fabEffects.setFabShareWhatsapp((FloatingActionButton) rootView.findViewById(R.id.fragment_message_fab_share_whatsapp));
//        fabEffects.setFabShareEmail((FloatingActionButton) rootView.findViewById(R.id.fragment_message_fab_share_email));
//        fabEffects.setFabLblFacebookContainer((LinearLayout) rootView.findViewById(R.id.fragment_message_ll_share_facebook_lbl));
//        fabEffects.setFabLblTwitterContainer((LinearLayout) rootView.findViewById(R.id.fragment_message_ll_share_twitter_lbl));
//        fabEffects.setFabLblWhatsappContainer((LinearLayout) rootView.findViewById(R.id.fragment_message_ll_share_whatsapp_lbl));
//        fabEffects.setFabLblEmailContainer((LinearLayout) rootView.findViewById(R.id.fragment_message_ll_share_email_lbl));
//    }

//    private void configureView(View rootView)
//    {
//        dvImageDetail = (SimpleDraweeView) rootView.findViewById(R.id.fragment_message_dv_detail_image);
//        setSimpleDraweeViewWidth();
//
//        loader.with(ImageLoader.Loaders.FRESCO).setUri(Uri.parse(message.getUrlImagen())).setDestination(dvImageDetail).reSize(1024, 600).build();
//
//        TextView tvInboxDesc = (TextView) rootView.findViewById(R.id.fragment_message_tv_promo_desc);
//        LinearLayout.LayoutParams tvParams = (LinearLayout.LayoutParams) tvInboxDesc.getLayoutParams();
//        tvParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, getResources().getDimension(R.dimen.dimen_margin_large), getResources().getDisplayMetrics());;
//        tvInboxDesc.requestLayout();
//
//        //Does message have "titulo"?
//        if(wouldViewsBeShown(message.getTitulo()))
//            ((TextView) rootView.findViewById(R.id.fragment_message_tv_promo_title)).setText(message.getTitulo());
//
//        //Check if there's information for "contenido".
//        if(wouldViewsBeShown(message.getContenido()))
//            tvInboxDesc.setText(message.getContenido());
//        else
//            tvInboxDesc.setVisibility(View.GONE);
//
//
//
//    }

//    private boolean wouldViewsBeShown(String content)
//    {
//        if(content != null)
//            if(content.trim().length() > 0)
//                return true;
//        return false;
//    }
//
//    private void setSimpleDraweeViewWidth()
//    {
//        Display display = getActivity().getWindowManager().getDefaultDisplay();
//        Point pointer = new Point();
//        display.getSize(pointer);
//        dvImageDetail.getLayoutParams().width = pointer.x;
//        dvImageDetail.requestLayout();
//    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    protected int setIdMainView() {
        return 0;
    }
}