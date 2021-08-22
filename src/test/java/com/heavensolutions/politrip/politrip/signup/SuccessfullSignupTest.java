package com.heavensolutions.politrip.politrip.signup;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class SuccessfullSignupTest extends AbstractChromeWebDriverTest{

    @Test
    public void testSuccesfullSignup() {
        driver.get("https://politrip.com/account/sign-up");

        //indentify elements
        WebElement firstName = driver.findElement(By.id("first-name"));
        WebElement lastName = driver.findElement(By.id("last-name"));
        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("sign-up-password-input"));
        WebElement confirmPassword = driver.findElement(By.id("sign-up-confirm-password-input"));

        // complete form and submit
        firstName.sendKeys("Dragos");
        lastName.sendKeys("Aconstantinesei");
        email.sendKeys("demoo@gmail.com");
        password.sendKeys("testDemo1234!");
        confirmPassword.sendKeys("testDemo1234!");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement signUp =  new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\" qa_loader-button\"]")));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", signUp);

        if(!driver.findElements(By.id("sign-up-error-div")).isEmpty())
        {
            Assert.fail("An user with this email is already registered");
        }

        // verify redirect new page
        WebDriverWait wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("sign-up-type-container-content")));

        String expectedURL = "https://politrip.com/account/sign-up/type-select";
        String actualURL = driver.getCurrentUrl();
        assertEquals(actualURL, expectedURL);

        //select "participant" option
        WebElement participantButton =  new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"qa_signup-participant\"]")));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", participantButton);

        // verify redirect Homepage
        wait.until(ExpectedConditions.titleIs("Politrip"));

        expectedURL = "https://politrip.com/";
        actualURL = driver.getCurrentUrl();
        assertEquals(actualURL, expectedURL);

    }

    @AfterMethod
    public void pressLogout(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS)
        {
            WebElement user = driver.findElement(By.xpath("/html/body/app-root/app-home/app-page-template/div/app-header/nav/div[3]/ul/li[6]"));
            Actions action = new Actions(driver);
            action.moveToElement(user).perform();
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            driver.findElement(By.id("qa_header-submenu-logout")).click();
        }
    }
}
