package com.mybank;

import com.mybank.data.BankAccount;
import com.mybank.jpa.BankAccountRepository;
import com.mybank.service.BankAccountService;
import com.mybank.utils.InvalidAccountException;
import com.mybank.utils.InvalidAmountException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BankAccountServiceTest {

    @Mock
    private BankAccountRepository accountRepository;

    @InjectMocks
    private BankAccountService accountService;

    @Test
    public void testTransfer_success() {
        BankAccount fromAccount = new BankAccount();
        fromAccount.setAccountNumber("123");
        fromAccount.setBalance(BigDecimal.valueOf(100.00));

        BankAccount toAccount = new BankAccount();
        toAccount.setAccountNumber("456");
        toAccount.setBalance(BigDecimal.valueOf(100.00));

        when(accountRepository.findByAccountNumber("123")).thenReturn(fromAccount);
        when(accountRepository.findByAccountNumber("456")).thenReturn(toAccount);

        accountService.transferMoney("123", "456", BigDecimal.valueOf(10.00));

        assertEquals(BigDecimal.valueOf(90.00), fromAccount.getBalance());
        assertEquals(BigDecimal.valueOf(110.00), toAccount.getBalance());
        verify(accountRepository, times(2)).save(any(BankAccount.class));
    }

    @Test(expected = InvalidAccountException.class)
    public void testTransfer_InvalidAnyAccount() {
        when(accountRepository.findByAccountNumber(anyString())).thenReturn(null);

        accountService.transferMoney("XXX", "ZZZ", BigDecimal.valueOf(1.00));
    }

    @Test(expected = InvalidAccountException.class)
    public void testTransfer_SameAccount() {
        BankAccount fromAccount = new BankAccount();
        fromAccount.setAccountNumber("123");
        fromAccount.setBalance(BigDecimal.valueOf(100.00));

        BankAccount toAccount = new BankAccount();
        toAccount.setAccountNumber("123");
        toAccount.setBalance(BigDecimal.valueOf(100.00));

        when(accountRepository.findByAccountNumber("123")).thenReturn(fromAccount);
        when(accountRepository.findByAccountNumber("123")).thenReturn(toAccount);

        accountService.transferMoney("123", "123", BigDecimal.valueOf(1.00));
    }

    @Test(expected = InvalidAmountException.class)
    public void testTransfer_InvalidAmount() {
        BankAccount fromAccount = new BankAccount();
        fromAccount.setAccountNumber("123");
        fromAccount.setBalance(BigDecimal.valueOf(100.00));

        BankAccount toAccount = new BankAccount();
        toAccount.setAccountNumber("456");
        toAccount.setBalance(BigDecimal.valueOf(100.00));

        when(accountRepository.findByAccountNumber("123")).thenReturn(fromAccount);
        when(accountRepository.findByAccountNumber("456")).thenReturn(toAccount);

        accountService.transferMoney("123", "456", BigDecimal.valueOf(1000.00));
    }
}
