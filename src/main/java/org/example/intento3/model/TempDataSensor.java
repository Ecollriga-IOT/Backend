package org.example.intento3.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "temp_data_sensor")
public class TempDataSensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cropField_id", nullable = false)
    private Long cropFieldId;

    @Column(name = "isIrrigation", nullable = false)
    private boolean isIrrigation;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
