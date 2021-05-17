package it.polito.ezshop.unitTest;

import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.SaleTransaction;
import it.polito.ezshop.data.TicketEntry;
import it.polito.ezshop.data.classes.*;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class BB_UnitTesting {
    EZShop ez = new EZShop();

    /*
        Methods to test for unit testing:
        - EZShop:
            * checkBarCodeValidity
            * checkCreditCardValidity
     */

    // --- Test bar code validity --- //
    @Test
    public void test_InvalidBarCode() {
        assertFalse(ez.checkBarCodeValidity("6291041500218"));
        assertFalse(ez.checkBarCodeValidity(null));
        assertFalse(ez.checkBarCodeValidity("42"));
        assertFalse(ez.checkBarCodeValidity("62910415002187326548"));
    }

    @Test
    public void test_ValidBarCode() {
        assertTrue(ez.checkBarCodeValidity("6291041500213"));
    }
    // --- Test credit card validity --- //
    @Test
    public void test_InvalidCreditCard() {
        assertFalse(ez.checkCreditCardValidity("79927398718"));
        assertFalse(ez.checkCreditCardValidity(null));
        assertFalse(ez.checkCreditCardValidity("cane"));
    }

    @Test
    public void test_ValidCreditCard() {
        assertTrue(ez.checkCreditCardValidity("79927398713"));
    }

    // --- Test file reader --- //
    @Test
    public void test_readFromFile() {
        EZFileReader ez = new EZFileReader();

        Map<String, Double> map = ez.readCreditCards();

        assertTrue(map.containsKey("273564276542"));
    }

    @Test
    public void test_writeToFile() {
        EZFileReader ez = new EZFileReader();

        Map<String, Double> map = new HashMap<>();

        map.put("938728567833", 50.00);
        map.put("938728568433", 40.00);
        map.put("273564276542", 16.00);

        ez.setCreditCards(map);

        Map<String, Double> map_res = ez.readCreditCards();

        assertTrue(map_res.containsKey("273564276542"));
    }

    // --- Test for BalanceOperation --- //
    @Test
    public void test_BalanceOperation() {
        EZBalanceOperation bo1 = new EZBalanceOperation(1, LocalDate.now(), 1.00);
        EZBalanceOperation bo2 = new EZBalanceOperation(2, LocalDate.of(2021, 6, 22), -4.00);

        assertEquals(1, bo1.getBalanceId());
        assertEquals(0, bo2.getDate().compareTo(LocalDate.of(2021, 6, 22)));
        assertEquals(1.00, bo1.getMoney(), 0.0);
        assertEquals("DEBIT", bo2.getType());

        bo2.setMoney(-4.00);
        bo2.setMoney(4.00);
        bo2.setBalanceId(4);
        bo2.setDate(LocalDate.of(2021, 6, 13));
        bo2.setType("DEBIT");

        assertEquals(4.00, bo2.getMoney(), 0.0);
        assertEquals(4, bo2.getBalanceId());
        assertEquals(0, bo2.getDate().compareTo(LocalDate.of(2021, 6, 13)));
    }

    @Test
    public void test_SaleTransaction() {
        EZSaleTransaction st1 = new EZSaleTransaction(1);
        EZSaleTransaction st2 = new EZSaleTransaction(2, 0.0, 1.00);
        EZSaleTransaction st3 = new EZSaleTransaction(3, 0.5, 9387.00, "PAID");

        st1.setPrice(40.00);
        st1.setStatus("cLOsEd");
        st1.setStatus("potato");
        st1.setDiscountRate(0.9);
        st1.setTicketNumber(40);

        assertEquals(40, (int) st1.getTicketNumber());
        assertEquals(0.0, st2.getDiscountRate(), 0.0);
        assertEquals(9387.0, st3.getPrice(), 0.0);
        assertEquals("PAID", st3.getStatus());

        assertEquals(40.00, st1.getPrice(), 0.0);
        assertEquals(0.9, st1.getDiscountRate(), 0.0);
        assertEquals("CLOSED", st1.getStatus());
    }

    @Test
    public void test_TicketEntry() {
        EZTicketEntry te1 = new EZTicketEntry("2837468", "potato", 876, 0.5, 0.1);

        assertEquals("2837468", te1.getBarCode());
        assertEquals("potato", te1.getProductDescription());
        assertEquals(876, te1.getAmount());
        assertEquals(0.5, te1.getPricePerUnit(), 0.0);
        assertEquals(0.1, te1.getDiscountRate(), 0.0);

        te1.setBarCode("42");
        te1.setProductDescription("banana");
        te1.setAmount(4);
        te1.setPricePerUnit(80.00);
        te1.setDiscountRate(0.0);

        assertEquals("42", te1.getBarCode());
        assertEquals("banana", te1.getProductDescription());
        assertEquals(4, te1.getAmount());
        assertEquals(80.00, te1.getPricePerUnit(), 0.0);
        assertEquals(0.0, te1.getDiscountRate(), 0.0);
    }

    @Test
    public void test_ReturnTransaction() {
        EZReturnTransaction rt1 = new EZReturnTransaction(1, 1);
        EZReturnTransaction rt2 = new EZReturnTransaction(1, 2, "cLOsEd");

        HashMap<String, Integer> map1 = new HashMap<>();
        map1.put("42", 3);
        rt1.setMapOfProducts(map1);
        rt1.setMoneyReturned(4.00);

        assertEquals(1, rt1.getReturnID());
        assertEquals(1, rt1.getSaleTransactionID());
        assertEquals("OPEN", rt1.getStatus());
        assertEquals(3, (int) rt1.getMapOfProducts().get("42"));
        assertEquals(4.00, rt1.getMoneyReturned(), 0.0);

        rt2.setReturnID(4);
        rt2.setSaleTransactionID(2);
        rt2.setStatus("banana");
        rt2.setStatus("PAID");

        assertEquals(4, rt2.getReturnID());
        assertEquals(2, rt2.getSaleTransactionID());
        assertEquals("PAID", rt2.getStatus());
    }
}
