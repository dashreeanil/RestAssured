package com.tests.demo;

import java.io.IOException;

import org.testng.annotations.Test;
import static  io.restassured.RestAssured.*;
import Com.GenericLib.GenericResourses;
import Com.init.IAutoConst;
import io.restassured.RestAssured;

public class CreateAndDeleteIssue implements IAutoConst {
	
	@Test
	public void createAndDeleteIssue() throws Exception
	{
		String issueID = GenericResourses.getIssueID("This issue was created to check delete", "Delete method check");
		RestAssured.baseURI=GenericResourses.getPropertyValue(CONFIG_PROPERTIES_PATH, "HOST");
		given().header("Content-Type", "application/json").
		header("Cookie","JSESSIONID="+GenericResourses.getSessionId("session.value")).log().all().
		when().delete("/rest/api/2/issue/"+issueID+"").
		then().statusCode(204).log().all().
		extract().response();
	}

}
