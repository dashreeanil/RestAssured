package com.tests.demo;

import java.io.IOException;

import org.testng.annotations.Test;

import Com.GenericLib.GenericResourses;
import Com.init.IAutoConst;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
public class GetIssue implements IAutoConst {

	@Test
	public void getIssue() throws Exception
	{
		String issueID= GenericResourses.getIssueID("Pawan ka pehla kida", "Kida karney ki addad hai pawan pujari ko");
		RestAssured.baseURI = GenericResourses.getPropertyValue(CONFIG_PROPERTIES_PATH, "HOST");
		Response res = given().header("Content-Type", "application/json").
		header("Cookie","JSESSIONID="+GenericResourses.getSessionId("session.value")).log().all().
		when().get("/rest/api/2/issue/"+issueID).
		then().statusCode(200).extract().response();
		
		String responserESULT = res.asString();
		System.out.println(responserESULT);
		GenericResourses.rawToJson(res);
		
	}
}
