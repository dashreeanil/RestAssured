package com.tests.demo;

import org.apache.logging.log4j.*;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import Com.ExtentListners.ExtentReport;
import Com.GenericLib.GenericResourses;
import Com.init.IAutoConst;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CreateIssueTest implements IAutoConst {
Logger log = LogManager.getLogger(CreateIssueTest.class.getName());
	@Test
	public void createIssue() throws IOException {
		ExtentReport.initExtentReport("./Reports/createIssueTest.html", "createIssue()", "The script is to raise a defect ");
		log.debug("The script is to raise a defect ");
		String sessionName = GenericResourses.getSessionId("session.name");
		ExtentReport.test.log(Status.DEBUG, "The session name for jira is "+sessionName);
		log.debug("The session name for jira is "+sessionName);
		System.out.println(sessionName);
		String sessionID = GenericResourses.getSessionId("session.value");
		ExtentReport.test.log(Status.DEBUG, "The session id for jira is "+sessionID);
		log.debug("The session id for jira is "+sessionID);
		System.out.println(sessionID);
		log.info(sessionID);
		// For creating a issue
		

		RestAssured.baseURI= GenericResourses.getPropertyValue(CONFIG_PROPERTIES_PATH, "HOST");
		Response res=given().header("Content-Type", "application/json").
		header("Cookie","JSESSIONID="+GenericResourses.getSessionId("session.value")).log().all().
		body("{"+
    "\"fields\": {"+
       "\"project\":{"+
          "\"key\": \"RES\""+
       "},"+
       "\"summary\": \"Issue 5 for adding comment\","+
       "\"description\": \"Creating my second bug\","+
       "\"issuetype\": {"+
          "\"name\": \"Bug\""+
       "}"+
   "}}").when().
		post("/rest/api/2/issue").then().log().all().statusCode(201).extract().response();
		   ExtentReport.test.log(Status.DEBUG, "The response generated after bug is raised   "+res.asString());
		   log.debug("The response generated after commenting the bug raised is  "+res.asString());
		   JsonPath js= GenericResourses.rawToJson(res);
		   String id=js.get("id");
		   ExtentReport.test.log(Status.DEBUG, "The issue id for the generated issue is  "+id);
		   log.debug("The issue id for the generated issue is  "+id);
		   ExtentReport.extent.flush();
	}
}
