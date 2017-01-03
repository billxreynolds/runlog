package com.breynolds.runlog.repository;

import java.sql.Date;

import org.springframework.data.repository.CrudRepository;

import com.breynolds.runlog.model.RunLogEntity;

public interface RunLogRepository extends CrudRepository<RunLogEntity, Long> {

  RunLogEntity findByRunDate (Date runDate);

}
