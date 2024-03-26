package com.mybank.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mybank.data.BankAccount;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    BankAccount findByAccountNumber(String accountNumber);
}
