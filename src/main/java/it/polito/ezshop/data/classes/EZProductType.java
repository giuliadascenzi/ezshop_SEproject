package it.polito.ezshop.data.classes;

import it.polito.ezshop.data.ProductType;

public class EZProductType implements ProductType {
    String barCode;
    String description;
    Double sellPrice;
    Integer quantity;
    Integer productid;
    Double discountRate;
    String notes;
    String location;
    @Override
    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(Integer quantity) {
        this.quantity=quantity;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location=location;
    }

    @Override
    public String getNote() {

        return notes;
    }

    @Override
    public void setNote(String note) {
        this.notes=note;
    }

    @Override
    public String getProductDescription() {

        return description;
    }

    @Override
    public void setProductDescription(String productDescription) {
        this.description=productDescription;
    }

    @Override
    public String getBarCode() {
        return barCode;
    }

    @Override
    public void setBarCode(String barCode) {
        this.barCode=barCode;
    }

    @Override
    public Double getPricePerUnit() {
        return sellPrice;
    }

    @Override
    public void setPricePerUnit(Double pricePerUnit) {
        this.sellPrice=pricePerUnit;
    }

    @Override
    public Integer getId() {
        return productid;
    }

    @Override
    public void setId(Integer id) {
        this.productid=id;
    }
}
