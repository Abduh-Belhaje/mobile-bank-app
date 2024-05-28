package com.example.account_managementservice.controller;

import com.example.account_managementservice.dto.AccountRequest;
import com.example.account_managementservice.dto.UpdateBalanceRequest;
import com.example.account_managementservice.exception.AccountAlreadyExistException;
import com.example.account_managementservice.exception.AccountNotFoundException;
import com.example.account_managementservice.model.Account;
import com.example.account_managementservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody AccountRequest request){
        try {
          accountService.createAccount(request);
          return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (AccountAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/get/{accountNb}")
    public ResponseEntity<?> getAccount(@PathVariable Long accountNb){
        try {
            return ResponseEntity.ok(accountService.getAccount(accountNb));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<?> getAccountByEmail(@PathVariable String email){
        try {
            return ResponseEntity.ok(accountService.getAccountByEmail(email));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/getAccounts")
    public List<Account> getAccounts(){
        return accountService.getAccounts();
    }

    @PostMapping("/updateBalance")
    public ResponseEntity<?> updateBalance(@RequestBody UpdateBalanceRequest request){
        try{
            accountService.updateBalance(request);
            return ResponseEntity.ok(request.getBalance());
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccount(@RequestParam("accountNb") Long accountNb ){
        try{
            accountService.deleteAccount(accountNb);
            return ResponseEntity.ok("deleted");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Deleted");
        }
    }

    @GetMapping("/Balance/{accountNb}")
    public ResponseEntity<?> getAccountBalance(@PathVariable Long accountNb ){
        try {
            return ResponseEntity.ok(accountService.getAccountBalance(accountNb));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
