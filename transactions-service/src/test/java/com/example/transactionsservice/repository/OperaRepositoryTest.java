package com.example.transactionsservice.repository;

import com.example.transactionsservice.model.Operation;
import com.example.transactionsservice.model.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OperaRepositoryTest {

    @Autowired
    private OperaRepository operaRepository;

    @BeforeEach
    void setUp() {
        Operation operation1 = new Operation();
        operation1.setAccount_nb(1L);
        operation1.setAmount(1402);
        operation1.setOperation_date(new Date());
        operation1.setType(Type.Withdraw);

        Operation operation2 = new Operation();
        operation2.setAccount_nb(1L);
        operation2.setAmount(1402);
        operation2.setOperation_date(new Date());
        operation2.setType(Type.Withdraw);

        Operation operation3 = new Operation();
        operation3.setAccount_nb(2L);
        operation3.setAmount(1402);
        operation3.setOperation_date(new Date());
        operation3.setType(Type.Withdraw);

        operaRepository.save(operation1);
        operaRepository.save(operation2);
        operaRepository.save(operation3);
    }

    @Test
    void findOptByAccountNb() {
        List<Operation> operations = operaRepository.findOptByAccountNb(1L);
        assertThat(operations).isNotNull();
        assertThat(operations.size()).isEqualTo(2);
    }
}
