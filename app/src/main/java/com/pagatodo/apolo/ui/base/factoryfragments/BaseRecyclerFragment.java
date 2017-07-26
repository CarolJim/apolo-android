package com.pagatodo.apolo.ui.base.factoryfragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pagatodo.apolo.ui.base.factoryadapters.GenericRecyclerViewAdapter;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IProcessData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jvazquez on 19/05/2017.
 */

public abstract class BaseRecyclerFragment<iProcessData extends IProcessData, item, VH extends RecyclerView.ViewHolder, Adapter extends GenericRecyclerViewAdapter<item, VH>> extends BaseFragment<iProcessData> implements SwipeRefreshLayout.OnRefreshListener {
    private List<item> itemsList = new ArrayList<>();
    protected Adapter adapter;
    @IdRes
    protected int idMainRecycler        = -1;
    private RecyclerView rvMainRecycler;
    @IdRes
    protected int idSwipeRefreshLayout  = -1;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idMainRecycler = setIdMainRecycler();
        idSwipeRefreshLayout = setIdSwipeRefreshLayout();
        initializeAdapter();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycler(view);
        initSwipeToRefresh(view);
    }
    @IdRes
    protected abstract int setIdMainRecycler();

    @IdRes
    protected abstract int setIdSwipeRefreshLayout();

    private void initSwipeToRefresh(View view) {
        if(idSwipeRefreshLayout != -1 && view != null){
            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(idSwipeRefreshLayout);
            if(swipeRefreshLayout != null){
                swipeRefreshLayout.setOnRefreshListener(this);
            }
        }
    }

    protected void initRecycler(View view) {
        if(idMainRecycler != -1 && view != null){
            RecyclerView recyclerView = (RecyclerView) view.findViewById(idMainRecycler);
            if(recyclerView != null){
                LinearLayoutManager lm = new LinearLayoutManager(getContext(), 1, false);
                lm.setOrientation(GridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(lm);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
            }
        }

    }

    public void setItemsList(List<item> itemsList) {
        this.itemsList = itemsList;
        adapter.updateList(itemsList);
    }

    public List<item> getItemsList() {
        return itemsList;
    }

    protected void updateList(List<item> itemsList){
        if(adapter != null){
            adapter.updateList(itemsList);
        }
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    public Adapter getAdapter() {
        return adapter;
    }

    public RecyclerView getRecyclerView() {
        return rvMainRecycler;
    }

    protected abstract void initializeAdapter();
    protected abstract void refreshView();

    @Override
    public void onRefresh() {
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(true);
        }
        refreshView();
    }
    @Override
    public void showError(String message) {
        super.showError(message);
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void hideProgressView() {
        super.hideProgressView();
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void hideProgressActivity() {
        super.hideProgressActivity();
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void showProgressView(String message) {
        super.showProgressView(message);
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void showMainView() {
        super.showMainView();
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void showProgressActivity(String message) {
        super.showProgressActivity(message);
        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }
}


