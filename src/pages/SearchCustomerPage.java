package pages;

import domain.MemberPersonDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import spec.MemberPerson;
import spec.SalesPerson;

import java.util.List;

/**
 * Created by jovana on 6/29/16.
 */
public class SearchCustomerPage extends BasePage {

    @FindBy(xpath="//input[@placeholder='Member name']")
    WebElement memberNameInput;

    @FindBy(xpath = "//button[contains(text(), 'or create new member')]")
    WebElement createNewMember;

    @FindBy(xpath="//input[@placeholder='First']")
    WebElement firstNameInput;

    @FindBy(xpath="//input[@placeholder='Last']")
    WebElement lastNameInput;

    @FindBy(xpath="//input[@placeholder='name@example.com']")
    WebElement emailInput;

    @FindBy(xpath="//input[@placeholder='312-555-5555']")
    WebElement phoneNumberInput;

    @FindBy(xpath="//input[@placeholder='Stylist name']")
    WebElement stylistNameInput;

    @FindBy(xpath="//button[contains(text(),'Men')]")
    WebElement mensButton;

    @FindBy(xpath="//button[contains(text(),'Women')]")
    WebElement womensButton;

    @FindBy(xpath="//button[contains(text(),'Create member')]")
    WebElement createMemberButton;





    public void searchFor( MemberPersonDetails memberPersonDetails){
        memberNameInput.sendKeys(memberPersonDetails.getFirstName());
        memberNameInput.sendKeys(" ");
        memberNameInput.sendKeys(memberPersonDetails.getLastName());

        waitForPageLoaded(By.className("SearchResults-item"));
        BasePage page = new BasePage();
        List<WebElement> searchResults = page.driver.findElements(By.className("SearchResults-item"));
        Assert.assertEquals(searchContains("div",memberPersonDetails.getEmail(),searchResults),
                memberPersonDetails.isValid);

    }

    public void fillMemberInfo (MemberPerson memberPerson, SalesPerson salesPerson, boolean isValid){
        createNewMember.click();
        waitForPageLoaded(firstNameInput);
        Assert.assertFalse(createMemberButton.isEnabled());
        firstNameInput.sendKeys(memberPerson.memberPersonDetails.getFirstName());
        lastNameInput.sendKeys(memberPerson.memberPersonDetails.getLastName());
        emailInput.sendKeys(memberPerson.memberPersonDetails.getEmail());
        phoneNumberInput.sendKeys(memberPerson.memberPersonDetails.getPhoneNumber());
        womensButton.click();
        salesPerson.searchesFor(salesPerson.salesPersonDetails, salesPerson.salesPersonDetails.getFullName());
    }

    public void addMember(boolean isValid){
        waitForPageLoaded(ExpectedConditions.elementToBeClickable(createMemberButton));
        createMemberButton.click();
        if (isValid){
            waitForPageLoaded("https://pos.stagingclub.com/purchase/select-stylist");
            waitForPageLoaded(BasePage.getContainer("Stylist").findElement(nextButton));
        }
    }
}
