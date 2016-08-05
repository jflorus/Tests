package pages;

import domain.SalesPersonDetails;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

public class POSLoginPage extends BaseLoginPage {
    private Map<String, String> data;
    private WebDriver driver;
    private int timeout = 15;

    @FindBy(css = "button.Button.Button--primary.u-sizeFull")
    @CacheLookup
    private WebElement logIn;

    @FindBy(css = "a.logout.hidden")
    @CacheLookup
    private WebElement logOut;

    private final String pageLoadedText = "Scan your badge and table to get started";

    private final String pageUrl = "/login";

    @FindBy(xpath = "//input[@placeholder='Email']")
    private WebElement email;

    @FindBy(xpath = "//input[@placeholder='Password']")
    private WebElement password;

    @FindBy(xpath = "//input[@placeholder='Table']")
    private WebElement table;

    public POSLoginPage() {
    }

    public POSLoginPage(WebDriver driver) {
        this();
        this.driver = driver;
    }

    public POSLoginPage(WebDriver driver, Map<String, String> data) {
        this(driver);
        this.data = data;
    }

    public POSLoginPage(WebDriver driver, Map<String, String> data, int timeout) {
        this(driver, data);
        this.timeout = timeout;
    }

    /**
     * Click on Log In Button.
     *
     * @return the POSLoginPage class instance.
     */
    public POSLoginPage clickLogInButton() {
        logIn.click();
        return this;
    }

    /**
     * Click on Log Out Link.
     *
     * @return the POSLoginPage class instance.
     */
    public POSLoginPage clickLogOutLink() {
        logOut.click();
        return this;
    }

    /**
     * Fill every fields in the page.
     *
     * @return the POSLoginPage class instance.
     */
    public POSLoginPage fill() {
        setEmail();
        setPassword();
        setTable();
        return this;
    }

    /**
     * Set default value to Scan Your Badge And Table To Get Startedlog In Password field.
     *
     * @return the POSLoginPage class instance.
     */
    public POSLoginPage setEmail() {
        return setEmail(data.get("SCAN_YOUR_BADGE_AND_TABLE_TO_1"));
    }

    /**
     * Set value to Scan Your Badge And Table To Get Startedlog In Password field.
     *
     * @return the POSLoginPage class instance.
     */
    public POSLoginPage setEmail(String scanYourBadgeAndTableTo1Value) {
        email.sendKeys(scanYourBadgeAndTableTo1Value);
        return this;
    }

    /**
     * Set default value to Scan Your Badge And Table To Get Startedlog In Password field.
     *
     * @return the POSLoginPage class instance.
     */
    public POSLoginPage setPassword() {
        return setPassword(data.get("SCAN_YOUR_BADGE_AND_TABLE_TO_2"));
    }

    /**
     * Set value to Scan Your Badge And Table To Get Startedlog In Password field.
     *
     * @return the POSLoginPage class instance.
     */
    public POSLoginPage setPassword(String scanYourBadgeAndTableTo2Value) {
        password.click();
        password.sendKeys(scanYourBadgeAndTableTo2Value);
        return this;
    }

    /**
     * Set default value to Scan Your Badge And Table To Get Startedlog In Text field.
     *
     * @return the POSLoginPage class instance.
     */
    public POSLoginPage setTable() {
        return setTable(data.get("SCAN_YOUR_BADGE_AND_TABLE_TO_3"));
    }

    /**
     * Set value to Scan Your Badge And Table To Get Startedlog In Text field.
     *
     * @return the POSLoginPage class instance.
     */
    public POSLoginPage setTable(String scanYourBadgeAndTableTo3Value) {
        table.click();
        table.sendKeys(scanYourBadgeAndTableTo3Value);
        return this;
    }

    /**
     * Verify that the page loaded completely.
     *
     * @return
     * the POSLoginPage class instance.
     */
    public POSLoginPage verifyPageLoaded() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Object>() {
            public Boolean apply(WebDriver d) {
                return d.getPageSource().contains(pageLoadedText);
            }
        });
        return this;
    }

    /**
     * Verify that current page URL matches the expected URL.
     *
     * @return the POSLoginPage class instance.
     */
    public POSLoginPage verifyPageUrl() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getCurrentUrl().contains(pageUrl);
            }
        });
        return this;
    }

    public POSLoginPage login(SalesPersonDetails salesPersonDetails){
        setEmail(salesPersonDetails.getEmail());
        setPassword(salesPersonDetails.getPassword());
        setTable(salesPersonDetails.getTable());
        clickLogInButton();

        return this;
    }
}
