package com.pagatodo.apolo.data.pojo;

/**
 * Created by rvargas on 21/07/2017.
 */

public class Cards {
    private String TypeCard;
    private String ThumbCard;
    private String CheckCard;

    public Cards() {}
    public Cards(String TypeCard,String ThumbCard,String CheckCard) {
        this.TypeCard = TypeCard;
        this.ThumbCard = ThumbCard;
        this.CheckCard = CheckCard;
    }

    public String getTypeCard() {
        return TypeCard;
    }

    public void setTypeCard(String typeCard) {
        TypeCard = typeCard;
    }

    public String getThumbCard() {
        return ThumbCard;
    }

    public void setThumbCard(String thumbCard) {
        ThumbCard = thumbCard;
    }

    public String getCheckCard() {
        return CheckCard;
    }

    public void setCheckCard(String checkCard) {
        CheckCard = checkCard;
    }

}
