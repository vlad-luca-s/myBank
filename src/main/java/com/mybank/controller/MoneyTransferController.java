package com.mybank.controller;

import com.mybank.dto.MoneyTransferDTO;
import com.mybank.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MoneyTransferController {
    private final BankAccountService accountService;

    @Autowired
    public MoneyTransferController(BankAccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @RequestMapping("/api/v1/transferMoney")
    public ResponseEntity<String> transferMoney(@RequestBody MoneyTransferDTO transferDTO) {
        try {
            accountService.transferMoney(transferDTO.getFromAccount(),
                    transferDTO.getToAccount(),
                    transferDTO.getAmount());
            return ResponseEntity.ok("Transfer completed!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Money transfer error: " + e.getMessage());
        }
    }

    @GetMapping
    @RequestMapping("/api/v1/getMoney")
    public ResponseEntity<String> getBalance(@RequestParam String accountNumber) {
        try {
            return ResponseEntity.ok(accountService.getBalance(accountNumber).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}

