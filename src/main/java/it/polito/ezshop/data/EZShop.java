package it.polito.ezshop.data;

import it.polito.ezshop.exceptions.*;
import it.polito.ezshop.data.classes.*;

import java.time.LocalDate;

import java.util.*;


public class EZShop implements EZShopInterface {

    List<User> userList = new ArrayList<>();
    Map<Integer, Customer> customerMap =new HashMap<>();
    Map<Integer, BalanceOperation> transactionMap = new HashMap<>();
    Map<Integer, SaleTransaction> saleTransactionMap = new HashMap<>();
    Map<Integer, Order> orderTransactionMap = new HashMap<>();
    Map<String, ProductType> productTypeMap = new HashMap<>();
    double balance=0;
    User userSession=null;
    int idUsers=0;
    int counter_transactionID = 0;

    /*
        checkUserRole(String expectedRole)
        @param expectedRole: il ruolo da controllare; può avere come valore "ADMINISTRATOR", "MANAGER" o "CASHIER"

        @return:
            true,  se l'utente è loggato e ha il permesso che ci si aspetta
            false, se l'utente non è loggato o non ha i permessi adatti
     */
    public boolean checkUserRole(String expectedRole) {
        if (this.userSession == null || !this.userSession.getRole().equalsIgnoreCase(expectedRole)) {
            return false;
        }

        return true;
    }

    @Override
    public void reset() {
        this.userList= null;
        this.customerMap= null;
        this.transactionMap =null;
        this.saleTransactionMap= null;
        this.orderTransactionMap = null;
        this.productTypeMap = null;
        this.balance=0;
        this.userSession= null;
        this.idUsers = 0;
        this.counter_transactionID = 0;
    }


    /**
     * This method creates a new user with given username, password and role. The returned value is a unique identifier
     * for the new user.
     *
     * @param username the username of the new user. This value should be unique and not empty.
     * @param password the password of the new user. This value should not be empty.
     * @param role the role of the new user. This value should not be empty and it should assume
     *             one of the following values : "Administrator", "Cashier", "ShopManager"
     *
     * @return The id of the new user ( > 0 ).
     *          -1 if there is an error while saving the user or if another user with the same username exists
     *
     * @throws InvalidUsernameException If the username has an invalid value (empty or null)
     * @throws InvalidPasswordException If the password has an invalid value (empty or null)
     * @throws InvalidRoleException     If the role has an invalid value (empty, null or not among the set of admissible values)
     */

    @Override
    public Integer createUser(String username, String password, String role) throws InvalidUsernameException, InvalidPasswordException, InvalidRoleException {
        //username unique and not empty

        if (username.trim()=="" || username==null)
            throw new InvalidUsernameException();

        for (User u : this.userList) {
            if (u.getUsername().equals(username))
            {
                return (-1);
            }
        }
        //password not empty
        if (password.trim()=="" || password== null)
            throw new InvalidPasswordException();
        //Role not valid
        if (role.trim() == "" || role==null || (!role.toUpperCase().equals("MANAGER") && !role.toUpperCase().equals("ADMINISTRATOR") && !role.toUpperCase().equals("CASHIER") ))
            throw new InvalidRoleException();

        int newuserId = this.idUsers;
        userList.add(new EZUser(newuserId, username,password,role));
        this.idUsers++;


        return newuserId;
    }

    // ------------------- ADMIN ------------------ //
    /**
     * This method deletes the user with given id. It can be invoked only after a user with role "Administrator" is
     * logged in.
     *
     * @param id the user id, this value should not be less than or equal to 0 or null.
     *
     * @return  true if the user was deleted
     *          false if the user cannot be deleted  (**PERCHE'??**)
     *
     * @throws InvalidUserIdException if id is less than or equal to 0 or if it is null.
     * @throws UnauthorizedException if there is no logged user or if it has not the rights to perform the operation
     */
    @Override
    public boolean deleteUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
        if (id<=0 || id==null)
            throw new InvalidUserIdException();
        if (this.userSession==null || !this.userSession.getRole().toUpperCase().equals("ADMINISTRATOR" ))
            throw new UnauthorizedException();

        /*FAI IL DELETE*/
        return false;
    }

    @Override
    public List<User> getAllUsers() throws UnauthorizedException {
        return null;
    }

    @Override
    public User getUser(Integer id) throws InvalidUserIdException, UnauthorizedException {
        return null;
    }

    @Override
    public boolean updateUserRights(Integer id, String role) throws InvalidUserIdException, InvalidRoleException, UnauthorizedException {
        return false;
    }

    @Override
    public User login(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
        return null;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public Integer createProductType(String description, String productCode, double pricePerUnit, String note) throws InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {
        return null;
    }

    @Override
    public boolean updateProduct(Integer id, String newDescription, String newCode, double newPrice, String newNote) throws InvalidProductIdException, InvalidProductDescriptionException, InvalidProductCodeException, InvalidPricePerUnitException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean deleteProductType(Integer id) throws InvalidProductIdException, UnauthorizedException {
        return false;
    }

    @Override
    public List<ProductType> getAllProductTypes() throws UnauthorizedException {
        return null;
    }

    @Override
    public ProductType getProductTypeByBarCode(String barCode) throws InvalidProductCodeException, UnauthorizedException {
        return null;
    }

    @Override
    public List<ProductType> getProductTypesByDescription(String description) throws UnauthorizedException {
        return null;
    }

    @Override
    public boolean updateQuantity(Integer productId, int toBeAdded) throws InvalidProductIdException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean updatePosition(Integer productId, String newPos) throws InvalidProductIdException, InvalidLocationException, UnauthorizedException {
        return false;
    }

    @Override
    public Integer issueOrder(String productCode, int quantity, double pricePerUnit) throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {
        return null;
    }

    @Override
    public Integer payOrderFor(String productCode, int quantity, double pricePerUnit) throws InvalidProductCodeException, InvalidQuantityException, InvalidPricePerUnitException, UnauthorizedException {
        return null;
    }

    @Override
    public boolean payOrder(Integer orderId) throws InvalidOrderIdException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean recordOrderArrival(Integer orderId) throws InvalidOrderIdException, UnauthorizedException, InvalidLocationException {
        return false;
    }

    @Override
    public List<Order> getAllOrders() throws UnauthorizedException {
        return null;
    }

    @Override
    public Integer defineCustomer(String customerName) throws InvalidCustomerNameException, UnauthorizedException {


        return null;
    }

    @Override
    public boolean modifyCustomer(Integer id, String newCustomerName, String newCustomerCard) throws InvalidCustomerNameException, InvalidCustomerCardException, InvalidCustomerIdException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean deleteCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
        return false;
    }

    @Override
    public Customer getCustomer(Integer id) throws InvalidCustomerIdException, UnauthorizedException {
        return null;
    }

    @Override
    public List<Customer> getAllCustomers() throws UnauthorizedException {
        return null;
    }

    @Override
    public String createCard() throws UnauthorizedException {
        return null;
    }

    @Override
    public boolean attachCardToCustomer(String customerCard, Integer customerId) throws InvalidCustomerIdException, InvalidCustomerCardException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean modifyPointsOnCard(String customerCard, int pointsToBeAdded) throws InvalidCustomerCardException, UnauthorizedException {
        return false;
    }

    // --- Manage Sale Transactions --- //

    /*
        computeSaleTransactionPrice(Integer transactionId)
        Calcola il prezzo totale di una singola transazione, iterando tutti i prodotti
        presenti al suo interno e applicando i relativi sconti. Non viene applicato
        lo sconto totale della transazione.
        @param transactionId: l'id della transazione di cui calcolare il prezzo

        @return il prezzo calcolato
     */
    public double computeSaleTransactionPrice(SaleTransaction st) {
        List<TicketEntry> list = st.getEntries();
        double total = 0;

        for (TicketEntry e : list) {
            total += e.getPricePerUnit() * e.getAmount() * e.getDiscountRate();
        }

        return total;
    }

    /**
     * This method starts a new sale transaction and returns its unique identifier.
     * It can be invoked only after a user with role "Administrator", "ShopManager" or "Cashier" is logged in.
     *
     * @return the id of the transaction (greater than or equal to 0)
     */
    @Override
    public Integer startSaleTransaction() throws UnauthorizedException {
        if (!this.checkUserRole("MANAGER") && !this.checkUserRole("ADMINISTRATOR")
                && !this.checkUserRole("CASHIER")) {
            throw new UnauthorizedException();
        }

        int newID = ++this.counter_transactionID;
        // aggiungi BalanceOperation alla mappa generica
        this.transactionMap.put(newID, new EZBalanceOperation(newID, LocalDate.now(), "CREDIT"));
        // aggiungi SaleTransaction alla mappa specifica
        this.saleTransactionMap.put(newID, new EZSaleTransaction(newID));

        return newID;
    }

    /**
     * This method adds a product to a sale transaction decreasing the temporary amount of product available on the
     * shelves for other customers.
     * It can be invoked only after a user with role "Administrator", "ShopManager" or "Cashier" is logged in.
     *
     * @param transactionId the id of the Sale transaction
     * @param productCode the barcode of the product to be added
     * @param amount the quantity of product to be added
     * @return  true if the operation is successful
     *          false   if the product code does not exist,
     *                  if the quantity of product cannot satisfy the request,
     *                  if the transaction id does not identify a started and open transaction.
     *
     * @throws InvalidTransactionIdException if the transaction id less than or equal to 0 or if it is null
     * @throws InvalidProductCodeException if the product code is empty, null or invalid
     * @throws InvalidQuantityException if the quantity is less than 0
     * @throws UnauthorizedException if there is no logged user or if it has not the rights to perform the operation
     */
    @Override
    public boolean addProductToSale(Integer transactionId, String productCode, int amount) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
        if (!this.checkUserRole("MANAGER") && !this.checkUserRole("ADMINISTRATOR")
                && !this.checkUserRole("CASHIER")) {
            throw new UnauthorizedException();
        }

        if (transactionId <= 0 || transactionId == null) {
            throw new InvalidTransactionIdException();
        }

        if (amount < 0) {
            throw new InvalidQuantityException();
        }

        productCode = productCode.trim();
        if (productCode == null || productCode.equals("")/* || !this.checkBarCodeValidity(productCode)*/) {
            throw new InvalidProductCodeException();
        }

        if (!saleTransactionMap.containsKey(transactionId)) {
            return false;
        }

        if (!productTypeMap.containsKey(productCode)) {
            return false;
        }
        else {
            ProductType p = productTypeMap.get(productCode);
            if (p.getQuantity() < amount) {
                return false;
            }

            EZSaleTransaction s = (EZSaleTransaction) saleTransactionMap.get(transactionId);

            // aggiorna la lista di prodotti della transazione e ricalcola il prezzo totale
            s.addEntry(p, amount, s.getDiscountRate());
            s.setPrice(this.computeSaleTransactionPrice(s));

            // aggiorna la mappa
            saleTransactionMap.put(transactionId, s);

            // aggiorna la quantità del prodotto e la mappa dei prodotti
            p.setQuantity(p.getQuantity() - amount);
            productTypeMap.put(productCode, p);

            return true;
        }
    }

    /**
     * This method deletes a product from a sale transaction increasing the temporary amount of product available on the
     * shelves for other customers.
     * It can be invoked only after a user with role "Administrator", "ShopManager" or "Cashier" is logged in.
     *
     * @param transactionId the id of the Sale transaction
     * @param productCode the barcode of the product to be deleted
     * @param amount the quantity of product to be deleted
     *
     * @return  true if the operation is successful
     *          false   if the product code does not exist,
     *                  if the quantity of product cannot satisfy the request,
     *                  if the transaction id does not identify a started and open transaction.
     *
     * @throws InvalidTransactionIdException if the transaction id less than or equal to 0 or if it is null
     * @throws InvalidProductCodeException if the product code is empty, null or invalid
     * @throws InvalidQuantityException if the quantity is less than 0
     * @throws UnauthorizedException if there is no logged user or if it has not the rights to perform the operation
     */
    @Override
    public boolean deleteProductFromSale(Integer transactionId, String productCode, int amount) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
        if (!this.checkUserRole("MANAGER") && !this.checkUserRole("ADMINISTRATOR")
         && !this.checkUserRole("CASHIER")) {
            throw new UnauthorizedException();
        }

        if (transactionId <= 0 || transactionId == null) {
            throw new InvalidTransactionIdException();
        }

        if (amount < 0) {
            throw new InvalidQuantityException();
        }

        productCode = productCode.trim();
        // TODO: IMPLEMENTARE FUNZIONE PER VALIDITA' CODICE A BARRE
        if (productCode == null || productCode.equals("") /*|| !this.checkBarCodeValidity(productCode)*/) {
            throw new InvalidProductCodeException();
        }

        if (!saleTransactionMap.containsKey(transactionId)) {
            return false;
        }

        if (!productTypeMap.containsKey(productCode)) {
            return false;
        }
        else {
            ProductType p = productTypeMap.get(productCode);

            EZSaleTransaction s = (EZSaleTransaction) saleTransactionMap.get(transactionId);

            List<TicketEntry> l = s.getEntries();

            boolean found = false;

            // cerca l'elemento corrispondente al prodotto nella lista dei prodotti
            // della transazione
            for (TicketEntry e : l) {
                if (e.getBarCode().equals(productCode)) {
                    // se non vi sono abbastanza unità del prodotto in magazzino, ritorna false
                    if (e.getAmount() < amount) {
                        return false;
                    }
                    found = true;
                    break;
                }
            }
            // se non viene trovato, ritorna false
            if (!found) {
                return false;
            }

            // elimina il prodotto dalla lista
            s.deleteProductFromEntry(productCode, amount);

            // aggiorna il prezzo della transazione e la mappa
            s.setPrice(this.computeSaleTransactionPrice(s));
            saleTransactionMap.put(transactionId, s);

            // aggiorna la quantità del prodotto in magazzino
            p.setQuantity(p.getQuantity() + amount);
            // aggiorna la mappa dei prodotti
            productTypeMap.put(productCode, p);

            return true;
        }
    }

    /**
     * This method applies a discount rate to all units of a product type with given type in a sale transaction. The
     * discount rate should be greater than or equal to 0 and less than 1.
     * The sale transaction should be started and open.
     * It can be invoked only after a user with role "Administrator", "ShopManager" or "Cashier" is logged in.
     *
     * @param transactionId the id of the Sale transaction
     * @param productCode the barcode of the product to be discounted
     * @param discountRate the discount rate of the product
     *
     * @return  true if the operation is successful
     *          false   if the product code does not exist,
     *                  if the transaction id does not identify a started and open transaction.
     *
     * @throws InvalidTransactionIdException if the transaction id less than or equal to 0 or if it is null
     * @throws InvalidProductCodeException if the product code is empty, null or invalid
     * @throws InvalidDiscountRateException if the discount rate is less than 0 or if it greater than or equal to 1.00
     * @throws UnauthorizedException if there is no logged user or if it has not the rights to perform the operation
     */
    @Override
    public boolean applyDiscountRateToProduct(Integer transactionId, String productCode, double discountRate) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidDiscountRateException, UnauthorizedException {
        if (!this.checkUserRole("MANAGER") && !this.checkUserRole("ADMINISTRATOR")
                && !this.checkUserRole("CASHIER")) {
            throw new UnauthorizedException();
        }

        if (transactionId <= 0 || transactionId == null) {
            throw new InvalidTransactionIdException();
        }

        if (discountRate < 0 || discountRate > 1) {
            throw new InvalidDiscountRateException();
        }

        productCode = productCode.trim();
        // TODO: IMPLEMENTARE FUNZIONE PER VALIDITA' CODICE A BARRE
        if (productCode == null || productCode.equals("") /*|| !this.checkBarCodeValidity(productCode)*/) {
            throw new InvalidProductCodeException();
        }

        if (!saleTransactionMap.containsKey(transactionId)) {
            return false;
        }

        if (!productTypeMap.containsKey(productCode)) {
            return false;
        }
        else {
            ProductType p = productTypeMap.get(productCode);

            EZSaleTransaction s = (EZSaleTransaction) saleTransactionMap.get(transactionId);

            List<TicketEntry> l = s.getEntries();

            boolean found = false;

            // trova l'elemento corrispondente nella lista di prodotti della transazione
            // e ne cambia il tasso di sconto
            for (TicketEntry e : l) {
                if (e.getBarCode().equals(productCode)) {
                    found = true;
                    e.setDiscountRate(discountRate);
                    break;
                }
            }
            if (!found) {
                return false;
            }

            // Aggiorna il prezzo della transazione e la mappa
            s.setPrice(this.computeSaleTransactionPrice(s));
            saleTransactionMap.put(transactionId, s);

            return true;
        }
    }

    /**
     * This method applies a discount rate to the whole sale transaction.
     * The discount rate should be greater than or equal to 0 and less than 1.
     * The sale transaction can be either started or closed but not already payed.
     * It can be invoked only after a user with role "Administrator", "ShopManager" or "Cashier" is logged in.
     *
     * @param transactionId the id of the Sale transaction
     * @param discountRate the discount rate of the sale
     *
     * @return  true if the operation is successful
     *          false if the transaction does not exists
     *
     * @throws InvalidTransactionIdException if the transaction id less than or equal to 0 or if it is null
     * @throws InvalidDiscountRateException if the discount rate is less than 0 or if it greater than or equal to 1.00
     * @throws UnauthorizedException if there is no logged user or if it has not the rights to perform the operation
     */
    @Override
    public boolean applyDiscountRateToSale(Integer transactionId, double discountRate) throws InvalidTransactionIdException, InvalidDiscountRateException, UnauthorizedException {
        if (!this.checkUserRole("MANAGER") && !this.checkUserRole("ADMINISTRATOR")
                && !this.checkUserRole("CASHIER")) {
            throw new UnauthorizedException();
        }

        if (transactionId <= 0 || transactionId == null) {
            throw new InvalidTransactionIdException();
        }

        if (discountRate < 0 || discountRate > 1) {
            throw new InvalidDiscountRateException();
        }

        if (!saleTransactionMap.containsKey(transactionId)) {
            return false;
        }

        EZSaleTransaction s = (EZSaleTransaction) saleTransactionMap.get(transactionId);

        // aggiorna il tasso di sconto per la transazione
        s.setDiscountRate(discountRate);
        // aggiorna la mappa delle transazioni
        saleTransactionMap.put(transactionId, s);

        return true;
    }

    /**
     * This method returns the number of points granted by a specific sale transaction.
     * Every 10€ the number of points is increased by 1 (i.e. 19.99€ returns 1 point, 20.00€ returns 2 points).
     * If the transaction with given id does not exist then the number of points returned should be -1.
     * The transaction may be in any state (open, closed, payed).
     * It can be invoked only after a user with role "Administrator", "ShopManager" or "Cashier" is logged in.
     *
     * @param transactionId the id of the Sale transaction
     *
     * @return the points of the sale (1 point for each 10€) or -1 if the transaction does not exists
     *
     * @throws InvalidTransactionIdException if the transaction id less than or equal to 0 or if it is null
     * @throws UnauthorizedException if there is no logged user or if it has not the rights to perform the operation
     */
    @Override
    public int computePointsForSale(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
        if (!this.checkUserRole("MANAGER") && !this.checkUserRole("ADMINISTRATOR")
                && !this.checkUserRole("CASHIER")) {
            throw new UnauthorizedException();
        }

        if (transactionId <= 0 || transactionId == null) {
            throw new InvalidTransactionIdException();
        }

        if (!saleTransactionMap.containsKey(transactionId)) {
            return -1;
        }

        EZSaleTransaction s = (EZSaleTransaction) saleTransactionMap.get(transactionId);

        // calcola e ritorna il numero di punti
        return (int) (s.getPrice() / 10);
    }

    /**
     * This method closes an opened transaction. After this operation the
     * transaction is persisted in the system's memory.
     * It can be invoked only after a user with role "Administrator", "ShopManager" or "Cashier" is logged in.
     *
     * @param transactionId the id of the Sale transaction
     *
     * @return  true    if the transaction was successfully closed
     *          false   if the transaction does not exist,
     *                  if it has already been closed,
     *                  if there was a problem in registering the data
     *
     * @throws InvalidTransactionIdException if the transaction id less than or equal to 0 or if it is null
     * @throws UnauthorizedException if there is no logged user or if it has not the rights to perform the operation
     */
    @Override
    public boolean endSaleTransaction(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
        if (!this.checkUserRole("MANAGER") && !this.checkUserRole("ADMINISTRATOR")
                && !this.checkUserRole("CASHIER")) {
            throw new UnauthorizedException();
        }

        if (transactionId <= 0 || transactionId == null) {
            throw new InvalidTransactionIdException();
        }

        if (!saleTransactionMap.containsKey(transactionId) || !transactionMap.containsKey(transactionId)) {
            return false;
        }

        BalanceOperation bo = this.transactionMap.get(transactionId);
        SaleTransaction st = this.saleTransactionMap.get(transactionId);

        // il prezzo di ogni vendita viene aggiornato automaticamente ogni
        // volta che viene aggiunto/rimosso un prodotto o quando ne viene
        // modificato il tasso di sconto
        bo.setMoney(st.getPrice() * st.getDiscountRate());
        // aggiorna la mappa
        this.transactionMap.put(transactionId, bo);
        // Nota: le mappe vengono salvate in memoria in modo persistente solo qui
        // TODO: AGGIORNA DB CON NUOVE TRANSAZIONI

        return true;
    }

    /**
     * This method deletes a sale transaction with given unique identifier from the system's data store.
     * It can be invoked only after a user with role "Administrator", "ShopManager" or "Cashier" is logged in.
     *
     * @param saleNumber the number of the transaction to be deleted
     *
     * @return  true if the transaction has been successfully deleted,
     *          false   if the transaction doesn't exist,
     *                  if it has been payed,
     *                  if there are some problems with the db
     *
     * @throws InvalidTransactionIdException if the transaction id number is less than or equal to 0 or if it is null
     * @throws UnauthorizedException if there is no logged user or if it has not the rights to perform the operation
     */
    @Override
    public boolean deleteSaleTransaction(Integer saleNumber) throws InvalidTransactionIdException, UnauthorizedException {
        if (!this.checkUserRole("MANAGER") && !this.checkUserRole("ADMINISTRATOR")
                && !this.checkUserRole("CASHIER")) {
            throw new UnauthorizedException();
        }

        if (saleNumber <= 0 || saleNumber == null) {
            throw new InvalidTransactionIdException();
        }

        if (!saleTransactionMap.containsKey(saleNumber) || !transactionMap.containsKey(saleNumber)) {
            return false;
        }

        this.transactionMap.remove(saleNumber);
        this.saleTransactionMap.remove(saleNumber);

        // TODO: AGGIORNA DB RIMUOVENDO TRANSAZIONI

        return true;
    }

    /**
     * This method returns  a closed sale transaction.
     * It can be invoked only after a user with role "Administrator", "ShopManager" or "Cashier" is logged in.
     *
     * @param transactionId the id of the CLOSED Sale transaction
     *
     * @return the transaction if it is available (transaction closed), null otherwise
     *
     * @throws InvalidTransactionIdException if the transaction id less than or equal to 0 or if it is null
     * @throws UnauthorizedException if there is no logged user or if it has not the rights to perform the operation
     */
    @Override
    public SaleTransaction getSaleTransaction(Integer transactionId) throws InvalidTransactionIdException, UnauthorizedException {
        if (!this.checkUserRole("MANAGER") && !this.checkUserRole("ADMINISTRATOR")
                && !this.checkUserRole("CASHIER")) {
            throw new UnauthorizedException();
        }

        if (transactionId <= 0 || transactionId == null) {
            throw new InvalidTransactionIdException();
        }

        if (!saleTransactionMap.containsKey(transactionId)) {
            return null;
        }

        // Why get the transaction from the DB? Because if a transaction is saved in the DB, then
        // it has certainly been closed. There's no way to know whether a transaction contained
        // in one of the maps has been closed or not.
        // TODO: PRENDI TRANSAZIONE DAL DB

        // Placeholder value until DB is implemented
        EZSaleTransaction e = new EZSaleTransaction(99999);

        return this.saleTransactionMap.get(transactionId);
    }

    // --- Manage Return Transactions --- //

    @Override
    public Integer startReturnTransaction(Integer saleNumber) throws /*InvalidTicketNumberException,*/InvalidTransactionIdException, UnauthorizedException {
        return null;
    }

    @Override
    public boolean returnProduct(Integer returnId, String productCode, int amount) throws InvalidTransactionIdException, InvalidProductCodeException, InvalidQuantityException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean endReturnTransaction(Integer returnId, boolean commit) throws InvalidTransactionIdException, UnauthorizedException {
        return false;
    }

    @Override
    public boolean deleteReturnTransaction(Integer returnId) throws InvalidTransactionIdException, UnauthorizedException {
        return false;
    }

    // --- Manage Payments --- //

    /**
     * This method record the payment of a sale transaction with cash and returns the change (if present).
     * This method affects the balance of the system.
     * It can be invoked only after a user with role "Administrator", "ShopManager" or "Cashier" is logged in.
     *
     * @param ticketNumber the number of the transaction that the customer wants to pay
     * @param cash the cash received by the cashier
     *
     * @return the change (cash - sale price)
     *         -1   if the sale does not exists,
     *              if the cash is not enough,
     *              if there is some problemi with the db
     *
     * @throws InvalidTransactionIdException if the  number is less than or equal to 0 or if it is null
     * @throws UnauthorizedException if there is no logged user or if it has not the rights to perform the operation
     * @throws InvalidPaymentException if the cash is less than or equal to 0
     */
    @Override
    public double receiveCashPayment(Integer ticketNumber, double cash) throws InvalidTransactionIdException, InvalidPaymentException, UnauthorizedException {
        if (cash <= 0) {
            throw new InvalidPaymentException();
        }

        if (!this.checkUserRole("MANAGER") && !this.checkUserRole("ADMINISTRATOR")
                && !this.checkUserRole("CASHIER")) {
            throw new UnauthorizedException();
        }

        if (ticketNumber <= 0 || ticketNumber == null) {
            throw new InvalidTransactionIdException();
        }

        // TODO: PRENDI TRANSAZIONE DAL DB
        // placeholder
        EZSaleTransaction result = new EZSaleTransaction(99999);

        if (cash < result.getPrice()) {
            return -1;
        }

        return (cash - result.getPrice());
    }

    /**
     * This method record the payment of a sale with credit card. If the card has not enough money the payment should
     * be refused.
     * The credit card number validity should be checked. It should follow the luhn algorithm.
     * The credit card should be registered in the system.
     * This method affects the balance of the system.
     * It can be invoked only after a user with role "Administrator", "ShopManager" or "Cashier" is logged in.
     *
     * @param ticketNumber the number of the sale that the customer wants to pay
     * @param creditCard the credit card number of the customer
     *
     * @return  true if the operation is successful
     *          false   if the sale does not exists,
     *                  if the card has not enough money,
     *                  if the card is not registered,
     *                  if there is some problem with the db connection
     *
     * @throws InvalidTransactionIdException if the sale number is less than or equal to 0 or if it is null
     * @throws InvalidCreditCardException if the credit card number is empty, null or if luhn algorithm does not
     *                                      validate the credit card
     * @throws UnauthorizedException if there is no logged user or if it has not the rights to perform the operation
     */
    @Override
    public boolean receiveCreditCardPayment(Integer ticketNumber, String creditCard) throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
        // TODO: IMPLEMENTARE FUNZIONE PER VALIDITA' CARTA DI CREDITO
        /*if (!this.checkCreditCardValidity(creditCard)) {
            return false;
        }*/

        if (!this.checkUserRole("MANAGER") && !this.checkUserRole("ADMINISTRATOR")
                && !this.checkUserRole("CASHIER")) {
            throw new UnauthorizedException();
        }

        if (ticketNumber <= 0 || ticketNumber == null) {
            throw new InvalidTransactionIdException();
        }

        // TODO: IMPLEMENTARE INSERIMENTO DEL PAGAMENTO NEL DB

        return false;
    }

    /**
     * This method record the payment of a closed return transaction with given id. The return value of this method is the
     * amount of money to be returned.
     * This method affects the balance of the application.
     * It can be invoked only after a user with role "Administrator", "ShopManager" or "Cashier" is logged in.
     *
     * @param returnId the id of the return transaction
     *
     * @return  the money returned to the customer
     *          -1  if the return transaction is not ended,
     *              if it does not exist,
     *              if there is a problem with the db
     *
     * @throws InvalidTransactionIdException if the return id is less than or equal to 0
     * @throws UnauthorizedException if there is no logged user or if it has not the rights to perform the operation
     */
    @Override
    public double returnCashPayment(Integer returnId) throws InvalidTransactionIdException, UnauthorizedException {
        // TODO: PAGAMENTI NEL DB
        return 0;
    }

    @Override
    public double returnCreditCardPayment(Integer returnId, String creditCard) throws InvalidTransactionIdException, InvalidCreditCardException, UnauthorizedException {
        return 0;
    }

    // --- Manage Accounting --- //
    /**
     * This method record a balance update. <toBeAdded> can be both positive and nevative. If positive the balance entry
     * should be recorded as CREDIT, if negative as DEBIT. The final balance after this operation should always be
     * positive.
     * It can be invoked only after a user with role "Administrator", "ShopManager" is logged in.
     *
     * @param toBeAdded the amount of money (positive or negative) to be added to the current balance. If this value
     *                  is >= 0 than it should be considered as a CREDIT, if it is < 0 as a DEBIT
     *
     * @return  true if the balance has been successfully updated
     *          false if toBeAdded + currentBalance < 0.
     * @throws UnauthorizedException if there is no logged user or if it has not the rights to perform the operation
     */
    @Override
    public boolean recordBalanceUpdate(double toBeAdded) throws UnauthorizedException {
        if (!this.checkUserRole("MANAGER") && !this.checkUserRole("ADMINISTRATOR")) {
            throw new UnauthorizedException();
        }
        String type = "";
        if (toBeAdded >= 0) {
            type = "CREDIT";
        }
        else {
            type = "DEBIT";
        }

        BalanceOperation bo = new EZBalanceOperation(++this.counter_transactionID, LocalDate.now(), type);

        if (this.balance + toBeAdded < 0) {
            return false;
        }

        // TODO: RIVEDERE GESTIONE DI BalanceOperation NELLA GESTIONE DELLE TRANSAZIONI DI VENDITA
        this.balance += toBeAdded;

        return true;
    }

    @Override
    public List<BalanceOperation> getCreditsAndDebits(LocalDate from, LocalDate to) throws UnauthorizedException {
        return null;
    }

    @Override
    public double computeBalance() throws UnauthorizedException {
        return 0;
    }
}
