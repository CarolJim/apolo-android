package com.pagatodo.apolo.data.model;


/**
 * Created by rvargas on 21/07/2017.
 */

public class Cards {
    private int TypeCard    = 0;
    private int ThumbCard   = 0;
    private int ivCheck     = 0;
    private Documento documento = new Documento();

    public Cards() {}

    public Cards(int typeCard, int thumbCard, int ivCheck, Documento documento) {
        TypeCard = typeCard;
        ThumbCard = thumbCard;
        this.ivCheck = ivCheck;
        this.documento = documento;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

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
