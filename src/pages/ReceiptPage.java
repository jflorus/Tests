package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by jovana on 7/8/16.
 */
public class ReceiptPage extends BasePage {

    @FindBy(xpath = "//span[contains(text(),'Invoice ID:')]")
    private WebElement invoiceID;

    @FindBy(xpath = "//button//span[contains(text(), 'Email')]")
    public WebElement emailReceiptButton;

    @FindBy(xpath = "//button[contains(text(), 'Print')]")
    public WebElement printReceiptButton;

    @FindBy(xpath = "//button[contains(text(), 'Done')]")
    public WebElement doneButton;

    @FindBy(xpath = "//button//span[contains(text(),'emailed')]")
    public WebElement emailed;


}
