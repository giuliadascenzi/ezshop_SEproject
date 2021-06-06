package it.polito.ezshop.data.classes;

public class EZProductInstance {
    private String RFID;
    private Integer productID;

    public EZProductInstance(String RFID, Integer productID) {
        this.RFID = RFID;
        this.productID = productID;
    }

    public String getRFID() {
        return this.RFID;
    }

    public Integer getProductID() {
        return this.productID;
    }

    public void setRFID(String RFID) {
        this.RFID = RFID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }
}
