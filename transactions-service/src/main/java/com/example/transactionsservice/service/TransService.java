package com.example.transactionsservice.service;

import com.example.transactionsservice.dto.TransResponse;
import com.example.transactionsservice.exception.*;
import com.example.transactionsservice.model.Operation;
import com.example.transactionsservice.model.Transfer;

import java.util.List;
import java.util.Optional;

public interface TransService {

    void credit(Long accountNb , double amount , String phone ) throws InsufficientBalanceException, AccountNotFoundException, UpdatingBalanceException, MessageSendingException;
    void debit(Long accountNb , double amount , String phone ) throws AccountNotFoundException, EmptyBodyException, UpdatingBalanceException, MessageSendingException;
    void transfer(Long sender , Long recipient , double amount , String phone ) throws EmptyBodyException, AccountNotFoundException, InsufficientBalanceException, UpdatingBalanceException, MessageSendingException;
    boolean decrementBalance(Long accountNb , double amount) throws AccountNotFoundException, InsufficientBalanceException;
    boolean incrementBalance(Long accountNb , double amount) throws AccountNotFoundException, EmptyBodyException;
    boolean updateBalance(Long accountNb , double newBalance);
    List<Operation> getOpHistory(Long account_nb);
    List<Transfer> getTransHistory(Long account_nb);
    List<TransResponse> getAllTransHistory(Long account_nb);
    List<TransResponse> mapOptHis(List<TransResponse> transactions , Long account_nb);
    List<TransResponse> mapTransHis(List<TransResponse> transactions , Long account_nb);
}
