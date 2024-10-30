package org.example.intento3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IrrigationSuggestionResponse {
    private double actualTemperature;
    private double actualHumidity;
    private boolean isIrrigation;
}