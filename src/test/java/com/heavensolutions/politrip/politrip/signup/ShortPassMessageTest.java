package com.heavensolutions.politrip.politrip.signup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

public class ShortPassMessageTest extends AbstractChromeWebDriverTest{

    @Test
    public void testShortPassMessage(){
        driver.get("https://politrip.com/account/sign-up");

        WebElement password = driver.findElement(By.id("sign-up-password-input"));
        password.sendKeys("test");
        WebElement confirmPassword = driver.findElement(By.id("sign-up-confirm-password-input"));
        confirmPassword.sendKeys("test");


        WebElement errorElement = driver.findElement(By.xpath("//*[@id=\"sign-up-password-div\"]/app-form-control-error-message/em//following-sibling::*"));
        List<WebElement> childs = errorElement.findElements(By.xpath("./child::*"));
        if(childs.size() != 0)
        {
            String textError = childs.get(0).getText();
            System.out.println("Text error:" + textError);
            assertEquals(textError, "Password must contain at least 8 characters, 1 uppercase letter, 1 lowercase letter and 1 digit");
        }
        else
        {
            Assert.fail("The message error does not appear!");
        }
    }
}
