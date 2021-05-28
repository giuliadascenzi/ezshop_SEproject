package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.UnauthorizedException;
import org.junit.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class IntegrationTest_Accounting {
    EZShop ez = new EZShop();

    @Before
    public void test_reset() {
        ez.reset();
    }

    @Test
    public void test_AccountingMethods() {
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
            ez.recordBalanceUpdate(10);
        });
        assertThrows(UnauthorizedException.class, () -> {
            ez.getCreditsAndDebits(LocalDate.of(2021, 01, 01), LocalDate.of(2021, 12, 31));
        });
        assertThrows(UnauthorizedException.class, () -> {
            ez.computeBalance();
        });

        try {
            ez.login("testSt", "equjoin");
        }
        catch (Exception e) {
            System.out.println("There was a problem with logging in:");
            e.printStackTrace();
            return;
        }

        try {
            assertTrue(ez.recordBalanceUpdate(84));
            assertTrue(ez.recordBalanceUpdate(42));

            assertEquals(126.0, ez.computeBalance(), 0.0);

            List<BalanceOperation> list = ez.getCreditsAndDebits(
                    LocalDate.of(2021, 1, 1),
                    LocalDate.of(2021, 12, 31)
                );

            assertFalse(list.isEmpty());

            list = ez.getCreditsAndDebits(
                    LocalDate.of(2021, 1, 1),
                    null
            );

            assertFalse(list.isEmpty());

            list = ez.getCreditsAndDebits(
                    null,
                    LocalDate.of(2021, 12, 31)
            );

            assertFalse(list.isEmpty());

            list = ez.getCreditsAndDebits(
                    null,
                    null
            );

            assertFalse(list.isEmpty());

            List<BalanceOperation> emptyList = ez.getCreditsAndDebits(
                    LocalDate.of(2021, 1, 1),
                    LocalDate.of(2021, 2, 28)
            );

            assertTrue(emptyList.isEmpty());

            assertFalse(ez.recordBalanceUpdate(-9999));
        }
        catch (Exception e) {
            System.out.println("Exception encountered when testing:");
            e.printStackTrace();
        }
    }
}
