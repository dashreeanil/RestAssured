package com.tests.demo;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Com.ExtentListners.ExtentReport;
import Com.GenericLib.GenericResourses;
import Com.init.IAutoConst;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateIssueAndComment implements IAutoConst {
	Logger log = LogManager.getLogger(AssignTest.class.getName());
	@Test
	public void createIssueAndComment() throws Exception
	{
		ExtentReport.initExtentReport("./Reports/createIssueAndCommentTest.html", "createIssueAndComment()", "The script is to raise a defect and comment it");
		log.debug("The script is to raise a defect and comment it");
		String sessionName = GenericResourses.getSessionId("session.name");
		ExtentReport.test.log(Status.DEBUG, "The session name for jira is "+sessionName);
		log.debug("The session name for jira is "+sessionName);
		System.out.println(sessionName);
		String sessionID = GenericResourses.getSessionId("session.value");
		ExtentReport.test.log(Status.DEBUG, "The session id for jira is "+sessionID);
		log.debug("The session id for jira is "+sessionID);
		System.out.println(sessionID);
		
		// For creating a issue
		

		RestAssured.baseURI= GenericResourses.getPropertyValue(CONFIG_PROPERTIES_PATH, "HOST");
		Response res=given().header("Content-Type", "application/json").
		header("Cookie","JSESSIONID="+GenericResourses.getSessionId("session.value")).log().all().
		body("{"+
    "\"fields\": {"+
       "\"project\":{"+
          "\"key\": \"RES\""+
       "},"+
       "\"summary\": \"Bug created to check if script is working\","+
       "\"description\": \"Script working check\","+
       "\"issuetype\": {"+
          "\"name\": \"Bug\""+
       "}"+
   "}}").when().
		post("/rest/api/2/issue").then().log().all().statusCode(201).extract().response();
		   ExtentReport.test.log(Status.DEBUG, "The respones generated after creating the bug is "+res);
		   log.debug("The respones generated after creating the bug is "+res);
		   JsonPath js= GenericResourses.rawToJson(res);
		   String id=js.get("id");
		   System.out.println(id);
		   ExtentReport.test.log(Status.DEBUG, "The issue id for bug created is "+id);
		   log.debug("The issue id for bug created is "+id);
		   Response res1=given().
		   header("Content-Type", "application/json").
		   header("Cookie","JSESSIONID="+GenericResourses.getSessionId("session.value")).log().all().
		   body("{ \"body\": \"Have updated the comment through script\"," + 
		   		"    \"visibility\": {" + 
		   		"        \"type\": \"role\"," + 
		   		"        \"value\": \"Administrators\"" + 
		   		"    }" + 
		   		"}").
		   when().
		   post("/rest/api/2/issue/"+id+"/comment").then().log().all().statusCode(201).extract().response();
		   ExtentReport.test.log(Status.DEBUG, "The response generated after commenting the bug raised is  "+res1.asString());
		   log.debug("The response generated after commenting the bug raised is  "+res1.asString());
		   ExtentReport.extent.flush();
	}
	}
