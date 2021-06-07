package it.polito.ezshop.data.classes;

import it.polito.ezshop.data.ReturnTransaction;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        * PAID
     */
    private String status;
    private double money;
    List<String> RFIDList;

    public EZReturnTransaction(int stID, int retID) {
        this.saleTransactionID = stID;
        this.returnID = retID;
        this.productMap = new HashMap<>();
        this.status = "OPEN";
        this.RFIDList = new ArrayList<>();
    }

    public EZReturnTransaction(int stID, int retID, String status) {
        this.saleTransactionID = stID;
        this.returnID = retID;
        this.productMap = new HashMap<>();
        this.status = status;
        this.RFIDList = new ArrayList<>();
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
        if (!s.equalsIgnoreCase("OPEN")
            && !s.equalsIgnoreCase("CLOSED")
            && !s.equalsIgnoreCase("PAID")) {
            return;
        }

        this.status = s.toUpperCase();
    }

    @Override
    public double getMoneyReturned() {
        return this.money;
    }

    @Override
    public void setMoneyReturned(double m) {
        this.money = m;
    }

    /**
     addRFID(String RFID)
     * Adds the RFID of a product in the object's list

     @return
     true:   if the RFID was added successfully
     false:  if the RFID is already in the list
     */
    public boolean addRFID(String RFID) {
        if (this.RFIDList.contains(RFID)) {
            return false;
        }

        this.RFIDList.add(RFID);

        return true;
    }

    /**
     * deleteRFID(String RFID)
     *  Deletes an RFID from the list.
     *
     * @param RFID: the RFID to delete
     * @return
     *      true:   if the RFID was contained in the list and successfully removed
     *      false:  if the RFID wasn't contained in the list
     */
    public boolean deleteRFID(String RFID) {
        if (!this.RFIDList.contains((RFID))) {
            return false;
        }

        this.RFIDList.remove(RFID);

        return true;
    }

    /**
     * Returns the object's RFID list
     *
     * @return
     *      the object's RFID list
     */
    public List<String> getRFIDList() {
        return this.RFIDList;
    }

}
