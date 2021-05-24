package it.polito.ezshop.integrationTests;
import it.polito.ezshop.data.*;
import it.polito.ezshop.data.classes.*;
import it.polito.ezshop.exceptions.*;

import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
public class EZShopOrderMethodsTest {

    EZShop sp = new EZShop();

    @Before
    public void resetShop()
    {
        sp.reset();

    }



    /****************** TESTS ISSUE ORDER********************************/

    @Test
    public void issueOrderInvalidProductCode()
    {
        //Invalid: null
        assertThrows(InvalidProductCodeException.class, ()->{this.sp.issueOrder(null, 3, 2.5);});
        //Invalid: empty
        assertThrows(InvalidProductCodeException.class, ()->{this.sp.issueOrder("", 3, 2.5);});
        assertThrows(InvalidProductCodeException.class, ()->{this.sp.issueOrder("      ", 3, 2.5);});
        //Invalid: not valid barcode
        assertThrows(InvalidProductCodeException.class, ()->{this.sp.issueOrder("ciao", 3, 2.5);});
        assertFalse(sp.checkBarCodeValidity("324536217273"));
        assertThrows(InvalidProductCodeException.class, ()->{this.sp.issueOrder("324536217273", 3, 2.5);});
    }

    @Test
    public void issueOrderInvalidQuantity()
    {    assertTrue(sp.checkBarCodeValidity("6291041500213"));
        //Invalid: 0
        assertThrows(InvalidQuantityException.class, ()->{this.sp.issueOrder("6291041500213", 0, 2.5);});
        //Invalid: <0
        assertThrows(InvalidQuantityException.class, ()->{this.sp.issueOrder("6291041500213", -13, 2.5);});

    }
    @Test
    public void issueOrderInvalidPricePerUnit()
    {    assertTrue(sp.checkBarCodeValidity("6291041500213"));
        //Invalid: 0
        assertThrows(InvalidPricePerUnitException.class, ()->{this.sp.issueOrder("6291041500213", 1, 0);});
        //Invalid: <0
        assertThrows(InvalidPricePerUnitException.class, ()->{this.sp.issueOrder("6291041500213", 1, -2);});
        assertThrows(InvalidPricePerUnitException.class, ()->{this.sp.issueOrder("6291041500213", 1, -0.00000002);});
    }

    @Test
    public void IssueOrderNoOneLoggedWrongRole() {
        /*Invalid 1= No one still logged, no user created*/
        assertThrows(UnauthorizedException.class, ()->{this.sp.issueOrder("6291041500213", 1, 50);});

        /*Invalid 2 = user created but still not logged*/
        try {
            int id = sp.createUser("giulia", "ciao", "Cashier");
            assertEquals(1, id);

            // Still no logged
            assertThrows(UnauthorizedException.class, ()->{this.sp.issueOrder("6291041500213", 1, 50);});
            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("Cashier"));
            assertFalse(this.sp.checkUserRole("ADMINISTRATOR"));
            assertFalse(this.sp.checkUserRole("ShopManager"));
            /*Invalid 2 = user created, logged but wrong role*/

            //Logged in a user with role ShopManager, not administrator
            assertThrows(UnauthorizedException.class, ()->{this.sp.issueOrder("6291041500213", 1, 50);});



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException e) {
            fail(e.getMessage());
        }


    }
    @Test
    public void IssueOrderProductNotExists() {

        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);

            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ShopManager"));

            //Product Does not exist
            assertEquals(-1, (int) this.sp.issueOrder("6291041500213", 1, 50));



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException|InvalidQuantityException|UnauthorizedException|InvalidPricePerUnitException|InvalidProductCodeException e) {
            fail(e.getMessage());
        }



    }

    @Test
    public void IssueOrderValid() {

        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);

            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ShopManager"));

            //add product
            assertTrue(this.sp.createProductType("milk", "6291041500213", 2.01, "ciao")>=0);
            //Product Does not exist
            assertEquals(1, (int) this.sp.issueOrder("6291041500213", 1, 50));

            //TRY WITH ADMINISTRATOR LOGGED IN
            assertTrue(sp.logout());
            int id2 = sp.createUser("giulia2", "ciao2", "Administrator");
            assertEquals(2, id2);

            //loggging in
            assertEquals(2 ,(int)this.sp.login("giulia2", "ciao2").getId());
            assertTrue(this.sp.checkUserRole("Administrator"));

            //Product Does not exist
            assertEquals(2, (int) this.sp.issueOrder("6291041500213", 1, 50));






        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | InvalidQuantityException | UnauthorizedException | InvalidPricePerUnitException | InvalidProductCodeException | InvalidProductDescriptionException e) {
            fail(e.getMessage());
        }



    }




    /****************** TESTS PAY ORDER FOR********************************/

    @Test
    public void PayOrderForInvalidProductCode()
    {
        //Invalid: null
        assertThrows(InvalidProductCodeException.class, ()->{this.sp.payOrderFor(null, 3, 2.5);});
        //Invalid: empty
        assertThrows(InvalidProductCodeException.class, ()->{this.sp.payOrderFor("", 3, 2.5);});
        assertThrows(InvalidProductCodeException.class, ()->{this.sp.payOrderFor("      ", 3, 2.5);});
        //Invalid: not valid barcode
        assertThrows(InvalidProductCodeException.class, ()->{this.sp.payOrderFor("ciao", 3, 2.5);});
        assertFalse(sp.checkBarCodeValidity("324536217273"));
        assertThrows(InvalidProductCodeException.class, ()->{this.sp.payOrderFor("324536217273", 3, 2.5);});
    }

    @Test
    public void PayOrderForInvalidQuantity()
    {    assertTrue(sp.checkBarCodeValidity("6291041500213"));
        //Invalid: 0
        assertThrows(InvalidQuantityException.class, ()->{this.sp.payOrderFor("6291041500213", 0, 2.5);});
        //Invalid: <0
        assertThrows(InvalidQuantityException.class, ()->{this.sp.payOrderFor("6291041500213", -13, 2.5);});

    }
    @Test
    public void PayOrderForInvalidPricePerUnit()
    {    assertTrue(sp.checkBarCodeValidity("6291041500213"));
        //Invalid: 0
        assertThrows(InvalidPricePerUnitException.class, ()->{this.sp.payOrderFor("6291041500213", 1, 0);});
        //Invalid: <0
        assertThrows(InvalidPricePerUnitException.class, ()->{this.sp.payOrderFor("6291041500213", 1, -2);});
        assertThrows(InvalidPricePerUnitException.class, ()->{this.sp.payOrderFor("6291041500213", 1, -0.00000002);});
    }

    @Test
    public void PayOrderForNoOneLoggedWrongRole() {
        /*Invalid 1= No one still logged, no user created*/
        assertThrows(UnauthorizedException.class, ()->{this.sp.payOrderFor("6291041500213", 1, 50);});

        /*Invalid 2 = user created but still not logged*/
        try {
            int id = sp.createUser("giulia", "ciao", "Cashier");
            assertEquals(1, id);

            // Still no logged
            assertThrows(UnauthorizedException.class, ()->{this.sp.payOrderFor("6291041500213", 1, 50);});
            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("Cashier"));
            assertFalse(this.sp.checkUserRole("ADMINISTRATOR"));
            assertFalse(this.sp.checkUserRole("ShopManager"));
            /*Invalid 2 = user created, logged but wrong role*/

            //Logged in a user with role ShopManager, not administrator
            assertThrows(UnauthorizedException.class, ()->{this.sp.payOrderFor("6291041500213", 1, 50);});



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException e) {
            fail(e.getMessage());
        }


    }

    @Test
    public void PayOrderForProductNotExists() {

        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);

            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ShopManager"));

            //Product Does not exist
            assertEquals(-1, (int) this.sp.payOrderFor("6291041500213", 1, 50));



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException|InvalidQuantityException|UnauthorizedException|InvalidPricePerUnitException|InvalidProductCodeException e) {
            fail(e.getMessage());
        }



    }

    @Test
    public void PayOrderForProductNoBalance() {

        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);

            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ShopManager"));

            //Product Does not exist
            double balance =sp.computeBalance();

            double pricePerunit= balance+100;
            assertEquals(-1, (int) this.sp.payOrderFor("6291041500213", 1,  pricePerunit));

            //Product exists:
            //add product
            assertTrue(this.sp.createProductType("milk", "6291041500213", 2.01, "ciao")>=0);

            double balance2 =sp.computeBalance();

            double pricePerunit2= balance+100;
            assertEquals(-1, (int) this.sp.payOrderFor("6291041500213", 1, pricePerunit2));





        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | InvalidQuantityException | UnauthorizedException | InvalidPricePerUnitException | InvalidProductCodeException | InvalidProductDescriptionException e) {
            fail(e.getMessage());
        }



    }

    @Test
    public void PayOrderForValid() {

        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);

            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ShopManager"));

            //add product
            assertTrue(this.sp.createProductType("milk", "6291041500213", 2.01, "ciao")>=0);
            //add credit to have enough balance
            sp.recordBalanceUpdate(100);
            assertEquals(100, (int)sp.computeBalance());
            assertEquals(2, (int) this.sp.payOrderFor("6291041500213", 1, 50));
            assertEquals(50, (int)sp.computeBalance());

            //TRY WITH ADMINISTRATOR LOGGED IN
            assertTrue(sp.logout());
            int id2 = sp.createUser("giulia2", "ciao2", "Administrator");
            assertEquals(2, id2);

            //loggging in
            assertEquals(2 ,(int)this.sp.login("giulia2", "ciao2").getId());
            assertTrue(this.sp.checkUserRole("Administrator"));

            assertEquals(50, (int)sp.computeBalance());
            assertEquals(3, (int) this.sp.payOrderFor("6291041500213", 1, 50));
            assertEquals(0, (int)sp.computeBalance());





        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | InvalidQuantityException | UnauthorizedException | InvalidPricePerUnitException | InvalidProductCodeException | InvalidProductDescriptionException e) {
            fail(e.getMessage());
        }



    }

    /****************** TESTS PAY ORDER********************************/

    @Test
    public void payOrderInvalidOrderId()
    {   //Invalid: 0
        assertThrows(InvalidOrderIdException.class, ()->{this.sp.payOrder(0);});
        //Invalid: <0
        assertThrows(InvalidOrderIdException.class, ()->{this.sp.payOrder(-12345);});
        //Invalid: null
        assertThrows(InvalidOrderIdException.class, ()->{this.sp.payOrder(null);});


    }


    @Test
    public void PayOrderNoOneLoggedWrongRole() {
        /*Invalid 1= No one still logged, no user created*/
        assertThrows(UnauthorizedException.class, ()->{this.sp.payOrder(2);});

        /*Invalid 2 = user created but still not logged*/
        try {
            int id = sp.createUser("giulia", "ciao", "Cashier");
            assertEquals(1, id);

            // Still no logged
            assertThrows(UnauthorizedException.class, ()->{this.sp.payOrder(2);});
            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("Cashier"));
            assertFalse(this.sp.checkUserRole("ADMINISTRATOR"));
            assertFalse(this.sp.checkUserRole("ShopManager"));
            /*Invalid 2 = user created, logged but wrong role*/

            //Logged in a user with role ShopManager, not administrator
            assertThrows(UnauthorizedException.class, ()->{this.sp.payOrder(2);});



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException e) {
            fail(e.getMessage());
        }


    }

    @Test
    public void PayOrderOrderNotExists()
    {

        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);

            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ShopManager"));

            assertFalse(sp.payOrder(2));



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | UnauthorizedException | InvalidOrderIdException e) {
        fail(e.getMessage());
    }
    }

    @Test
    public void PayOrderOrderNotISSUEDState()
    {
        /*Not issued= payed/completed*/
        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);

            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ShopManager"));

            //add product
            assertTrue(this.sp.createProductType("milk", "6291041500213", 2.01, "ciao")>=0);
            //add credit to have enough balance
            sp.recordBalanceUpdate(100);
            assertEquals(100, (int)sp.computeBalance());
            assertEquals(2, (int) this.sp.payOrderFor("6291041500213", 1, 50));
            assertEquals(50, (int)sp.computeBalance());
            //now order 2 is in payed state. payorder should return true without doing anything
            assertTrue(sp.payOrder(2));
            assertEquals(50, (int)sp.computeBalance());



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | UnauthorizedException | InvalidOrderIdException | InvalidProductDescriptionException | InvalidProductCodeException | InvalidPricePerUnitException | InvalidQuantityException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void PayOrderOrderValid()
    {
        /*Not issued= payed/completed*/
        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);

            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ShopManager"));

            //add product
            assertTrue(this.sp.createProductType("milk", "6291041500213", 2.01, "ciao")>=0);
            //add credit to have enough balance
            sp.recordBalanceUpdate(100);
            assertEquals(100, (int)sp.computeBalance());
            assertEquals(2, (int) this.sp.issueOrder("6291041500213", 1, 50));
            assertEquals(100, (int)sp.computeBalance());
            assertTrue(sp.payOrder(2));
            assertEquals(50, (int)sp.computeBalance());




        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | UnauthorizedException | InvalidOrderIdException | InvalidProductDescriptionException | InvalidProductCodeException | InvalidPricePerUnitException | InvalidQuantityException e) {
            fail(e.getMessage());
        }
    }

    /****************** TESTS RECORD ORDER ARRIVAL********************************/

    @Test
    public void recordOrderArrivalInvalidOrderId()
    {   //Invalid: 0
        assertThrows(InvalidOrderIdException.class, ()->{this.sp.recordOrderArrival(0);});
        //Invalid: <0
        assertThrows(InvalidOrderIdException.class, ()->{this.sp.recordOrderArrival(-12345);});
        //Invalid: null
        assertThrows(InvalidOrderIdException.class, ()->{this.sp.recordOrderArrival(null);});


    }
    @Test
    public void recordOrderArrivalInvalidLocation() {

        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);

            //loggging in
            assertEquals(1, (int) this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ShopManager"));

            //add product
            assertTrue(this.sp.createProductType("milk", "6291041500213", 2.01, "ciao") >= 0);
            //add credit to have enough balance
            sp.recordBalanceUpdate(100);

            assertEquals(2, (int) this.sp.issueOrder("6291041500213", 1, 50));

            assertThrows(InvalidLocationException.class, () -> { this.sp.recordOrderArrival(2); });




        } catch (InvalidQuantityException | UnauthorizedException | InvalidPasswordException | InvalidProductDescriptionException | InvalidRoleException | InvalidUsernameException | InvalidPricePerUnitException | InvalidProductCodeException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void recordOrderArrivalNoOneLoggedWrongRole() {
        /*Invalid 1= No one still logged, no user created*/
        assertThrows(UnauthorizedException.class, ()->{this.sp.recordOrderArrival(2);});

        /*Invalid 2 = user created but still not logged*/
        try {
            int id = sp.createUser("giulia", "ciao", "Cashier");
            assertEquals(1, id);

            // Still no logged
            assertThrows(UnauthorizedException.class, ()->{this.sp.recordOrderArrival(2);});
            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("Cashier"));
            assertFalse(this.sp.checkUserRole("ADMINISTRATOR"));
            assertFalse(this.sp.checkUserRole("ShopManager"));
            /*Invalid 2 = user created, logged but wrong role*/

            //Logged in a user with role ShopManager, not administrator



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException e) {
            fail(e.getMessage());
        }


    }

    @Test
    public void RecordOrderArrivalOrderNotExists()
    {

        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);

            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ShopManager"));

            assertFalse(sp.payOrder(2));



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | UnauthorizedException | InvalidOrderIdException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void  RecordOrderArrivalOrderISSUEDState()
    {

        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);

            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ShopManager"));

            //add product
            int productId=this.sp.createProductType("milk", "6291041500213", 2.01, "ciao");
            assertTrue(productId>=0);
            //add credit to have enough balance
            sp.recordBalanceUpdate(100);
            assertEquals(100, (int)sp.computeBalance());
            assertEquals(2, (int) this.sp.issueOrder("6291041500213", 1, 50));

            //The product should be in a location
            assertTrue(sp.updatePosition(productId, "2-b-3"));
            //Order 2 is in ISSUED state, its not possible to record the order arrival because it has not been payed yet
            assertFalse(sp.recordOrderArrival(2));



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | UnauthorizedException | InvalidOrderIdException | InvalidProductDescriptionException | InvalidProductCodeException | InvalidPricePerUnitException | InvalidQuantityException | InvalidLocationException | InvalidProductIdException e) {
            fail(e.getMessage() + e.toString());

        }
    }

    @Test
    public void  RecordOrderArrivalOrderCOMPLETEDState()
    {
        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);

            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ShopManager"));

            //add product
            int productId=this.sp.createProductType("milk", "6291041500213", 2.01, "ciao");
            assertTrue(productId>=0);
            //add credit to have enough balance
            sp.recordBalanceUpdate(100);
            assertEquals(100, (int)sp.computeBalance());
            assertEquals(2, (int) this.sp.issueOrder("6291041500213", 1, 50));
            assertTrue(sp.payOrder(2));
            //The product should be in a location
            assertTrue(sp.updatePosition(productId, "2-b-3"));
            //Order 2 is in PAYED state
            assertTrue(sp.recordOrderArrival(2));
            assertEquals(sp.getAllOrders().get(0).getStatus(), "COMPLETED");

            //Order2 now is in COMPLETED STATE!!
            assertTrue(sp.recordOrderArrival(2));



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | UnauthorizedException | InvalidOrderIdException | InvalidProductDescriptionException | InvalidProductCodeException | InvalidPricePerUnitException | InvalidQuantityException | InvalidLocationException | InvalidProductIdException e) {
            fail(e.getMessage() + e.toString());

        }
    }

    @Test
    public void  RecordOrderArrivalValid()
    {

        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);

            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ShopManager"));

            //add product
            int productId=this.sp.createProductType("milk", "6291041500213", 2.01, "ciao");
            assertTrue(productId>=0);
            //add credit to have enough balance
            sp.recordBalanceUpdate(100);
            assertEquals(100, (int)sp.computeBalance());
            assertEquals(2, (int) this.sp.issueOrder("6291041500213", 1, 50));
            assertTrue(sp.payOrder(2));
            //The product should be in a location
            assertTrue(sp.updatePosition(productId, "2-b-3"));
            //Order 2 is in PAYED state
            assertTrue(sp.recordOrderArrival(2));
            assertEquals(sp.getAllOrders().get(0).getStatus(), "COMPLETED");



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | UnauthorizedException | InvalidOrderIdException | InvalidProductDescriptionException | InvalidProductCodeException | InvalidPricePerUnitException | InvalidQuantityException | InvalidLocationException | InvalidProductIdException e) {
            fail(e.getMessage() + e.toString());

        }
    }


    /****************** TESTS GET ALL ORDERS********************************/


    @Test
    public void GetAllOrdersNoOneLoggedWrongRole() {
        /*Invalid 1= No one still logged, no user created*/
        assertThrows(UnauthorizedException.class, ()->{this.sp.getAllOrders();});

        /*Invalid 2 = user created but still not logged*/
        try {
            int id = sp.createUser("giulia", "ciao", "Cashier");
            assertEquals(1, id);

            // Still no logged
            assertThrows(UnauthorizedException.class, ()->{this.sp.getAllOrders();});
            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("Cashier"));
            assertFalse(this.sp.checkUserRole("ADMINISTRATOR"));
            assertFalse(this.sp.checkUserRole("ShopManager"));
            /*Invalid 2 = user created, logged but wrong role*/

            //Logged in a user with role ShopManager, not administrator
            assertThrows(UnauthorizedException.class, ()->{this.sp.getAllOrders();});



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException e) {
            fail(e.getMessage());
        }


    }

    @Test
    public void getAllOrdersValid()
    {

        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);

            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ShopManager"));

            //add product
            assertTrue(this.sp.createProductType("milk", "6291041500213", 2.01, "ciao")>=0);
            //add credit to have enough balance
            sp.recordBalanceUpdate(500);
            int id1= this.sp.issueOrder("6291041500213", 1, 50);
            int id2= this.sp.issueOrder("6291041500213", 1, 50);
            int id3= this.sp.issueOrder("6291041500213", 1, 50);
            int id4=this.sp.payOrderFor("6291041500213", 2, 50);
            sp.payOrder(id4);

            assertEquals(4, (int)sp.getAllOrders().size());
            assertTrue(sp.getAllOrders().stream().map(o->o.getOrderId()).collect(Collectors.toList()).contains(id3));




        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | UnauthorizedException | InvalidOrderIdException | InvalidProductDescriptionException | InvalidProductCodeException | InvalidPricePerUnitException | InvalidQuantityException e) {
            fail(e.getMessage());
        }
    }




}
