package com.rabo.RaboAssignment.controller;

import com.rabo.RaboAssignment.constants.AppConstants;
import com.rabo.RaboAssignment.model.AppResponse;
import com.rabo.RaboAssignment.model.Record;
import com.rabo.RaboAssignment.service.FileValidationService;
import com.rabo.RaboAssignment.service.TransformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/rabobank")
@Slf4j
public class StatementProcessController {

	@Autowired
	private FileValidationService validatorService;

	@Autowired
	private TransformService extractorService;

	/**
	 * This method has a get verb to test the connection from client side
	 * @return ResponseEntity with 200 Status code and custom message
	 * @throws Exception
	 */
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity test() throws Exception {
		AppResponse appResponse = new AppResponse();
		appResponse.setMessage(AppConstants.CONNECTION_STATUS);
		return new ResponseEntity<>(appResponse, HttpStatus.OK);
	}

	/**
	 * handleFileUpload method in controller
	 * with endPoint as 'uploadStatement'
	 * calls the service to process csv and xml file
	 *
	 * @return ResponseEntity
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadStatement", produces = "application/json", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile multipart) throws Exception {
		log.info("In handleFileUpload method");
		AppResponse appResponse = new AppResponse();
		// check1 : Empty check
		if (!multipart.isEmpty()) {
			// check2: type check. Only CSV and XML file
			if (isValidFileTye(multipart)) {
				List<Record> duplicateRecords = new ArrayList<Record>();
				List<Record> invalidRecords = new ArrayList<Record>();
				File inputFile = convertFileFormat(multipart);
				List<Record> extractedRecords = extractorService.extractRecordsFromFile(inputFile,multipart.getContentType());
				duplicateRecords.addAll(validatorService.getDuplicateRecords(extractedRecords));
				invalidRecords.addAll(validatorService.getEndBalanceErrorRecords(extractedRecords));
				// check3: if processed records have some duplicated transaction reference or invalid end balance
				if (!duplicateRecords.isEmpty() || !invalidRecords.isEmpty()) {
					appResponse.setMessage(AppConstants.VALIDATION_ERROR);
					appResponse.setStatus(AppConstants.INVALID_STATUS);
					appResponse.setDuplicateRecords(duplicateRecords);
					appResponse.setInvalidEndBalanceRecords(invalidRecords);
				} else {
					appResponse.setStatus(AppConstants.VALID_STATUS);
					appResponse.setMessage(AppConstants.VALIDATION_SUCCESS);
				}
			} else {
				appResponse.setMessage(AppConstants.UNSUPPORTED_FILE_FORMAT);
				return new ResponseEntity<>(appResponse, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
			}
		} else {
			appResponse.setMessage(AppConstants.INVALID_INPUT);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		log.info("end handleFileUpload method");
		return new ResponseEntity<>(appResponse, HttpStatus.OK);
	}

	private boolean isValidFileTye(MultipartFile multipart) {
		return multipart.getContentType().equalsIgnoreCase(AppConstants.FILE_TYPE_CSV) || multipart.getContentType().equalsIgnoreCase(AppConstants.FILE_TYPE_XML)
				|| multipart.getContentType().equalsIgnoreCase(AppConstants.FILE_TYPE_TEXT_XML) || multipart.getContentType().equalsIgnoreCase(AppConstants.FILE_TYPE_EXCEL);
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody ResponseEntity handleException(HttpServletRequest request, Exception ex) {
		AppResponse appResponse = new AppResponse();
		appResponse.setMessage(AppConstants.INVALID_INPUT);
		return new ResponseEntity<>(appResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 *	convertFileFormat method converts the Multipart file as input to File format file as output
	 **/
	private File convertFileFormat(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
}
