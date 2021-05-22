package it.polito.ezshop.integrationTests;
import it.polito.ezshop.data.classes.*;
import it.polito.ezshop.data.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TestDBClass {



    @Test
    public void testCreationDB()
    {
        try {
            EZDatabase db;
            db = new EZDatabase();

        }
        catch (SQLException throwables) {
            fail("Should have not thrown the exception creating the db");
        }
    }


    @Before
    public void ResetDB()
    {

        try {
            EZDatabase db = new EZDatabase();
            db.clearUsers();
            db.clearOrders();
            db.deleteProductTable(); /*Messa perchè mi serviva per testare order!!*/


        }
        catch (SQLException throwables) {

            fail(throwables.getMessage());
        }

    }

    /*********************************************************************/
    /**                                                                  */
    /**                             USERS                                */
    /**                                                                  */
    /*********************************************************************/


    /****************** TESTS FOR GET USERS********************************/

    @Test
    public void testGetUsersNoException()
    {
        User u,o;
        EZDatabase db;
        try {
            u = new EZUser(1, "ciao", "asddas", "SHOPMANAGER");
            db = new EZDatabase();
            db.insertUser(u);
            o = new EZUser(2, "ciao2", "asddas", "SHOPMANAGER");
            db.insertUser(o);
            List<User> users =db.getUsers();




        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }

    }


    /****************** TESTS FOR INSERT USER********************************/
    @Test
    public void testInsertUsersNoException()
    {
        User u,o;
        EZDatabase db;
        try {
            u = new EZUser(1, "ciao", "asddas", "SHOPMANAGER");
            db = new EZDatabase();
            db.insertUser(u);
            o = new EZUser(2, "ciao2", "asddas", "SHOPMANAGER");
            db.insertUser(o);




        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }

    }


    @Test
    public void testInsertUserValid() {
        User u;
        EZDatabase db;
        try {
            u = new EZUser(1, "ciao", "asddas", "SHOPMANAGER");
            db = new EZDatabase();
            db.insertUser(u);

            List<User> users = db.getUsers();
            assertEquals(1, users.size());
            assertEquals(u.getId(), users.get(0).getId());




        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }

    }

    @Test
    public void testInsertUserValid2() {
        User u,o;
        EZDatabase db;

        try {
            u = new EZUser(1, "ciao", "asddas", "SHOPMANAGER");
            db = new EZDatabase();
            db.insertUser(u);

            o = new EZUser(2, "ciao2", "asddas", "SHOPMANAGER");
            db.insertUser(o);

            List<User> users = db.getUsers();
            assertEquals(2, users.size());
            assertEquals(u.getId(), users.get(0).getId());
            assertEquals(o.getId(), users.get(1).getId());



        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }

    }


    @Test
    public void testInsertUserIdAlreadyExists() {
        User u;
        EZDatabase db;
        try {
            u = new EZUser(1, "ciao", "asddas", "SHOPMANAGER");
            db = new EZDatabase();
            db.insertUser(u);

            /*Two user with the same id!*/
            assertThrows(SQLException.class, ()->{db.insertUser(u);});




        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }

    }

    @Test
    public void testInsertUserUsernameAlreadyExists() {
        User u, o;
        EZDatabase db;
        try {
            u = new EZUser(1, "ciao", "asddas", "SHOPMANAGER");
            db = new EZDatabase();
            db.insertUser(u);

            o = new EZUser(2, "ciao", "asddas", "SHOPMANAGER");

            /*Two user with the same username!*/
            assertThrows(SQLException.class, ()->{db.insertUser(o);});




        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }

    }



    /****************** TESTS FOR GET NEXT USER ID********************************/

    @Test
    public void testGetNextUserIdEmptyTable()
    {
        User u,o;
        EZDatabase db;
        try {
            db = new EZDatabase();
            assertEquals(1, db.getNextUserId());


        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }
    }



    @Test
    public void testGetNextUserIdValid()
    {
        User u,o;
        EZDatabase db;
        try {
            u = new EZUser(1, "ciao", "asddas", "SHOPMANAGER");
            db = new EZDatabase();
            db.insertUser(u);

            assertEquals(2, db.getNextUserId());

            o = new EZUser(2, "ciao2", "asddas", "SHOPMANAGER");

            db.insertUser(o);
            assertEquals(3, db.getNextUserId());




        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }
    }

    /****************** TESTS FOR DELETE USER********************************/

    @Test
    public void testDeleteUserValid()
    {
        User u,o;
        EZDatabase db;
        try {
            u = new EZUser(1, "ciao", "asddas", "SHOPMANAGER");
            db = new EZDatabase();
            db.insertUser(u);

            assertEquals(1, db.getUsers().size());
            assertTrue(db.getUsers().stream().map(user -> user.getId()).collect(Collectors.toList()).contains(u.getId()));

            db.deleteUser(u.getId());

            assertFalse(db.getUsers().stream().map(user -> user.getId()).collect(Collectors.toList()).contains(u.getId()));


        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }
    }

    @Test
    public void testDeleteUserNotPresent()
    {
        User u,o;
        EZDatabase db;
        try {
            u = new EZUser(1, "ciao", "asddas", "SHOPMANAGER");
            db = new EZDatabase();
            db.insertUser(u);

            assertEquals(1, db.getUsers().size());
            assertTrue(db.getUsers().stream().map(user -> user.getId()).collect(Collectors.toList()).contains(u.getId()));

            db.deleteUser(u.getId());
            /*Nothing should happen*/


        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }
    }


    /****************** TESTS FOR UPDATE USER ROLE********************************/

    @Test
    public void testUpdateUserRoleValid()
    {
        User u,o;
        EZDatabase db;
        try {
            u = new EZUser(1, "ciao", "asddas", "SHOPMANAGER");
            db = new EZDatabase();
            db.insertUser(u);

            assertEquals("SHOPMANAGER", db.getUsers().get(0).getRole());

            db.updateUserRole(1, "ADMINISTRATOR");
            assertEquals("ADMINISTRATOR", db.getUsers().get(0).getRole());



        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }
    }

    @Test
    public void testUpdateUserRoleUserNotPresent()
    {
        User u,o;
        EZDatabase db;
        try {
            db = new EZDatabase();
            db.updateUserRole(1, "ADMINISTRATOR");

            /*It should happen nothing*/


        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }
    }


    /****************** TESTS FOR CLEAR USERS*******************************/
   @Test
    public void testClearUsers ()
    {
        User u,o,p;
        EZDatabase db;
        try {
            u = new EZUser(1, "ciao", "ciao", "SHOPMANAGER");
            o =new EZUser(2, "ciao2", "ciao", "SHOPMANAGER");
            p =new EZUser(3, "ciao3", "ciao", "SHOPMANAGER");

            db = new EZDatabase();
            db.insertUser(u);
            db.insertUser(o);
            db.insertUser(p);

            assertEquals(3, db.getUsers().size());

            db.clearUsers();
            assertEquals(0, db.getUsers().size());




        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }
    }


    @Test
    public void testClearUsersEmptyTable ()
    {

        EZDatabase db;
        try {
            db = new EZDatabase();

            assertEquals(0, db.getUsers().size());

            db.clearUsers();
            assertEquals(0, db.getUsers().size());

            /*Nothing should happen*/



        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }
    }


    /*********************************************************************/
    /**                                                                  */
    /**                             ORDERS                               */
    /**                                                                  */
    /*********************************************************************/

    /****************** TESTS FOR GET ORDERS********************************/

    @Test
    public void testGetOrdersNoException()
    {
        Order u;
        EZDatabase db;
        try {

            EZProductType p = new EZProductType("milk", "12345", 1.00, "", 1);
            u = new EZOrder(1, "12345", 3, 2.5);
            db= new EZDatabase();
            db.insertProductType(p);  //To have the dependency
            db.insertOrder(u);

            Map<Integer, Order> orders = db.getOrders();




        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }

    }

    /****************** TESTS FOR INSERT ORDERS********************************/

    @Test
    public void testInsertOrderNoException()
    {
        Order u;
        EZDatabase db;
        try {
            EZProductType p = new EZProductType("milk", "12345", 1.00, "", 1);
            u = new EZOrder(1, "12345", 3, 2.5);
            db= new EZDatabase();
            db.insertProductType(p);  //To have the dependency
            db.insertOrder(u);


        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }

    }


    @Test
    public void testInsertOrderIdAlreadyExists()
    {
        Order u,o;
        EZDatabase db;
        try {
            EZProductType p = new EZProductType("milk", "12345", 1.00, "", 1);
            u = new EZOrder(1, "12345", 3, 2.5);
            db= new EZDatabase();
            db.insertProductType(p);  //To have the dependency
            db.insertOrder(u);

            o = new EZOrder(1, "12345", 3, 2.5);
            assertThrows(SQLException.class, ()->{db.insertOrder(o);});


        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }

    }


    //TODO: l'insert non fa il check sulla foreign key
    /*
    @Test
    public void testInsertOrderIdBarCodeNotExisting()
    {
        Order u,o;
        EZDatabase db;
        try {

            o = new EZOrder(1, "1234567", 3, 2.5);
            db= new EZDatabase();
            //ProductCode not present in product table! Foreign key constrain fails!
            assertThrows(SQLException.class, ()->{db.insertOrder(o);});


        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }

    }
    */
   @Test
    public void insertOrderValid()
    {

        Order u;
        EZDatabase db;
        try {
            EZProductType p = new EZProductType("milk", "12345", 1.00, "", 1);
            u = new EZOrder(1, "12345", 3, 2.5);
            db= new EZDatabase();
            db.insertProductType(p);  //To have the dependency
            db.insertOrder(u);

            Map<Integer, Order> orders = db.getOrders();
            assertEquals(1, orders.size());
            assertTrue(orders.containsKey(1));

            u = new EZOrder(2, "12345", 3, 2.5);
            db.insertOrder(u);
            orders=db.getOrders();
            assertEquals(2, orders.size());
            assertTrue(orders.containsKey(2));




        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }


    }

    /****************** TESTS FOR DELETE ORDER********************************/

    @Test
    public void testDeleteOrderValid()
    {
        Order u,o;
        EZDatabase db;
        try {
            EZProductType p = new EZProductType("milk", "12345", 1.00, "", 1);
            u = new EZOrder(1, "12345", 3, 2.5);
            db= new EZDatabase();
            db.insertProductType(p);  //To have the dependency
            db.insertOrder(u);

            assertEquals(1, db.getOrders().size());
            assertTrue(db.getOrders().containsKey(u.getOrderId()));

            db.deleteOrder(u.getOrderId());

            assertFalse(db.getOrders().containsKey(u.getOrderId()));

        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }
    }

    @Test
    public void testDeleteOrderNotPresent()
    {
        Order u,o;
        EZDatabase db;
        try {
            EZProductType p = new EZProductType("milk", "12345", 1.00, "", 1);
            u = new EZOrder(1, "12345", 3, 2.5);
            db= new EZDatabase();
            db.insertProductType(p);  //To have the dependency
            db.insertOrder(u);

            assertEquals(1, db.getOrders().size());
            assertTrue(db.getOrders().containsKey(u.getOrderId()));

            db.deleteOrder(u.getOrderId());
            /*Nothing should happen*/

        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }
    }

    /****************** TESTS FOR CLEAR USERS*******************************/
    @Test
    public void testClearOrders ()
    {
        Order u,u2,u3;
        EZDatabase db;
        try {
            EZProductType p = new EZProductType("milk", "12345", 1.00, "", 1);
            u = new EZOrder(1, "12345", 3, 2.5);
            u2 = new EZOrder(2, "12345", 3, 2.5);
            u3 = new EZOrder(3, "12345", 3, 2.5);


            db= new EZDatabase();
            db.insertProductType(p);  //To have the dependency
            db.insertOrder(u);
            db.insertOrder(u2);
            db.insertOrder(u3);


            assertEquals(3, db.getOrders().size());

            db.clearOrders();
            assertEquals(0, db.getOrders().size());


        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }
    }

    /****************** TESTS FOR UPDATE ORDER STATUS********************************/

    @Test
    public void testUpdateOrderStatus()
    {
        Order u,o;
        EZDatabase db;
        try {
            EZProductType p = new EZProductType("milk", "12345", 1.00, "", 1);
            u = new EZOrder(1, "12345", 3, 2.5);
            u.setStatus("PAYED");
            db= new EZDatabase();
            db.insertProductType(p);  //To have the dependency
            db.insertOrder(u);

            assertEquals("PAYED", db.getOrders().get(u.getOrderId()).getStatus());

            db.updateOrderStatus(u.getOrderId(), "ISSUED");
            assertEquals("ISSUED", db.getOrders().get(u.getOrderId()).getStatus());



        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }
    }

    /****************** TESTS FOR UPDATE BALANCE ID********************************/

    @Test
    public void testUpdateBalanceId()
    {
        Order u,o;
        EZDatabase db;
        try {
            EZProductType p = new EZProductType("milk", "12345", 1.00, "", 1);
            u = new EZOrder(1, "12345", 3, 2.5);
            u.setBalanceId(3);
            db= new EZDatabase();
            db.insertProductType(p);  //To have the dependency
            db.insertOrder(u);

            assertEquals(3,(int)db.getOrders().get(u.getOrderId()).getBalanceId());

            db.updateOrderBalanceId(u.getOrderId(), 6);
            assertEquals(6,(int)db.getOrders().get(u.getOrderId()).getBalanceId());



        } catch (SQLException throwables) {

            fail(throwables.getMessage());
        }
    }










}
