package com.example.SpringTestExample.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @Type(type="org.hibernate.type.PostgresUUIDType")
    @Column(name = "client_id", columnDefinition = "uuid")
    private UUID clientId;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return clientId.equals(wallet.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId);
    }
}
