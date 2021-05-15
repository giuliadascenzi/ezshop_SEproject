package it.polito.ezshop.unitTest;

import it.polito.ezshop.data.*;

import it.polito.ezshop.data.classes.*;

import org.junit.Test;

import static org.junit.Assert.*;

public class WB_UnitTesting {
    @Test
    public void test_barCodeCheck() {
        EZShop ez = new EZShop();
        String b1 = "42"; // invalid, too short
        String b2 = "6291041500213"; // valid
        String b3 = "6291041500218"; // invalid, check digit is wrong

        assertFalse(ez.checkBarCodeValidity(b1));
        assertTrue(ez.checkBarCodeValidity(b2));
        assertFalse(ez.checkBarCodeValidity(b3));
    }

    @Test
    public void test_creditCardCheck() {
        EZShop ez = new EZShop();
        String c1 = "cane"; // invalid, not a string of digits
        String c2 = "79927398713"; // valid
        String c3 = "79927398718"; // invalid, check digit is wrong

        assertFalse(ez.checkCreditCardValidity(c1));
        assertTrue(ez.checkCreditCardValidity(c2));
        assertFalse(ez.checkCreditCardValidity(c3));
    }

    @Test
    public void test_OrderClassMethods() {
        Order o = new EZOrder(2, "6291041500213", 3, 1.50);
        int orderId = o.getOrderId();
        assertEquals (2 , orderId);

        String productCode = o.getProductCode();
        assertEquals("6291041500213",productCode );

        int quantity = o.getQuantity();
        assertEquals(3,quantity );

        double pricePerUnit = o.getPricePerUnit();
        assertEquals (1.50, pricePerUnit, 0.00001);

        String status = "PAYED";
        o.setStatus(status);
        assertEquals(status, o.getStatus());

        status = "COMPLETED";
        o.setStatus(status);
        assertEquals(status, o.getStatus());

        status = "ISSUED";
        o.setStatus(status);
        assertEquals(status, o.getStatus());

        int balanceId = 5;
        o.setBalanceId(balanceId);
        assertSame(balanceId, o.getBalanceId());

    }

    @Test
    public void test_UserClassMethods() {
        User u = new EZUser(4, "giulia", "ciaociao", "SHOPMANAGER");

        assert(4== u.getId());
        assertEquals("giulia", u.getUsername());
        assertEquals("ciaociao", u.getPassword());
        assertEquals("SHOPMANAGER", u.getRole());

        String role = "ADMINISTRATOR";
        u.setRole(role);
        assertEquals(role, u.getRole());

        role = "CASHIER";
        u.setRole(role);
        assertEquals(role, u.getRole());


    }



}
