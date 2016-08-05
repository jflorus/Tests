package pages;

import domain.SalesPersonDetails;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

public class TailoringLoginPage extends BaseLoginPage {
    private Map<String, String> data;
    private WebDriver driver;
    private int timeout = 100;

    @FindBy(css = "#react-root div.Page.Page--narrow form.MgmtForm div.MgmtPanel fieldset:nth-of-type(1) input[type='text']")
    @CacheLookup
    private WebElement email;

    @FindBy(css = "button.MgmtForm-floatingButton")
    @CacheLookup
    private WebElement go;

    private final String pageLoadedText = "";

    private final String pageUrl = "/tickets/start";

    @FindBy(css = "#react-root div.Page.Page--narrow form.MgmtForm div.MgmtPanel fieldset:nth-of-type(2) input[type='password']")
    @CacheLookup
    private WebElement password;

    public TailoringLoginPage() {
    }

    public TailoringLoginPage(WebDriver driver) {
        this();
        this.driver = driver;
    }

    public TailoringLoginPage(WebDriver driver, Map<String, String> data) {
        this(driver);
        this.data = data;
    }

    public TailoringLoginPage(WebDriver driver, Map<String, String> data, int timeout) {
        this(driver, data);
        this.timeout = timeout;
    }

    /**
     * Click on Go Button.
     *
     * @return the TailoringLoginPage class instance.
     */
    public TailoringLoginPage clickGoButton() {
        go.click();
        return this;
    }

    /**
     * Fill every fields in the page.
     *
     * @return the TailoringLoginPage class instance.
     */
    public TailoringLoginPage fill() {
        setEmailTextField();
        setPasswordPasswordField();
        return this;
    }

    /**
     * Fill every fields in the page and submit it to target page.
     *
     * @return the tailoring.stagingclub.com/tickets/start class instance.
     */
    public TailoringLoginPage fillAndSubmit() {
        fill();
        return submit();
    }

    /**
     * Set default value to Email Text field.
     *
     * @return the TailoringLoginPage class instance.
     */
    public TailoringLoginPage setEmailTextField() {
        return setEmailTextField(data.get("EMAIL"));
    }

    /**
     * Set value to Email Text field.
     *
     * @return the TailoringLoginPage class instance.
     */
    public TailoringLoginPage setEmailTextField(String emailValue) {
        email.sendKeys(emailValue);
        return this;
    }

    /**
     * Set default value to Password Password field.
     *
     * @return the TailoringLoginPage class instance.
     */
    public TailoringLoginPage setPasswordPasswordField() {
        return setPasswordPasswordField(data.get("PASSWORD"));
    }

    /**
     * Set value to Password Password field.
     *
     * @return the TailoringLoginPage class instance.
     */
    public TailoringLoginPage setPasswordPasswordField(String passwordValue) {
        password.sendKeys(passwordValue);
        return this;
    }

    /**
     * Submit the form to target page.
     *
     * @return the tailoring.stagingclub.com/tickets/start class instance.
     */
    public TailoringLoginPage submit() {
        clickGoButton();
        TailoringLoginPage target = new TailoringLoginPage(driver, data, timeout);
        PageFactory.initElements(driver, target);
        return target;
    }

    /**
     * Verify that the page loaded completely.
     *
     * @return the TailoringLoginPage class instance.
     */
    public TailoringLoginPage verifyPageLoaded() {
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
     * @return the TailoringLoginPage class instance.
     */
    public TailoringLoginPage verifyPageUrl() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getCurrentUrl().contains(pageUrl);
            }
        });
        return this;
    }

    public TailoringLoginPage login(SalesPersonDetails salesPersonDetails){
        setEmailTextField(salesPersonDetails.getEmail());
        setPasswordPasswordField(salesPersonDetails.getPassword());
        submit();
        return this;
    }
}
