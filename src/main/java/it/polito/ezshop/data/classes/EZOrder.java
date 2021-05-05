package it.polito.ezshop.data.classes;

import it.polito.ezshop.data.Order;
/*Giulia*/
public class EZOrder  implements Order  {

    /*DOVREBBE ESTENDERE EZBALANCE OPERATION! ma conflitto con getBalanceId!*/


    //CAMPO BALANCE ID!!
    private String productCode;
    private Integer balanceId;
    private double pricePerUnit;
    private int quantity;
    private Integer id;
    private String status;




/* ERRORE -> getBalanceId di BalanceOperation ritorna un integer! */


    public Integer getBalanceId() {
        return null;
    }


    @Override
    public void setBalanceId(Integer balanceId) {

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
        return this.status;
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
