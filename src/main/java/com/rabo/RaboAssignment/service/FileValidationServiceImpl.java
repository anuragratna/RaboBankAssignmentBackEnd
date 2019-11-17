package com.rabo.RaboAssignment.service;

import com.rabo.RaboAssignment.model.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FileValidationServiceImpl implements FileValidationService {
    @Override
    public List<Record> getDuplicateRecords(List<Record> records) {
        Map<Integer, Record> uniqeRecords = new HashMap<Integer, Record>();
        List<Record> duplicateRecords = new ArrayList<Record>();
        for (Record record : records) {
            if (uniqeRecords.containsKey(record.getTransactionRef())) {
                duplicateRecords.add(record);
            } else {
                uniqeRecords.put(record.getTransactionRef(), record);
            }
        }
        List<Record> finalDuplicateRecords = new ArrayList<Record>();
        finalDuplicateRecords.addAll(duplicateRecords);
        for (Record record : duplicateRecords) {
            if (null != uniqeRecords.get(record.getTransactionRef())) {
                finalDuplicateRecords.add(uniqeRecords.get(record.getTransactionRef()));
                uniqeRecords.remove(record.getTransactionRef());
            }
        }
        return finalDuplicateRecords;
    }

    @Override
    public List<Record> getEndBalanceErrorRecords(List<Record> records) {
        List<Record> endBalanceErrorRecords = new ArrayList<Record>();
        for (Record record : records) {
            int diff = BigDecimal.valueOf(record.getMutation()).compareTo(BigDecimal.valueOf(record.getEndBalance()).subtract(BigDecimal.valueOf(record.getStartBalance())));
            if( diff != 0) {
                endBalanceErrorRecords.add(record);
            }
        }
        return endBalanceErrorRecords;
    }
}
