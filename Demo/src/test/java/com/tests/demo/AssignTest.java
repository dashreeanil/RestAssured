package com.tests.demo;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Com.ExtentListners.ExtentReport;
import Com.GenericLib.GenericResourses;
import Com.init.IAutoConst;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
public class AssignTest implements IAutoConst {
	Logger log = LogManager.getLogger(AssignTest.class.getName());
	@Test
	public void assign() throws Exception
	{
		ExtentReport.initExtentReport("./Reports/assignTest.html", "assign()", "The script is to assign a Tester or Developer to the bug raised");
		log.debug("The script is to assign a Tester or Developer to the bug raised");
		String issueID = GenericResourses.getIssueID("Bug created for demo purpose", "The bug was created for demo purpose");
		ExtentReport.test.log(Status.DEBUG, "The issue id for the bug created is"+issueID);
		RestAssured.baseURI=GenericResourses.getPropertyValue(CONFIG_PROPERTIES_PATH, "HOST");
		Response res =given().header("Content-Type", "application/json").
		header("Cookie","JSESSIONID="+GenericResourses.getSessionId("session.value")).log().all().body("{" + 
				"    \"name\": \"dashreeanil\"" + 
				"}").
		when().put("/rest/api/2/issue/"+issueID+"/assignee").
		then().statusCode(204).log().all().
		extract().response();
		
		String response = res.asString();
		log.debug("The response generate is"+response);
		ExtentReport.test.log(Status.DEBUG, "The response generate is"+response);
		ExtentReport.extent.flush();
		
	}

}
