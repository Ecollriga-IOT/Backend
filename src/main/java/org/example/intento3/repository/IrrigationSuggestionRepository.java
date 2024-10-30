package org.example.intento3.repository;

import org.example.intento3.model.IrrigationSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IrrigationSuggestionRepository extends JpaRepository<IrrigationSuggestion, Long> {
    IrrigationSuggestion findByCropFieldId(Long cropFieldId);
}
