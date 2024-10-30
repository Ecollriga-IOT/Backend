package org.example.intento3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_names", nullable = false, length = 50)
    private String userNames;

    @Column(name = "user_last_name", nullable = false, length = 50)
    private String userLastName;

    @Column(name = "user_mother_last_name", nullable = false, length = 50)
    private String userMotherLastName;

    @Column(name = "user_email", nullable = false, length = 50)
    private String userEmail;

    @Column(name = "user_birth_date")
    private LocalDate userBirthDate;

    @Column(name = "user_password", nullable = false, length = 100)
    private String userPassword;

    @Column(name = "user_phone", length = 50)
    private String userPhone;

    @Column(name = "image_data")
    private String imageData;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CropField> cropFields;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;

    @Enumerated(EnumType.STRING)
    private Roles role;

}
