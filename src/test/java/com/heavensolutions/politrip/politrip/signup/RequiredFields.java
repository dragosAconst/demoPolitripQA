package com.heavensolutions.politrip.politrip.signup;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class RequiredFields extends AbstractChromeWebDriverTest{

    @Test
    public void submitWithBlankFields() throws InterruptedException {
        driver.get("https://politrip.com/account/sign-up");

        WebElement firstName = driver.findElement(By.id("first-name"));
        WebElement lastName = driver.findElement(By.id("last-name"));
        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("sign-up-password-input"));
        WebElement confirmPassword = driver.findElement(By.id("sign-up-confirm-password-input"));

        firstName.sendKeys("");
        lastName.sendKeys("");
        email.sendKeys("");
        password.sendKeys("");
        confirmPassword.sendKeys("");

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement signUp =  new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\" qa_loader-button\"]")));
        Actions actions = new Actions(driver);
        actions.moveToElement(signUp);

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", signUp);

        // verify redirect new page
        Thread.sleep(2000);

        String expectedURL = "https://politrip.com/account/sign-up";
        String actualURL = driver.getCurrentUrl();
        assertEquals(actualURL, expectedURL);
    }


}
