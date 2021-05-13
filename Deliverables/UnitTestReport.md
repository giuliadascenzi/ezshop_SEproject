# Unit Testing Documentation

Authors: Giulia D'Ascenzi, Francesco Di Franco, Antonino Monti

Date:

Version: 1.0

# Contents

- [Black Box Unit Tests](#black-box-unit-tests)


- [White Box Unit Tests](#white-box-unit-tests)


# Black Box Unit Tests

    <Define here criteria, predicates and the combination of predicates for each function of each class.
    Define test cases to cover all equivalence classes and boundary conditions.
    In the table, report the description of the black box test case and (traceability) the correspondence with the JUnit test case writing the 
    class and method name that contains the test case>
    <JUnit test classes must be in src/test/java/it/polito/ezshop   You find here, and you can use,  class TestEzShops.java that is executed  
    to start tests
    >

 ### **Class *EZShop* - method *checkBarCodeValidity***

**Criteria for method *checkBarCodeValidity*:**

 - Validity of barCode

**Predicates for method *checkBarCodeValidity*:**

| Criteria | Predicate |
| -------- | --------- |
| Validity of barCode         | valid           |
| '' | invalid/NULL |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
| Validity of barCode       | valid, invalid, NULL |

**Combination of predicates**:


| Validity of barCode | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|
|Invalid/NULL|Invalid|checkBarCodeValidity("42")<br/> -> false<br/>checkBarCodeValidity("6291041500218")<br/> -> false<br/>checkBarCodeValidity("62910415002187326548")<br/> -> false<br/>checkBarCodeValidity(null)<br/> -> false|BB_UnitTesting.test_InvalidBarCode|
|Valid|Valid|checkBarCodeValidity("6291041500213")<br/> -> true|BB_UnitTesting.test_ValidBarCode|

 ### **Class *EZShop* - method *checkCreditCardValidity***

**Criteria for method *checkCreditCardValidity*:**

 - Validity of barCode

**Predicates for method *checkCreditCardValidity*:**

| Criteria               | Predicate |
| ---------------------- | --------- |
| Validity of creditCard | valid     |
| ''                     | invalid/NULL   |

**Boundaries**:

| Criteria               | Boundary values |
| ---------------------- | --------------- |
| Validity of creditCard | valid, invalid, NULL  |

**Combination of predicates**:


| Validity of creditCard | Valid / Invalid | Description of the test case                         | JUnit test case                       |
| ---------------------- | --------------- | ---------------------------------------------------- | ------------------------------------- |
| Invalid/NULL                | Invalid         | checkCreditCardValidity("79927398718")<br/> -> false<br/>checkCreditCardValidity("cane")<br/> -> false<br/>checkCreditCardValidity(null)<br/> -> false | BB_UnitTesting.test_InvalidCreditCard |
| Valid                  | Valid           | checkBarCodeValidity("79927398713")<br/> -> true     | BB_UnitTesting.test_ValidCreditCard   |


# White Box Unit Tests

### Test cases definition

    <JUnit test classes must be in src/test/java/it/polito/ezshop>
    <Report here all the created JUnit test cases, and the units/classes under test >
    <For traceability write the class and method name that contains the test case>


| Unit name | JUnit test case |
| -- | -- |
| Method - EZShop.checkBarCodeValidity | WB_UnitTesting.test_barCodeCheck |
| Method - EZShop.checkCreditCardValidity | WB_UnitTesting.test_creditCardCheck |

### Code coverage report

    <Add here the screenshot report of the statement and branch coverage obtained using
    the Eclemma tool. >

![wb_unit_coverage](TestingPNGs/wb_unit_coverage.png)

### Loop coverage analysis

    <Identify significant loops in the units and reports the test cases
    developed to cover zero, one or multiple iterations >

|Unit name | Loop rows | Number of iterations | JUnit test case |
|---|---|---|---|
|Method - EZShop.checkBarCodeValidity|6|0|WB_UnitTesting.test_barCodeCheck|
|Method - EZShop.checkBarCodeValidity|6|22+|WB_UnitTesting.test_barCodeCheck|
|Method - EZShop.checkCreditCardValidity|7|0|WB_UnitTesting.test_creditCardCheck|
|Method - EZShop.checkCreditCardValidity|7|1+|WB_UnitTesting.test_creditCardCheck|

