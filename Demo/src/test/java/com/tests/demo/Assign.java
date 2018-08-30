package com.tests.demo;

import java.io.IOException;

import org.testng.annotations.Test;

import Com.GenericLib.GenericResourses;
import Com.init.IAutoConst;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
public class Assign implements IAutoConst {
	
	@Test
	public void assign() throws Exception
	{
		String issueID = GenericResourses.getIssueID("Pawan ko muh kalla karana hai", "pawan ney delhi ja kar muh kalla nahi karaya");
		RestAssured.baseURI=GenericResourses.getPropertyValue(CONFIG_PROPERTIES_PATH, "HOST");
		Response res =given().header("Content-Type", "application/json").
		header("Cookie","JSESSIONID="+GenericResourses.getSessionId("session.value")).log().all().body("{" + 
				"    \"name\": \"dashreeanil\"" + 
				"}").
		when().put("/rest/api/2/issue/"+issueID+"/assignee").
		then().statusCode(204).log().all().
		extract().response();
		
		String response = res.asString();
		System.out.println(response);
		
	}

}
