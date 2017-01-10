package com.breynolds.runlog;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.testng.annotations.Test;

import com.breynolds.runlog.web.RunLogInput;

public class RunLogTests {


	private final String DATE_FORMAT = "MM/dd/yyyy";
	
	@Test
	public void testRunLog () {

		RunLogInput runLogInput = new RunLogInput ();
		runLogInput.setRunDate("01/07/2016");
		
		SimpleDateFormat sdf = new SimpleDateFormat (DATE_FORMAT);
    	
    	Date runDate = null;
    	try {
    		runDate = sdf.parse(runLogInput.getRunDate());
    		java.sql.Date runSQLDate = new java.sql.Date (runDate.getTime());
    		System.out.println("got date: " + runSQLDate);
    	}
    	catch (Exception e) {
    		System.out.println("unable to parse date: " + runLogInput.getRunDate());
    	}
		
	}
	
}
