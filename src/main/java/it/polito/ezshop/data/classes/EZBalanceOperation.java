package it.polito.ezshop.data.classes;

import it.polito.ezshop.data.BalanceOperation;

import java.time.LocalDate;

/*ANTONINO*/
public class EZBalanceOperation implements BalanceOperation {
    int balanceId;
    LocalDate date;
    double money;
    String type;

    public EZBalanceOperation(int balanceId, LocalDate date, double money) {
        this.balanceId = balanceId;
        this.date = date;
        this.money = money;
        if (money < 0) {
            this.type = "DEBIT";
        }
        else {
            this.type = "CREDIT";
        }
    }

    @Override
    public int getBalanceId() {
        return this.balanceId;
    }

    @Override
    public void setBalanceId(int balanceId) {
        this.balanceId = balanceId;
    }

    @Override
    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public double getMoney() {
        return this.money;
    }

    @Override
    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }
}
