package it.polito.ezshop.data.classes;

public class EZCustomerCard {

    String cardId;
    Integer points;

    public EZCustomerCard(String cardId) {
        this.cardId = cardId;
        this.points=0;
    }
    public EZCustomerCard(String cardId,Integer points) {
        this.cardId = cardId;
        this.points=points;
    }

    public EZCustomerCard() {
        this.points=0;
    }


    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }


    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }


}
