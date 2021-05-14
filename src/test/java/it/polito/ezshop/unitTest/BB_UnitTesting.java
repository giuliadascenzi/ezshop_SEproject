package it.polito.ezshop.unitTest;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.classes.EZFileReader;
import org.junit.Test;

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

        assertTrue(map.containsKey("4716258050958645"));
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

}
