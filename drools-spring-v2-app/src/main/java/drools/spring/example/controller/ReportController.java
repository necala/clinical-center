package drools.spring.example.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import drools.spring.example.model.Patient;
import drools.spring.example.model.Report;
import drools.spring.example.service.DiagnoseService;
import drools.spring.example.service.IngridientAllergyService;
import drools.spring.example.service.MedicamentAllergyService;
import drools.spring.example.service.PatientService;
import drools.spring.example.service.ReportService;

@RestController
@RequestMapping("/api")
public class ReportController {
	
	private static Logger log = LoggerFactory.getLogger(ReportController.class);
	
	private final ReportService reportService;
	
	@Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
	
	@RequestMapping(value = "/reports", method = RequestMethod.GET, 
			produces = "application/json")
	public ResponseEntity<ArrayList<Report>> getReports(){
		
		ArrayList<Report> reports = reportService.getReport();
		
		if (reports.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(reports, HttpStatus.OK);
		
	}
	


}
