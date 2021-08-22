package com.heavensolutions.politrip.politrip.signup;

import com.opencsv.CSVReader;
import org.javatuples.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class PasswordDifferent extends AbstractChromeWebDriverTest{

    // Create the Data Provider and give the data provider a name
    @DataProvider(name = "different-passwords-csv-data-provider")
    public Object[][] wrongEmailsFormatCSVDataProvider() {
        return readFromCSVFile("./src/test/resources/DifferentPasswords.csv");
    }

    @Test(dataProvider = "different-passwords-csv-data-provider")
    public void chechDifferentPassword(String pass, String confirmPass) throws IOException {
        driver.get("https://politrip.com/account/sign-up");

        WebElement password = driver.findElement(By.id("sign-up-password-input"));
        WebElement confirmPassword = driver.findElement(By.id("sign-up-confirm-password-input"));

        System.out.println(pass + "  ,   " + confirmPass);
        password.sendKeys(pass);
        confirmPassword.sendKeys(confirmPass);

        Actions actionProvider = new Actions(driver);
        // Performs mouse move action onto the element
        WebElement label = driver.findElement(By.id("sign-up-confirm-password-label"));
        actionProvider.moveToElement(label).click().build().perform();

        WebElement errorElement = driver.findElement(By.xpath("//*[@id=\"sign-up-confirm-password-div\"]/app-form-control-error-message/em"));
        List<WebElement> childs = errorElement.findElements(By.xpath("./child::*"));
        if(childs.size() != 0)
        {
            String textError = childs.get(0).getText();
            System.out.println("Text error:" + textError);
            assertEquals(textError, "Passwords must match");
        }
        else
        {
            Assert.fail("The message error(Passwords must match) does not appear!");
        }
    }

    private Object[][] readFromCSVFile(String csvFilePath) {
        try {
            Reader reader = new FileReader(csvFilePath);
            CSVReader csvreader = new CSVReader(reader);
            List<String[]> list = csvreader.readAll();
            Iterator<String[]> ite= list.iterator();

            List<Pair<String,String>> listTuple= new ArrayList<Pair<String,String>>();

            while(ite.hasNext()){
                String[] data = ite.next();
                listTuple.add(Pair.with(data[0], data[1]));
            }

            Object[][] o = new Object[listTuple.size()][2];
            int i = 0;
            for(Pair<String,String> pereche: listTuple)
            {
                o[i][0] = pereche.getValue0();
                o[i][1] = pereche.getValue1();
                i++;
            }
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
