package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.*;
import it.polito.ezshop.data.classes.*;
import it.polito.ezshop.exceptions.*;

import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class EZShopUserMethodsTest {

    EZShop sp = new EZShop();

    @Before
    public void resetShop()
    {
          sp.reset();

    }



    /****************** TESTS CREATE USER********************************/
    @Test
    public void createUserInvalidUsernameOrPasswordOrRole()
    {

        /*Invalid 1*/
        assertThrows(InvalidUsernameException.class, ()->{this.sp.createUser(null, "ciao", "SHOPMANAGER");});

        /*Invalid 2*/
        assertThrows(InvalidUsernameException.class, ()->{this.sp.createUser("", "ciao", "SHOPMANAGER");});

        /*Invalid 3*/
        assertThrows(InvalidPasswordException.class, ()->{this.sp.createUser("giulia", "", "SHOPMANAGER");});

        /*Invalid 4*/
        assertThrows(InvalidPasswordException.class, ()->{this.sp.createUser("giulia", null, "SHOPMANAGER");});


        /*Invalid 5*/
        assertThrows(InvalidPasswordException.class,()->{this.sp.createUser("giulia", "     ", "SHOPMANAGER");});

        /*Invalid 6*/
        assertThrows(InvalidUsernameException.class, ()->{this.sp.createUser("      ", "ciao", "SHOPMANAGER");});

        /*Invalid 7*/
        assertThrows(InvalidRoleException.class, ()->{this.sp.createUser("giulia", "ciao", "manager");});

        /*Invalid 8*/
        assertThrows(InvalidRoleException.class, ()->{this.sp.createUser("giulia", "ciao", "hola");});

        /*Invalid 9*/
        assertThrows(InvalidRoleException.class, ()->{this.sp.createUser("giulia", "ciao", "");});
        /*Invalid 10*/
        assertThrows(InvalidRoleException.class, ()->{this.sp.createUser("giulia", "ciao", "      ");});



    }

    @Test
    public void createUserInvalidSameUsername()
    {
        try {
            sp.createUser("giulia", "ciao", "Administrator");
            assertEquals(-1, (int)sp.createUser("giulia", "ciao2", "ShopManager"));

        } catch (InvalidUsernameException e) {
            fail(e.getMessage());
        } catch (InvalidPasswordException e) {
            fail(e.getMessage());
        } catch (InvalidRoleException e) {
            fail(e.getMessage());
        }





    }

    @Test
    public void createUserValid()
    {
        try {
            int id = sp.createUser("giulia", "ciao", "Administrator");
            assertEquals(1, id);
            int id2= sp.createUser("giulia2", "ciao2", "Cashier");
            assertEquals(2, id2);
            int id3= sp.createUser("giulia3", "ciao3", "ShopManager");
            assertEquals(3, id3);

        } catch (InvalidUsernameException | InvalidRoleException | InvalidPasswordException e) {
            fail(e.getMessage());
        }


    }


    /****************** TESTS LOGIN********************************/



    @Test
    public void testLoginInvalidUsernameOrPassword()
    {

        /*Invalid 1*/
        assertThrows(InvalidUsernameException.class, ()->{this.sp.login(null, "ciao");});

        /*Invalid 2*/
        assertThrows(InvalidUsernameException.class, ()->{this.sp.login("", "ciao");});

        /*Invalid 3*/
        assertThrows(InvalidPasswordException.class, ()->{this.sp.login("giulia", null);});

        /*Invalid 4*/
        assertThrows(InvalidPasswordException.class, ()->{this.sp.login("giulia", "");});


        /*Invalid 5*/
        assertThrows(InvalidPasswordException.class, ()->{this.sp.login("giulia", "     ");});

        /*Invalid 6*/
        assertThrows(InvalidUsernameException.class, ()->{this.sp.login("      ", "ciao");});


    }


    @Test
    public void testLoginValid() {
        try {
            int id = sp.createUser("giulia", "ciao", "Administrator");
            assertEquals(1, id);

        } catch (InvalidUsernameException e) {
            fail(e.getMessage());
        } catch (InvalidPasswordException e) {
            fail(e.getMessage());
        } catch (InvalidRoleException e) {
            fail(e.getMessage());
        }

        try {
            User u = sp.login("giulia", "ciao");
            assertEquals("giulia", u.getUsername());
            assertTrue(sp.checkUserRole("ADMINISTRATOR"));

        } catch (InvalidUsernameException e) {
            fail(e.getMessage());
        } catch (InvalidPasswordException e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void testLoginInvalidUser() {
        try {
            int id = sp.createUser("giulia", "ciao", "Administrator");
            assertEquals(1, id);

        } catch (InvalidUsernameException e) {
            fail(e.getMessage());
        } catch (InvalidPasswordException e) {
            fail(e.getMessage());
        } catch (InvalidRoleException e) {
            fail(e.getMessage());
        }

        try {
            /*Invalid User not Exists*/
            assertNull(this.sp.login("giulia2", "ciao"));
            /*Invalid User wrong password*/
            assertNull(this.sp.login("giulia", "ciao2"));
        } catch (InvalidUsernameException e) {
            fail(e.getMessage());
        } catch (InvalidPasswordException e) {
            fail(e.getMessage());
        }


    }

    /****************** TESTS LOGOUT********************************/

    @Test
    public void testLogoutNoLoggedUsers() {

        assertFalse(sp.logout());


    }

    @Test
    public void testLogoutLoggedUsers() {

        try {
            int id = sp.createUser("giulia", "ciao", "Administrator");
            assertEquals(1, id);

        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException e) {
            fail(e.getMessage());
        }

        try {
            User u = sp.login("giulia", "ciao");
            assertEquals("giulia", u.getUsername());
            assertTrue(sp.checkUserRole("ADMINISTRATOR"));

        } catch (InvalidUsernameException | InvalidPasswordException e) {
            fail(e.getMessage());
        }

        assertTrue(sp.logout());
        //The user logs out, so now there is no user logged in
        assertFalse(sp.logout());
    }

    /****************** TEST DELETE USER********************************/
    @Test
    public void testDeleteUserInvalidUser() {
        /*Invalid 1*/
        assertThrows(InvalidUserIdException.class, ()->{this.sp.deleteUser(null);});
        /*Invalid 2*/
        assertThrows(InvalidUserIdException.class, ()->{this.sp.deleteUser(-1);});
        /*Invalid 3*/
        assertThrows(InvalidUserIdException.class, ()->{this.sp.deleteUser(0);});

    }

    @Test
    public void testDeleteUserNoOneLoggedWrongRole() {
        /*Invalid 1= No one still logged, no user created*/
        assertThrows(UnauthorizedException.class, ()->{this.sp.deleteUser(1);});

        /*Invalid 2 = user created but still not logged*/
        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);
            int id2 = sp.createUser("giulia2", "ciao2", "Cashier");
            assertEquals(2, id2);


            // Still no logged
            assertThrows(UnauthorizedException.class, ()->{this.sp.deleteUser(1);});
            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("SHOPMANAGER"));
            assertFalse(this.sp.checkUserRole("ADMINISTRATOR"));
            /*Invalid 2 = user created, logged but wrong role*/

            //Logged in a user with role ShopManager, not administrator
            assertThrows(UnauthorizedException.class, ()->{this.sp.deleteUser(2);});
            assertTrue(sp.logout());

            //logging in a uuser with role Cashier not administrator
            assertEquals(2 ,(int)this.sp.login("giulia2", "ciao2").getId());
            assertTrue(this.sp.checkUserRole("CASHIER"));
            assertFalse(this.sp.checkUserRole("ADMINISTRATOR"));
            assertThrows(UnauthorizedException.class, ()->{this.sp.deleteUser(1);});



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException e) {
            fail(e.getMessage());
        }


    }

    @Test
    public void testDeleteUserCannotBeDeleted() {


        try {

            //Invalid1: user not existing

            int id = sp.createUser("giulia", "ciao", "Administrator");
            assertEquals(1, id);
            int id2 = sp.createUser("giulia2", "ciao2", "Cashier");
            assertEquals(2, id2);


            //loggging in
            assertEquals(1, (int) this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ADMINISTRATOR"));

            assertFalse(sp.deleteUser(3));

        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | InvalidUserIdException | UnauthorizedException e) {
            fail(e.getMessage());
        }


    }

    @Test
    public void testDeleteUserValid() {

        try {


            int id = sp.createUser("giulia", "ciao", "Administrator");
            assertEquals(1, id);
            int id2 = sp.createUser("giulia2", "ciao2", "Cashier");
            assertEquals(2, id2);


            //loggging in
            assertEquals(1, (int) this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ADMINISTRATOR"));

            assertTrue(sp.deleteUser(2));

            //so now if I would try to log in with 2, I would get null!
            assertTrue(sp.logout());
            assertNull(sp.login("giulia2", "ciao2"));


        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | InvalidUserIdException | UnauthorizedException e) {
            fail(e.getMessage());
        }

    }

    /****************** TEST GET USER********************************/

    @Test
    public void testGetUserInvalidUser() {
        /*Invalid 1*/
        assertThrows(InvalidUserIdException.class, ()->{this.sp.getUser(null);});
        /*Invalid 2*/
        assertThrows(InvalidUserIdException.class, ()->{this.sp.getUser(-1);});
        /*Invalid 3*/
        assertThrows(InvalidUserIdException.class, ()->{this.sp.getUser(0);});

    }

    @Test
    public void testGetUserNoOneLoggedWrongRole() {
        /*Invalid 1= No one still logged, no user created*/
        assertThrows(UnauthorizedException.class, ()->{this.sp.getUser(1);});

        /*Invalid 2 = user created but still not logged*/
        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);
            int id2 = sp.createUser("giulia2", "ciao2", "Cashier");
            assertEquals(2, id2);


            // Still no logged
            assertThrows(UnauthorizedException.class, ()->{this.sp.getUser(1);});
            //loggging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("SHOPMANAGER"));
            assertFalse(this.sp.checkUserRole("ADMINISTRATOR"));
            /*Invalid 2 = user created, logged but wrong role*/

            //Logged in a user with role ShopManager, not administrator
            assertThrows(UnauthorizedException.class, ()->{this.sp.getUser(2);});
            assertTrue(sp.logout());

            //logging in a uuser with role Cashier not administrator
            assertEquals(2 ,(int)this.sp.login("giulia2", "ciao2").getId());
            assertTrue(this.sp.checkUserRole("CASHIER"));
            assertFalse(this.sp.checkUserRole("ADMINISTRATOR"));
            assertThrows(UnauthorizedException.class, ()->{this.sp.getUser(1);});



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException e) {
            fail(e.getMessage());
        }


    }

    @Test
    public void testGetUserNotExisting() {

        try {

            //Invalid1: user not existing

            int id = sp.createUser("giulia", "ciao", "Administrator");
            assertEquals(1, id);
            int id2 = sp.createUser("giulia2", "ciao2", "Cashier");
            assertEquals(2, id2);


            //loggging in
            assertEquals(1, (int) this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ADMINISTRATOR"));

            assertNull(sp.getUser(3));

        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | InvalidUserIdException | UnauthorizedException e) {
            fail(e.getMessage());
        }


    }

    @Test
    public void testGetUserValid() {

        try {


            int id = sp.createUser("giulia", "ciao", "Administrator");
            assertEquals(1, id);
            int id2 = sp.createUser("giulia2", "ciao2", "Cashier");
            assertEquals(2, id2);


            //loggging in
            assertEquals(1, (int) this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ADMINISTRATOR"));
            assertEquals(2, (int)sp.getUser(2).getId());

            //try delete it
            assertTrue(sp.deleteUser(2));
            assertNull(sp.getUser(2));



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | InvalidUserIdException | UnauthorizedException e) {
            fail(e.getMessage());
        }

    }

    /****************** TEST GET ALL USERS********************************/

    @Test
    public void testGetAllUsersNoOneLoggedWrongRole() {
        /*Invalid 1= No one still logged, no user created*/
        assertThrows(UnauthorizedException.class, ()->{this.sp.getAllUsers();});

        /*Invalid 2 = user created but still not logged*/
        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);
            int id2 = sp.createUser("giulia2", "ciao2", "Cashier");
            assertEquals(2, id2);


            // Still no logged
            assertThrows(UnauthorizedException.class, ()->{this.sp.getAllUsers();});
          //logging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("SHOPMANAGER"));
            assertFalse(this.sp.checkUserRole("ADMINISTRATOR"));
            /*Invalid 2 = user created, logged but wrong role*/

            //Logged in a user with role ShopManager, not administrator
            assertThrows(UnauthorizedException.class, ()->{this.sp.getAllUsers();});
            assertTrue(sp.logout());

            //logging in a uuser with role Cashier not administrator
            assertEquals(2 ,(int)this.sp.login("giulia2", "ciao2").getId());
            assertTrue(this.sp.checkUserRole("CASHIER"));
            assertFalse(this.sp.checkUserRole("ADMINISTRATOR"));
            assertThrows(UnauthorizedException.class, ()->{this.sp.getAllUsers();});



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException e) {
            fail(e.getMessage());
        }


    }

    @Test
    public void testGetAllUsersValidNoUsers() {

        try {


            int id = sp.createUser("giulia", "ciao", "Administrator");
            assertEquals(1, id);

            //loggging in
            assertEquals(1, (int) this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ADMINISTRATOR"));

            assertTrue(this.sp.deleteUser(1));
            assertTrue(this.sp.getAllUsers().isEmpty());


        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | InvalidUserIdException | UnauthorizedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetAllUsersValid() {

        try {


            int id = sp.createUser("giulia", "ciao", "Administrator");
            assertEquals(1, id);
            int id2 = sp.createUser("giulia2", "ciao2", "ShopManager");
            assertEquals(2, id2);
            int id3 = sp.createUser("giulia3", "ciao2", "ShopManager");
            assertEquals(3, id3);

            //loggging in
            assertEquals(1, (int) this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ADMINISTRATOR"));


            assertEquals(3,this.sp.getAllUsers().size());
            assertTrue(sp.getAllUsers().stream().map(u -> u.getUsername()).collect(Collectors.toList()).contains("giulia"));
            assertTrue(sp.getAllUsers().stream().map(u -> u.getUsername()).collect(Collectors.toList()).contains("giulia2"));
            assertTrue(sp.getAllUsers().stream().map(u -> u.getUsername()).collect(Collectors.toList()).contains("giulia3"));



        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException |  UnauthorizedException e) {
            fail(e.getMessage());
        }
    }

    /****************** TEST UPDATE USER RIGHTS********************************/

    @Test
    public void testUpdateUserRightsInvalidUser() {
        /*Invalid 1*/
        assertThrows(InvalidUserIdException.class, ()->{this.sp.updateUserRights(null, "ShopManager");});
        /*Invalid 2*/
        assertThrows(InvalidUserIdException.class, ()->{this.sp.updateUserRights(-1,"ShopManager");});
        /*Invalid 3*/
        assertThrows(InvalidUserIdException.class, ()->{this.sp.updateUserRights(0,"ShopManager");});

    }
    @Test
    public void testUpdateUserRightsInvalidRole() {
        /*Invalid 1*/
        assertThrows(InvalidRoleException.class, ()->{this.sp.updateUserRights(1, null);});
        /*Invalid 2*/
        assertThrows(InvalidRoleException.class, ()->{this.sp.updateUserRights(1,"");});
        /*Invalid 3*/
        assertThrows(InvalidRoleException.class, ()->{this.sp.updateUserRights(1,"      ");});
        /*Invalid 4*/
        assertThrows(InvalidRoleException.class, ()->{this.sp.updateUserRights(1,"ShopManager      ");});
        /*Invalid 5*/
        assertThrows(InvalidRoleException.class, ()->{this.sp.updateUserRights(1,"Shop   Manager      ");});
        /*Invalid 6*/
        assertThrows(InvalidRoleException.class, ()->{this.sp.updateUserRights(1,"ciao");});

    }

    @Test
    public void testUpdateUserRightsNoOneLoggedWrongRole() {
        /*Invalid 1= No one still logged, no user created*/
        assertThrows(UnauthorizedException.class, ()->{this.sp.updateUserRights(2, "Administrator");});

        /*Invalid 2 = user created but still not logged*/
        try {
            int id = sp.createUser("giulia", "ciao", "ShopManager");
            assertEquals(1, id);
            int id2 = sp.createUser("giulia2", "ciao2", "Cashier");
            assertEquals(2, id2);


            // Still no logged
            assertThrows(UnauthorizedException.class, ()->{this.sp.updateUserRights(2, "Administrator");});
            //logging in
            assertEquals(1 ,(int)this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("SHOPMANAGER"));
            assertFalse(this.sp.checkUserRole("ADMINISTRATOR"));
            /*Invalid 2 = user created, logged but wrong role*/

            //Logged in a user with role ShopManager, not administrator
            assertThrows(UnauthorizedException.class, ()->{this.sp.updateUserRights(2, "Administrator");});
            assertTrue(sp.logout());

            //logging in a user with role Cashier not administrator
            assertEquals(2 ,(int)this.sp.login("giulia2", "ciao2").getId());
            assertTrue(this.sp.checkUserRole("CASHIER"));
            assertFalse(this.sp.checkUserRole("ADMINISTRATOR"));
            assertThrows(UnauthorizedException.class, ()->{this.sp.updateUserRights(1, "Administrator");});





        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException e) {
            fail(e.getMessage());
        }


    }

    @Test
    public void testUpdateUserRightsNotExisting() {

        try {

            //Invalid1: user not existing

            int id = sp.createUser("giulia", "ciao", "Administrator");
            assertEquals(1, id);
            int id2 = sp.createUser("giulia2", "ciao2", "Cashier");
            assertEquals(2, id2);


            //loggging in
            assertEquals(1, (int) this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ADMINISTRATOR"));

            assertFalse(this.sp.updateUserRights(3, "Administrator"));


        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | InvalidUserIdException | UnauthorizedException e) {
            fail(e.getMessage());
        }


    }

    @Test
    public void testUpdateUserRightsValid() {

        try {


            int id = sp.createUser("giulia", "ciao", "Administrator");
            assertEquals(1, id);
            int id2 = sp.createUser("giulia2", "ciao2", "ShopManager");
            assertEquals(2, id2);

            //loggging in
            assertEquals(1, (int) this.sp.login("giulia", "ciao").getId());
            assertTrue(this.sp.checkUserRole("ADMINISTRATOR"));

            //Check the user role before update
            assertEquals("ShopManager", sp.getUser(2).getRole());
            assertTrue(this.sp.updateUserRights(2, "Administrator"));
            assertEquals("Administrator", sp.getUser(2).getRole());


        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | UnauthorizedException | InvalidUserIdException e) {
            fail(e.getMessage());
        }
    }
















}
