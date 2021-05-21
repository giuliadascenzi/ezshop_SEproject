package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.*;
import it.polito.ezshop.data.classes.*;
import it.polito.ezshop.exceptions.*;

import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class EZShopUserMethodsTest {

    EZShop sp = new EZShop();

    @Before
    public void resetShop()
    {    sp.resetLocal();
          //sp.reset();

    }



    /****************** TESTS USER********************************/
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

        } catch (InvalidUsernameException e) {
            fail(e.getMessage());
        } catch (InvalidPasswordException e) {
            fail(e.getMessage());
        } catch (InvalidRoleException e) {
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
    public void testLoginInvalidUser()
    {
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
            assertNull( this.sp.login("giulia2", "ciao"));
            /*Invalid User wrong password*/
            assertNull( this.sp.login("giulia", "ciao2"));
        } catch (InvalidUsernameException e) {
            fail(e.getMessage());
        } catch (InvalidPasswordException e) {
            fail(e.getMessage());
        }



    }


}
