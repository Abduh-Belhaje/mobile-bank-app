package com.example.transactionsservice.controller;

import com.example.transactionsservice.exception.*;
import com.example.transactionsservice.service.TransService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/transaction")
public class TransController {

    private TransService transService;
    @PostMapping("/credit")
    public ResponseEntity<?> depositMoney(@RequestParam("accountNb") Long accountNb,@RequestParam("amount") double amount , @RequestParam("phone") String phone){
        try{
            transService.credit(accountNb,amount,phone);
            return ResponseEntity.ok("successful credit operation");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UpdatingBalanceException | MessageSendingException | InsufficientBalanceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/debit")
    public ResponseEntity<?> withDrawMoney(@RequestParam("accountNb") Long accountNb,@RequestParam("amount") double amount , @RequestParam("phone") String phone){
        try{
            transService.debit(accountNb,amount,phone);
            return ResponseEntity.ok("successful debit operation");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UpdatingBalanceException | MessageSendingException | EmptyBodyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> withDrawMoney(@RequestParam("sender") Long sender, @RequestParam("recp") Long recp,@RequestParam("amount") double amount , @RequestParam("phone") String phone){
        try{
            transService.transfer(sender,recp,amount,phone);
            return ResponseEntity.ok("successful transfer operation");
        }catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UpdatingBalanceException | MessageSendingException | InsufficientBalanceException | EmptyBodyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/OptHistory")
    public ResponseEntity<?> getOptHistory(Long account_nb){
        return ResponseEntity.ok(transService.getOpHistory(account_nb));
    }
    @GetMapping("/TransHistory")
    public ResponseEntity<?> getTransHistory(Long account_nb){
        return ResponseEntity.ok(transService.getTransHistory(account_nb));
    }

    @GetMapping("/GetAllTrans/{account_nb}")
    public ResponseEntity<?> getAllTransHistory(@PathVariable Long account_nb){
        return ResponseEntity.ok(transService.getAllTransHistory(account_nb));
    }

}
