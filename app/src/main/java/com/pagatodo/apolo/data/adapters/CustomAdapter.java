package com.pagatodo.apolo.data.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.data.model.Cards;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnFragment;
import com.pagatodo.apolo.utils.customviews.MaterialTextView;

import java.util.List;

import static com.pagatodo.apolo.ui.base.BaseEventContract.DOCUMENTS_RV_ITEM_SELECTED;

/**
 * Created by rvargas on 19/07/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.mViewHolder>  {

    private List<Cards> cardsList;
    private Context mContext;
    private final static int FADE_DURATION = 1000; // in milliseconds

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
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cardview, parent, false);

        return new mViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, final int position) {
        final Cards items = cardsList.get(position);
        holder.typeCards.setText(items.getTypeCard());
        holder.thumbCards.setImageResource(items.getThumbCard());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mContext instanceof IEventOnFragment)
                    ((IEventOnFragment)mContext).onEvent(DOCUMENTS_RV_ITEM_SELECTED, items.getDocumento());
            }
        });

        setFadeAnimation(holder.itemView);
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return cardsList.size();
    }

    protected class mViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView typeCards;
        AppCompatImageView thumbCards;
        AppCompatImageView checkCards;

        public mViewHolder(View view) {
            super(view);
            typeCards  = (MaterialTextView) view.findViewById(R.id.typeCard);
            thumbCards = (AppCompatImageView) view.findViewById(R.id.thumbCard);
            checkCards = (AppCompatImageView) view.findViewById(R.id.ivCheck);
        }
    }

}