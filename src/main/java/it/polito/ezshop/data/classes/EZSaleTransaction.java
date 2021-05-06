package it.polito.ezshop.data.classes;

import it.polito.ezshop.data.ProductType;
import it.polito.ezshop.data.SaleTransaction;
import it.polito.ezshop.data.TicketEntry;

import java.util.ArrayList;
import java.util.List;

/*ANTONINO*/

public class EZSaleTransaction implements SaleTransaction {
    Integer transactionID;
    List<TicketEntry> entryList;
    double discountRate;
    double price;

    public EZSaleTransaction(Integer transactionID) {
        this.transactionID = transactionID;
        this.discountRate = 1;
        this.price = 0;
        this.entryList = new ArrayList<>();
    }

    public EZSaleTransaction(Integer transactionID, double discountRate, double price) {
        this.transactionID = transactionID;
        this.discountRate = discountRate;
        this.price = price;
        this.entryList = new ArrayList<>();
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
    /*
        addEntry(ProductType product, int amount, double discountRate)
        * Adds an entry to the transaction
     */
    public void addEntry(ProductType product, int amount, double discountRate) {
        // Create a new entry and add it to the list of entries
        this.entryList.add(new EZTicketEntry(
                product.getBarCode(),
                product.getProductDescription(),
                amount,
                product.getPricePerUnit(),
                discountRate
        ));
    }

    /*
        deleteProductFromEntry(String barCode, int amountToRemove)
        * Deletes a certain amount of products from the entry with
            the specified bar code. If the new amount is <= 0, then the
            entry is deleted from the list.
     */
    public boolean deleteProductFromEntry(String barCode, int amountToRemove) {
        for (TicketEntry t : this.entryList) {
            if (t.getBarCode().equals(barCode)) {
                if (t.getAmount() <= amountToRemove) {
                    // If the amount to remove is larger or equal to the current amount,
                    // delete the entry altogether
                    this.entryList.remove(t);
                }
                else {
                    // Otherwise, just decrease the amount
                    t.setAmount(t.getAmount() - amountToRemove);
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
}
