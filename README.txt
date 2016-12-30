AdmitOne
Bill Reynolds (q774292)
12/27/2016

-------------------------------------------------------------------------------

Abstract
--------

Sample Spring Boot project to model an Order Entry system.

Local Deployment
----------------

Run the following command to start up the application:

	$ mvn spring-boot:run 

This will launch a new Tomcat server on port 8090. 

Login
-----

Use the following credentials to log in:

username: admin
password: admin

REST API
--------

The service supports the following REST API methods:
	
	/api/purchase
	/api/cancel
	/api/exchange

For more on how to call these methods, please see OrderManagementTests.java.
