package org.example.intento3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.intento3.model.Roles;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String userName;
    private String userLastName;
    private String userMotherLastName;
    private String userEmail;
    private String userPassword;
    private String userPhone;
    private LocalDate userBirthDate;
    private String imageData;
    private Roles role;
}
