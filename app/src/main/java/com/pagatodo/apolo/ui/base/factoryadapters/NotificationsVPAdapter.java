package com.pagatodo.apolo.ui.base.factoryadapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pagatodo.apolo.data.model.Mensaje;
import com.pagatodo.apolo.data.remote.notifications.ui.MessageFragment;

import java.util.List;

/**
 * Created by jvazquez on 22/06/2017.
 */

public class NotificationsVPAdapter extends FragmentStatePagerAdapter {

    private List<Mensaje> items;


    public NotificationsVPAdapter(FragmentManager fm, List<Mensaje> items){
        super(fm);
        this.items = items;
    }
    @Override
    public Fragment getItem(int position) {
        Mensaje currentPromoObject = items.get(position);
        return MessageFragment.newInstance(currentPromoObject);
    }
    public void updateList(List<Mensaje> objectList){
        if(objectList != null && objectList.size() > 0){
            items = objectList;
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        return items.size();
    }

    public List<Mensaje> getItems() {
        return items;
    }

    @Override
    public int getItemPosition(Object object) {
//        return super.getItemPosition(object);
        return POSITION_NONE;
    }
}
