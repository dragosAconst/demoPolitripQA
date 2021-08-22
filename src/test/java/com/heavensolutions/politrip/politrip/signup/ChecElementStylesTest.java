package com.heavensolutions.politrip.politrip.signup;


import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.codeborne.selenide.Selenide.*;

import static org.testng.Assert.assertEquals;

public class ChecElementStylesTest extends AbstractChromeWebDriverTest {

    @Test
    public void testLabelsForm() {
        driver.get("https://politrip.com/account/sign-up");

        // Labels
        String firstNameLabel = driver.findElement(By.id("sign-up-first-name-label")).getText();
        String lastNameLabel = driver.findElement(By.id("sign-up-last-name-label")).getText();
        String emailLabel = driver.findElement(By.id("sign-up-email-label")).getText();
        String passwordLabel = driver.findElement(By.id("sign-up-password-label")).getText();
        String confirmPasswordLabel = driver.findElement(By.id("sign-up-confirm-password-label")).getText();
        String aboutUSLabel = driver.findElement(By.id("sign-up-heard-label")).getText();
        Select drpHeard = new Select(driver.findElement(By.id("sign-up-heard-input")));
        List<WebElement> optionsDrp = drpHeard.getOptions();
        List<String> optionsText = new ArrayList<String>();
        for(WebElement o : optionsDrp) {
            System.out.println(o.getText());
            if (o.isEnabled()) {
                optionsText.add(o.getText());
            }
        }
        List<String> expectedOptions = Arrays.asList("Web-Search", "Social networks", "From a friend", "Other");

        // Placeholders
        String firstNamePlaceholder = driver.findElement(By.id("first-name")).getAttribute("placeholder");
        String lastNamePlaceholder = driver.findElement(By.id("last-name")).getAttribute("placeholder");
        String emailPlaceholder = driver.findElement(By.id("email")).getAttribute("placeholder");
        String passwordPlaceholder = driver.findElement(By.id("sign-up-password-input")).getAttribute("placeholder");
        String confirmPasswordPlaceholder = driver.findElement(By.id("sign-up-confirm-password-input")).getAttribute("placeholder");

        assertEquals(firstNameLabel, "First Name");
        assertEquals(lastNameLabel, "Last Name");
        assertEquals(emailLabel, "Email");
        assertEquals(passwordLabel, "Password");
        assertEquals(confirmPasswordLabel, "Confirm Password");
        assertEquals(aboutUSLabel, "How did you hear about us?");
        assertEquals(optionsText, expectedOptions);

        assertEquals(firstNamePlaceholder, "Joane");
        assertEquals(lastNamePlaceholder, "Smith");
        assertEquals(emailPlaceholder, "your@email.com");
        assertEquals(passwordPlaceholder, "Enter your password");
        assertEquals(confirmPasswordPlaceholder, "Repeat your password");
    }

}
