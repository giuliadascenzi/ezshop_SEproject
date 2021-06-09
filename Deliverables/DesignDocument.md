# Design Document 

Authors: Giulia D'Ascenzi, Francesco Di Franco, Antonino Monti

Date: 09/06/2021

Version: 3.0 


# Contents

- [High level design](#package-diagram)
- [Low level design](#class-diagram)
- [Verification traceability matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)

# Instructions

The design must satisfy the Official Requirements document, notably functional and non functional requirements

# High level design 

Since the project is very simple and small, the application is composed of only five packages. The application follows the MVC pattern: the View is implemented in the view package (that contains the GUI), while the Model and Control are implemented in the data, the classes and the exceptions package. The classes package contains the implementation of the classes interfaces defined in the data package.

![package diagram](Diagrams/Design/packageDiagram.png)


# Low level design

EZShopInterface file (contained in the data package):

![data package diagram](Diagrams/Design/dataPackage.png)

This interface is implemented by the Shop class in the data package, the structure of which is reported below:

![model class diagram](Diagrams/Design/classDiagram.png)

All classes except for EZFileReader are persistent.

# Verification traceability matrix

| Class \ FR        | FR1  | FR3  | FR4  | FR5  | FR6  | FR7  | FR8  |
| ----------------- | :--: | :--: | :--: | :--: | :--: | :--: | :--: |
| Shop              |  X   |  X   |  X   |  X   |  X   |  X   |  X   |
| User              |  X   |      |      |      |      |      |      |
| BalanceOperation  |      |      |  X   |      |  X   |      |  X   |
| ReturnTransaction |      |      |      |      |  X   |      |      |
| Order             |      |      |  X   |      |      |      |      |
| SaleTransaction   |      |      |      |      |  X   |      |      |
| ProductType       |      |  X   |  X   |      |      |      |      |
| TicketEntry       |      |      |      |      |  X   |      |      |
| Customer          |      |      |      |  X   |      |      |      |
| EZCustomerCard    |      |      |      |  X   |      |      |      |
| EZDatabase        |  X   |  X   |  X   |  X   |  X   |  X   |  X   |
| EZFileReader      |      |      |      |      |      |  X   |      |

# Verification sequence diagrams

#### Scenario 1.1: Create product type X

![sequence 1.1](Diagrams/Design/1-1-CreateProductType.png)

#### Scenario 2.1: Create user and define rights

![sequence 2.1](Diagrams/Design/2-1-CreateNewUser.png)

#### Scenario 3.3: Record order of product type X arrival

![sequence 3.3](Diagrams/Design/3-3-RecordOrderArrival.png)

#### Scenario 4.1: Create customer record

![sequence 4.1](Diagrams/Design/4-1-CreateNewCustomer.png)

#### Scenario 5.1: Login

![sequence 5.1](Diagrams/Design/5-1-Login.png)

#### Scenario 5.2: Logout

![sequence 5.2](Diagrams/Design/5-2-Logout.png)

#### Scenario 6.4: Sale of product type X with loyalty card update

![sequence 6.4](Diagrams/Design/6-4-SaleTransactionCompleted-LoyaltyAccount.png)

#### Scenario 7.1: Manage payment by valid credit card

![sequence 7.1](Diagrams/Design/7-1-ManageSale-CreditCard.png)

#### Scenario 8.1: Return transaction of product type X completed, credit card

![sequence 8.1](Diagrams/Design/8-1-ReturnTransactionCompleted-CreditCard.png)

#### Scenario 9.1: List credits and debits

![sequence 9.1](Diagrams/Design/9-1-ListCreditsAndDebits.png)

#### Scenario 10.1: Return payment by credit card

![sequence 10.1](Diagrams/Design/10-1-ReturnPayment-CreditCard.png)

