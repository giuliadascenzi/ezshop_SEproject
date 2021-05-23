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
    @Test
    public void Test_updateProduct(){
        Integer id;
        try {
            //No User Logged
            assertThrows(UnauthorizedException.class, ()->{sp.updateProduct(1,"Granarolo","6291041500213",1.5,"milk");});
            sp.createUser("fridanco1","pass","Cashier"); //Created,but no logged
            assertThrows(UnauthorizedException.class, ()->{sp.updateProduct(1,"Granarolo","6291041500213",1.5,"milk");});
            sp.login("fridanco1","pass");//Wrong Authorization
            assertThrows(UnauthorizedException.class, ()->{sp.updateProduct(1,"Granarolo","6291041500213",1.5,"milk");});
            sp.logout();
            sp.createUser("fridanco","pass","Administrator");
            sp.login("fridanco","pass");
        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException e){
            fail(e.getMessage());
        }
        //Test-ProductIdException
        assertThrows(InvalidProductIdException.class,()->{sp.updateProduct(null,"Granarolo","6291041500213",1.5,"milk");});
        assertThrows(InvalidProductIdException.class,()->{sp.updateProduct(-2,"Granarolo","6291041500213",1.5,"milk");});
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
            assertFalse(sp.updateProduct(20,"Granarolo","6291041500213",1.5,"milk")); //id doesn't exist
            id=sp.createProductType("Granarolo","6291041500213",1.5,"milk");
            System.out.println(id);
            System.out.println(sp.checkBarCodeValidity("3391041500213"));
            assertTrue(sp.updateProduct(id,"Grana","3391041500213",1.3,"mqk")); //It exists
        } catch (InvalidProductIdException | InvalidProductDescriptionException | InvalidProductCodeException | InvalidPricePerUnitException | UnauthorizedException e) {
            System.out.println(e);
            fail(e.getMessage());
        }
    }
    public Integer Before_updateQuantity(){
        Integer id;
        try {
            sp.createUser("fridanco","pass","Administrator");
            sp.login("fridanco","pass");
            return id= sp.createProductType("as","6291041500213",1.5,"milk");
        } catch (InvalidUsernameException | InvalidPasswordException | InvalidRoleException | InvalidProductDescriptionException | InvalidProductCodeException | InvalidPricePerUnitException | UnauthorizedException e){
            fail(e.getMessage());
        }
        return -1;
    }
    @Test
    public void Test_updateQuantity(){
        assertThrows(UnauthorizedException.class, ()->{sp.updateQuantity(1,3);});
        Integer id = Before_updateQuantity();
        //Test-ProductIdException
        assertThrows(InvalidProductIdException.class,()->{sp.updateQuantity(null,3);});
        assertThrows(InvalidProductIdException.class,()->{sp.updateQuantity(-2,3);});
        //Test-toBeAddedreturn false
        try {
            assertFalse(sp.updateQuantity(20,3));//it doesn't exist
            assertFalse(sp.updateQuantity(id,-3)); // 0-3 -> False
            assertFalse(sp.updateQuantity(id,4));
            sp.updatePosition(id,"2-C-4");
            assertTrue(sp.updateQuantity(id,4));
            assertEquals(4,(int)sp.getProductTypeByBarCode("6291041500213").getQuantity());
        } catch (InvalidProductIdException | UnauthorizedException | InvalidLocationException | InvalidProductCodeException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
    public void Test_updatePosition(){
    }

}

