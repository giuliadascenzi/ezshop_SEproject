package it.polito.ezshop.unitTest;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.data.TicketEntry;
import it.polito.ezshop.data.classes.*;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class WB_UnitTesting {
    // --- Class tests --- //
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

        // test product entry list
        EZProductType p1 = new EZProductType("potato", "42", 0.5, "tasty", 1);

        st1.addEntry(p1, 4, 0.1);

        EZTicketEntry e1 = (EZTicketEntry) st1.getEntries().get(0);

        assertEquals("42", e1.getBarCode());

        assertFalse(st1.deleteEntry("8"));
        assertTrue(st1.updateProductInEntry("42", 84));
        assertFalse(st1.updateProductInEntry("923734", 16));
        assertTrue(st1.updateProductInEntry("42", -89));
        assertFalse(st1.deleteEntry("42"));

        st1.addEntry(p1, 4, 0.1);

        assertTrue(st1.deleteEntry("42"));

        List<TicketEntry> eList = new ArrayList<>();
        eList.add(e1);

        st1.setEntries(eList);

        // test return list
        EZReturnTransaction r1 = new EZReturnTransaction(st1.getTicketNumber(), 1);
        EZReturnTransaction r2 = new EZReturnTransaction(st1.getTicketNumber(), 2);

        st1.addReturn(r1);

        assertFalse(st1.updateReturn(r2));

        r1.setReturnID(3);

        assertTrue(st1.updateReturn(r1));

        assertFalse(st1.deleteReturn(r2.getReturnID()));
        assertTrue(st1.deleteReturn(r1.getReturnID()));
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
