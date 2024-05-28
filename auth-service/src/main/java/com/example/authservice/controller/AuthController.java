package com.example.authservice.controller;

import com.example.authservice.dto.JwtResponse;
import com.example.authservice.dto.SigninRequest;
import com.example.authservice.dto.SignupRequest;
import com.example.authservice.exception.AuthenticationFailedException;
import com.example.authservice.exception.EmailAlreadyExistsException;
import com.example.authservice.exception.InvalidEmailException;
import com.example.authservice.exception.MessageSendingException;
import com.example.authservice.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest request){

        try {
            JwtResponse jwtResponse = authService.Signup(request);
            return ResponseEntity.ok(jwtResponse);

        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed : " + e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SigninRequest request){
        try {
            JwtResponse jwtResponse = authService.Signin(request) ;
            return ResponseEntity.ok(jwtResponse);
        } catch (AuthenticationFailedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch (InvalidEmailException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/isEmailExist")
    public boolean isEmailExist(@RequestParam("email") String email ){
        return authService.checkEmail(email);
    }

    @PostMapping("/generateOtp")
    public ResponseEntity<?> generateOtp(String phone){
        try{
            return ResponseEntity.ok(authService.generateRandomNumber(phone));
        }
        catch (MessageSendingException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
