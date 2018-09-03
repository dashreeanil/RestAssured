package com.tests.demo;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import static  io.restassured.RestAssured.*;

import Com.ExtentListners.ExtentReport;
import Com.GenericLib.GenericResourses;
import Com.init.IAutoConst;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CreateAndDeleteIssueTest implements IAutoConst {
	Logger log = LogManager.getLogger(AssignTest.class.getName());
	@Test
	public void createAndDeleteIssue() throws Exception
	{
		ExtentReport.initExtentReport("./Reports/createAndDeleteIssueTest.html", "createAndDeleteIssue()", "The script is to raise a defect and delete it");
		log.debug("The script is to raise a defect and delete it");
		String issueID = GenericResourses.getIssueID("This issue was created to check delete", "Delete method check");
		ExtentReport.test.log(Status.DEBUG, "The issue id for the bug created is"+issueID);
		RestAssured.baseURI=GenericResourses.getPropertyValue(CONFIG_PROPERTIES_PATH, "HOST");
		Response res =given().header("Content-Type", "application/json").
		header("Cookie","JSESSIONID="+GenericResourses.getSessionId("session.value")).log().all().
		when().delete("/rest/api/2/issue/"+issueID+"").
		then().statusCode(204).log().all().
		extract().response();
		String response = res.asString();
		log.debug("The response generate is"+response);
		ExtentReport.test.log(Status.DEBUG, "The response generate is"+response);
		ExtentReport.extent.flush();
	}

}
