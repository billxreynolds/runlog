package com.breynolds.runlog.service;

import java.sql.Date;

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
		// grr, this repo should call merge for me automatically, 
		// but it isn't. so, delete/reinsert the entity.
		// i hate spring.
		RunLogEntity saved = runLogRepo.findByRunDate(runLog.getRunDate());
		if (saved != null) {
			runLogRepo.delete(runLog);
		}
		
		saved = runLogRepo.save(runLog);
		
		if (log.isDebugEnabled()) {
			log.debug("saved: " + saved);
		}
		return saved;
	}

	public RunLogEntity findByRunDate(Date runDate) {
		RunLogEntity runLog = runLogRepo.findByRunDate(runDate);
		return runLog;
	}
	
}
