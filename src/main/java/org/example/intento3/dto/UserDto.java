package org.example.intento3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.intento3.model.CropField;

import java.time.LocalDate;
import java.util.List;

import org.example.intento3.model.Roles;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String userName;
    private String userLastName;
    private String userMotherLastName;
    private String userEmail;
    private String userPhone;
    private LocalDate userBirthDate;
    private String imageData;
    private List<CropField> cropFields;
    //el rol
    private Roles role;
}
