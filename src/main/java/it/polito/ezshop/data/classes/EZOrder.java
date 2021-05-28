package it.polito.ezshop.data.classes;

import it.polito.ezshop.data.Order;

import java.util.Locale;

/*Giulia*/

public class EZOrder  implements Order  {

    private String productCode;
    private Integer balanceId=-1;
    private double pricePerUnit;
    private int quantity;
    private Integer id;
    private String status="";
    //Possible status:
    //issued is sent to the supplier (and not paid),
    // completed  is when the physical product is received (and recorded in inventory) (Same as arrived)
    // paid when the supplier is paid

    public EZOrder(int newID, String productCode, int quantity, double pricePerUnit) {
        this.productCode=productCode;
        this.quantity=quantity;
        this.pricePerUnit=pricePerUnit;
        this.id=newID;
    }

    public Integer getBalanceId() {
        return this.balanceId;
    }


    @Override
    public void setBalanceId(Integer balanceId) {
        this.balanceId=balanceId;

    }

    @Override
    public String getProductCode() {
        return this.productCode;
    }

    @Override
    public void setProductCode(String productCode) {
        this.productCode=productCode;

    }

    @Override
    public double getPricePerUnit() {
        return this.pricePerUnit;
    }

    @Override
    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit=pricePerUnit;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity=quantity;

    }

    @Override
    public String getStatus() {
        return this.status.toUpperCase(Locale.ROOT);
    }

    @Override
    public void setStatus(String status) {
       this.status=status;
    }

    @Override
    public Integer getOrderId() {
        return this.id;
    }

    @Override
    public void setOrderId(Integer orderId) {
        this.id=orderId;

    }
}
