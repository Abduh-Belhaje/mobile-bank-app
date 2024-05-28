package com.example.transactionsservice.service.impl;

import com.example.transactionsservice.dto.TransResponse;
import com.example.transactionsservice.dto.UpdateBalanceRequest;
import com.example.transactionsservice.feign.AccountInterface;
import com.example.transactionsservice.feign.NotifInterface;
import com.example.transactionsservice.model.Operation;
import com.example.transactionsservice.model.Transfer;
import com.example.transactionsservice.model.Type;
import com.example.transactionsservice.repository.OperaRepository;
import com.example.transactionsservice.repository.TransRepository;
import com.example.transactionsservice.service.TransService;
import org.apache.hc.client5.http.impl.Operations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TansServiceImplTest {
    private TransService transService;
    @Mock
    private OperaRepository operaRepository;
    @Mock
    private TransRepository transRepository;
    @Mock
    private AccountInterface accountInterface;
    private NotifInterface notifInterface;

    @BeforeEach
    public void setup(){
        transService = new TansServiceImpl(transRepository,operaRepository,accountInterface,notifInterface);
    }

    @Test
    void testCredit_Successful() {
        // Mocking account balance response
        Long accountNb = 123456789L;
        double amount = 100.0;



    }

    @Test
    void debit() {
    }

    @Test
    void transfer() {
    }

    @Test
    void shouldIncrementBalance() {
        // Mocking account balance response with body
        Long accountNb = 123456789L;
        double amount = 100.0;

        when(accountInterface.getAccountBalance(accountNb)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        // Mocking update balance response
        when(transService.updateBalance(accountNb, 600.0)).thenReturn(true);
        // Executing incrementBalance method
        assertDoesNotThrow(() -> transService.incrementBalance(accountNb, amount));
        // Verifying update balance called with correct parameters
        verify(transService, times(1)).updateBalance(accountNb, 600.0);

    }

    @Test
    void decrementBalance() {
    }

    @Test
    void testUpdateBalance_Successful() {

        // Mocking update balance request
        Long accountNb = 123456789L;
        double newBalance = 1000.0;
        UpdateBalanceRequest updateBalanceRequest = new UpdateBalanceRequest(accountNb, newBalance);

        when(accountInterface.updateBalance(updateBalanceRequest)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
        // Executing updateBalance method
        boolean result = transService.updateBalance(accountNb, newBalance);
        // Verifying result
        assertTrue(result);

    }

    @Test
    void getAllTransHistory() {
    }

    @Test
    void mapTransHis() {
        Transfer transfer1 = new Transfer();
        transfer1.setOperation_date(new Date());
        transfer1.setAmount(100);
        transfer1.setSender_acc(1L);
        transfer1.setRecipient_acc(2L);

        Transfer transfer2 = new Transfer();
        transfer2.setOperation_date(new Date());
        transfer2.setAmount(1000);
        transfer2.setSender_acc(1L);
        transfer2.setRecipient_acc(2L);

        Transfer transfer3 = new Transfer();
        transfer3.setOperation_date(new Date());
        transfer3.setAmount(10000);
        transfer3.setSender_acc(1L);
        transfer3.setRecipient_acc(2L);

        List<Transfer> transfers = new ArrayList<>();
        transfers.add(transfer1);
        transfers.add(transfer2);
        transfers.add(transfer3);

        when(transService.getTransHistory(1L)).thenReturn(transfers);
        List<TransResponse> transResponsesList = new ArrayList<>();
        transResponsesList =  transService.mapTransHis(transResponsesList,1L);

        assertThat(transResponsesList).isNotNull();
        assertThat(transResponsesList.size()).isEqualTo(3);

    }

    @Test
    void mapOptHis() {
        Operation operation1 = new Operation();
        operation1.setType(Type.Withdraw);
        operation1.setAccount_nb(1L);
        operation1.setOperation_date(new Date());
        operation1.setAmount(1000);

        Operation operation2 = new Operation();
        operation2.setType(Type.Withdraw);
        operation2.setAccount_nb(1L);
        operation2.setOperation_date(new Date());
        operation2.setAmount(1000);

        Operation operation3 = new Operation();
        operation3.setType(Type.Withdraw);
        operation3.setAccount_nb(1L);
        operation3.setOperation_date(new Date());
        operation3.setAmount(1000);

        List<Operation> operations = new ArrayList<>();
        operations.add(operation1);
        operations.add(operation2);
        operations.add(operation3);

        when(transService.getOpHistory(1L)).thenReturn(operations);
        List<TransResponse> transResponsesList = new ArrayList<>();
        transResponsesList =  transService.mapOptHis(transResponsesList,1L);

        assertThat(transResponsesList).isNotNull();
        assertThat(transResponsesList.size()).isEqualTo(3);

    }
}