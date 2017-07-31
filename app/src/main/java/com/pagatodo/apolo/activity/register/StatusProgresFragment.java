package com.pagatodo.apolo.activity.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.utils.customviews.StatusViewCupo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jvazquez on 28/07/2017.
 */

public class StatusProgresFragment extends DialogFragment {

    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.btn_reintentar)
    Button btnReintentar;
    @BindView(R.id.status_view)
    StatusViewCupo statusViewCupo;


    public static StatusProgresFragment newInstance() {

        Bundle args = new Bundle();

        StatusProgresFragment fragment = new StatusProgresFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.dialog_progress_register, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
    }

    @OnClick(R.id.btn_next)
    public void nextStatusView(){
        statusViewCupo.updateStatus(10, 30);
    }
}
