package com.mybank.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mybank.data.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    BankAccount findByAccountNumber(String accountNumber);
}
