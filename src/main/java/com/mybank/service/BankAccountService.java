package com.mybank.service;

import com.mybank.data.BankAccount;
import com.mybank.jpa.BankAccountRepository;
import com.mybank.utils.InvalidAccountException;
import com.mybank.utils.InvalidAmountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BankAccountService {
    private final BankAccountRepository accountRepository;

    @Autowired
    public BankAccountService(BankAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void transferMoney(String fromAccountNumber, String toAccountNumber, BigDecimal amount) throws InvalidAccountException, InvalidAmountException {
        BankAccount fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
        if (fromAccount == null) {
            throw new InvalidAccountException(fromAccountNumber);
        }

        BankAccount toAccount = accountRepository.findByAccountNumber(toAccountNumber);
        if (toAccount == null || fromAccount.getAccountNumber().equals(toAccount.getAccountNumber())) {
            throw new InvalidAccountException(toAccountNumber);
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InvalidAmountException();
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public BigDecimal getBalance(String accountNumber) throws InvalidAccountException {
        BankAccount account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new InvalidAccountException(accountNumber);
        }

        return account.getBalance();
    }
}

