package it.polito.ezshop.data.classes;

import it.polito.ezshop.data.Customer;


/*Giulia*/
public class EZCustomer implements Customer {

    private String customerName;
    private Integer id;
    private EZCustomerCard customerCard;

    /*Class constructor*/
    public EZCustomer (String customerName, Integer id)
    {
        this.customerName=customerName;
        this.customerCard =new EZCustomerCard("");
        this.id=id;
        this.customerCard.setPoints(0);

    }
    @Override
    public String getCustomerName() {
        return this.customerName;
    }

    @Override
    public void setCustomerName(String customerName) {
        this.customerName=customerName;
    }

    @Override
    public String getCustomerCard() {
        return this.customerCard.getCardId();
    }

    @Override
    public void setCustomerCard(String customerCard) {
          this.customerCard.setCardId(customerCard);
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;

    }

    @Override
    public Integer getPoints() {
        return this.customerCard.getPoints();
    }

    @Override
    public void setPoints(Integer points) {
        this.customerCard.setPoints(points);

    }
}
