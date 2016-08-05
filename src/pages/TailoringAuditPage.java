package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;


public class TailoringAuditPage  extends BasePage{
    private Map<String, String> data;
    private WebDriver driver;
    private int timeout = 100;

    private final String pageLoadedText = "";

    private final String pageUrl = "/tickets/audit";

    @FindBy(className = "ScanAddress-link")
    @CacheLookup
    private WebElement scannerLink;

    @FindBy(css = "a.BottomMessage.BottomMessage-dark")
    @CacheLookup
    private WebElement printList;

    public TailoringAuditPage() {
    }

    public TailoringAuditPage(WebDriver driver) {
        this();
        this.driver = driver;
    }

    public TailoringAuditPage(WebDriver driver, Map<String, String> data) {
        this(driver);
        this.data = data;
    }

    public TailoringAuditPage(WebDriver driver, Map<String, String> data, int timeout) {
        this(driver, data);
        this.timeout = timeout;
    }

    /**
     * Click on Print List Link.
     *
     * @return the TailoringAuditPage class instance.
     */
    public TailoringAuditPage clickPrintListLink() {
        printList.click();
        return this;
    }

    /**
     * Verify that the page loaded completely.
     *
     * @return the TailoringAuditPage class instance.
     */
    public TailoringAuditPage verifyPageLoaded() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getPageSource().contains(pageLoadedText);
            }
        });
        return this;
    }

    /**
     * Verify that current page URL matches the expected URL.
     *
     * @return the TailoringAuditPage class instance.
     */
    public TailoringAuditPage verifyPageUrl() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getCurrentUrl().contains(pageUrl);
            }
        });
        return this;
    }
}
