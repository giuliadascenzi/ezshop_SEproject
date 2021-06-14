package it.polito.ezshop.data.classes;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.data.ReturnTransaction;
import it.polito.ezshop.data.SaleTransaction;
import it.polito.ezshop.data.TicketEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/*ANTONINO*/

public class EZSaleTransaction implements SaleTransaction {
    Integer transactionID;
    List<TicketEntry> entryList;
    double discountRate;
    double price;
    List<ReturnTransaction> returnList;
    /*
        Values for "status":
        - OPEN
        - CLOSED
        - PAID
     */
    String status;
    List<String> RFIDList;

    public EZSaleTransaction(Integer transactionID) {
        this.transactionID = transactionID;
        this.discountRate = 0;
        this.price = 0;
        this.entryList = new ArrayList<>();
        this.returnList = new ArrayList<>();
        this.status = "OPEN";
        this.RFIDList = new ArrayList<>();
    }

    public EZSaleTransaction(Integer transactionID, double discountRate, double price) {
        this.transactionID = transactionID;
        this.discountRate = discountRate;
        this.price = price;
        this.entryList = new ArrayList<>();
        this.returnList = new ArrayList<>();
        this.status = "OPEN";
        this.RFIDList = new ArrayList<>();
    }

    public EZSaleTransaction(Integer transactionID, double discountRate, double price, String status) {
        this.transactionID = transactionID;
        this.discountRate = discountRate;
        this.price = price;
        this.entryList = new ArrayList<>();
        this.returnList = new ArrayList<>();
        this.status = status;
        this.RFIDList = new ArrayList<>();
    }

    @Override
    public Integer getTicketNumber() {
        return this.transactionID;
    }

    @Override
    public void setTicketNumber(Integer ticketNumber) {
        this.transactionID = ticketNumber;
    }

    @Override
    public List<TicketEntry> getEntries() {
        return this.entryList;
    }

    @Override
    public void setEntries(List<TicketEntry> entries) {
        this.entryList = entries;
    }

    @Override
    public double getDiscountRate() {
        return this.discountRate;
    }

    @Override
    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    // --- //
    // Added methods:
    // Get/Set methods for the transaction's status:
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String s) {
        if (!s.equalsIgnoreCase("OPEN")
                && !s.equalsIgnoreCase("CLOSED")
                && !s.equalsIgnoreCase("PAID")) {
            return;
        }

        this.status = s.toUpperCase();
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

    /*
        addEntry(ProductType product, int amount, double discountRate)
        * Adds an entry to the transaction
     */
    public void addEntry(ProductType product, int amount, double discountRate) {
        // check if product is already in sale
        for (TicketEntry t : this.entryList) {
            // if there's already an entry with the product, then update the entry
            if (t.getBarCode().equals(product.getBarCode())) {
                t.setAmount(t.getAmount() + amount);
                t.setDiscountRate(discountRate);
                return;
            }
        }

        // otherwise, create a new entry and add it to the list of entries
        this.entryList.add(new EZTicketEntry(
                product.getBarCode(),
                product.getProductDescription(),
                amount,
                product.getPricePerUnit(),
                discountRate
        ));
    }

    /**
        updateProductInEntry(String barCode, int amount)
        * Updates the entry for the product with the specified bar code by adding
            the specified amount (which can be either positive or negative) to
            the entry's quantity.
            If the new amount is <= 0, then the entry is deleted from the list.
     **/
    public boolean updateProductInEntry(String barCode, int amount) {
        for (TicketEntry t : this.entryList) {
            if (t.getBarCode().equals(barCode)) {
                if (amount < 0 && t.getAmount() <= Math.abs(amount)) {
                    // If the amount to remove is larger or equal to the current amount,
                    // delete the entry altogether
                    this.entryList.remove(t);
                }
                else {
                    // Otherwise, just update the amount
                    t.setAmount(t.getAmount() + amount);
                }
                return true;
            }
        }

        // If it got to this point, it means the object hasn't been found in the list.
        return false;
    }

    /*
        deleteEntry(String barCode)
        * Deletes an entry from the list
     */
    public boolean deleteEntry(String barCode) {
        for (TicketEntry t : this.entryList) {
            if (t.getBarCode().equals(barCode)) {
                this.entryList.remove(t);
                return true;
            }
        }

        // If it got to this point, it means the object hasn't been found in the list.
        return false;
    }

    /*
        addReturn(ReturnTransaction return)
        * Adds a return transaction to the list
     */
    public void addReturn(ReturnTransaction r) {
        this.returnList.add(r);
    }

    /*
        updateReturn(int returnID, int amount)
        * Adds a return transaction to the list
     */
    public boolean updateReturn(ReturnTransaction ret) {
        int key = ret.getReturnID();
        for (ReturnTransaction r : this.returnList) {
            if (r.getReturnID() == key) {
                this.returnList.remove(r);
                this.returnList.add(ret);
                return true;
            }
        }

        return false;
    }

    /*
        deleteReturn(int returnID)
        * Delete a return from the list given the id
    */
    public boolean deleteReturn(int returnID) {
        for (int i = 0; i < this.returnList.size(); i++) {
            if (this.returnList.get(i).getReturnID() == returnID) {
                this.returnList.remove(i);
                return true;
            }
        }

        return false;
    }
}
