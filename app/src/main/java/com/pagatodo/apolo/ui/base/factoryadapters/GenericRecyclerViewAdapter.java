package com.pagatodo.apolo.ui.base.factoryadapters;

import android.support.v7.widget.RecyclerView;

import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnFragment;

import java.util.List;

/**
 * Created by jvazquez on 19/05/2017.
 */

public abstract class GenericRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>{
    protected List<T> items;
    protected IEventOnFragment eventOnFragment;

    public GenericRecyclerViewAdapter(List<T> items, IEventOnFragment onEventListener) {
        this.items = items;
        this.eventOnFragment = onEventListener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<T> getItems() {
        return items;
    }

    public Boolean updateList(List<T> items) {
        if (items != null && items.size() > 0) {
            this.items.clear();
            this.items.addAll(items);
            notifyDataSetChanged();
            return items.size() > 0;
        }
        return false;
    }
    public boolean isEmpty(){
        return items != null && items.isEmpty();
    }

    protected void sendEvent(String event, Object data){
        if(eventOnFragment != null){
            eventOnFragment.onEvent(event, data);
        }
    }
}
