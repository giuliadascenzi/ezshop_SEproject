package it.polito.ezshop.data.classes;

public class EZProductInstance {
    String RFID;
    String barcode;
    Integer saleId;

    public EZProductInstance(String RFID, String barcode, Integer saleId) {
        this.RFID = RFID;
        this.barcode = barcode;
        this.saleId = saleId;
    }

    public String getRFID() {
        return this.RFID;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public Integer getSaleId() {
        return this.saleId;
    }

    public void setRFID(String p) {
        this.RFID = p;
    }

    public void setBarcode(String p) {
        this.barcode = p;
    }

    public void setSaleId(Integer p) {
        this.saleId = p;
    }
}
