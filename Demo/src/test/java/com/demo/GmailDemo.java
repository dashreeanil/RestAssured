package com.demo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;

public class GmailDemo {
	@Test
	public void demo1()
	{
		RestAssured.baseURI="https://www.googleapis.com/gmail/v1/users/";
		given().auth().oauth("527576737095-tiq4cgbnl4maoumb760puvri7l1h4rm4.apps.googleusercontent.com", "Dmj49njz_yFzeGV2iypDLEIr", "", "");
	}

}
