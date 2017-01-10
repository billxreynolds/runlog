package com.breynolds.runlog.service;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.breynolds.runlog.model.RunLogEntity;
import com.breynolds.runlog.repository.RunLogRepository;

@Service
public class RunLogService {

	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	RunLogRepository runLogRepo;

	public RunLogEntity saveRunLog (RunLogEntity runLog) {
		RunLogEntity saved = runLogRepo.save(runLog);
		
		if (log.isDebugEnabled()) {
			log.debug("saved: " + saved);
		}
		return saved;
	}

	public RunLogEntity findByRunDate(Date runDate) {
		RunLogEntity runLog = runLogRepo.findByRunDate (runDate);
		return runLog;
	}
	
	public RunLogEntity findByRunDateBetween(Date dayStart, Date dayEnd) {
		RunLogEntity runLog = runLogRepo.findByRunDateBetween(dayStart, dayEnd);
		return runLog;
	}
	
	public RunLogEntity findById (Integer id) {
		RunLogEntity runLog = runLogRepo.findById(id);
		return runLog;
	}
	
}
