package com.company.project.stepdefinition;

import org.testng.annotations.BeforeTest;
import com.company.project.utilities.RestAssuredUtility;

public class TestInitialize {
    @BeforeTest
    public void TestSetup(){
        RestAssuredUtility.getInstance().resetRestAssured();
    }
}
