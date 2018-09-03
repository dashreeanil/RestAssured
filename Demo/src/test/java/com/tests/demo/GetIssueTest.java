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
public class GetIssueTest implements IAutoConst {
	Logger log = LogManager.getLogger(CreateIssueTest.class.getName());	
	@Test
	public void getIssue() throws Exception
	{
		ExtentReport.initExtentReport("./Reports/getIssueTest.html", "getIssue()", "The script is to fetch perticular defect");
		log.debug("The script is to fetch perticular defect");
		String issueID= GenericResourses.getIssueID("The defect raised to check getIssue Api", "The defect raised to check getIssue Api");
		ExtentReport.test.log(Status.DEBUG, "The issue id of the raised defect is "+issueID);
		log.debug("The issue id of the raised defect is "+issueID);
		RestAssured.baseURI = GenericResourses.getPropertyValue(CONFIG_PROPERTIES_PATH, "HOST");
		Response res = given().header("Content-Type", "application/json").
		header("Cookie","JSESSIONID="+GenericResourses.getSessionId("session.value")).log().all().
		when().get("/rest/api/2/issue/"+issueID).
		then().statusCode(200).extract().response();
		
		String responserESULT = res.asString();
		System.out.println(responserESULT);
		ExtentReport.test.log(Status.DEBUG, "The response generated  is  "+responserESULT);
		log.debug("The response generated  is  "+responserESULT);
		GenericResourses.rawToJson(res);
		ExtentReport.extent.flush();
	}
}
