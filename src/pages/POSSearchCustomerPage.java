package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by jovana on 6/16/16.
 */
public class POSSearchCustomerPage {

    @FindBy(xpath = "//*[@id=\"pos-app\"]/div/div[2]/div/section[1]/div/div/div/div/div/div[1]/div/input")
    public WebElement memberNameSearchBox;

    public void seachesForMember(String memberLocator, String expectedMemberName){
        memberNameSearchBox.sendKeys(memberLocator);
    }
}
