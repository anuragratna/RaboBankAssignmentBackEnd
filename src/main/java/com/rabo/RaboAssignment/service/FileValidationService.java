package com.rabo.RaboAssignment.service;

import com.rabo.RaboAssignment.model.Record;

import java.util.List;

public interface FileValidationService {
    public List<Record> getDuplicateRecords(List<Record> records);

    public List<Record> getEndBalanceErrorRecords(List<Record> records);
}
