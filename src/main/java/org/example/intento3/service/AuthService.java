package org.example.intento3.service;

import org.example.intento3.dto.AuthenticationResponse;
import org.example.intento3.dto.LoginRequest;
import org.example.intento3.dto.RegisterRequest;

public interface AuthService {
    public abstract AuthenticationResponse register(RegisterRequest registerRequest);
    public abstract AuthenticationResponse login(LoginRequest loginRequest);
    public void validateRegisterRequest(RegisterRequest registerRequest);
    public void existsUserByEmail(RegisterRequest registerRequest);
}
