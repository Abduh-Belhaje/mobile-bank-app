package com.example.transactionsservice.service.impl;

import com.example.transactionsservice.dto.NotifRequest;
import com.example.transactionsservice.dto.TransResponse;
import com.example.transactionsservice.dto.UpdateBalanceRequest;
import com.example.transactionsservice.exception.*;
import com.example.transactionsservice.feign.AccountInterface;
import com.example.transactionsservice.feign.NotifInterface;
import com.example.transactionsservice.model.Operation;
import com.example.transactionsservice.model.Transfer;
import com.example.transactionsservice.model.Type;
import com.example.transactionsservice.repository.OperaRepository;
import com.example.transactionsservice.repository.TransRepository;
import com.example.transactionsservice.service.TransService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class TansServiceImpl implements TransService {

    private TransRepository transRepository;

    private OperaRepository operaRepository;

    private AccountInterface accountInterface;

    private NotifInterface notifInterface;

    @Override
    public void debit(Long accountNb, double amount , String phone ) throws UpdatingBalanceException, EmptyBodyException, AccountNotFoundException, MessageSendingException {
        boolean resp = this.incrementBalance(accountNb,amount);
        if(resp){
            // if the update is successful than add the trans to the database
            Operation operation = new Operation();
            operation.setAccount_nb(accountNb);
            operation.setAmount(amount);
            operation.setOperation_date(new Date());
            operation.setType(Type.Deposit);

            operation = operaRepository.save(operation);
            String notificationMessage = "Your recent transaction was successful:\\n" +
                    "🔹 TYPE: Deposit \\n" +
                    "🔹 Date: " + operation.getOperation_date().toString().substring(0,10) + "\\n" +
                    "🔹 Amount: " + operation.getAmount();

            sendNotification(phone,notificationMessage);
        }else{
            throw new UpdatingBalanceException("the process of updating account balance not completed");
        }
    }

    @Override
    public void credit(Long accountNb, double amount , String phone ) throws InsufficientBalanceException, AccountNotFoundException, UpdatingBalanceException, MessageSendingException {

        boolean resp = this.decrementBalance(accountNb,amount);
        if(resp){
            // if the update is successful than add the trans to the database
            Operation operation = new Operation();
            operation.setAccount_nb(accountNb);
            operation.setAmount(amount);
            operation.setOperation_date(new Date());
            operation.setType(Type.Withdraw);

            operation = operaRepository.save(operation);
            String notificationMessage = "Your recent transaction was successful:\\n" +
                    "🔹 TYPE: Withdraw \\n" +
                    "🔹 Date: " + operation.getOperation_date() + "\\n" +
                    "🔹 Amount: " + operation.getAmount();

            sendNotification(phone,notificationMessage);
        }else{
            throw new UpdatingBalanceException("the process of updating account balance not completed");
        }
    }

    @Override
    public void transfer(Long sender, Long recipient, double amount, String phone ) throws EmptyBodyException, AccountNotFoundException, InsufficientBalanceException, UpdatingBalanceException, MessageSendingException {
        boolean resp1 = incrementBalance(recipient,amount);
        boolean resp2 = decrementBalance(sender,amount);

        if(resp1 && resp2){
            // if both operations succeeded we save it to DB
            Transfer transfer = new Transfer();
            transfer.setAmount(amount);
            transfer.setSender_acc(sender);
            transfer.setRecipient_acc(recipient);
            transfer.setOperation_date(new Date());

            transfer = transRepository.save(transfer);
            String notificationMessage = "Your recent transaction was successful:\\n" +
                    "🔹 TYPE: TRANSFER \\n" +
                    "🔹 Date: " + transfer.getOperation_date() + "\\n" +
                    "🔹 Amount: " + transfer.getAmount() + "\\n" +
                    "🔹 TO: " + transfer.getRecipient_acc();


            sendNotification(phone,notificationMessage);

        }else{
            throw new UpdatingBalanceException("the process of updating accounts balance not completed");
        }
    }

    @Override
    public boolean incrementBalance(Long accountNb , double amount) throws AccountNotFoundException, EmptyBodyException {
        ResponseEntity<?> response = accountInterface.getAccountBalance(accountNb);

        if(response.getStatusCode().is2xxSuccessful()){
            if (response.getBody() != null){
                // Adding the amount to prev balance
                double prevBalance = (double) response.getBody();
                double newBalance = prevBalance + amount;
                return this.updateBalance(accountNb,newBalance);
            }else{
                throw new EmptyBodyException("Empty Body !");
            }
        }else{
            throw new AccountNotFoundException("Account with nb : " + accountNb + " Not found");
        }

    }

    @Override
    public boolean decrementBalance(Long accountNb , double amount) throws AccountNotFoundException, InsufficientBalanceException {
        ResponseEntity<?> response = accountInterface.getAccountBalance(accountNb);

        if (response.getStatusCode().is2xxSuccessful()){
            if ( response.getBody() != null && (double) response.getBody() >= amount){
                // Adding the amount to prev balance
                double prevBalance = (double) response.getBody();
                double newBalance = prevBalance - amount;

                return this.updateBalance(accountNb,newBalance);
            }else{
                throw new InsufficientBalanceException("Insufficient account Balance");
            }
        }else{
            throw new AccountNotFoundException("Account with nb : " + accountNb + " Not found");
        }
    }

    public void sendNotification(String phone , String message) throws MessageSendingException {
        NotifRequest request = new NotifRequest(
                phone,
                message
        );

        ResponseEntity<?> response = notifInterface.sendMessage(request);

        if(!response.getStatusCode().is2xxSuccessful()){
            throw new MessageSendingException("A problem was acqurred when sending message");
        }
    }
    @Override
    public boolean updateBalance(Long accountNb, double newBalance) {
        UpdateBalanceRequest updateBalanceRequest = new UpdateBalanceRequest(accountNb, newBalance);
        ResponseEntity<?> response = accountInterface.updateBalance(updateBalanceRequest);

        return response.getStatusCode().is2xxSuccessful();
    }

    @Override
    public List<Operation> getOpHistory(Long account_nb) {
        return operaRepository.findOptByAccountNb(account_nb);
    }

    @Override
    public List<Transfer> getTransHistory(Long account_nb) {
        return transRepository.findTransByAccountNb(account_nb);
    }

    @Override
    public List<TransResponse> getAllTransHistory(Long account_nb) {
        List<TransResponse> transactions = new ArrayList<>();
        mapTransHis(transactions,account_nb);
        mapOptHis(transactions,account_nb);
        return transactions;
    }

    public List<TransResponse> mapTransHis(List<TransResponse> transactions , Long account_nb){
        List<Transfer> transfers = getTransHistory(account_nb);
        for (int i=0;i<transfers.size();i++) {
            TransResponse transResponse = new TransResponse();
            transResponse.setType("Transfer");
            transResponse.setAmount(transfers.get(i).getAmount());
            transResponse.setDate(transfers.get(i).getOperation_date());
            if(transfers.get(i).getSender_acc().equals(account_nb)){
                transResponse.setTo(transfers.get(i).getRecipient_acc().toString());
            }else {
                transResponse.setFrom(transfers.get(i).getSender_acc().toString());
            }
            transactions.add(transResponse);
        }
        return transactions;
    }

    public List<TransResponse> mapOptHis(List<TransResponse> transactions , Long account_nb){
        List<Operation> operations = getOpHistory(account_nb);
        for (int i=0;i<operations.size();i++) {
            TransResponse transResponse = new TransResponse();
            transResponse.setType(String.valueOf(operations.get(i).getType()));
            transResponse.setAmount(operations.get(i).getAmount());
            transResponse.setDate(operations.get(i).getOperation_date());
            transactions.add(transResponse);
        }
        return transactions;
    }
}
