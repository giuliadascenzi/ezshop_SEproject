package it.polito.ezshop.unitTest;

import it.polito.ezshop.data.Customer;
import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.classes.EZCustomer;
import it.polito.ezshop.data.classes.EZCustomerCard;
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
    public void test_CustomerClassMethod(){
        EZCustomerCard cc = new EZCustomerCard("0000000011", 100);
        EZCustomer c = new EZCustomer("Federico", 19, cc);

        String name = c.getCustomerName();
        assertEquals("Federico",name);

        int id = c.getId();
        assertEquals(19,id);

        String ccard = c.getCustomerCard();
        assertEquals("0000000011",ccard);

        int points = c.getPoints();
        assertEquals(100,points);

        Integer tid = 3;
        c.setId(tid);
        assertEquals(tid,c.getId());

        c.setCustomerName("Frank");
        assertEquals("Frank",c.getCustomerName());

        c.setCustomerCard("0000000012");
        assertEquals("0000000012",c.getCustomerCard());

        Integer po = 2;
        c.setPoints(po);
        assertEquals(po,c.getPoints());

        po =0;
        c.removeCustomerCard();
        assertEquals("",c.getCustomerCard());
        assertEquals(po,c.getPoints());


    }
}
