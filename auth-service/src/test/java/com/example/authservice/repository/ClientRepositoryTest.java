package com.example.authservice.repository;

import com.example.authservice.model.Client;
import com.example.authservice.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository unterTest;

    @BeforeEach
    public void setup(){
        Client client = new Client(
                "abdo",
                "abdel",
                "abdul@gmail.com",
                "renten",
                "062254557",
                Role.USER
        );
        Client client2 = new Client(
                "ali",
                "alol",
                "ali@gmail.com",
                "renten",
                "062254557",
                Role.USER
        );

        unterTest.save(client);
        unterTest.save(client2);
    }

    @Test
    void CheckExistingEmail() {

        String email = "ali@gmail.com";
        Boolean exist = unterTest.existsByEmail(email);

        assertThat(exist).isTrue();
    }

    @Test
    void CheckInExistingEmail() {
        String email = "mohamed@gmail.com";
        Boolean exist = unterTest.existsByEmail(email);

        assertThat(exist).isFalse();
    }

    @Test
    void findExistingClientByEmail() {

        String email = "abdul@gmail.com";
        Optional<Client> client = unterTest.findClientByEmail(email);

        // email is unique so I don't need to add more checks
        assertThat(client).isPresent();
    }
}