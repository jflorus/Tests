package pages;

import domain.SalesPersonDetails;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by jovana on 6/14/16.
 */
public class POSTransactionTypeSelectPage extends BasePage {

    @FindBy(xpath= "//button[contains(text(),'Ready to Wear')]")
    public static WebElement readyToWearButton;

    @FindBy(xpath= "//button[contains(text(),'Custom')]")
    public WebElement customButton;

    @FindBy(xpath= "//button[contains(text(),'Refund Not Available')]")
    public WebElement refundButton;

    @FindBy(xpath = "//span[contains(text(), 'Are you sure you want to log out?')]")
    public WebElement logoutMessage;

    @FindBy(className = "Modal--cancel-button")
    public WebElement logoutCancelButton;

    @FindBy(className = "Modal--ok-button")
    public WebElement logoutOKButton;


    public POSTransactionTypeSelectPage(WebDriver driver){
        this.driver = driver;
    }

    public boolean isUserLoggedIn (SalesPersonDetails salesPersonDetails)
    {
        return (logoutLink.getText().toLowerCase().contains(salesPersonDetails.getEmail().toLowerCase()));
    }

    public void logOut(){
        logoutLink.click();
        waitForPageLoaded(logoutMessage);
        logoutOKButton.click();
        waitForPageLoaded("https://pos.stagingclub.com/login");


    }
}
