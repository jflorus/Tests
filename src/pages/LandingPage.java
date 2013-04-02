package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class LandingPage {

    WebDriver driver;

    @FindBy(linkText = "Flights")
    private WebElement flightsPageLink;

    public LandingPage(WebDriver driver){
        this.driver = driver;
    }



    public void goToFlightsSearchPage() {
        flightsPageLink.click();
    }
}
