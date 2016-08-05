package pages;

import domain.SalesPersonDetails;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import spec.SalesPerson;

import java.util.List;

/**
 * Created by jovana on 6/30/16.
 */
public class SelectStylistPage extends BasePage{
    @FindBy(className = "purchase-page-section-body")
    WebElement memberDetails;

    @FindBy(xpath="//*[@id=\"pos-app\"]/div/div[2]/div/section[2]/div")
    private WebElement stylistListArea;

    @FindBy(xpath="//input[@placeholder='Stylist name']")
    WebElement stylistNameInput;

    @FindBy(xpath="//*[@id=\"pos-app\"]/div/div[2]/div/section[1]/div/div/div/div[2]/div/div[1]/div[2]")
    WebElement memberEmail;

    @FindBy(xpath = "//header//div[contains(text(),'Member')]")
    public WebElement memberHeader;

    By primaryButton = By.xpath("//button[contains(text(),'Primary')]");

    By additionalButton = By.xpath("//button[contains(text(),'Additional')]");

    By customButton = By.xpath("//button[contains(text(),'Custom')]");

    By stylist = By.className("Arrange--withGutter");

    public String getMemberEmail(){
        return memberEmail.getText();
    }

    public List<WebElement> getStylistList(){
        return stylistListArea.findElements(stylist);
    }


    public static SalesPersonDetails getFirstStylist(){
        String email = driver.findElement(By.xpath("//*[@id=\"pos-app\"]/div/div[2]/div/section[2]/div/div/div[1]/div[1]/div[3]/div[2]")).getText();
        String firstName = driver.findElement(By.xpath("//*[@id=\"pos-app\"]/div/div[2]/div/section[2]/div/div/div[1]/div[1]/div[3]/div[1]")).getText().split(" ")[0];

        SalesPersonDetails salesPersonDetails = new SalesPersonDetails(email, "", "",firstName, "", true);
        return salesPersonDetails;
    }

    public WebElement isStylist(SalesPersonDetails salesPersonDetails){
        Assert.assertTrue(contains("div", salesPersonDetails.getEmail(), getStylistList()));
        for (WebElement stylist: getStylistList()) {
            List<WebElement> fields = stylist.findElements(By.tagName("div"));
            for (WebElement field : fields) {
                if (field.getText().equalsIgnoreCase(salesPersonDetails.getEmail())) {
                    return stylist;
                }
            }
        }
        return null;
    }

    public void changeRole (SalesPersonDetails salesPersonDetails, SalesPerson.Role role){
        isStylist(salesPersonDetails);
        for (WebElement stylist: getStylistList()){
            List <WebElement> fields = stylist.findElements(By.tagName("div"));
            for (WebElement field:fields){
                if (field.getText().equalsIgnoreCase(salesPersonDetails.getEmail())){
                    switch (role)
                    {
                        case ADDTIONAL:
                            stylist.findElements(By.tagName("button")).get(2).click();
                            break;
                        case CUSTOM:
                            stylist.findElement(customButton).click();
                            break;
                        case SHOPPING:
                            stylist.findElements(By.tagName("button")).get(1).click();
                            break;
                    }
                    break;
                }
            }
        }
    }

    public void searchesFor(SalesPersonDetails salesPersonDetails, String searchTerm){
        stylistNameInput.sendKeys(searchTerm);

        waitForPageLoaded(By.className("SearchResults-item"));
        List<WebElement> searchResults = driver.findElements(By.className("SearchResults-item"));
        org.testng.Assert.assertEquals(searchContains("div",salesPersonDetails.getEmail(),searchResults),
                salesPersonDetails.isValid);
    }

    public static void checkUniqueError(String expectedErrorMessage, boolean nextButtonEnabled, String headerText){
        BasePage.checkUniqueError(expectedErrorMessage);
        Assert.assertTrue(getNextButton(headerText).isEnabled() == nextButtonEnabled);
    }

}
