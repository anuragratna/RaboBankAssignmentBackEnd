package com.rabo.RaboAssignment.model;

import lombok.Data;

import java.util.List;
@Data
public class AppResponse {
    private String message;
    private int status;
    private List<Record> duplicateRecords;
    private List<Record> invalidEndBalanceRecords;

    public List<Record> getDuplicateRecords() {
        return duplicateRecords;
    }

    public void setDuplicateRecords(List<Record> duplicateRecords) {
        this.duplicateRecords = duplicateRecords;
    }

    public List<Record> getInvalidEndBalanceRecords() {
        return invalidEndBalanceRecords;
    }

    public void setInvalidEndBalanceRecords(List<Record> invalidEndBalanceRecords) {
        this.invalidEndBalanceRecords = invalidEndBalanceRecords;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

