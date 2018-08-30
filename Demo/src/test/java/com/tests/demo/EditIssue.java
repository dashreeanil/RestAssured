package com.tests.demo;

import java.io.IOException;

import org.testng.annotations.Test;

import Com.GenericLib.GenericResourses;
import Com.init.IAutoConst;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;


public class EditIssue implements IAutoConst{
	
	@Test
	public void editIssue() throws Exception
	{
		String issueID = GenericResourses.getIssueID("Depit cardd issue", "Debit card issue");
		RestAssured.baseURI=GenericResourses.getPropertyValue(CONFIG_PROPERTIES_PATH, "HOST");
		given().header("Content-Type", "application/json").
		header("Cookie","JSESSIONID="+GenericResourses.getSessionId("session.value")).log().all().
		body("{\"update\":{\"summary\":[{\"set\":\"Bug in business logic\"}],}").
		when().post("/rest/api/2/issue/"+issueID).
		then().statusCode(204).log().all().extract().response();
	}

}
