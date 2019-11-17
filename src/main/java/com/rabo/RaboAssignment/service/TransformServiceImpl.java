package com.rabo.RaboAssignment.service;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.rabo.RaboAssignment.constants.AppConstants;
import com.rabo.RaboAssignment.model.Record;
import com.rabo.RaboAssignment.model.Records;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class TransformServiceImpl implements TransformService {

    /**
     * This method extract records from csv or xml and convert into a list of records
     * @param file
     * @param fileType
     * @return List<Record>
     * @throws Exception
     */
    @Override
    public List<Record> extractRecordsFromFile(File file, String fileType) throws Exception {
        if(fileType.equalsIgnoreCase((AppConstants.FILE_TYPE_CSV)) || fileType.equalsIgnoreCase((AppConstants.FILE_TYPE_EXCEL))) {
            return extractStatmentFromCSV(file);
        }
        if(fileType.equalsIgnoreCase((AppConstants.FILE_TYPE_XML)) || fileType.equalsIgnoreCase((AppConstants.FILE_TYPE_TEXT_XML))) {
            return extractStatmentFromXML(file);
        }
        return null;
    }

    /**
     * This method extract records from CSV file
     * @param file
     * @return List<Record>
     * @throws Exception
     */
    private List<Record> extractStatmentFromCSV(File file) throws Exception {

        HeaderColumnNameTranslateMappingStrategy<Record> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<Record>();
        beanStrategy.setType(Record.class);

        Map<String, String> columnMapping = new HashMap<String, String>();
        columnMapping.put("Reference", "transactionRef");
        columnMapping.put("AccountNumber", "accountNumber");
        columnMapping.put("Description", "description");
        columnMapping.put("Start Balance", "startBalance");
        columnMapping.put("Mutation", "mutation");
        columnMapping.put("End Balance", "endBalance");

        beanStrategy.setColumnMapping(columnMapping);

        CsvToBean<Record> csvToBean = new CsvToBean<Record>();
        CSVReader reader = new CSVReader(new FileReader(file));
        List<Record> records = csvToBean.parse(beanStrategy, reader);
        return records;
    }

    /**
     * This method extract records from XML file
     * @param file
     * @return List<Record>
     * @throws Exception
     */
    private List<Record> extractStatmentFromXML(File file) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Records.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Records rootRecord= (Records) jaxbUnmarshaller.unmarshal(file);
        return rootRecord.getRecord();
    }
}
