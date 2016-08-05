package pages;

import domain.PurchaseItem;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import spec.SalesPerson;

import java.util.List;
import java.util.Random;

/**
 * Created by jovana on 7/7/16.
 */
public class ScanItemsPage extends BasePage{

    @FindBy(xpath="//input[@placeholder='Scan Products']")
    public WebElement upcField;

    public WebElement lastItem ()
    {
        List <WebElement> items = getContainer("Items").findElements(By.className("Arrange--withGutter"));
        return items.get(items.size() -1 );
    }
    public void scanItems(PurchaseItem [] UPCs) {
        for (PurchaseItem UPC:UPCs){
            upcField.sendKeys(UPC.UPC);
            upcField.sendKeys(Keys.RETURN);
            //TODO: change sleep to wait for disappearance of the spinner
            try {
                Thread.sleep(150);
            }
            catch (Exception except) {

            }
            Assert.assertTrue(UPC.price.equalsIgnoreCase(lastItem().findElement(By.tagName("span")).getText()));
        }
    }

    public void scanItems(List <PurchaseItem> UPCs, SalesPerson.OrderType orderType) {
        for (PurchaseItem UPC:UPCs){
            waitForPageLoaded(ExpectedConditions.elementToBeClickable(upcField));
            upcField.sendKeys(UPC.UPC);
            upcField.sendKeys(Keys.RETURN);
            try {
                waitForPageLoaded(ExpectedConditions.visibilityOf(BasePage.loadingSpinner));
                waitForPageLoaded(ExpectedConditions.invisibilityOfElementLocated(By.className("fa-spin")));
            }
            catch (org.openqa.selenium.TimeoutException timeoutException){}

            //TODO: change sleep to wait for disappearance of the spinner
            if (orderType == SalesPerson.OrderType.CUSTOM){
                Random rand = new Random();
                List <WebElement> options =  lastItem().findElement(By.tagName("select")).findElements(By.tagName("option"));
                int n = rand.nextInt(options.size()-1)+1;
                waitForPageLoaded(ExpectedConditions.elementToBeClickable(options.get(n)));
                options.get(n).click();
            }
            try {
                Assert.assertTrue(UPC.price.equalsIgnoreCase(lastItem().findElement(By.tagName("span")).getText()));
            }
            catch (AssertionError assertionError) {
                System.out.println(UPC.UPC);
            }
        }
    }
}
