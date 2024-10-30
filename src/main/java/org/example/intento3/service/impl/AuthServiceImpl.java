package org.example.intento3.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.intento3.dto.AuthenticationResponse;
import org.example.intento3.dto.LoginRequest;
import org.example.intento3.dto.RegisterRequest;
import org.example.intento3.exception.ValidationException;
import org.example.intento3.model.Roles;
import org.example.intento3.model.User;
import org.example.intento3.repository.TempDataSensorRepository;
import org.example.intento3.repository.UserRepository;
import org.example.intento3.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TempDataSensorRepository tempDataSensorRepository;




    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .userNames(registerRequest.getUserName())
                .userLastName(registerRequest.getUserLastName())
                .userMotherLastName(registerRequest.getUserMotherLastName())
                .userEmail(registerRequest.getUserEmail())
                .userPassword(registerRequest.getUserPassword())
                .userPhone(registerRequest.getUserPhone())
                .userBirthDate(registerRequest.getUserBirthDate())
                .imageData(registerRequest.getImageData())
                .role(registerRequest.getRole())
                .build();
        var savedUser = userRepository.save(user);
        return AuthenticationResponse.builder()
                .user_id(savedUser.getId())
                .build();
    }

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        // Buscar el usuario por email
        User user = userRepository.findByUserEmail(loginRequest.getUserEmail());

        // Verificar si el usuario existe
        if (user == null) {
            throw new ValidationException("Usuario no encontrado");
        }

        if (!loginRequest.getUserPassword().equals(user.getUserPassword())) {
            throw new ValidationException("Credenciales incorrectas");
        }



        return AuthenticationResponse.builder()
                .user_id(user.getId())
                .build();
    }

    public void validateRegisterRequest(RegisterRequest registerRequest)
    {
        if(registerRequest.getUserName()==null || registerRequest.getUserName().isEmpty())
        {
            throw new ValidationException("El nombre del usuario debe ser obligatorio");
        }
        if(registerRequest.getUserName().length()>50)
        {
            throw new ValidationException("El nombre del usuario no debe exceder los 50 caracteres");
        }
        if(registerRequest.getUserLastName()==null || registerRequest.getUserLastName().isEmpty())
        {
            throw new ValidationException("El apellido del usuario debe ser obligatorio");
        }
        if(registerRequest.getUserLastName().length()>50)
        {
            throw new ValidationException("El apellido del usuario no debe exceder los 50 caracteres");
        }
        if (registerRequest.getUserEmail() == null || registerRequest.getUserEmail().isEmpty()) {
            throw new ValidationException("El email del usuario debe ser obligatorio");
        }
        if (registerRequest.getUserEmail().length() > 50) {
            throw new ValidationException("El email del usuario no debe exceder los 50 caracteres");
        }
        if (registerRequest.getUserPassword() == null || registerRequest.getUserPassword().isEmpty()) {
            throw new ValidationException("La contraseña del usuario debe ser obligatorio");
        }
        if (registerRequest.getUserPassword().length() > 100) {
            throw new ValidationException("La contraseña del usuario no debe exceder los 100 caracteres");
        }
        if (registerRequest.getRole() == null) {
            throw new ValidationException("El rol es requerido");
        }
        if (registerRequest.getRole() != Roles.AGRICULTOR &&
                registerRequest.getRole() != Roles.ADMINISTRADOR &&
                registerRequest.getRole() != Roles.EMPRESA) {
            throw new ValidationException("Rol no válido");
        }

    }

    public void existsUserByEmail(RegisterRequest registerRequest) {
        if (userRepository.existsByUserEmail(registerRequest.getUserEmail())) {
            throw new ValidationException("Ya existe un usuario con el email " + registerRequest.getUserEmail());
        }
    }

}
