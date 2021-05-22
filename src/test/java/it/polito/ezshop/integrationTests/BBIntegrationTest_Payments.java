package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.classes.EZSaleTransaction;
import it.polito.ezshop.exceptions.InvalidPaymentException;
import it.polito.ezshop.exceptions.InvalidTransactionIdException;
import it.polito.ezshop.exceptions.UnauthorizedException;
import org.junit.*;

import static org.junit.Assert.*;

public class BBIntegrationTest_Payments {
    EZShop ez = new EZShop();

    @Before
    public void test_reset() {
        ez.reset();
    }

    @Test
    public void test_paymentTest() {
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
            ez.receiveCashPayment(1, 42);
        });
        assertThrows(UnauthorizedException.class, () -> {
            ez.receiveCreditCardPayment(1, "4485370086510891");
        });
        assertThrows(UnauthorizedException.class, () -> {
            ez.returnCashPayment(1);
        });
        assertThrows(UnauthorizedException.class, () -> {
            ez.returnCreditCardPayment(1, "4485370086510891");
        });

        try {
            ez.login("testSt", "equjoin");
        }
        catch (Exception e) {
            System.out.println("There was a problem with logging in:");
            e.printStackTrace();
            return;
        }

        assertThrows(InvalidPaymentException.class, () -> {
            ez.receiveCashPayment(1, -42);
        });
        assertThrows(InvalidTransactionIdException.class, () -> {
            ez.receiveCashPayment(-1, 42);
        });

        String validBC = "6291041500213";

        try {
            int a = ez.createProductType("potato", validBC, 1, "tasty");
            ez.updateQuantity(a, 999);
        }
        catch (Exception e) {
            System.out.println("problem in adding the product");
            e.printStackTrace();
            return;
        }

        try {
            int tID = ez.startSaleTransaction();
            ez.addProductToSale(tID, validBC, 10);
            assertTrue(ez.endSaleTransaction(tID));

            int tID2 = ez.startSaleTransaction();
            ez.addProductToSale(tID2, validBC, 5);
            assertTrue(ez.endSaleTransaction(tID2));

            assertEquals(-1, ez.receiveCashPayment(42, 1), 0.0);
            assertEquals(1, ez.receiveCashPayment(tID, 11), 0.0);

            assertFalse(ez.receiveCreditCardPayment(tID2, "8"));
            assertFalse(ez.receiveCreditCardPayment(-1, "4485370086510891"));
            assertFalse(ez.receiveCreditCardPayment(tID2, "4716258050958645"));

            assertTrue(ez.receiveCreditCardPayment(tID2, "4485370086510891"));

            EZSaleTransaction st = (EZSaleTransaction) ez.getSaleTransaction(tID2);

            assertTrue(st.getStatus().equalsIgnoreCase("PAID"));

            // TODO: test return payments
        }
        catch (Exception e) {
            System.out.println("Exception encountered while testing:");
            e.printStackTrace();
        }
    }
}
