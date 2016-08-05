package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by jovana on 7/8/16.
 */
public class SelectDeliveryMethodPage extends BasePage {

    @FindBy(xpath = "//button[contains(text(),'Pick up')]")
    public WebElement pickUpButton;

    @FindBy(xpath = "//button[contains(text(),'Ship')]")
    public WebElement shipButton;
}
