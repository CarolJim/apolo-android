package com.pagatodo.apolo.data.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.data.pojo.Cards;
import java.util.List;

/**
 * Created by rvargas on 19/07/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>  {

    private List<Cards> cardsList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView typeCards;
        AppCompatImageView thumbCards;
        AppCompatImageView checkCards;

        public MyViewHolder(View view) {
            super(view);
            typeCards  =  view.findViewById(R.id.typeCard);
            thumbCards =  view.findViewById(R.id.thumbCard);
            checkCards =  view.findViewById(R.id.checkCard);
        }
    }

    public CustomAdapter(Context mContext, List<Cards> cardList) {
        this.mContext = mContext;
        this.cardsList = cardList;
    }


    public Object getItem(int location) {
        return cardsList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cardview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Cards items = cardsList.get(position);
        Glide.with(mContext).load(items.getTypeCard()).placeholder(R.drawable.small_preview).crossFade().into(holder.typeCards);
        Glide.with(mContext).load(items.getThumbCard()).placeholder(R.drawable.small_preview).crossFade().into(holder.thumbCards);
    }

    @Override
    public int getItemCount() {
        return cardsList.size();
    }
}