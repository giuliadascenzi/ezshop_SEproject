package it.polito.ezshop.integrationTests;

import it.polito.ezshop.data.EZShop;
import it.polito.ezshop.exceptions.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EZShopProductTypeTest {

    EZShop sp = new EZShop();

    @Before
    public void resetShop() {
        sp.reset();

    }



    @Test
    public void Test_createProductType(){
        Integer id;
        //TestAuthorization
        try {
            //No User Logged
            assertThrows(UnauthorizedException.class, ()->{sp.createProductType("Granarolo","6291041500213",1.5,"milk");});
            sp.createUser("fridanco1","pass","Cashier"); //Created,but no logged
            assertThrows(UnauthorizedException.class, ()->{sp.createProductType("Granarolo","6291041500213",1.5,"milk");});
            sp.login("fridanco1","pass");//Wrong Authorization
            assertThrows(UnauthorizedException.class, ()->{sp.createProductType("Granarolo","6291041500213",1.5,"milk");});
            sp.logout();
            sp.createUser("fridanco","pass","Administrator");
            sp.login("fridanco","pass");
        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException e){
            fail(e.getMessage());
        }
        //Test-Description
        assertThrows(InvalidProductDescriptionException.class, ()->{sp.createProductType(null,"6291041500213",1.5,"milk");});
        assertThrows(InvalidProductDescriptionException.class, ()->{sp.createProductType("","6291041500213",1.5,"milk");});

        //Test-ProductCode
        assertThrows(InvalidProductCodeException.class, ()->{sp.createProductType("Granarolo",null,1.5,"milk");});
        assertThrows(InvalidProductCodeException.class, ()->{sp.createProductType("Granarolo","",1.5,"milk");});
        assertThrows(InvalidProductCodeException.class, ()->{sp.createProductType("Granarolo","I'm not a number",1.5,"milk");});
        assertThrows(InvalidProductCodeException.class, ()->{sp.createProductType("Granarolo","6291041500218",1.5,"milk");});
        //Test-PriceperUnit
        assertThrows(InvalidPricePerUnitException.class,()->{sp.createProductType("Granarolo","6291041500213",-0.3,"milk");});
        assertThrows(InvalidPricePerUnitException.class,()->{sp.createProductType("Granarolo","6291041500213",0,"milk");});
        try {
            id=sp.createProductType("Granarolo","6291041500213",1.5,"milk");
            assertTrue(id>0);
            id=sp.createProductType("Granarolo","6291041500213",1.5,"milk");

        } catch (InvalidProductDescriptionException | InvalidProductCodeException | InvalidPricePerUnitException | UnauthorizedException e) {
            fail(e.getMessage());
        }

    }
}
