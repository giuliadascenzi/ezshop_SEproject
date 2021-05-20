package it.polito.ezshop.data.classes;

import it.polito.ezshop.data.TicketEntry;

/*
    Note: this class should be called "ProductEntry", and it is kind of the
    equivalent of the "Sale_QuantityDiscount" class in the design class diagram,
    i.e. it refers to a specific product in a specific sale.
*/

public class EZTicketEntry implements TicketEntry {
    String barCode;
    String prodDescription;
    int amount;
    double pricePerUnit;
    double discountRate;

    public EZTicketEntry(String barCode, String productDescription, int amount, double pricePerUnit, double discountRate) {
        this.barCode = barCode;
        this.prodDescription = productDescription;
        this.amount = amount;
        this.pricePerUnit = pricePerUnit;
        this.discountRate = discountRate;
    }

    @Override
    public String getBarCode() {
        return this.barCode;
    }

    @Override
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Override
    public String getProductDescription() {
        return this.prodDescription;
    }

    @Override
    public void setProductDescription(String productDescription) {
        this.prodDescription = productDescription;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public double getPricePerUnit() {
        return this.pricePerUnit;
    }

    @Override
    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public double getDiscountRate() {
        return this.discountRate;
    }

    @Override
    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }
}
