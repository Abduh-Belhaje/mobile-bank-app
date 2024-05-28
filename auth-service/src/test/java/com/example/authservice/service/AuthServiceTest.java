package com.example.authservice.service;

import com.example.authservice.dto.JwtResponse;
import com.example.authservice.dto.SigninRequest;
import com.example.authservice.dto.SignupRequest;
import com.example.authservice.exception.AuthenticationFailedException;
import com.example.authservice.exception.EmailAlreadyExistsException;
import com.example.authservice.exception.InvalidEmailException;
import com.example.authservice.feign.NotifInterface;
import com.example.authservice.model.Client;
import com.example.authservice.model.Role;
import com.example.authservice.repository.ClientRepository;
import com.example.authservice.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {


    private AuthServiceImpl authService;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private NotifInterface notifInterface;


    @BeforeEach
    void setup(){
        authService = new AuthServiceImpl(
                jwtService,
                clientRepository,
                authenticationManager,
                passwordEncoder,
                notifInterface);
    }

    @Test
    void signinForExistingClient() throws AuthenticationFailedException, InvalidEmailException {
        SigninRequest request = new SigninRequest(
            "ali@gmail.com",
                "renten"
        );

        Client client = new Client();

        given(clientRepository.findClientByEmail(request.getC_email()))
                .willReturn(Optional.of(client));

        given(jwtService.generateToken(client))
                .willReturn("generated-token");

        // Call the method under test
        JwtResponse jwtResponse = authService.Signin(request);


        verify(clientRepository).findClientByEmail("ali@gmail.com");
        assertThat(jwtResponse.getToken()).isEqualTo("generated-token");

    }

    @Test
    void signinForInExistingClient() throws AuthenticationFailedException, InvalidEmailException {
        SigninRequest request = new SigninRequest(
            "ali@gmail.com",
                "renten"
        );

        assertThatThrownBy(() -> authService.Signin(request))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessageContaining("Email doesn't exist : " + request.getC_email());

        Client client = new Client();
        verify(jwtService , never()).generateToken(client);
    }

    @Test
    void signupForNewClient() throws EmailAlreadyExistsException {
        SignupRequest request = new SignupRequest(
                "ali",
                "alol",
                "ali@gmail.com",
                "renten",
                "062254557"
        );


        when(clientRepository.existsByEmail(request.getC_email()))
                .thenReturn(false);


        JwtResponse jwtResponse = authService.Signup(request);

        ArgumentCaptor<Client> clientArgumentCaptor = ArgumentCaptor.forClass(Client.class);
        verify(clientRepository).save(clientArgumentCaptor.capture());
        assertThat(clientArgumentCaptor.getValue().getC_email()).isEqualTo(request.getC_email());
        assertThat(clientArgumentCaptor.getValue().getRole()).isEqualTo(Role.USER);

    }


    @Test
    void signupForExistingClient(){
        SignupRequest request = new SignupRequest(
                "ali",
                "alol",
                "ali@gmail.com",
                "rente",
                "062254557"
        );


        when(clientRepository.existsByEmail(request.getC_email()))
                .thenReturn(true);

        assertThatThrownBy(()-> authService.Signup(request))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessage("Email already exists in the database.");
    }
}