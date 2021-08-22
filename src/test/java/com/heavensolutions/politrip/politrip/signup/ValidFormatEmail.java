package com.heavensolutions.politrip.politrip.signup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.testng.Assert.assertEquals;

public class ValidFormatEmail extends AbstractChromeWebDriverTest{
    // Create the Data Provider and give the data provider a name
    @DataProvider(name = "wrong-emails-format-csv-data-provider")
    public Object[] validEmailsFormatCSVDataProvider() {
        return readFromTextFile("./src/test/resources/validEmailsFormat.txt").toArray();
    }

    @Test(dataProvider = "wrong-emails-format-csv-data-provider")
    public void validFormatEmail(Object emailAddress) {
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
        email.sendKeys(emailAddress.toString());
        password.sendKeys("testDemo1234!");
        confirmPassword.sendKeys("testDemo1234!");

        WebElement errorElement = driver.findElement(By.xpath("//*[@id=\"sign-up-email-div\"]/app-form-control-error-message"));
        List<WebElement> childs = errorElement.findElements(By.xpath("./child::*"));
        String textError = "";
        if(childs.size() != 0)
        {
            textError = childs.get(0).getText();

        }
        assertEquals(textError, "", "The email adrres is considered wrong");

    }

    private List<String> readFromTextFile(String TextFilePath) {
        List<String> lines = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(new File(TextFilePath));

            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lines;
    }
}
