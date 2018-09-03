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


public class EditIssueTest implements IAutoConst{
	Logger log = LogManager.getLogger(CreateIssueTest.class.getName());	
	@Test
	public void editIssue() throws Exception
	{
		ExtentReport.initExtentReport("./Reports/editIssueTest.html", "editIssue()", "The script is to raise a defect and edit it");
		log.debug("The script is to raise a defect and edit it");
		String issueID = GenericResourses.getIssueID("Depit cardd issue", "Debit card issue");
		ExtentReport.test.log(Status.DEBUG, "The issue id of the raised defect is "+issueID);
		log.debug("The issue id of the raised defect is "+issueID);
		RestAssured.baseURI=GenericResourses.getPropertyValue(CONFIG_PROPERTIES_PATH, "HOST");
		Response res = given().header("Content-Type", "application/json").
		header("Cookie","JSESSIONID="+GenericResourses.getSessionId("session.value")).log().all().
		body("{\"update\":{\"summary\":[{\"set\":\"Bug in business logic\"}],}").
		when().post("/rest/api/2/issue/"+issueID).
		then().statusCode(204).log().all().extract().response();
		
		String response = res.toString();
		ExtentReport.test.log(Status.DEBUG, "The response generated after editing the issue is  "+response);
		log.debug("The response generated after editing the issue is  "+response);
		ExtentReport.extent.flush();
	}

}
