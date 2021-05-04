package it.polito.ezshop.data.classes;

import it.polito.ezshop.data.BalanceOperation;

import java.time.LocalDate;

public class EZBalanceOperation implements BalanceOperation {
    @Override
    public int getBalanceId() {
        return 0;
    }

    @Override
    public void setBalanceId(int balanceId) {

    }

    @Override
    public LocalDate getDate() {
        return null;
    }

    @Override
    public void setDate(LocalDate date) {

    }

    @Override
    public double getMoney() {
        return 0;
    }

    @Override
    public void setMoney(double money) {

    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public void setType(String type) {

    }
}
