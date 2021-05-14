package it.polito.ezshop.data.classes;


import it.polito.ezshop.data.BalanceOperation;
import it.polito.ezshop.data.SaleTransaction;

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

    public void insertUser(EZUser user) throws SQLException {
        String values = user.getId()+", '"+user.getUsername()+"', '"+user.getPassword()+"', '"+user.getRole()+"'";
        String sql ="INSERT INTO USERS VALUES ("+ values +")";
        Statement statement =this.connection.createStatement();
        statement.executeUpdate(sql);
    }

    public List<EZUser> getUsers() throws SQLException {
        String query = "SELECT * FROM USERS";
        Statement statement =this.connection.createStatement();
        ResultSet rs= statement.executeQuery(query);
        List<EZUser> users = new ArrayList<>();

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

    public void updateUser (EZUser updatedUser) throws SQLException {
        String sql = "UPDATE USERS SET username = ?, password = ?, role = ? WHERE id = ?";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setString(1, updatedUser.getUsername());
        pstm.setString(2, updatedUser.getPassword());
        pstm.setString(3, updatedUser.getRole());
        pstm.setInt(4, updatedUser.getId());

        pstm.executeUpdate();

    }

    /********************* METODI PER LA TABELLA ORDER **************************/
    public void insertOrder(EZOrder order) throws SQLException {

        String values = order.getOrderId()+", '"+order.getBalanceId()+"', '"+order.getProductCode()+"', '"+order.getPricePerUnit()+"', '"+order.getQuantity()+"', '"+order.getStatus()+"'";
        String sql ="INSERT INTO ORDERS VALUES ("+ values +")";
        Statement statement =this.connection.createStatement();
        statement.executeUpdate(sql);
    }

    public List<EZOrder> getOrders() throws SQLException {
        String query = "SELECT * FROM ORDERS";
        Statement statement =this.connection.createStatement();
        ResultSet rs= statement.executeQuery(query);
        List<EZOrder> orders = new ArrayList<>();

        while (rs.next())
        {
            EZOrder ordr = new EZOrder(rs.getInt("id"), rs.getString("productCode"), rs.getInt("quantity"), rs.getDouble("pricePerUnit"));
            ordr.setStatus(rs.getString("status"));
            ordr.setBalanceId(rs.getInt("balanceId"));
            orders.add(ordr);
        }

        return orders;

    }

    public void deleteOrder (Integer id) throws SQLException {


        String sql ="DELETE FROM ORDERS WHERE id =?";
        PreparedStatement pstm =this.connection.prepareStatement(sql);
        pstm.setInt(1, id);
        pstm.executeUpdate();

    }

    public void updateOrder (EZOrder updatedOrder) throws SQLException {
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

    public List<EZBalanceOperation> getBalanceOperations() throws SQLException {
        String query = "SELECT * FROM BalanceOperations;";
        Statement statement =this.connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        List<EZBalanceOperation> boList = new ArrayList<>();

        while(rs.next()) {
            EZBalanceOperation bo = new EZBalanceOperation(
                    rs.getInt("id"),
                    LocalDate.parse(rs.getString("date")),
                    rs.getDouble("money")
            );
            boList.add(bo);
        }

        return boList;
    }

    // ---------------------- METODI PER LA TABELLA SALETRANSACTIONS --------------- //
    public void addSaleTransaction(EZSaleTransaction st) throws SQLException {
        String sql = "INSERT INTO SaleTransactions(id, discountRate, price, status) VALUES (?, ?, ?, ?);";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setInt(1, st.getTicketNumber());
        pstm.setDouble(2, st.getDiscountRate());
        pstm.setDouble(3, st.getPrice());
        pstm.setString(4, st.getStatus());

        pstm.executeUpdate();
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

        pstm.executeUpdate();
    }

    public void deleteSaleTransaction(int id) throws SQLException {
        String sql = "DELETE FROM SaleTransactions" +
                "WHERE id = ?;";
        PreparedStatement pstm =this.connection.prepareStatement(sql);

        pstm.setInt(1, id);

        pstm.executeUpdate();
    }

    public List<EZSaleTransaction> getSaleTransactions() throws SQLException {
        String query = "SELECT * FROM SaleTransactions;";
        Statement statement =this.connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        List<EZSaleTransaction> stList = new ArrayList<>();

        while(rs.next()) {
            EZSaleTransaction st = new EZSaleTransaction(
                    rs.getInt("id"),
                    rs.getDouble("discountRate"),
                    rs.getDouble("price"),
                    rs.getString("status")
            );
            stList.add(st);
            //TODO: PRENDERE LE PRODUCT ENTRY RELATIVE ALLA ST E AGGIUNGERLE ALL'OGGETTO
        }

        return stList;
    }

    public EZSaleTransaction getSaleTransaction(int id) throws SQLException {
        String query = "SELECT * FROM SaleTransactions WHERE id = ?;";
        PreparedStatement statement = this.connection.prepareStatement(query);

        statement.setInt(1, id);

        ResultSet rs = statement.executeQuery(query);
        List<EZSaleTransaction> stList = new ArrayList<>();

        while(rs.next()) {
            EZSaleTransaction st = new EZSaleTransaction(
                    rs.getInt("id"),
                    rs.getDouble("discountRate"),
                    rs.getDouble("price"),
                    rs.getString("status")
            );
            stList.add(st);
            //TODO: PRENDERE LE PRODUCT ENTRY RELATIVE ALLA ST E AGGIUNGERLE ALL'OGGETTO
        }

        return stList.get(0);
    }

    //TODO: DB - METODO PER AGGIORNARE I PRODOTTI DI UNA TRANSAZIONE DI VENDITA

    //TODO: DB - METODO PER AGGIORNARE LA QUANTITA' IN MAGAZZINO DEI PRODOTTI

    //TODO: METODI DB PER LE RETURN TRANSACTION

/*******************************************************************************************/
    public static void main (String[] args) throws SQLException
    {
        EZDatabase db = new EZDatabase();

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
