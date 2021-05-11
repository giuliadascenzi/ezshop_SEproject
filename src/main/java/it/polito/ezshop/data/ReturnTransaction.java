package it.polito.ezshop.data;

import java.util.Map;

public interface ReturnTransaction {
    // saleTransactionID
    public int getSaleTransactionID();
    public void setSaleTransactionID(int id);
    // returnID
    public int getReturnID();
    public void setReturnID(int id);
    // list of products
    public Map<String, Integer> getMapOfProducts();
    public void setMapOfProducts(Map<String, Integer> map);
    // status
    public String getStatus();
    public void setStatus(String s);
}
