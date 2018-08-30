package com.tests.demo;

import org.testng.annotations.Test;

import Com.GenericLib.GenericResourses;
import Com.init.IAutoConst;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CreateIssue implements IAutoConst {
 
	@Test
	public void demoOneTest() throws IOException {
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
       "\"summary\": \"Issue 5 for adding comment\","+
       "\"description\": \"Creating my second bug\","+
       "\"issuetype\": {"+
          "\"name\": \"Bug\""+
       "}"+
   "}}").when().
		post("/rest/api/2/issue").then().log().all().statusCode(201).extract().response();
		
		   JsonPath js= GenericResourses.rawToJson(res);
		   String id=js.get("id");
		   System.out.println(id);
		
		
	}
}
