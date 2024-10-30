package org.example.intento3.service;

import org.example.intento3.model.IrrigationRecord;
public interface IrrigationService {
    public abstract void startIrrigation(Long cropFieldId);
    public abstract IrrigationRecord completeIrrigationRecord(Long cropFieldId);
}
