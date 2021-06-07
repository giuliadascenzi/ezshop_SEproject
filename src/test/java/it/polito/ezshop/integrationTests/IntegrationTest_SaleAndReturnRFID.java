package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.classes.EZProductInstance;
import it.polito.ezshop.exceptions.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntegrationTest_SaleAndReturnRFID {
    EZShop ez = new EZShop();

    @Before
    public void test_reset() {
        ez.reset();
    }

    @Test
    public void test_SaleAndReturnRFID() {
        // first create a user for testing
        try {
            ez.createUser("testSt", "equijoin", "SHOPMANAGER");
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
            ez.login("testSt", "equijoin");
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
            // -- add products to inventory through an order
            //add credit to have enough balance
            ez.recordBalanceUpdate(100);
            assertEquals(100, (int) ez.computeBalance());

            int oID = this.ez.issueOrder("6291041500213", 10, 5);
            assertTrue(ez.payOrder(oID));

            //Order is in PAYED state
            assertTrue(ez.recordOrderArrivalRFID(oID,"0000111111"));

            assertEquals(ez.getAllOrders().get(0).getStatus(), "COMPLETED");
        }
        catch (Exception e) {
            System.out.println("problem in ordering the product");
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

            assertTrue(ez.addProductToSaleRFID(tID, "0000111111"));
            assertTrue(ez.deleteProductFromSaleRFID(tID, "0000111111"));
            assertFalse(ez.deleteProductFromSaleRFID(tID, "0000111112"));
            assertTrue(ez.addProductToSaleRFID(tID, "0000111111"));
            assertTrue(ez.addProductToSaleRFID(tID, "0000111112"));

            assertThrows(InvalidRFIDException.class, () -> {
                ez.addProductToSaleRFID(finalTID, "patata");
            });

            assertTrue(ez.applyDiscountRateToProduct(tID, validBC, 0.5));
            assertTrue(ez.applyDiscountRateToSale(tID, 0.5));

            assertNull(ez.getSaleTransaction(tID));

            assertTrue(ez.endSaleTransaction(tID));

            assertFalse(ez.deleteProductFromSaleRFID(tID, "0000111112"));

            double price = ez.computeSaleTransactionPrice(ez.getSaleTransaction(tID));

            assertTrue(price > 0);
            // pay for the sale
            assertTrue(ez.receiveCashPayment(tID, 800) > 0);

            int rID = ez.startReturnTransaction(tID);

            assertTrue(rID != -1);

            assertTrue(ez.returnProductRFID(rID, "0000111112"));
            assertFalse(ez.returnProductRFID(rID, "0000111118"));
            assertFalse(ez.returnProductRFID(rID, "0000111142"));
            assertThrows(InvalidRFIDException.class, () -> {
                ez.returnProductRFID(rID, "patata");
            });

            assertTrue(ez.endReturnTransaction(rID, true));
        }
        catch (Exception e) {
            System.out.println("Encountered exception while testing:");
            e.printStackTrace();
            return;
        }
    }
}
