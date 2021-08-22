package com.heavensolutions.politrip.politrip.signup;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import static org.testng.Assert.*;

public abstract class AbstractChromeWebDriverTest {

    protected static WebDriver driver;

    public AbstractChromeWebDriverTest() {
        super();
    }

    @BeforeSuite
    public void beforeSuite() {
        //Download the web driver executable
        WebDriverManager.chromedriver().setup();

        //Create a instance of your web driver - chrome
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterSuite
    public void afterSuite() {
        driver.quit();
    }
}
