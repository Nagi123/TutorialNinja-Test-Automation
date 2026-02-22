package com.example.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

 
public class TestHooks {

    @Before
    public void setUp(Scenario scenario) {
        DriverFactory.initDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        DriverFactory.quitDriver();
    }
}