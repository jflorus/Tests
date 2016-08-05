package pages;

import domain.SalesPersonDetails;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by jovana on 6/24/16.
 */
public class BaseLoginPage extends BasePage{



    public BaseLoginPage() {

    }

    public BaseLoginPage (WebDriver driver){

        this.driver = driver;
    }

    public void login(SalesPersonDetails salesPersonDetails, By loginButton) {

        String[] credentials = { salesPersonDetails.getEmail(), salesPersonDetails.getPassword(), salesPersonDetails.getTable()};
        login(credentials, loginButton);
        if (salesPersonDetails.isValid) {
            waitForPageLoaded(POSTransactionTypeSelectPage.readyToWearButton);
        }
        else{
            waitForPageLoaded(commonError);
            waitForPageLoaded(uniqueError);
        }
    }

    public void login(String [] credentials, By loginButton){
        List<WebElement> inputs = driver.findElements(By.tagName("input"));
        Assert.assertTrue(credentials.length <= inputs.size());
        for (int i = 0; i<credentials.length; i++){
            inputs.get(i).sendKeys(credentials[i]);
        }
        driver.findElement(loginButton).click();
    }

}
