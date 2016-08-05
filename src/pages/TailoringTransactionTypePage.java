package pages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

public class TailoringTransactionTypePage extends BasePage {
    private Map<String, String> data;
    private WebDriver driver;
    private int timeout = 100;

    @FindBy(xpath = "//div[contains(text(),'Logout')]")
    @CacheLookup
    private static WebElement logoutLink;

    @FindBy(css = "a[href='/tickets/create']")
    @CacheLookup
    private WebElement createNewTailoringTicketOrder;

    private final String pageLoadedText = "What would you like to do";

    private final String pageUrl = "/tickets/start";

    @FindBy(css = "a[href='/tickets/audit']")
    @CacheLookup
    private WebElement printPackingList;

    @FindBy(css = "a[href='/tickets/order-list']")
    @CacheLookup
    private WebElement searchOrders;

    @FindBy(css = "a[href='/tickets/transfer-rack']")
    @CacheLookup
    private WebElement transferARack;

    @FindBy(css = "a[href='/tickets/transfer-garments']")
    @CacheLookup
    private WebElement transferGarments;

    public TailoringTransactionTypePage() {
    }

    public TailoringTransactionTypePage(WebDriver driver) {
        this();
        this.driver = driver;
    }

    public TailoringTransactionTypePage(WebDriver driver, Map<String, String> data) {
        this(driver);
        this.data = data;
    }

    public TailoringTransactionTypePage(WebDriver driver, Map<String, String> data, int timeout) {
        this(driver, data);
        this.timeout = timeout;
    }

    /**
     * Click on Create New Tailoring Ticket Order Link.
     *
     * @return the TailoringTransactionTypePage class instance.
     */
    public TailoringTransactionTypePage clickCreateNewTailoringTicketOrderLink() {
        createNewTailoringTicketOrder.click();
        return this;
    }

    /**
     * Click on Print Packing List Link.
     *
     * @return the TailoringTransactionTypePage class instance.
     */
    public TailoringTransactionTypePage clickPrintPackingListLink() {
        printPackingList.click();
        return this;
    }

    /**
     * Click on Search Orders Link.
     *
     * @return the TailoringTransactionTypePage class instance.
     */
    public TailoringTransactionTypePage clickSearchOrdersLink() {
        searchOrders.click();
        return this;
    }

    /**
     * Click on Transfer A Rack Link.
     *
     * @return the TailoringTransactionTypePage class instance.
     */
    public TailoringTransactionTypePage clickTransferARackLink() {
        transferARack.click();
        return this;
    }

    /**
     * Click on Transfer Garments Link.
     *
     * @return the TailoringTransactionTypePage class instance.
     */
    public TailoringTransactionTypePage clickTransferGarmentsLink() {
        transferGarments.click();
        return this;
    }

    /**
     * Verify that the page loaded completely.
     *
     * @return the TailoringTransactionTypePage class instance.
     */
    public TailoringTransactionTypePage verifyPageLoaded() {
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
     * @return the TailoringTransactionTypePage class instance.
     */
    public TailoringTransactionTypePage verifyPageUrl() {
        (new WebDriverWait(driver, timeout)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getCurrentUrl().contains(pageUrl);
            }
        });
        return this;
    }

    public static void logout (){
        logoutLink.click();
    }
}
