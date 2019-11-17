package com.rabo.RaboAssignment.service;

import com.rabo.RaboAssignment.model.Record;

import java.io.File;
import java.util.List;

public interface TransformService {
    public List<Record> extractRecordsFromFile(File file, String fileType) throws Exception;
}
