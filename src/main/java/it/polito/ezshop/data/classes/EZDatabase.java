package it.polito.ezshop.data.classes;

import it.polito.ezshop.data.*;

import it.polito.ezshop.data.*;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class EZDatabase {

    String jdbcUrl;
    Connection connection;

    public EZDatabase() throws SQLException {
        this.jdbcUrl = "jdbc:sqlite:EZshop.db";
        this.connection = DriverManager.getConnection(jdbcUrl);
    }

    /********************* METODI PER LA TABELLA USER **************************/

    public int getNextUserId () throws SQLException {
        String query = "SELECT COALESCE(MAX(id), 0) as maxid FROM USERS";
        Statement statement =this.connection.createStatement();
        ResultSet rs= statement.executeQuery(query);
        return (rs.getInt("maxid")+1);
    }
    public void insertUser(User user) throws SQLException {
        String values = user.getId()+", '"+user.getUsername()+"', '"+user.getPassword()+"', '"+user.getRole()+"'";
        String sql ="INSERT INTO USERS VALUES ("+ values +")";
        Statement statement =this.connection.createStatement();
        statement.executeUpdate(sql);
    }

    public List<User> getUsers() throws SQLException {
        String query = "SELECT * FROM USERS";
        Statement statement =this.connection.createStatement();
        ResultSet rs= statement.executeQuery(query);
        List<User> users = new ArrayList<>();

        while (rs.next())
        {
            EZUser usr = new EZUser(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("role"));
            users.add(usr);
        }

        return users;

    }

    public void deleteUser (Integer id) throws SQLException {


        String sql ="DELETE FROM USERS WHERE id =?";
        PreparedStatement pstm =this.connection.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();

    }

    public void updateUser (User updatedUser) throws SQLException {
        String sql = "UPDATE USERS SET username = ?, password = ?, role = ? WHERE id = ?";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setString(1, updatedUser.getUsername());
        pstm.setString(2, updatedUser.getPassword());
        pstm.setString(3, updatedUser.getRole());
        pstm.setInt(4, updatedUser.getId());

        pstm.executeUpdate();

    }
    public void updateUserRole(Integer id, String role) throws SQLException {
        String sql = "UPDATE USERS SET  role = ? WHERE id = ?";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setString(1, role);
        pstm.setInt(2, id);

        pstm.executeUpdate();
    }

    /********************* METODI PER LA TABELLA ORDER **************************/
    public void insertOrder(Order order) throws SQLException {

        String values = order.getOrderId()+", '"+order.getBalanceId()+"', '"+order.getProductCode()+"', '"+order.getPricePerUnit()+"', '"+order.getQuantity()+"', '"+order.getStatus()+"'";
        String sql ="INSERT INTO ORDERS VALUES ("+ values +")";
        Statement statement =this.connection.createStatement();
        statement.executeUpdate(sql);
    }

    public Map<Integer, Order> getOrders() throws SQLException {
        String query = "SELECT * FROM ORDERS";
        Statement statement =this.connection.createStatement();
        ResultSet rs= statement.executeQuery(query);
        Map<Integer, Order> orders = new HashMap<>();

        while (rs.next())
        {
            EZOrder ordr = new EZOrder(rs.getInt("id"), rs.getString("productCode"), rs.getInt("quantity"), rs.getDouble("pricePerUnit"));
            ordr.setStatus(rs.getString("status"));
            ordr.setBalanceId(rs.getInt("balanceId"));
            orders.put(rs.getInt("id"), ordr);
        }

        return orders;

    }

    public void deleteOrder (Integer id) throws SQLException {


        String sql ="DELETE FROM ORDERS WHERE id =?";
        PreparedStatement pstm =this.connection.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();

    }

    public void updateOrder (Order updatedOrder) throws SQLException {
        String sql = "UPDATE ORDERS SET balanceId = ?, productCode = ?, pricePerUnit = ?, quantity=?, status=? WHERE id = ?";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setInt(1, updatedOrder.getBalanceId());
        pstm.setString(2, updatedOrder.getProductCode());
        pstm.setDouble(3, updatedOrder.getPricePerUnit());
        pstm.setInt(4, updatedOrder.getQuantity());
        pstm.setString(5, updatedOrder.getStatus());
        pstm.setInt(6, updatedOrder.getOrderId());

        pstm.executeUpdate();
    }

    public void updateOrderStatus(Integer orderId, String stat) throws SQLException {
        String sql = "UPDATE ORDERS SET  status=? WHERE id = ?";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setInt(2, orderId);
        pstm.setString(1, stat);
        pstm.executeUpdate();

    }
    public void updateOrderBalanceId(Integer orderId, Integer balanceId) throws SQLException {
        String sql = "UPDATE ORDERS SET  balanceId=? WHERE id = ?";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setInt(1, orderId);
        pstm.setInt(2, balanceId);
        pstm.executeUpdate();

    }


    /*******************************************************************************************/
    /********************* METODI PER LA TABELLA CUSTOMER **************************/
    public boolean insertCustomer(EZCustomer customer) throws SQLException {
        String values = customer.getId()+", '"+customer.getCustomerName()+"', '"+customer.getCustomerCard()+"', '"+customer.getPoints()+"'";
        String sql ="INSERT INTO CUSTOMERS VALUES ("+ values +")";
        Statement statement =this.connection.createStatement();
        if(statement.executeUpdate(sql)!=1) //ritorna il numero di righe cambiate executeUpdate -> in questo caso é una insert, quindi deve essere per forza una.
            return false;
        return true;
    }
    public boolean updateCustomer (EZCustomer updatedCustomer) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET CustomerName= ?, CustomerCard = ?, points = ? WHERE id = ?";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setString(1, updatedCustomer.getCustomerName());
        pstm.setString(2, updatedCustomer.getCustomerCard());
        pstm.setInt(3, updatedCustomer.getPoints());
        pstm.setInt(4, updatedCustomer.getId());

        if(pstm.executeUpdate()!=4)
            return false;

        return true;
    }
    public Map<Integer, Customer> getCustomerMap() throws SQLException{
        String query = "SELECT * FROM CUSTOMERS;";
        Statement statement = this.connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        Map<Integer, Customer> cuMap = new HashMap<>();
        while(rs.next()){
            EZCustomerCard cuscard = new EZCustomerCard(rs.getString(3),rs.getInt(4));
            EZCustomer cu = new EZCustomer(
                    rs.getString(2),
                    rs.getInt(1),
                    cuscard
            );
            cuMap.put(cu.getId(),cu);
        }
        return cuMap;
    }
    public int getLastCustomer () throws SQLException {
        String sql = "SELECT COALESCE(MAX(CustomerId), 0) AS maxcid FROM CUSTOMERS;";
        Statement statement = this.connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        int cid = rs.getInt("maxcid");
        if( cid<=0 )
            return 0;
        return cid;
    }
    public Integer getCustomerCard () throws SQLException{
        String sql = "SELECT CustomerCard AS cucard FROM CUSTOMERS;";
        Statement statement = this.connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        String cucard = new String("");
        Integer max = new Integer(0);
        while(rs.next()){
            if(max < Integer.parseInt(rs.getString("cucard"))){
                max = Integer.parseInt(rs.getString("cucard"));
                cucard = rs.getString("cucard");
            }
        }
        if (cucard==null || cucard.trim().equals(""))
            return 0;
        cucard = cucard.substring(1);
        while(cucard.charAt(0)==0){
            cucard = cucard.substring(1);
        }
        return Integer.parseInt(cucard);
    }
    public void deleteCustomer (Integer id) throws SQLException {
        String sql ="DELETE FROM CUSTOMERS WHERE id =?";
        PreparedStatement pstm =this.connection.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();
    }
    public void deleteCustomerTable () throws SQLException {
        String sql ="DELETE FROM CUSTOMERS";
        PreparedStatement pstm =this.connection.prepareStatement(sql);
        pstm.executeUpdate();
    }
    public boolean deleteCustomerCard (Integer id) throws SQLException {
        String sql ="UPDATE CUSTOMERS SET CustomerCard = NULL WHERE id =?";
        PreparedStatement pstm =this.connection.prepareStatement(sql);
        pstm.setInt(1, id);
        if(pstm.executeUpdate() != 1)
            return false;
        return true;
    }
    public boolean updateCustomerCard (Integer id, String newCustomerCard) throws SQLException {
        String sql ="UPDATE CUSTOMERS SET CustomerCard = ?  WHERE id =?";
        PreparedStatement pstm =this.connection.prepareStatement(sql);
        pstm.setString(1, newCustomerCard);
        pstm.setInt(2, id);
        if(pstm.executeUpdate()!=2)
            return false;
        return true;
    }
    public boolean updatePoints (Integer id, Integer Points) throws SQLException {
        String sql ="UPDATE CUSTOMERS SET Points = ? WHERE id =?";
        PreparedStatement pstm =this.connection.prepareStatement(sql);
        pstm.setInt(1, Points);
        pstm.setInt(2, id);
        if(pstm.executeUpdate()!=2)
            return false;
        return true;
    }
    /*******************************************************************************************/
    /********************* METODI PER LA TABELLA PRODUCT TYPE **************************/
    public boolean insertProductType(EZProductType product) throws SQLException {
        String values = product.getBarCode()+", '"+product.getId()+"', '"+product.getPricePerUnit()+"', '"+product.getLocation()+"', '"+product.getNote()+"', '"+product.getQuantity()+"', '"+product.getProductDescription()+"'";
        String sql ="INSERT INTO PRODUCTS VALUES ("+ values +")";
        Statement statement =this.connection.createStatement();
        if(statement.executeUpdate(sql)!=1) //ritorna il numero di righe cambiate executeUpdate -> in questo caso é una insert, quindi deve essere per forza una.
            return false;
        return true;
    }
    public void updateProduct (EZProductType product) throws SQLException {
        String sql = "UPDATE PRODUCTS SET productId= ?, PricePerUnit = ?, Location = ?, Note = ?, Quantity = ?,Description = ?, WHERE Barcode = ?";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setInt(1, product.getId());
        pstm.setDouble(2, product.getPricePerUnit());
        pstm.setString(3, product.getLocation());
        pstm.setString(4, product.getNote());
        pstm.setInt(5, product.getQuantity());
        pstm.setString(6, product.getProductDescription());
        pstm.setString(7, product.getBarCode());
    }
    public void deleteProduct (EZProductType product) throws SQLException {
        String sql ="DELETE FROM CUSTOMERS WHERE Barcode =?";
        PreparedStatement pstm =this.connection.prepareStatement(sql);
        pstm.setString(1, product.getBarCode());
        pstm.executeUpdate();

    }
    public void deleteProductTable () throws SQLException {
        String sql ="DELETE FROM PRODUCTS";
        PreparedStatement pstm =this.connection.prepareStatement(sql);
        pstm.executeUpdate();

    }
    public int getLastProductId() throws SQLException{
        String sql = "SELECT COALESCE(MAX(ProductId), 0) AS maxpid FROM PRODUCTS ;";
        Statement stmt  = this.connection.createStatement();
        ResultSet rs   = stmt.executeQuery(sql);
        int pid = rs.getInt("maxpid");
        if(pid <=0)
            return 0;
        return pid;
    }
    public Map<String, ProductType> getProductTypeMap() throws SQLException {
        String query = "SELECT * FROM PRODUCTS;";
        Statement statement = this.connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        Map<String, ProductType> proMap = new HashMap<>();

        while(rs.next()) {
            EZProductType pro = new EZProductType(
                    rs.getString(7),
                    rs.getString(1),
                    rs.getDouble(3),
                    rs.getString(5),
                    rs.getInt(2),
                    rs.getString(4)
            );
            proMap.put(pro.getBarCode(), pro);
        }

        return proMap;
    }
    // ---------------- METODI PER LA TABELLA BALANCEOPERATIONS ------------------- //
    public void addBalanceOperation(EZBalanceOperation bo) throws SQLException {
        String sql = "INSERT INTO BalanceOperations(id, money, date, type) VALUES (?, ?, ?, ?);";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setInt(1, bo.getBalanceId());
        pstm.setDouble(2, bo.getMoney());
        pstm.setString(3, bo.getDate().toString());
        pstm.setString(4, bo.getType());

        pstm.executeUpdate();
    }

    public void updateBalanceOperation(EZBalanceOperation bo) throws SQLException {
        String sql = "UPDATE BalanceOperations" +
                "SET money = ?, date = ?, type = ?" +
                "WHERE id = ?;";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setDouble(1, bo.getMoney());
        pstm.setString(2, bo.getDate().toString());
        pstm.setString(3, bo.getType());
        pstm.setInt(4, bo.getBalanceId());

        pstm.executeUpdate();
    }

    public void deleteBalanceOperation(int balanceId) throws SQLException {
        String sql = "DELETE FROM BalanceOperations" +
                "WHERE id = ?;";
        PreparedStatement pstm = this.connection.prepareStatement(sql);

        pstm.setInt(1, balanceId);

        pstm.executeUpdate();
    }

    public Map<Integer, BalanceOperation> getBalanceOperations() throws SQLException {
        String query = "SELECT * FROM BalanceOperations;";
        Statement statement =this.connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        Map<Integer, BalanceOperation> boMap = new HashMap<>();

        while(rs.next()) {
            EZBalanceOperation bo = new EZBalanceOperation(
                    rs.getInt("id"),
                    LocalDate.parse(rs.getString("date")),
                    rs.getDouble("money")
            );
            boMap.put(bo.getBalanceId(), bo);
        }

        return boMap;
    }

    public int getLastTransactionID() throws SQLException {
        String sql = "SELECT COALESCE(MAX(id), 0) AS maxTransID FROM BalanceOperations;";
        Statement stat = this.connection.createStatement();
        ResultSet rs = stat.executeQuery(sql);

        return rs.getInt("maxTransID");
    }

    public void clearBalanceOperations() throws SQLException {
        String sql = "DELETE FROM BalanceOperations;";
        Statement stat = this.connection.createStatement();
        ResultSet rs = stat.executeQuery(sql);
    }

    // ---------------------- METODI PER LA TABELLA SALETRANSACTIONS --------------- //
    public void addSaleTransaction(EZSaleTransaction st) throws SQLException {
        // NOTA: in questo metodo viene aggiunta anche la lista di ProductEntry
        String sql = "INSERT INTO SaleTransactions(id, discountRate, price, status) VALUES (?, ?, ?, ?);";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setInt(1, st.getTicketNumber());
        pstm.setDouble(2, st.getDiscountRate());
        pstm.setDouble(3, st.getPrice());
        pstm.setString(4, st.getStatus());

        pstm.executeUpdate();

        // add all entries
        List<TicketEntry> entryList = st.getEntries();

        for (TicketEntry e : entryList) {
            String query_e = "INSERT INTO ProductEntry(barCode, saleId, prodDesc, amount, discountRate, pricePerUnit) " +
                    "VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement stat_e = this.connection.prepareStatement(query_e);

            stat_e.setString(1, e.getBarCode());
            stat_e.setInt(2, st.getTicketNumber());
            stat_e.setString(3, e.getProductDescription());
            stat_e.setInt(4, e.getAmount());
            stat_e.setDouble(5, e.getDiscountRate());
            stat_e.setDouble(6, e.getPricePerUnit());

            stat_e.executeUpdate();
        }
    }

    public void updateSaleTransaction(EZSaleTransaction st) throws SQLException {
        String sql = "UPDATE SaleTransactions" +
                "SET discountRate = ?, price = ?, status = ?" +
                "WHERE id = ?;";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setDouble(1, st.getDiscountRate());
        pstm.setDouble(2, st.getPrice());
        pstm.setString(3, st.getStatus());
        pstm.setInt(4, st.getTicketNumber());

        // update every entry
        List<TicketEntry> entryList = st.getEntries();

        for (TicketEntry e : entryList) {
            String query_e = "UPDATE ProductEntry" +
                    "SET prodDesc = ?, amount = ?,  discountRate = ?, pricePerUnit = ?" +
                    "WHERE barCode = ?, saleId = ?;";
            PreparedStatement stat_e = this.connection.prepareStatement(query_e);

            stat_e.setString(1, e.getProductDescription());
            stat_e.setInt(2, e.getAmount());
            stat_e.setDouble(3, e.getDiscountRate());
            stat_e.setDouble(4, e.getPricePerUnit());

            stat_e.setString(5, e.getBarCode());
            stat_e.setInt(6, st.getTicketNumber());

            stat_e.executeUpdate();
        }

        pstm.executeUpdate();
    }

    public void deleteSaleTransaction(EZSaleTransaction st) throws SQLException {
        String sql = "DELETE FROM SaleTransactions" +
                "WHERE id = ?;";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setInt(1, st.getTicketNumber());

        // delete every entry associated with the st
        List<TicketEntry> entryList = st.getEntries();

        for (TicketEntry e : entryList) {
            String query_e = "DELETE FROM ProductEntry WHERE barCode = ?, saleId = ?;";
            PreparedStatement stat_e = this.connection.prepareStatement(query_e);

            stat_e.setString(1, e.getBarCode());
            stat_e.setInt(2, st.getTicketNumber());

            stat_e.executeUpdate();
        }
        // delete the st
        pstm.executeUpdate();
    }

    public Map<Integer, SaleTransaction> getSaleTransactions() throws SQLException {
        String query = "SELECT * FROM SaleTransactions;";
        Statement statement =this.connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        Map<Integer, SaleTransaction> stMap = new HashMap<>();

        while(rs.next()) {
            // create a new st object
            EZSaleTransaction st = new EZSaleTransaction(
                    rs.getInt("id"),
                    rs.getDouble("discountRate"),
                    rs.getDouble("price"),
                    rs.getString("status")
            );

            // get all product entries associated with the st
            String productQuery = "SELECT * FROM productEntry WHERE saleId = ?;";
            PreparedStatement pstat = this.connection.prepareStatement(productQuery);

            pstat.setInt(1, st.getTicketNumber());

            ResultSet rs_prod = pstat.executeQuery(productQuery);
            List<TicketEntry> entryList = new ArrayList<>();

            // for each product entry...
            while (rs_prod.next()) {
                // create a new object
                EZTicketEntry e = new EZTicketEntry(
                        rs_prod.getString("barCode"),
                        rs_prod.getString("prodDesc"),
                        rs_prod.getInt("amount"),
                        rs_prod.getDouble("pricePerUnit"),
                        rs_prod.getDouble("discountRate")
                );
                // add it to the temporary list
                entryList.add(e);
            }
            // set the list for the sale transaction
            st.setEntries(entryList);
            // add the st to the st list
            stMap.put(st.getTicketNumber(), st);
        }

        return stMap;
    }

    public EZSaleTransaction getSaleTransaction(int id) throws SQLException {
        String query = "SELECT * FROM SaleTransactions WHERE id = ?;";
        PreparedStatement statement = this.connection.prepareStatement(query);

        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery(query);
        List<EZSaleTransaction> stList = new ArrayList<>();

        // for each sale transaction returned...
        while(rs.next()) {
            // create a new st object
            EZSaleTransaction st = new EZSaleTransaction(
                    rs.getInt("id"),
                    rs.getDouble("discountRate"),
                    rs.getDouble("price"),
                    rs.getString("status")
            );

            // get all product entries associated with the st
            String productQuery = "SELECT * FROM productEntry WHERE saleId = ?;";
            PreparedStatement pstat = this.connection.prepareStatement(productQuery);

            pstat.setInt(1, st.getTicketNumber());

            ResultSet rs_prod = pstat.executeQuery(productQuery);
            List<TicketEntry> entryList = new ArrayList<>();

            // for each product entry...
            while (rs_prod.next()) {
                // create a new object
                EZTicketEntry e = new EZTicketEntry(
                        rs_prod.getString("barCode"),
                        rs_prod.getString("prodDesc"),
                        rs_prod.getInt("amount"),
                        rs_prod.getDouble("pricePerUnit"),
                        rs_prod.getDouble("discountRate")
                );
                // add it to the temporary list
                entryList.add(e);
            }
            // set the list for the sale transaction
            st.setEntries(entryList);
            // add the st to the st list
            stList.add(st);
        }

        return stList.get(0);
    }

    public void updateSaleInventoryQuantity(EZSaleTransaction st) throws SQLException {
        // get list of entries
        List<TicketEntry> entryList = st.getEntries();

        // update all of the product quantities in the DB
        for (TicketEntry e : entryList) {
            String query_e = "UPDATE Products" +
                    "SET Quantity = Quantity - ?" +
                    "WHERE barcode = ?;";
            PreparedStatement stat_e = this.connection.prepareStatement(query_e);

            stat_e.setInt(1, e.getAmount());
            stat_e.setString(2, e.getBarCode());

            stat_e.executeUpdate();
        }
    }

    public void clearSaleTransactions() throws SQLException {
        String sql = "DELETE FROM SaleTransactions;";
        Statement stat = this.connection.createStatement();
        ResultSet rs = stat.executeQuery(sql);
    }

    // ---------------------- METODI PER LA TABELLA RETURNTRANSACTION ------------------ //
    public void addReturnTransaction(EZReturnTransaction rt) throws SQLException {
        String sql = "INSERT INTO ReturnTransactions(returnId, saleId, status, money) VALUES (?, ?, ?, ?);";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setInt(1, rt.getReturnID());
        pstm.setDouble(2, rt.getSaleTransactionID());
        pstm.setString(3, rt.getStatus());
        pstm.setDouble(4, rt.getMoneyReturned());

        pstm.executeUpdate();

        Map<String, Integer> prodMap = rt.getMapOfProducts();

        for(Map.Entry<String, Integer> e : prodMap.entrySet()) {
            String query_e = "INSERT INTO ReturnProductEntry(returnId, barCode, amount)" +
                    "VALUES(?, ?, ?);";
            PreparedStatement stat_e = this.connection.prepareStatement(query_e);

            stat_e.setInt(1, rt.getReturnID());
            stat_e.setString(2, e.getKey());
            stat_e.setInt(3, e.getValue());

            stat_e.executeUpdate();
        }
    }

    public void updateReturnTransaction(EZReturnTransaction rt) throws SQLException {
        String sql = "UPDATE ReturnTransactions " +
                "SET status = ?, money = ?" +
                "WHERE returnId = ?;";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setString(1, rt.getStatus());
        pstm.setDouble(2, rt.getMoneyReturned());
        pstm.setInt(3, rt.getReturnID());

        pstm.executeUpdate();

        Map<String, Integer> prodMap = rt.getMapOfProducts();

        for(Map.Entry<String, Integer> e : prodMap.entrySet()) {
            String query_e = "UPDATE ReturnProductEntry" +
                    "SET amount = ?" +
                    "WHERE returnId = ? AND barCode = ?;";
            PreparedStatement stat_e = this.connection.prepareStatement(query_e);

            stat_e.setInt(1, e.getValue());
            stat_e.setInt(2, rt.getReturnID());
            stat_e.setString(3, e.getKey());

            stat_e.executeUpdate();
        }
    }

    public Map<Integer, ReturnTransaction> getReturnTransactions() throws SQLException {
        String query = "SELECT * FROM ReturnTransactions;";
        Statement statement =this.connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        Map<Integer, ReturnTransaction> rtMap = new HashMap<>();

        while(rs.next()) {
            // create a new rt object
            EZReturnTransaction rt = new EZReturnTransaction(
                    rs.getInt("saleId"),
                    rs.getInt("returnId"),
                    rs.getString("status")
            );

            // get all return product entries associated with the rt
            String productQuery = "SELECT * FROM ReturnProductEntry WHERE returnId = ?;";
            PreparedStatement pstat = this.connection.prepareStatement(productQuery);

            pstat.setInt(1, rt.getReturnID());

            ResultSet rs_prod = pstat.executeQuery(productQuery);
            Map<String, Integer> prodMap = rt.getMapOfProducts();

            // for each product entry...
            while (rs_prod.next()) {
                // add an element to the rt's product map
                prodMap.put(rs_prod.getString("barCode"), rs_prod.getInt("amount"));
            }
            // set the list for the sale transaction
            rt.setMapOfProducts(prodMap);
            // add the rt to the rt map
            rtMap.put(rt.getReturnID(), rt);
        }

        return rtMap;
    }

    public int getLastReturnID() throws SQLException {
        String sql = "SELECT COALESCE(MAX(returnId), 0) AS maxRetID FROM ReturnTransactions;";
        Statement stat = this.connection.createStatement();
        ResultSet rs = stat.executeQuery(sql);

        return rs.getInt("maxRetID");
    }

    public void clearReturnTransactions() throws SQLException {
        String sql = "DELETE FROM ReturnTransactions;";
        Statement stat = this.connection.createStatement();
        ResultSet rs = stat.executeQuery(sql);
    }

    /*******************************************************************************************/

    public static void main (String[] args) throws SQLException
    {
        EZDatabase db = new EZDatabase();
        //db.createTableCustomer();

        //EZUser user =new EZUser(2, "antonino", "ciao2", "Manager");
        //db.insertUser(user);
        //db.updateUser(user);
        //db.deleteUser(2);

        //EZOrder order =new EZOrder(1, "12345", 5, 3.40);
        //order.setBalanceId(1);
        //order.setStatus("PAYED");

        //db.insertOrder(order2);

        //List<EZOrder> ordini= db.getOrders();

        //System.out.println(ordini.stream().map(o -> o.getOrderId()).count());
        //db.deleteOrder(3);
        //db.updateOrder(order);

    }
}
