package com.rabo.RaboAssignment.constants;

import lombok.Data;

@Data
public class AppConstants {
    public static final String VALIDATION_ERROR = "Errr, Validation failed,Check the invalid records and try again";
    public static final String VALIDATION_SUCCESS = "Hurraah, File processed successfully";
    public static final String INVALID_INPUT = "Invalid Input";
    public static final String UNSUPPORTED_FILE_FORMAT = "Only CSV and XML file format is allowed. Try Again";
    public static final int VALID_STATUS = 200;
    public static final int INVALID_STATUS = 400;
    public static final String FILE_TYPE_XML = "application/xml";
    public static final String FILE_TYPE_TEXT_XML = "text/xml";

    public static final String FILE_TYPE_CSV = "text/csv";
    public static final String FILE_TYPE_EXCEL = "application/vnd.ms-excel";
    public static final String CONNECTION_STATUS= "Connection Established";
}
