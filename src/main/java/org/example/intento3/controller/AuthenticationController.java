package org.example.intento3.controller;

import org.example.intento3.dto.AuthenticationResponse;
import org.example.intento3.dto.LoginRequest;
import org.example.intento3.dto.RegisterRequest;
import org.example.intento3.model.CropField;
import org.example.intento3.repository.UserRepository;
import org.example.intento3.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/regadiot/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CropFieldService cropFieldService;

    // URL: http://localhost:8080/api/regadiot/v1/auth/register
    // Method: POST
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegisterRequest request) {
        authService.existsUserByEmail(request);
        authService.validateRegisterRequest(request);
        AuthenticationResponse registeredUser = authService.register(request);
        return new ResponseEntity<AuthenticationResponse>(registeredUser, HttpStatus.CREATED);
    }

    // URL: http://localhost:8080/api/regadiot/v1/auth/login
    // Method: POST
    @Transactional(readOnly = true)
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        AuthenticationResponse loggedUser = authService.login(request);
        return new ResponseEntity<AuthenticationResponse>(loggedUser, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/cropfield/{userId}")
    public ResponseEntity<List<CropField>> getCropFieldsByUserId(@PathVariable(name = "userId") Long userId) {
        List<CropField> cropFields = cropFieldService.getCropFieldsByUserId(userId);
        return new ResponseEntity<List<CropField>>(cropFields, HttpStatus.OK);
    }
}
