package com.company.project.utilities;

import com.company.project.stepdefinition.AssessmentStepDef;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;

import java.util.Map;
//**********************************************************************************************************
//Description: RestAssuredUtility class takes care to getting JSON response using GET, POST method using Rest Assured
//**********************************************************************************************************
public class RestAssuredUtility {
    private static RequestSpecification httpRequest;
    private static RestAssuredUtility apiUtilsInstance = null;
    private static String baseUrl = PropertyManager.getInstance().getBaseUrl();
    private static String passKey = PropertyManager.getInstance().getPassKey();
    private static String passValue = PropertyManager.getInstance().getPassValue();
    static Logger log = Logger.getLogger(AssessmentStepDef.class.getName());

    private RestAssuredUtility() throws ClassNotFoundException{
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(baseUrl);
        builder.setContentType(ContentType.JSON);
        builder.addHeader("Accept-Language", "en");
        RequestSpecification requestSpec = builder.build();
        httpRequest = RestAssured.given().spec(requestSpec);
        httpRequest.queryParams(passKey, passValue);
    }

    // Method created to manipulate the Base URL for negative testing
    private RestAssuredUtility(String tempUrl) throws ClassNotFoundException{
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(tempUrl);
        builder.setContentType(ContentType.JSON);
        builder.addHeader("Accept-Language", "en");
        RequestSpecification requestSpec = builder.build();
        httpRequest = RestAssured.given().spec(requestSpec);
        httpRequest.queryParams(passKey, passValue);
    }

    public static RestAssuredUtility getInstance() {
            try {
                apiUtilsInstance = new RestAssuredUtility();
            }catch (ClassNotFoundException e){
                log.error(e.getStackTrace());
            }
        return apiUtilsInstance;
    }
    // Method created to instantiate the manipulated Base URL constructor for negative testing
    public static RestAssuredUtility getInstance(String tempUrl) {
        try {
            apiUtilsInstance = new RestAssuredUtility(tempUrl);
        }catch (ClassNotFoundException e){
            log.error(e.getStackTrace());
        }
        return apiUtilsInstance;
    }

    public ResponseOptions<Response> GetWithPathParams(String url, Map<String, String> pathParams)  {
        httpRequest.queryParams(pathParams);
        return httpRequest.get(url);
    }

    public ResponseOptions<Response> GetWithPathParams(String url)  {
        return httpRequest.get(url);
    }

    public ResponseOptions<Response> PostWithBody(String url, Object obj)  {
        httpRequest.body(obj);
        return httpRequest.post(url);
    }

    public RequestSpecification getRequestSpecification() {
        return httpRequest;
    }

    public void resetRestAssured() {
        apiUtilsInstance = null;
    }
}
