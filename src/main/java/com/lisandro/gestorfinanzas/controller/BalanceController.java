package com.lisandro.gestorfinanzas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import com.lisandro.gestorfinanzas.model.Balance;
import com.lisandro.gestorfinanzas.service.balance.IBalanceService;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class BalanceController {
    @Autowired
    private IBalanceService balanceService;

    @GetMapping
    public ResponseEntity<List<Balance>> getAllBalance() {
        List<Balance> balances = balanceService.findAll();
        return ResponseEntity.ok(balances);

    }

    @GetMapping("/me/balance")
    public ResponseEntity<Balance> getUserBalance(Authentication auth) {
        Balance balance = balanceService.findByUsername(auth.getName());
        return ResponseEntity.ok(balance);
    }

}
