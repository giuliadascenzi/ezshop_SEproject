package it.polito.ezshop.unitTest;

import it.polito.ezshop.data.EZShop;
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
}
