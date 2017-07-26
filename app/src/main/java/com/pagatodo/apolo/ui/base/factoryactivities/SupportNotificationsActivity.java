package com.pagatodo.apolo.ui.base.factoryactivities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.gson.Gson;
import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.data.local.Preferences;
import com.pagatodo.apolo.data.model.Mensaje;
import com.pagatodo.apolo.ui.base.factoryadapters.NotificationsVPAdapter;
import com.pagatodo.apolo.utils.customviews.MaterialTextView;
import com.pagatodo.apolo.utils.transformations.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.pagatodo.apolo.data.local.PreferencesContract.LIST_NOTIFICATIONS;
import static com.pagatodo.apolo.data.remote.notifications.FCMService.ACTION_HAVE_NOTIFICATION;

/**
 * Created by jvazquez on 22/06/2017.
 */

public abstract class SupportNotificationsActivity extends SupportUXActivity{
    protected Preferences pref = null;
    private ViewPager mPagerNotifications = null;
    private NotificationsVPAdapter mAdapter = null;
    private List<Mensaje> mensajes = new ArrayList<>();
    private View flNotifications = null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        pref = App.getInstance().getPrefs();


    }

    protected void enableNotificationsAtRuntime(boolean enable){
        if(enable){
            IntentFilter filter = new IntentFilter();
            filter.addAction(ACTION_HAVE_NOTIFICATION);
            registerReceiver(broadcastReceiver, filter);
        }else{
            unregisterReceiver(broadcastReceiver);
        }
    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case ACTION_HAVE_NOTIFICATION:
                    if(isShowingNotifications()){
                        mensajes = pref.obtenerColaDeMensajes();
                        getmAdapter().notifyDataSetChanged();
                    }else{
                        showNotifications();
                    }
                    break;
            }
        }
    };

    private boolean isShowingNotifications() {
        return getFlNotifications() != null && getFlNotifications().getVisibility() == VISIBLE;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            unregisterReceiver(broadcastReceiver);
        }catch (Exception e){

        }
    }

    public ViewPager getmPagerNotifications() {
        if(mPagerNotifications == null){
            mPagerNotifications = (ViewPager) findViewById(R.id.vpNotifications);
        }
        return mPagerNotifications;
    }


    protected View getFlNotifications() {
        if(flNotifications == null){
            flNotifications = findViewById(R.id.flNotifications);
        }
        return flNotifications;
    }

    protected void showNotifications() {
        mensajes = pref.obtenerColaDeMensajes();
        if(mensajes.isEmpty()){
            getFlNotifications().setVisibility(GONE);
            return;
        }
        if(getmPagerNotifications() != null){
            handleViewPager(getmPagerNotifications(), findViewById(R.id.ivArrowLeft), findViewById(R.id.ivArrowRight));
        }
    }

    public NotificationsVPAdapter getmAdapter() {
        if(mAdapter == null){
            mAdapter = new NotificationsVPAdapter(getSupportFragmentManager(), mensajes);
        }
        return mAdapter;
    }

    private void handleViewPager(final ViewPager pager, final View arrowLeft, final View arrowRight) {
        final MaterialTextView tvNext = (MaterialTextView) findViewById(R.id.tvNextNotification);
        final MaterialTextView tvCloseNotifications = (MaterialTextView) findViewById(R.id.tvCloseNotifications);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
        pager.setAdapter(getmAdapter());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    tvCloseNotifications.setText("SALIR");
                    startAnimViewMinimize(arrowLeft, GONE);
                    startAnimViewMinimize(arrowRight, mensajes.size() == 1 ? GONE : VISIBLE);
                    tvNext.setEnabled(mensajes.size() != 1);
                    tvNext.setTextColor(ContextCompat.getColor(SupportNotificationsActivity.this, mensajes.size() == 1 ? R.color.colorHint : R.color.colorPrimary));
                } else if (position == mensajes.size() - 1) {
                    deleteMessageInbox(getmAdapter().getItems().get(position));
                    tvNext.setEnabled(false);
                    tvNext.setTextColor(ContextCompat.getColor(SupportNotificationsActivity.this, R.color.colorHint));
                    startAnimViewMinimize(arrowLeft, VISIBLE);
                    startAnimViewMinimize(arrowRight, GONE);
                } else {
                    deleteMessageInbox(getmAdapter().getItems().get(position));
                    tvNext.setEnabled(true);
                    tvNext.setTextColor(ContextCompat.getColor(SupportNotificationsActivity.this, R.color.colorPrimary));
                    tvCloseNotifications.setText("ANTERIOR");
                    startAnimViewMinimize(arrowLeft, VISIBLE);
                    startAnimViewMinimize(arrowRight, VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(pager.getCurrentItem() - 1);
            }
        });
        arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(pager.getCurrentItem() + 1);
            }
        });
        if(tvNext != null){
            tvNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pager.setCurrentItem(pager.getCurrentItem() + 1);
                }
            });
        }
        if(tvCloseNotifications != null){
            tvCloseNotifications.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (pager.getCurrentItem()){
                        case 0:
                            startAnimView(getFlNotifications(), GONE, 1000);
                            if(!mensajes.isEmpty()){
                                deleteMessageInbox(mensajes.get(0));
                            }
                            break;
                        default:
                            pager.setCurrentItem(pager.getCurrentItem() - 1);
                            break;
                    }
                }
            });
        }
        getmAdapter().updateList(mensajes);
        startAnimView(getFlNotifications(), VISIBLE, 1000);
    }
    private void deleteMessageInbox(Mensaje mensajeToDelete){
        List<Mensaje> newListMensaje = new ArrayList<>();
        for(Mensaje mensaje: mensajes){
            if(!(mensaje.getIdMensaje() == mensajeToDelete.getIdMensaje())){
                newListMensaje.add(mensaje);
            }
        }
        pref.saveData(LIST_NOTIFICATIONS, new Gson().toJson(newListMensaje));
    }
}
