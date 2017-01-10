package com.breynolds.runlog.web;

import java.math.BigDecimal;
import java.util.Date;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.breynolds.runlog.model.RunLogEntity;
import com.breynolds.runlog.service.RunLogService;

@Controller
public class RunLogController {

	@Autowired
	RunLogService runLogService;
	
	private final String DATE_FORMAT = "MM/dd/yyyy";
	
	private Log log = LogFactory.getLog(this.getClass());

    @GetMapping(value = { "/", "/view_calendar"})
    public String viewCalendarPage(Model model) {
    	
    	//
    	// start all dates at midnight - starting at .now() never
    	// guarantees we'll load the same date...
    	//
    	LocalDateTime ldt = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
    	String today = getFormattedDate (ldt);
    	
    	// load today by default
    	RunLogInput runLogInput = new RunLogInput();
    	runLogInput.setRunDate(today);
    	log.info("setting to date: " + today);
    	model.addAttribute("runLogInput", runLogInput); 
        return "view_calendar";	
    }

    @PostMapping(value = { "/view_log"})
    public String viewLogPage( Model model, @ModelAttribute RunLogInput runLogInput, BindingResult bindingResult ) { 
    	for (ObjectError error : bindingResult.getAllErrors() ) {
    		log.info("got error: " + error.getDefaultMessage());
    	}

    	// inspect the date selected by the user, try and load from the DB
    	log.info("looking for run: " + runLogInput.getRunDate());
    	
    	// load saved run from the database, if any
    	Date runDate = parseRunDate (runLogInput);
    	RunLogEntity savedEntity = runLogService.findByRunDate (runDate);
    	
    	if (savedEntity != null) {
    		log.info("loading saved run for " + runDate);
    		runLogInput.setRunID(savedEntity.getId());
    		runLogInput.setComments(savedEntity.getComments());
    		runLogInput.setMileage(savedEntity.getMileageAsString());
    		runLogInput.setRoute(savedEntity.getRoute());    		
    		runLogInput.setRunTime(savedEntity.getRunTimeAsString());    		
    		runLogInput.setExisting(true);
    	}
    	else {
    		log.info("creating new run for " + runDate);
    		runLogInput.setExisting(false);
    	}
    	
		model.addAttribute("runLogInput", runLogInput);
    	return "view_log";
    }

    @PostMapping(value = { "/process_entity"})
    public String editLog(Model model, @ModelAttribute RunLogInput runLogInput, BindingResult bindingResult ) {
    	
    	for (ObjectError error : bindingResult.getAllErrors() ) {
    		log.info("got error: " + error.getDefaultMessage());
    	}
    	
    	RunLogEntity runLogEntity = null;
    	
    	log.info ("isNew? " + runLogInput.isExisting());
    	if (!runLogInput.isExisting()) {
    		// create new run
    		Date runDate = parseRunDate(runLogInput);
    		runLogEntity = new RunLogEntity ();
    		runLogEntity.setRunDate(runDate);
    	}
    	else {
    		// load exsting run
    		runLogEntity = runLogService.findById(runLogInput.getRunID());
    	}
    	
    	// copy data from the form into the entity    	
		runLogEntity.setComments(runLogInput.getComments());
		if (StringUtils.isNotBlank(runLogInput.getMileage())) {		
			runLogEntity.setMileage( formatBigDecimal ( runLogInput.getMileage() ) );
		}
		else {
			runLogEntity.setMileage( null );
		}
		runLogEntity.setRoute(runLogInput.getRoute());
		
		if (StringUtils.isNotBlank(runLogInput.getRunTime())) {
			runLogEntity.setRunTime( formatTime (runLogInput.getRunTime()) );			
		}
		else {
			runLogEntity.setRunTime( null );
		}

    	// insert/update the run
    	RunLogEntity savedEntity = runLogService.saveRunLog(runLogEntity);
    	
    	if (savedEntity == null) {
    		log.error("error saving log");
    	}
    	
    	// redirect back to the input page
    	runLogInput.setRunID(savedEntity.getId());    	    	
		runLogInput.setComments(savedEntity.getComments());
		runLogInput.setMileage(savedEntity.getMileageAsString());
		runLogInput.setRoute(savedEntity.getRoute());    		
		runLogInput.setRunTime(savedEntity.getRunTimeAsString());    		
		runLogInput.setExisting(true);    	
    	
    	model.addAttribute("recordSaved", true);
    	model.addAttribute("saveMsg", "Run saved for: " + runLogInput.getRunDate());
    	model.addAttribute("runLogInput", runLogInput);
    	return "view_log";
    }
   
    @GetMapping(value = { "/view_report"})
    public String viewReportPage(Model model) {
    	
    	// TODO: implement this page
    	
        return "view_report";	
    }
    
    
    private String getFormattedDate (LocalDateTime ldt) {
    	Instant instant = ldt.atZone(ZoneId.systemDefault()).toInstant();
    	Date d = Date.from(instant);
    	SimpleDateFormat sdf = new SimpleDateFormat (DATE_FORMAT);
    	String dfmt = sdf.format(d);
    	return dfmt;
    } 
    
    private Date parseRunDate (RunLogInput runLogInput) {
    	SimpleDateFormat sdf = new SimpleDateFormat (DATE_FORMAT);
    	
    	Date runDate = null;
    	try {
    		runDate = sdf.parse(runLogInput.getRunDate());
    	}
    	catch (Exception e) {
    		log.error("unable to parse date: " + runLogInput.getRunDate());
    	}
    	return runDate;	
    }
    
    private Time formatTime (String input) {
    	Time output = null;
    	try {
			output = Time.valueOf( input);
		}
		catch (Exception e) {
			log.debug("unable to parse time: " + input);
		}
    	return output;
    }
    
    private BigDecimal formatBigDecimal (String input) {
    	BigDecimal output = null;
    	try {
    		output = BigDecimal.valueOf( Double.parseDouble(input));
    	}
    	catch (Exception e) {
			log.debug("unable to parse big decimal: " + input);
		}
    	return output;
    }
    
}


