package com.tests.demo;

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

import java.io.IOException;
public class UpdateCommentTest implements IAutoConst {
	
	Logger log = LogManager.getLogger(CreateIssueTest.class.getName());	
	@Test
	public void updateComment() throws Exception {
		ExtentReport.initExtentReport("./Reports/updateCommentTest.html", "updateComment()", "The script is to update the comment of already existing defect");
		log.debug("The script is to update the comment of already existing defect");
		String issueID=GenericResourses.getIssueID("Bug raised for getting comment", "hey this is bug");
		RestAssured.baseURI=GenericResourses.getPropertyValue(CONFIG_PROPERTIES_PATH, "HOST");
		Response res =given().header("Content-Type", "application/json").
		header("Cookie","JSESSIONID="+GenericResourses.getSessionId("session.value")).log().all().body("{" + 
				"    \"body\": \"helllo world\"," + 
				"    \"visibility\": {" + 
				"        \"type\": \"role\"," + 
				"        \"value\": \"Administrators\"" + 
				"    }" + 
				"}").when().post("/rest/api/2/issue/"+issueID+"/comment").
		then().statusCode(201).log().all().extract().response();
		String response = res.asString();
		System.out.println(response);
		ExtentReport.test.log(Status.DEBUG, "The response generated  is  "+response);
		log.debug("The response generated  is  "+response);
	}

}
