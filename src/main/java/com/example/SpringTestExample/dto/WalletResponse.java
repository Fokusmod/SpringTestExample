package com.example.SpringTestExample.dto;

import com.example.SpringTestExample.models.Wallet;

import java.math.BigDecimal;

public class WalletResponse {

    private BigDecimal balance;


    public WalletResponse() {
    }

    public WalletResponse(BigDecimal balance) {
        this.balance = balance;
    }

    public WalletResponse(Wallet wallet) {
        this.balance = wallet.getBalance();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Баланс вашего счёта: " + balance + " р.";
    }
}
