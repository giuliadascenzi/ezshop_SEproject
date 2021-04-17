# Requirements Document 

Authors:

Date:

Version:

# Contents

- [Essential description](#essential-description)
- [Stakeholders](#stakeholders)
- [Context Diagram and interfaces](#context-diagram-and-interfaces)
	+ [Context Diagram](#context-diagram)
	+ [Interfaces](#interfaces) 
	
- [Stories and personas](#stories-and-personas)
- [Functional and non functional requirements](#functional-and-non-functional-requirements)
	+ [Functional Requirements](#functional-requirements)
	+ [Non functional requirements](#non-functional-requirements)
- [Use case diagram and use cases](#use-case-diagram-and-use-cases)
	+ [Use case diagram](#use-case-diagram)
	+ [Use cases](#use-cases)
    	+ [Relevant scenarios](#relevant-scenarios)
- [Glossary](#glossary)
- [System design](#system-design)
- [Deployment diagram](#deployment-diagram)

# Essential description

Small shops require a simple application to support the owner or manager. A small shop (ex a food shop) occupies 50-200 square meters, sells 500-2000 different item types, has one or a few cash registers 
EZShop is a software application to:

* manage sales
* manage inventory
* manage customers
* support accounting


# Stakeholders


| Stakeholder name  | Description |
| ----------------- |:-----------:|
|   Manager   | Manager of the shop - buys the system |
| Cashier | Cashier who works at the shop; manages cash registers and customer purchases |
| Customer | Regular or occasional customer; does not have a fidelity account |
| Loyal customer | Regular customer who has a fidelity account and collects points for discounts and offers |
| Inventory manager | Shop employee who manages the inventory |
| Supplier | Organization/company contacted by the owner to order supplies |
| Accountant | Employee who manages accounting |
| Payment system | System with which EZShop interacts to process payments |
| Product | Generic product sold by the shop |
| IT Administrator | Employee tasked with maintaining and operating the IT part of the system |
| Fidelity card | Card granted to fidelity customers, identified by a bar code |
| Anonymous user | Employee not logged in to the system |

# Context Diagram and interfaces

## Context Diagram
[context diagram]

## Interfaces

*- add interaction through employee card? -*

| Actor | Logical Interface | Physical Interface  |
| ------------- |:-------------:| -----:|
|   Manager   | Application GUI | Device |
| Cashier | Application GUI | Cash register |
| Inventory manager | Application GUI | Device |
| Accountant | Application GUI | Device |
| Product | Read the bar code | Bar code reader |
| Payment system | Payment APIs | POS, Device |
| IT Administrator | Application GUI | Device |
| Customer | POS GUI | POS |
| Fidelity card | Read the card | Bar code reader |
| Anonymous User | Application GUI | Device, Cash register |

For reference:

- Payment APIs: https://rapidapi.com/serg.osipchuk/api/SquareECommerce?endpoint=apiendpoint_c2456e40-f966-11e7-b2fb-5fca152468f2createCustomerCard 

# Stories and personas


Alice, manager of a small shop with 4 employees; 2 cashiers, inventory manager, and an accountant. 
Alice and her employees us commercial notebooks to record and manage to work operations of the shop without any technological support. 
As the shop is getting more customers, and the number of orders is increasing, she decided to digitalize the management of the shop by getting a system to manage sales, inventory, customers, and accounting with the same number of employees.
## User Stories:
## Manager

As a manager :
-	She wants to monitor the working hours of the employees, so that he can observe the work process in the shop. 
- 	She wants to monitor the financial situation by watching the cash flow anytime, so that she can schedule the future expenses.
- 	She wants to monitor sales by watching the number of purchases and customers who frequented to the shop, so that she can decide new offers and special discount to loyal customers. 
- She wants to get daily updates about the products in the inventory, so that she can order the sold-out items. 
## Cashier
As a cashier: 
-	He needs the prices of all the items to be recorded in the system and to be easy to retrieve, so that he can manage the sales easily and effectively.
- 	He needs a payment system with credit card system, so that he can receive money from customers with different payment method.
- 	He wants the calculations of a transaction to be accurate and the printing of receipt to be fast, so that the sales process will be precise and takes less time from the customers.
## The Inventory Manager 
As an inventory manager: 
-	He needs to track the availability of each item in the shop, to be able to report the shortage of any item to the manager in order to order new quantities of it.
- He needs to be able to insert new products to the system easily or update the current items in the shop, so that the records will be always up-to-date.
## The Accountant
As an accountant: 
-	He wants to monitor the transactions done by the cashiers time by time, so the he can include them in his financial reports to be delivered to the manager. 
- 	He wants to record the expenses of the shop and other revenues to the financial records, so he can report the financial situation to the manager. 


# Functional and non functional requirements

## Functional Requirements

| ID        | Description  |
| ------------- |:--------------|
|  FR1     | Manage sales |
| - FR1.1 | - Create transaction |
| - - FR1.1.1 | - - Scan products |
| - FR1.2 | - Make payment |
| - - FR1.2.1 | - - Contact payment system |
| - - FR1.2.2 | - - Print receipt |
| - FR1.3 | - Handle refund |
| ... | --- |
|  FR2     | Manage inventory |
| - FR2.1 | - View available supplies |
| - - FR2.1.1 | - - Search and filter products |
| - FR2.2 | - Create inventory report |
| - FR2.3 | - Update inventory |
| - - FR2.3.1 | - - Add new product |
| - - FR2.3.1 | - - Add new supply |
| - FR2.4 | - Filter products |
| ... | --- |
| FR3 | Manage customers |
| - FR3.1 | - Create fidelity account |
| - FR3.2 | - Modify account |
| - FR3.3 | - Delete account |
| - FR3.4 | - Update account points |
| - FR3.5 | - Scan fidelity card |
| - FR3.6 | - Change points value |
| ... | --- |
| FR4 | Manage accounting |
| - FR4.1 | - Compute current balance |
| - FR4.2 | - Create accounting report |
| - FR4.3 | - Add expense |
| - FR4.4 | - Add revenue |
| ... | --- |
| FR5 | Security management |
| - FR5.1 | - Log in |
| - FR5.2 | - Log out |
| - FR5.3 | - View log history |
| - FR5.4 | - Add employee account |
| - - FR5.4.1 | - - Give permissions |
| - FR5.5 | - Modify employee account |
| - - FR5.5.1 | - - Modify permissions |
| - FR5.6 | - Delete employee account |

### Access rights, actor vs. function

| Function    | Manager | Accountant | Cashier | Inventory manager | IT Administrator | Anonymous user |
| ----------- | ------- | ---------- | ------- | ----------------- | ---------------- | -------------- |
| FR1         | Yes     | No         | Yes     | No                | No               | No             |
| FR2         | Yes     | No         | No      | Yes               | No               | No             |
| FR3.1 - 3.5 | Yes     | No         | Yes     | No                | Yes              | No             |
| FR3.6       | Yes     | No         | No      | No                | No               | No             |
| FR4         | Yes     | Yes        | No      | No                | No               | No             |
| FR5.1       | No      | No         | No      | No                | No               | Yes            |
| FR5.2       | Yes     | Yes        | Yes     | Yes               | Yes              | No             |
| FR5.3 - 5.6 | Yes     | No         | No      | No                | Yes              | No             |

In short:

- The Accountant can access all functions regarding FR4;
- The Cashier can access all functions regarding FR1 and FR3 except FR3.6;
- The Inventory Manager can access all functions regarding FR2;
- The IT Administrator can access all functions regarding FR3 (except FR3.6) and FR5;
- The Manager can access any function;
- An anonymous user cannot access any function of the system except FR5.1 (Log in); any logged-in employee can log out of the system (FR5.2).

## Non Functional Requirements

| ID        | Type (efficiency, reliability, ..)           | Description  | Refers to |
| ------------- |:-------------:| :-----:| -----:|
|  NFR1     | Security | Only authenticated employees may interact with the system | All FR |
|  NFR2     | Efficiency | All functions should execute in <0.5s | All FR |
|  NFR3     | Privacy | The system will never save credit card information | All FR |
| NFR4 | Availability | The system should be available at least 99% of the time | All FR |
| NFR5 | Usability | Any employee should be able to learn how to use the system in about 15 minutes | All FR |


# Use case diagram and use cases

## Use case diagram

[use case diagrams]

### Use case 1, UC1 - Create transaction

| Actors Involved  |                      Cashier                       |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                              -                               |
| Post condition   |                              Transaction (purchase) T has been added, inventory has been updated, fidelity account A is updated if it exists                               |
| Nominal Scenario | 1. The cashier scans the products with the bar code reader and adds them to a purchase list and eventually modifies each product's quantity;</br> 2. the system computes the total amount and applies eventual discounts; </br> 3. the cashier then proceeds to process the payment as computed in UC2; </br> 4. the transaction is added to the system, the system prints the receipt, the inventory is updated |
| Variant |  If the customer has a fidelity account, they can decide to convert some points in euros to have a discount; after the payment the account's points are updated as computed in UC11, regardless of whether points were spent or not |
| Variant | If the payment is rejected (e.g. the payment system doesn't respond or rejects the payment, the customer doesn't have enough money, etc.) the transaction is rolled back |

#### Scenario 1.1

| Scenario 1.1 | The customer decides to use points to get a discount |
| -------------- | :----------------------------------------------------------: |
| Precondition   | - |
| Post condition | Transaction (purchase) T has been added, inventory has been updated, fidelity account A is updated |
| Step#          |                         Description                          |
| 1 | The cashier scans the products with the bar code reader and adds them to a purchase list and eventually modifies each product's quantity |
| 2 | The cashier inputs the amount of points the customer wants to convert |
| 3 | The system computes the total amount and applies the discount |
| 4 | The cashier proceeds to process the payment as computed in UC2 |
| 5 | The transaction is added to the system, the system prints the receipt, the inventory is updated, the customer's fidelity account is updated |

### Use case 2, UC2 - Make payment

| Actors Involved  |                      Cashier, Payment system, Customer                       |
| ---------------- | :----------------------------------------------------------: |
| Precondition     | A pending transaction has been created |
| Post condition   | The payment has been processed |
| Nominal Scenario | The cashier inputs the payment method chosen by the customer and processes the payment |
| Variant | The cashier inputs "cash" as the payment method |
| Variant | The cashier inputs "credit card" as the payment method |
| Variant | If the amount of euros to pay is 0, the use case isn't executed |

#### Scenario 2.1

| Scenario 2.1   | The customer decides to pay with cash |
| -------------- | :----------------------------------------------------------: |
| Precondition   | A pending transaction has been created |
| Post condition | The payment has been processed |
| Step#          |                         Description                          |
| 1 | The cashier inputs "cash" as the payment method |
| 2 | The cashier inputs the amount paid by the customer |
| 3 | The system automatically computes the change |
| 4 | The cashier confirms the payment |

#### Scenario 2.2

| Scenario 2.2   | The customer decides to pay with a credit card |
| -------------- | :----------------------------------------------------------: |
| Precondition   | A pending transaction has been created |
| Post condition | The payment has been processed |
| Step#          |                         Description                          |
| 1 | The cashier inputs "credit card" as the payment method |
| 2 | The cashier inserts the customer's credit card in the POS |
| 3 | The customer inputs the card's PIN code |
| 4 | The system interacts with the payment system to process the payment |
| 5 | The payment system responds with the result of the payment |

### Use case 3, UC3 - Handle refund

| Actors Involved  |                      Cashier                       |
| ---------------- | :----------------------------------------------------------: |
| Precondition     | Purchase P exists and hasn't been fully refunded |
| Post condition   | Refund R is added  |
| Nominal Scenario | The cashier selects "Refund"; the cashier searches for the transaction by either scanning the receipt or writing the transaction code written on the receipt; the cashier selects the products the customer wants to have refunded along with the respective quantities; the cashier registers the refund |
| Variants         | - |

### Use case 4, UC4 - Filter products

| Actors Involved  |                      Inventory manager                       |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                              -                               |
| Post condition   |                              -                               |
| Nominal Scenario | The inventory manager filters the list of products in the inventory by inserting some filtering and sorting parameters |
| Variants         |         restricted to certain categories of products         |
| Variants         |         restricted to a specific product ID or name          |
| Variants         | restricted to products containing some keywords in their description |
| Variants         |             restricted to a certain price range              |
| Variants         |        restricted to a certain expiration date range         |
| Variants         |                     sorted by date added                     |
| Variants         |          sorted by expiration date (if applicable)           |
| Variants         |                       sorted by price                        |

### Use case 5, UC5 - Create inventory report

| Actors Involved  |                      Inventory manager                       |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                              -                               |
| Post condition   |                              -                               |
| Nominal Scenario | The inventory manager filters and sorts transactions as computed in UC4; the inventory manager then creates a PDF containing a sales report, containing the generated list of products |
| Variants         |                              -                               |

### Use case 6, UC6 - Add new supply

| Actors Involved  |                  Inventory manager                  |
| ---------------- | :-------------------------------------------------: |
| Precondition     |                          -                          |
| Post condition   | A new supply for product P has been added |
| Nominal Scenario | The inventory manager scans the product's bar code to find the product in the inventory, and then inserts all information regarding the new supplies (e.g. quantity, expiration date, etc.) |
| Variants         |                          -                          |

### Use case 7, UC7 - Add new product

| Actors Involved  |                  Inventory manager                  |
| ---------------- | :-------------------------------------------------: |
| Precondition     |                          -                          |
| Post condition   | A new product P has been added to the inventory |
| Nominal Scenario | The inventory manager selects "add new product" from the inventory screen; the inventory manager scans the product's bar code and then inputs all information regarding the product |
| Variants         |                          -                          |

### Use case 8, UC8 - Create fidelity account

| Actors Involved  |                   Cashier/IT Administrator                   |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |              Fidelity account A does not exist               |
| Post condition   |           Fidelity account A exists; A.points = 0            |
| Nominal Scenario | The actor creates a new fidelity account for a customer who requested it and populates its fields and scans a new fidelity card to be associated with the new account |
| Variants         | A customer can have only one fidelity account, this is checked through the email (one email per account) provided by the customer |

### Use case 9, UC9 - Modify fidelity account

| Actors Involved  |                   Cashier/IT Administrator                   |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                  Fidelity account A exists                   |
| Post condition   |             Fidelity account A has been modified             |
| Nominal Scenario | The actor selects the fidelity account to modify as computed in UC12; the actor modifies the selected fidelity account and eventually scans a new fidelity card to be associated with the fidelity account |
| Variants         |                              -                               |

### Use case 10, UC10 - Delete fidelity account

| Actors Involved  |                   Cashier/IT Administrator                   |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                  Fidelity account A exists                   |
| Post condition   |             Fidelity account A has been deleted              |
| Nominal Scenario | The actor selects the fidelity account to delete as computed in UC12; the actor confirms the choice when prompted by the system |
| Variants         |                              -                               |

### Use case 11, UC11 - Update account points

| Actors Involved  |                   Cashier/IT Administrator                   |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                  Fidelity account A exists                   |
| Post condition   |                  A.points has been modified                  |
| Nominal Scenario | The actor searches for the customer's fidelity account as computed in UC12; the actor modifies the amount of points associated with account A |
| Variant          | The actor subtracts a certain amount of points from the account (e.g. after making a purchase with points) |
| Variant          | The actor adds a certain amount of points to the account (e.g. after a purchase) |

### Use case 12, UC12 - Search fidelity account

| Actors Involved  |                   Cashier/IT Administrator                   |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                              -                               |
| Post condition   |                              -                               |
| Nominal Scenario | The actor searches for the fidelity account associated with the email or fidelity card to display its information |
| Variant          | The actor scans the fidelity card with a bar code reader to find the account |
| Variant          |   The actor manually inserts the email to find the account   |

### Use case 13, UC13 - Filter transactions

| Actors Involved  |                          Accountant                          |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                              -                               |
| Post condition   |                              -                               |
| Nominal Scenario | The accountant filters the list of transaction by inserting some filtering and sorting parameters |
| Variants         |            restricted to a certain period of time            |
| Variants         | restricted to a certain category of transactions (e.g. revenues and expenses) |
| Variants         | restricted to transactions with a payment method (i.e. cash or credit card) |
| Variants         | restricted to certain employees who handled/inserted the transaction |
| Variants         |                        sorted by date                        |
| Variants         |                sorted by amount spent/earned                 |

### Use case 14, UC14 - Create sales report

| Actors Involved  |                          Accountant                          |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                              -                               |
| Post condition   |                              -                               |
| Nominal Scenario | The accountant filters and sorts transactions as computed in UC13; the accountant then creates a PDF containing a sales report, containing the list of transactions and the overall balance |
| Variants         |                              -                               |

### Use case 15, UC15 - Add expense

| Actors Involved  |                          Accountant                          |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                              -                               |
| Post condition   |                  An expense has been added                   |
| Nominal Scenario | The accountant clicks on the "Add expense" button; the accountant inserts the date and the amount of euros spent |
| Variants         |                              -                               |

### Use case 16, UC16 - Add revenue

| Actors Involved  |                          Accountant                          |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                              -                               |
| Post condition   |                   A revenue has been added                   |
| Nominal Scenario | The accountant clicks on the "Add revenue" button; the accountant inserts the date and the amount of euros added |
| Variants         |                              -                               |

### Use case 17, UC17 - Log in

| Actors Involved  |                        Anonymous user                        |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                              -                               |
| Post condition   |    The employee is logged in to their respective account     |
| Nominal Scenario | An employee logs in to the system by scanning the employee card with a bar code reader |
| Variants         | The manager can log in by inserting the user ID and the password associated with it |

### Use case 18, UC18 - Log out

| Actors Involved  |    Cashier/Inventory manager/Accountant/IT Administrator     |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                              -                               |
| Post condition   | The employee is logged out of their respective account and is considered an anonymous user |
| Nominal Scenario | The employee logs out of their account by clicking the "Log Out" button in their home page |
| Variants         |                              -                               |

### Use case 19, UC19 - View log history

| Actors Involved  |                       IT Administrator                       |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                              -                               |
| Post condition   |                              -                               |
| Nominal Scenario | The administrator clicks on the "View log history" button; the administrator inputs a period of time for which they want to know the log history; the application displays the log history of employees to see who has logged in or out and at what time for that time period |
| Variants         |                              -                               |

### Use case 20, UC20 - Add employee account

| Actors Involved  |                       IT Administrator                       |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                              -                               |
| Post condition   | Employee account E has been created; E has certain permissions; E has an employee card associated with it |
| Nominal Scenario | The Administrator creates a new employee account by inserting a new user code, their name and surname and the permissions to be granted to the new user; the administrator scans an unused employee card to associate it with the new account |
| Variants         |                              -                               |

### Use case 21, UC21 - Modify employee account

| Actors Involved  |                       IT Administrator                       |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                  Employee account E exists                   |
| Post condition   | The data associated with E, which can include permissions, is modified |
| Nominal Scenario | The administrator selects the employee account to modify; the administrator modifies the selected employee account's information, and eventually scanning a new employee card to be associated with the account |
| Variants         |                              -                               |

### Use case 22, UC22 - Delete employee account

| Actors Involved  |                       IT Administrator                       |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                  Employee account E exists                   |
| Post condition   |                 E is deleted from the system                 |
| Nominal Scenario | The administrator selects the employee account to delete; the administrator confirms the choice when prompted by the system |
| Variants         |                              -                               |

### Use case 23, UC23 - Change shop settings

| Actors Involved  |                           Manager                            |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                              -                               |
| Post condition   |                 Shop settings have been modified                 |
| Nominal Scenario | The manager changes the shop's settings (name, phone number, points value and conversion rate, etc.) |
| Variants         | - |



# Glossary

[glossary photo]

Notes:
- Fidelity points: By having a fidelity account the customer is able to get points for every purchase made in proportion to the total amount paid in the purchase. (The value in euros of a point is choosen by the shop manager.) The points accumulated can be used by the customer to get a discount (i.e: the customer has 4000 points. For that shop 50€-> 1 point. The customer wants to use 500 of his/her points to get a 10€ discount. The customer now has 3500 points remaining on his/her account)
- Product: In the inventory a product record is a generical description of a product sold by the shop.
- Supply: Group of products bought by the shop from a supplier. It is added to the inventory by specifying what product it is (i.e. by scanning the product's bar code or inserting its code), but also adding more informations such as expiration date (if exists), selling price, price paid, discount (if any) and quantity.

# System Design

[system diagram photo]

# Deployment Diagram

[deployment diagram photo]
