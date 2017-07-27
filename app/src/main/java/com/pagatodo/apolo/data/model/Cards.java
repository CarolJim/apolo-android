package com.pagatodo.apolo.data.model;


/**
 * Created by rvargas on 21/07/2017.
 */

public class Cards {
    private int TypeCard;
    private int ThumbCard;
    private int ivCheck;

    public Cards() {}

    public int getTypeCard() {
        return TypeCard;
    }

    public void setTypeCard(int typeCard) {
        TypeCard = typeCard;
    }

    public int getThumbCard() {
        return ThumbCard;
    }

    public void setThumbCard(int thumbCard) {
        ThumbCard = thumbCard;
    }

    public int getIvCheck() {
        return ivCheck;
    }

    public void setIvCheck(int ivCheck) {
        this.ivCheck = ivCheck;
    }

}
