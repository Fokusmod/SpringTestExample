package com.example.SpringTestExample.repositories;

import com.example.SpringTestExample.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WallerRepository extends JpaRepository<Wallet, UUID> {

    Wallet findByClientId(UUID uuid);
}
