package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.classes.EZSaleTransaction;
import it.polito.ezshop.exceptions.InvalidProductCodeException;
import it.polito.ezshop.exceptions.InvalidQuantityException;
import it.polito.ezshop.exceptions.InvalidTransactionIdException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntegrationTest_ReturnTransaction {
    EZShop ez = new EZShop();

    @Before
    public void test_reset() {
        ez.reset();
    }

    @Test
    public void test_ReturnTransactions() {
        // first create a user for testing
        try {
            ez.createUser("testSt", "equjoin", "SHOPMANAGER");
        }
        catch (Exception e) {
            System.out.println("There was a problem in creating the user:");
            e.printStackTrace();
            return;
        }

        assertThrows(UnauthorizedException.class, () -> {
            ez.startSaleTransaction();
        });

        try {
            ez.login("testSt", "equjoin");
        }
        catch (Exception e) {
            System.out.println("There was a problem with logging in:");
            e.printStackTrace();
            return;
        }

        int tID = -1;
        try {
            tID = ez.startSaleTransaction();
        }
        catch (UnauthorizedException e) {
            System.out.println("User not authorized/logged in.");
            e.printStackTrace();
            return;
        }

        assertTrue(tID >= 0);

        String validBC = "6291041500213";

        try {
            int a = ez.createProductType("potato", validBC, 1, "tasty");
            ez.updatePosition(a,"2-C-4");
            ez.updateQuantity(a, 999);
        }
        catch (Exception e) {
            System.out.println("problem in adding the product");
            e.printStackTrace();
            return;
        }

        try {
            int finalTID = tID;
            assertThrows(InvalidProductCodeException.class, () -> {
                ez.addProductToSale(finalTID, "42", 1);
            });
            assertThrows(InvalidQuantityException.class, () -> {
                ez.addProductToSale(finalTID, validBC, -1);
            });
            assertThrows(InvalidTransactionIdException.class, () -> {
                ez.addProductToSale(-1, validBC, -1);
            });

            assertTrue(ez.addProductToSale(tID, validBC, 4));

            assertTrue(ez.endSaleTransaction(tID));
            // the ST needs to be paid in order to successfully ask for a return
            assertEquals(496, ez.receiveCashPayment(tID, 500), 0.0);

            assertTrue(((EZSaleTransaction) ez.getSaleTransaction(tID)).getStatus().equalsIgnoreCase("PAID"));

            assertThrows(InvalidTransactionIdException.class, () -> {
                ez.startReturnTransaction(-1);
            });

            assertEquals(-1, (int) ez.startReturnTransaction(420));
            assertTrue(ez.startReturnTransaction(tID) != -1);

            int rID = ez.startReturnTransaction(tID);

            int finalRID = rID;
            assertThrows(InvalidProductCodeException.class, () -> {
                ez.returnProduct(finalRID, "8", 2);
            });
            // too many potatoes
            assertFalse(ez.returnProduct(rID, validBC, 800));
            // return doesn't exist
            assertFalse(ez.returnProduct(42, validBC, 2));

            assertTrue(ez.returnProduct(rID, validBC, 2));

            assertTrue(ez.deleteReturnTransaction(rID));

            rID = ez.startReturnTransaction(tID);

            assertTrue(ez.returnProduct(rID, validBC, 2));

            assertTrue(ez.endReturnTransaction(rID, true));

            assertEquals(997, (int) ez.getProductTypeByBarCode(validBC).getQuantity());

            assertFalse(ez.deleteReturnTransaction(rID));
        }
        catch (Exception e) {
            System.out.println("Encountered exception while testing:");
            e.printStackTrace();
            return;
        }
    }
}
