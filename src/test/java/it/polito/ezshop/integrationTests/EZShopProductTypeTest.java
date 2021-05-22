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

    public void UserLoginAdmin(){
        try {
            sp.createUser("fridanco","pass","Administrator");
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        } catch (InvalidPasswordException e) {
            e.printStackTrace();
        } catch (InvalidRoleException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void Test_createProductType(){
        //Test1
        assertThrows(UnauthorizedException.class, ()->{sp.createProductType("Granarolo","6291041500213",1.5,"milk");});
        UserLoginAdmin();


        //Test-Description
        assertThrows(InvalidProductDescriptionException.class, ()->{sp.createProductType(null,"6291041500213",1.5,"milk");});
        assertThrows(InvalidProductDescriptionException.class, ()->{sp.createProductType("","6291041500213",1.5,"milk");});

        //Test-ProductCode
        assertThrows(InvalidProductCodeException.class, ()->{sp.createProductType("Granarolo",null,1.5,"milk");});
        assertThrows(InvalidProductCodeException.class, ()->{sp.createProductType("Granarolo","",1.5,"milk");});
        assertThrows(InvalidProductCodeException.class, ()->{sp.createProductType("Granarolo","I'm not a number",1.5,"milk");});
        assertThrows(InvalidProductCodeException.class, ()->{sp.createProductType("Granarolo","6291041500218",1.5,"milk");});
        //Test-



    }
}
