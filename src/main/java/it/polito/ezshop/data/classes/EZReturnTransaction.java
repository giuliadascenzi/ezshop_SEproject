package it.polito.ezshop.data.classes;

import it.polito.ezshop.data.ReturnTransaction;

import java.util.HashMap;
import java.util.Map;

public class EZReturnTransaction implements ReturnTransaction{
    private int saleTransactionID;
    private int returnID;
    // entries: (productCode, amount)
    private Map<String, Integer> productMap;
    /*
        Values:
        * OPEN
        * CLOSED
     */
    private String status;

    public EZReturnTransaction(int stID, int retID) {
        this.saleTransactionID = stID;
        this.returnID = retID;
        this.productMap = new HashMap<>();
    }

    @Override
    public int getSaleTransactionID() {
        return this.saleTransactionID;
    }

    @Override
    public void setSaleTransactionID(int id) {
        this.saleTransactionID = id;
    }

    @Override
    public int getReturnID() {
        return this.returnID;
    }

    @Override
    public void setReturnID(int id) {
        this.returnID = id;
    }

    @Override
    public Map<String, Integer> getMapOfProducts() {
        return this.productMap;
    }

    @Override
    public void setMapOfProducts(Map<String, Integer> map) {
        this.productMap = map;
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(String s) {
        this.status = s;
    }
}
