package it.polito.ezshop.data.classes;

import it.polito.ezshop.data.User;

import java.util.Locale;

public class EZUser implements User{
    private Integer userId;
    private String username;
    private String password;
    private UserRole role;

    public EZUser(Integer userId,String username, String password,String role){
        this.userId=userId;
        this.username=username;
        this.password=password;
        this.setRole(role);
    }

    @Override
    public Integer getId() {
        return userId;
    }

    @Override
    public void setId(Integer id) {
        this.userId=id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
            this.username=username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password=password;

    }

    @Override
    public String getRole() {
        if(this.role==UserRole.MANAGER){
            return "ShopManager";
        }
        if(this.role==UserRole.ADMINISTRATOR){
            return "Administrator";
        }
        if(this.role==UserRole.CASHIER)
            return "Cashier";
        System.out.println(this.username+ this.role);
        return "";
    }

    @Override
    public void setRole(String role) {
        if((role).trim().equalsIgnoreCase("SHOPMANAGER"))
            this.role=UserRole.MANAGER;
        if((role).trim().equalsIgnoreCase("ADMINISTRATOR"))
            this.role=UserRole.ADMINISTRATOR;
        if((role).trim().equalsIgnoreCase("CASHIER"))
            this.role=UserRole.CASHIER;
    }
}
