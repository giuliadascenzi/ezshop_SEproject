package it.polito.ezshop.data.classes;

import it.polito.ezshop.data.ProductType;

/*Francesco*/
public class EZProductType implements ProductType {
    private String barCode;
    private String description;
    private Double sellPrice;
    private Integer quantity;
    private Integer productId;
    //private Double discountRate;
    private String notes;
    private String location;

    public EZProductType(String description, String productCode, double pricePerUnit, String note, int newProductId) {
        this.barCode=productCode;
        this.description=description;
        this.sellPrice=pricePerUnit;
        this.notes=note;
        this.productId=newProductId;
    }
    public EZProductType(String description, String productCode, double pricePerUnit, String note, int newProductId,String location) {
        this.barCode=productCode;
        this.description=description;
        this.sellPrice=pricePerUnit;
        this.notes=note;
        this.productId=newProductId;
        this.location=location;
    }

    @Override
    public Integer getQuantity() {
        return this.quantity;
    }

    @Override
    public void setQuantity(Integer quantity) {
        this.quantity=quantity;
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(String location) {
        this.location=location;
    }

    @Override
    public String getNote() {

        return this.notes;
    }

    @Override
    public void setNote(String note) {
        this.notes=note;
    }

    @Override
    public String getProductDescription() {

        return this.description;
    }

    @Override
    public void setProductDescription(String productDescription) {
        this.description=productDescription;
    }

    @Override
    public String getBarCode() {
        return this.barCode;
    }

    @Override
    public void setBarCode(String barCode) {
        this.barCode=barCode;
    }

    @Override
    public Double getPricePerUnit() {
        return this.sellPrice;
    }

    @Override
    public void setPricePerUnit(Double pricePerUnit) {
        this.sellPrice=pricePerUnit;
    }

    @Override
    public Integer getId() {
        return this.productId;
    }

    @Override
    public void setId(Integer id) {
        this.productId=id;
    }
}
