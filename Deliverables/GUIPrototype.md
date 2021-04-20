# Graphical User Interface Prototype  

Authors: Giulia D'Ascenzi, Francesco Di Franco, Mahmoud Hassan Ali, Antonino Monti

Date: 21/04/2021

Version: 1.0

# Contents

- [Login Page](#login-page)
- [Home Page](#home-page)
- [Security](#security)
	+ [Change shop settings](#change-shop-settings)
	+ [Log history](#log-history)
	+ [New account](#new-account)
	+ [Modify account](#modify-account)

- [Graphical User Interface Prototype](#graphical-user-interface-prototype)
- [Contents](#contents)
- [Login Page](#login-page)
- [Home Page](#home-page)
- [Security](#security)
  - [Change shop settings](#change-shop-settings)
  - [Log history](#log-history)
  - [New account](#new-account)
  - [Modify account](#modify-account)
- [Transaction](#transaction)
  - [New transaction](#new-transaction)
    - [Scan new product](#scan-new-product)
    - [Modify product quantity](#modify-product-quantity)
    - [Manage customer](#manage-customer)
    - [Use points to pay](#use-points-to-pay)
    - [Pay](#pay)
      - [Using credit card](#using-credit-card)
      - [Using cash](#using-cash)
  - [New refund](#new-refund)
- [Inventory](#inventory)
- [Inventory](#inventory-1)
  - [New product](#new-product)
  - [New supply](#new-supply)
- [Accounting](#accounting)
  - [New accounting record](#new-accounting-record)

	+ [New refund](#new-refund) 
- [Inventory](#inventory)
    + [New product](#new-product)
    + [New supply](#new-supply)

- [Accounting](#accounting)
    + [New accounting record](#new_accounting_record)
	

# Login Page

The employee logs in scanning his/her card. The manager instead needs to insert the user and the password for security reasons.

![](GuiPrototypePNGs/Login_page.png)

# Home Page

After logging in this is the page that every user finds.
In the left the user can find five buttons. 
From the top: the first one is to go back to the home page, the second one to manage the transactions of the customers, the third to update the inventory, the fourth to take care of the shop's accounting and the last one to reach the page where the manager and the IT administrator handle all the settings that only them can change.

Depending on the rights that a user has (set by the manager/IT administrator at the creation of the user account) a user can or can not access the page linked by these buttons.

![](GuiPrototypePNGs\Hope_page.png)

# Security

If the user does not have the rights to access this part of the GUI:

![](GuiPrototypePNGs/SecurityUnathorised.png)

Otherwise:

![](GuiPrototypePNGs/Security.png)

The accounts are the employee accounts created by the manager or the IT administrator.


## Change shop settings

Clicking on "change shop settings":

![](GuiPrototypePNGs/SecurityShopSetting.png)

## Log history

Clicking on "Log history":

![](GuiPrototypePNGs/SecurityLoghistory.png)

## New account

Clicking on "New Account":

![](GuiPrototypePNGs/SecurityNewAccount.png)

To create an account all the fields need to be filled in. 
The card Id is the barcode of the employee card that he/she will use to log in.

![](GuiPrototypePNGs/SecurityNewAccountErrir.png)

## Modify account

Clicking on "Modify account":

![](GuiPrototypePNGs/SecurityModifyAccount.png)

# Transaction

If the user does not have the rights to access this part of the GUI:

![](GuiPrototypePNGs/TransUnathorised.png)

Otherwise:

![](GuiPrototypePNGs/Transaction1.png)

The user needs to chose whether to begin a new transaction or a new refund.

## New transaction

Clicking on "New transaction"

![](GuiPrototypePNGs/Transaction2.png)

### Scan new product

Clicking on "New Product"

![](GuiPrototypePNGs/NTrAddProduct.png)

### Modify product quantity

Clicking on the quantity of a record already inserted.

![](GuiPrototypePNGs/NTrQuantityRecord.png)

### Manage customer

Clicking on "Customer"

In case no customer has been inserted yet:
![](GuiPrototypePNGs/NTrCustomer.png)

Scanning the fidelity card of the customer the informations about that customer are shown.
Instead, if the customer does not have a fidelity customer account and wants to create one, the cashier can click on "New customer" and fill in all the informations.

![](GuiPrototypePNGs/NTrSignInCustomer.png)

After a customer has been registered by the cashier, the window opened by clicking on "Customer" gets updated with all the informations about that customer, giving the cashier the ability of modifying those informations, checking the points balance or also deleting the account (if the customer requests so).

![](GuiPrototypePNGs/NTrCustomer_2.png)


### Use points to pay

This feature is usable only if a customer has been registered.
Otherwise:
![](GuiPrototypePNGs/NtrPointsNoCustomer.png)

If a customer has been registered, the customer can choose how many of the points he has, convert in euros in order to pay a part of the purchase in this way. 

![](GuiPrototypePNGs/NtrSignUsePoints.png)

### Pay

When all the products are inserted and the customer had already choose whether to use some points to pay the cashier can click on "End and pay".

![](GuiPrototypePNGs/NtrPayment1.png)

#### Using credit card

If the customer wants to pay with credit card:

![](GuiPrototypePNGs/NtrPayment4.png)

![](GuiPrototypePNGs/NtrPayment5.png)

![](GuiPrototypePNGs/NtrPayment6.png)
In case the transaction is declined, the window closes going back to the transaction page. The transaction stays pending until the payment is completed.

#### Using cash 

![](GuiPrototypePNGs/NtrPayment2.png)

![](GuiPrototypePNGs/NtrPayment3.png)

## New refund

![](GuiPrototypePNGs/Transaction3.png)

The customer needs to give the cashier the purchase receipt of the product he/she wants to refund.
Scanning the barcode of the receipts the system looks for the informations about that transaction.
![](GuiPrototypePNGs/Refund1.png)

In case the system does not find that transaction record:
![](GuiPrototypePNGs/Refund2.png)

If the transaction is found, the cashier can see the informatons about it. For every product bought It is memorised if it has been already refunded or not.
![](GuiPrototypePNGs/Refund4.1.png)


If the product has already been refunded, clicking on it, it happears a window with the date and the total of that past refund.

![](GuiPrototypePNGs/Refund4.png)

The cashier selects the items that the customer wants to refund.

![](GuiPrototypePNGs/Refund3.png)

Clicking on "Refund items selected":

![](GuiPrototypePNGs/Refund5.png)

The cashier gives the customer the money back and the system memorizes the refund transaction.

# Inventory

# Inventory
In case the user does not have the rights to access this part of GUI:

![](GuiPrototypePNGs/InventoryUnathorised.png)

The table shows all the product available in the repository.
The database can be filtered or sorted by a specific key.
The user can download the report, add new product or new Supply (set of products bought by a supplier).


![](GuiPrototypePNGs/Inventory1.png)

## New product
Clicking on "Add new Product" button, the user will be directed to this screen where he/she can add a new product record.
To complete the entry, the user must fill in all the fields and press the save button.

![](GuiPrototypePNGs/Inventory2.png)

## New supply
Same approach for the new supply.

![](GuiPrototypePNGs/Inventory3.png)


# Accounting

In case the user does not have the rights to access this part of GUI:
![](GuiPrototypePNGs/AccountingUnathorised.png)

Otherwise:
![](GuiPrototypePNGs/Accounting.png)
This is the table where all entries and exits are shown. The user can filter or sort the table by a specific key.

Clicking on "Download report" the table is downloaded.

## New accounting record
To add a new record, click on "New Record":
![](GuiPrototypePNGs/Accounting2.png)
	