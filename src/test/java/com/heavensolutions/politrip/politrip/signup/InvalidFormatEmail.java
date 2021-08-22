package com.heavensolutions.politrip.politrip.signup;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.opencsv.CSVReader;

import static org.testng.Assert.assertEquals;

public class InvalidFormatEmail extends AbstractChromeWebDriverTest{

    // Create the Data Provider and give the data provider a name
    @DataProvider(name = "wrong-emails-format-csv-data-provider")
    public Object[] wrongEmailsFormatCSVDataProvider() {
        return readFromCSVFile("./src/test/resources/wrongEmailsFormat.txt").toArray();
    }

    @Test(dataProvider = "wrong-emails-format-csv-data-provider")
    public void invalidFormatEmail(Object emailAddress) throws InterruptedException {
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
        if(childs.size() != 0)
        {
            String textError = childs.get(0).getText();
            System.out.println("Text error:" + textError);
            assertEquals(textError, "Please enter a valid email address");
        }
        else
        {
            Assert.fail("Text message for the wrong email format does not appear");
        }

        // daca mesajul de format gresit apare verificam sa nu cumva sa putem crea contul cu emailul formatat gresit
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement signUp =  new WebDriverWait(driver, 2).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\" qa_loader-button\"]")));
        Actions actions = new Actions(driver);
        actions.moveToElement(signUp);

        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", signUp);

        // verify if is not redirected
        Thread.sleep(2000);

        String expectedURL = "https://politrip.com/account/sign-up";
        String actualURL = driver.getCurrentUrl();
        assertEquals(actualURL, expectedURL);
    }

    private List<String> readFromCSVFile(String csvFilePath) {
        List<String> lines = new ArrayList<String>();
        try {
            Scanner scanner = new Scanner(new File(csvFilePath));

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
