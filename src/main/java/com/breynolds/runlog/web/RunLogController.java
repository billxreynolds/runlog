package com.breynolds.runlog.web;

import java.sql.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.breynolds.runlog.model.RunLogEntity;
import com.breynolds.runlog.service.RunLogService;

@Controller
public class RunLogController {

	@Autowired
	RunLogService runLogService;
	
	private Log log = LogFactory.getLog(this.getClass());
	
    @GetMapping(value = { "/", "/view_calendar"})
    public String viewCalendarPage(Model model) {
    	RunLogEntity runLogEntity = new RunLogEntity();
    	
    	Date today = new Date (System.currentTimeMillis());
    	log.info("setting to date: " + today);
    	runLogEntity.setRunDate(today);
    	model.addAttribute("runLogEntity", runLogEntity); 
        return "view_calendar";
    }

    @PostMapping(value = { "/view_log"})
    public String viewLogPage( @ModelAttribute RunLogEntity runLogEntity, BindingResult bindingResult, Model model ) { 
    	//if (bindingResult.hasErrors()) {
        //    log.error("error loading page");
        //}
    	
    	log.info("looking for run: " + runLogEntity.getRunDate());
    	
    	// load saved run from the database, if any
    	RunLogEntity savedEntity = runLogService.findByRunDate(runLogEntity.getRunDate());
    	
    	if (savedEntity != null) {
    		log.info("loading saved run");
    		model.addAttribute("runLogEntity", savedEntity);	
    	}
    	else {
    		log.info("creating new run");
    		model.addAttribute("runLogEntity", runLogEntity);
    	}
    	
    	return "view_log";
    }

    @PostMapping(value = { "/process_entity"})
    public String editLog(@ModelAttribute RunLogEntity runLogEntity, BindingResult bindingResult, Model model) {
    	//if (bindingResult.hasErrors()) {
        //    log.error("error loading page");
        //}
    	
    	// insert/update the run
    	RunLogEntity savedEntity = runLogService.saveRunLog(runLogEntity);
    	
    	if (savedEntity == null) {
    		log.error("error saving log");
    	}
    	
    	// redirect back to the input page
    	model.addAttribute("recordSaved", true);
    	model.addAttribute("saveMsg", "Run saved for: " + runLogEntity.getRunDate());
    	model.addAttribute("runLogEntity", runLogEntity);
    	return "view_log";
    }

}


