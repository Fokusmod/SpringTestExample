package com.example.SpringTestExample.dto;

import java.math.BigDecimal;

public class WalletOperationRequest {

    private String walletUUID;

    private String operationType;

    private BigDecimal amount;

    public WalletOperationRequest () {

    }

    public WalletOperationRequest(String walletUUID, String operationType, BigDecimal amount) {
        this.walletUUID = walletUUID;
        this.operationType = operationType;
        this.amount = amount;
    }

    public String getWalletUUID() {
        return walletUUID;
    }

    public void setWalletUUID(String walletUUID) {
        this.walletUUID = walletUUID;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
