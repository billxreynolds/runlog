package com.breynolds.runlog.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.breynolds.runlog.model.RunLogEntity;

public interface RunLogRepository extends CrudRepository<RunLogEntity, Long> {

	RunLogEntity findByRunDate(Date runDate);

	List<RunLogEntity> findByRunDateBetween(Date dayStart, Date dayEnd);

	RunLogEntity findById(Integer id);

}
