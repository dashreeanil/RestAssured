package Com.GenericLib;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import Com.init.IAutoConst;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class GenericResourses implements IAutoConst {

	public static XmlPath rawToXml(Response res) {
		String rawResponse = res.asString();
		XmlPath response = new XmlPath(rawResponse);
		return response;
	}

	public static JsonPath rawToJson(Response res) {
		String rawResponse = res.asString();
		JsonPath response = new JsonPath(rawResponse);
		return response;
	}

	public static String getPropertyValue(String filePath, String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(filePath);
		prop.load(fis);
		String value = prop.getProperty(key);
		return value;
	}

	public static String getSessionId(String value) throws IOException {
		RestAssured.baseURI = getPropertyValue(CONFIG_PROPERTIES_PATH, "HOST");
	Response res =given().header("content-type", "application/json").log().all()
				.body("{ \"username\": \"dashreeanil\", \"password\": \"07gaem1017\" }")
				.when().post("/rest/auth/1/session").then().statusCode(200).log().all().extract().response();
	JsonPath js =rawToJson(res);
	String sessionID = js.get(value);
	return sessionID;
	}
	
	public static String getIssueID(String summary,String description) throws Exception {
		RestAssured.baseURI= getPropertyValue(CONFIG_PROPERTIES_PATH, "HOST");
		Response res=given().header("Content-Type", "application/json").
		header("Cookie","JSESSIONID="+GenericResourses.getSessionId("session.value")).log().all().
		body("{"+
    "\"fields\": {"+
       "\"project\":{"+
          "\"key\": \"RES\""+
       "},"+
       "\"summary\": \""+summary+"\","+
       "\"description\": \""+description+"\","+
       "\"issuetype\": {"+
          "\"name\": \"Bug\""+
       "}"+
   "}}").when().
		post("/rest/api/2/issue").then().log().all().statusCode(201).extract().response();
		
		   JsonPath js= GenericResourses.rawToJson(res);
		   String id=js.get("id");
		   return id;
	}
}
