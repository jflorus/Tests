package pages;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by jovana on 6/14/16.
 */
public class BasePage {

    public static WebDriver driver;

    @FindBy(className = "Error--common")
    public WebElement commonError;

    @FindBy(className = "logout")
    static WebElement logoutLink;

    @FindBy(className = "AppHeader-segment")
    public WebElement transactionType;

    @FindBy(className = "Footer")
    private static WebElement footer;

    @FindBy(className = "fa-spin")
    public static WebElement loadingSpinner;

    public static String orderType;

    static By cancelButton = By.xpath("//a[contains(text(), 'Cancel')]");

    static By checkoutButton = By.xpath("//button[contains(text(), 'Check Out')]");

    static By total = By.xpath("//span[contains(text(),'$')]");

    static By nextButton = By.xpath("//button[contains(text(),'Next')]");

    static By uniqueError = By.className("Error--unique");

    private static void waitForPageLoaded(){
        WebDriverWait wait = new WebDriverWait(driver, 30);
        try{Thread.sleep(500);}
        catch(InterruptedException interruptionException){}
        wait.until(new ExpectedCondition<Object>() {
            public Boolean apply(WebDriver wdriver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState"
                ).equals("complete");
            }
        });
    }

    public static void waitForPageLoaded (String url){
        waitForPageLoaded();
        WebDriverWait wait = new WebDriverWait(driver, 30);

        wait.until(new ExpectedCondition<Object>() {
            public Boolean apply(WebDriver wdriver) {
                return ((JavascriptExecutor) driver).executeScript(
                        "return window.location.href"
                ).equals(url);
            }
        });

    }

    public static void waitForPageLoaded (By by){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        waitForPageLoaded();
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static void waitForPageLoaded (ExpectedCondition condition){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        waitForPageLoaded();
        wait.until(condition);
    }

    public BasePage() {

    }

    public BasePage (WebDriver driver){
        this.driver = driver;
    }

    public String getTitle (){
        return driver.getTitle();
    }

    public void waitForPageLoaded (WebElement webElement){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        waitForPageLoaded();
        wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public boolean contains(String tagname, String expectedValue, List<WebElement> searchResultsItems){
        boolean isFound = false;
        for(WebElement searchResultsItem:searchResultsItems){
            List <WebElement> fields = searchResultsItem.findElements(By.tagName(tagname));
            for (WebElement field:fields){
                isFound = isFound || field.getText().equalsIgnoreCase(expectedValue);
                if (isFound) {
                    return true;
                }
            }
        }
        return isFound;
    }

    public boolean exists (WebElement webElement){
        try{
            webElement.getSize();
        }
        catch(NoSuchElementException exception){
            return false;
        }

        return true;
    }

    public boolean searchContains(String tagname, String expectedValue, List<WebElement> searchResultsItems){
        boolean isFound = false;
        for(WebElement searchResultsItem:searchResultsItems){
            List <WebElement> fields = searchResultsItem.findElements(By.tagName(tagname));
            for (WebElement field:fields){
                isFound = isFound || (field.getText().compareToIgnoreCase(expectedValue)==0);
                if (isFound) {
                    selectItemFromList(searchResultsItem,searchResultsItems);
                    return true;
                }
            }
        }
        return isFound;
    }

    private void selectItemFromList(WebElement selectableElement, List<WebElement> selectableList){
        selectableElement.click();
        //waitForPageLoaded(ExpectedConditions.invisibilityOfAllElements(selectableList));

    }

    public static void checkUniqueError(String expectedError){
        try{Thread.sleep(500);}
        catch(InterruptedException interruptedException){}
        waitForPageLoaded(ExpectedConditions.visibilityOfElementLocated(uniqueError));
        WebElement error = driver.findElement(uniqueError);
        Assert.assertTrue("Actual: " + error.getText() +"\nExpected: " + expectedError,error.getText().equalsIgnoreCase(expectedError));
    }

    public static void checkUniqueError(String expectedError, String headerText){
        //TODO remove explicit sleep and wait on element
        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException interrupted){}

        List <WebElement> errors = getContainer(headerText).findElements(uniqueError);
        for(WebElement error:errors)
        {
            try {
                Assert.assertTrue("Actual: " + error.getText() +"\nExpected: " + expectedError,error.getText().equalsIgnoreCase(expectedError));
                return;
            }
            catch (AssertionError assertionError){}
        }
        throw new AssertionError ("unique error not found");

    }

    public void editField(String headerText){
        String headerXPath = "//header//div[contains(text(),'"+headerText+"')]";
        WebElement header = driver.findElement(By.xpath(headerXPath));
        header.findElement(By.xpath("..")).findElement(By.tagName("Button")).click();
    }

    public static WebElement getNextButton(String headerText){
        for (WebElement element:getContainer(headerText).findElements(nextButton)) {
            if (0 == element.getText().compareToIgnoreCase("Next")) {
                return element;
            }
        }
        return null;
    }

    public static int getResultCount(String headerText, By by){
        WebElement container = getContainer(headerText);
        return container.findElements(by).size();
    }

    public static void logout (){
        logoutLink.click();
    }

    public static WebElement getCancelButton(){
        return footer.findElement(cancelButton);
    }

    public static WebElement getCheckOutButton(){
        return footer.findElement(checkoutButton);
    }

    public static WebElement getTotal(){
        return footer.findElement(total);
    }

    public static WebElement getContainer (String headerText){
        String containerXPath = "//header//div[contains(text(),'"+headerText+"')]//..//..";
        return driver.findElement(By.xpath(containerXPath));
    }
}
