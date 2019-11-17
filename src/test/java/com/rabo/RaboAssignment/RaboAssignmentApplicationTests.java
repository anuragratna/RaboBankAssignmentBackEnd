package com.rabo.RaboAssignment;

import com.rabo.RaboAssignment.constants.AppConstants;
import com.rabo.RaboAssignment.model.Record;
import com.rabo.RaboAssignment.service.FileValidationService;
import com.rabo.RaboAssignment.service.TransformService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class RaboAssignmentApplicationTests {
	@Autowired
	private FileValidationService validationService;
	@Autowired
	private TransformService transformService;

	/**
	 * Test : Duplicate Record Test
	 * scenario : Duplicate records check in given Financial Statement
	 * with Duplicate records
	 */
	@Test
	public void getDuplicateRecordsTestCaseWithDuplilcate() {
		List<Record> inputList = Arrays.asList(
				new Record(152122, "NL20INGB3030303033", 50, 49, "Tickets for Willem Theuß", 24.98),
				new Record(152122, "NL43INGBO0773393871", 100, -20, "Tickets for Willem Theuß", 59.61));
		List<Record> duplicateRecords = validationService.getDuplicateRecords(inputList);
		assertEquals(inputList.size(), duplicateRecords.size());
	}
	/**
	 * Test : Duplicate Record Test
	 * scenario : Duplicate records check in given Financial Statement
	 * with No Duplicate record
	 */
	@Test
	public void getDuplicateRecordsTestCaseWithOutDuplilcate() {
		List<Record> inputList = Arrays.asList(
				new Record(232121, "NL20INGB3030303033", 50, 49, "Tickets for Willem Theuß", 24.98),
				new Record(152122, "NL20INGB1234566777", 100, -20, "Tickets for Willem Theuß", 59.61));
		List<Record> duplicateRecords = validationService.getDuplicateRecords(inputList);
		assertEquals(0, duplicateRecords.size());

	}

	/**
	 * Type : Erroneous End Balance Test
	 * scenario : EndBalance validation in given Financial
	 * Statement with few erroneous calculation records
	 */
	@Test
	public void getEndBalanceErrorRecordsTestCaseWithWrongValue() {
		List<Record> inputList = Arrays.asList(
				new Record(12345, "NL20INGB3030303033", 66.72, -41.74, "Tickets for Willem Theuß", 24.99),
				new Record(54321, "NL40INGB0773393871", 16.52, +43.09, "Tickets for Willem Theuß", 59.80)
				);
		List<Record> endBalanceErrorRecords = validationService.getEndBalanceErrorRecords(inputList);
		assertEquals(inputList.size(), endBalanceErrorRecords.size());

	}

	/**
	 * Type : Erroneous End Balance Test
	 * scenario : EndBalance validation in given Financial
	 * Statement with No erroneous calculation
	 */
	@Test
	public void getEndBalanceErrorRecordsTestCaseWithRightValue() {
		List<Record> inputList = Arrays.asList(
				new Record(12345, "NL40INGB0773393871", 66.72, -41.74, "Tickets for Willem Theuß", 24.98),
				new Record(54321, "NL20INGB3030303033", 16.00, +9.00, "Tickets for Willem Theuß", 25.00)
		);
		List<Record> endBalanceErrorRecords = validationService.getEndBalanceErrorRecords(inputList);
		assertEquals(0, endBalanceErrorRecords.size());
	}

	/**
	 * Type : Extract Data from XML file test
	 * scenario : Processing the input XML file and extracting
	 * values as POJO object for validation process
	 */
	@Test
	public void extractStatmentFromXMLTestCase() {
		File inputFile = new File("records.xml");
		try {
			int totalLineInInputXML = 3; /// let. input XML file has 10 records.
			List<Record> extractedRecords = transformService.extractRecordsFromFile(inputFile, AppConstants.FILE_TYPE_XML);
			assertEquals(totalLineInInputXML, extractedRecords.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Type : Extract Data from CSV file Test
	 * scenario : Processing the input CSV file and extracting
	 * values as POJO object for validation process
	 */
	@Test
	public void extractStatmentFromCSVTestCase() {
		File inputFile = new File("records.csv");
		try {
			int totalLineInInputCSV = 11;// let input csv has 10 records
			List<Record> extractedRecords = transformService.extractRecordsFromFile(inputFile,AppConstants.FILE_TYPE_CSV);
			assertEquals(totalLineInInputCSV, extractedRecords.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
