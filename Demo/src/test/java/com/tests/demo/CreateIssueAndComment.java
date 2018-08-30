package com.tests.demo;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import Com.GenericLib.GenericResourses;
import Com.init.IAutoConst;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateIssueAndComment implements IAutoConst {

	@Test
	public void createIssueAndComment() throws Exception
	{
		String sessionName = GenericResourses.getSessionId("session.name");
		System.out.println(sessionName);
		String sessionID = GenericResourses.getSessionId("session.value");
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
		
		   JsonPath js= GenericResourses.rawToJson(res);
		   String id=js.get("id");
		   System.out.println(id);
		   
		   given().
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
		
		
	}
	}
