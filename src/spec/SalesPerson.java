package spec;

import com.sun.xml.internal.ws.developer.MemberSubmissionEndpointReference;
import domain.MemberPersonDetails;
import domain.PurchaseItem;
import domain.SalesPersonDetails;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import pages.*;
import pages.POSLoginPage;
import pages.PageStore;

import java.util.List;


public class SalesPerson {


    PageStore pageStore;
    private boolean isLoggedIn;
    public SalesPersonDetails salesPersonDetails;
    private App app;
    public enum App {GENERIC, POS, TAILORING }
    public enum OrderType {CUSTOM, READYTOWEAR}
    public enum Role {ADDTIONAL, SHOPPING, CUSTOM}
    public enum ReceiptType {EMAIL, PRINT, DONE}
    public enum DeliveryMethod {PICKUP, SHIP}

    public SalesPerson(PageStore pageStore, SalesPersonDetails salesPersonDetails, App app) {
        this.pageStore = pageStore;
        this.app = app;
        this.salesPersonDetails = salesPersonDetails;
    }

    public void choosesToLogin(){
        //TODO navigate to login for POS

    }

    public void logsInWithCredentials(){
        if (this.app == App.POS) {
            POSLoginPage onLoginPage = pageStore.get(POSLoginPage.class);
            POSTransactionTypeSelectPage onPOSTransactionTypeSelectPage = pageStore.get(POSTransactionTypeSelectPage.class);
            onLoginPage.login(salesPersonDetails);
            if (salesPersonDetails.isValid) {
                onPOSTransactionTypeSelectPage.waitForPageLoaded(onPOSTransactionTypeSelectPage.readyToWearButton);
                isLoggedIn = onPOSTransactionTypeSelectPage.isUserLoggedIn(salesPersonDetails);
            } else {
                onLoginPage.waitForPageLoaded(onLoginPage.commonError);
                isLoggedIn = false;
            }
        }
        else if (this.app == App.TAILORING){
            TailoringLoginPage onTailoringLoginPage = pageStore.get(TailoringLoginPage.class);
            onTailoringLoginPage.login(salesPersonDetails);
            try{Thread.sleep(500);}
            catch(InterruptedException interruptionException){}
        }
    }

    public void isLoggedIn(boolean expected){
        BasePage page = pageStore.get(BasePage.class);
        boolean containsLogout = false;
        try {
            if(this.app == App.TAILORING) {
                containsLogout = page.driver.findElements(By.className("OrderHeader-button")).get(1).getText().toLowerCase().contains("logout");
            }
            if(this.app == App.POS){
                containsLogout = page.driver.findElement(By.className("logout")).getText().toLowerCase().contains(salesPersonDetails.getEmail().toLowerCase());
            }
        }
        catch(IndexOutOfBoundsException outOfBounds){
            containsLogout = false;
        }
        Assert.assertEquals(expected,containsLogout);
    }
    public void logsIn()
    {
        logsInWithCredentials();
        isLoggedIn(salesPersonDetails.isValid);
    }

    public void logsOut()
    {
        if (this.app == App.POS) {
            POSTransactionTypeSelectPage onPOSTransactionTypeSelectPage = pageStore.get(POSTransactionTypeSelectPage.class);
            onPOSTransactionTypeSelectPage.logOut();
        }
        else if (this.app == App.TAILORING) {
            TailoringTransactionTypePage onTailoringTransactionTypePage = pageStore.get(TailoringTransactionTypePage.class);
            onTailoringTransactionTypePage.logout();
        }
    }

    public void isShopping (OrderType orderType){
        POSTransactionTypeSelectPage onPOSTransactionTypeSelectPage = pageStore.get(POSTransactionTypeSelectPage.class);
        POSLoginPage onLoginPage = pageStore.get(POSLoginPage.class);

        try{
            isLoggedIn(true);
        }
        catch(AssertionError assertionError){
            logsIn();
        }

        if (orderType == OrderType.CUSTOM){
            onPOSTransactionTypeSelectPage.customButton.click();
        }
        else if (orderType == OrderType.READYTOWEAR){
            onPOSTransactionTypeSelectPage.readyToWearButton.click();
        }
        onPOSTransactionTypeSelectPage.waitForPageLoaded(onPOSTransactionTypeSelectPage.transactionType);
    }

    public void searchesFor (MemberPersonDetails memberPersonDetails){
        SearchCustomerPage searchCustomerPage = pageStore.get(SearchCustomerPage.class);
        searchCustomerPage.searchFor(memberPersonDetails);
    }

    public void searchesFor (SalesPersonDetails salesPersonDetails, String searchTerm){
        SelectStylistPage onSelectStylistPage = pageStore.get(SelectStylistPage.class);
        onSelectStylistPage.searchesFor(salesPersonDetails, searchTerm);
    }

    public void selectedMember(MemberPersonDetails memberPersonDetails) {
        SelectStylistPage onSelectStylistPage = pageStore.get(SelectStylistPage.class);
        Assert.assertEquals(memberPersonDetails.getEmail(), onSelectStylistPage.getMemberEmail());
    }

    public void fillsInMemberInfo (MemberPerson memberPerson, SalesPerson stylist,boolean isValid){
        SearchCustomerPage onSearchCustomerPage = pageStore.get(SearchCustomerPage.class);
        onSearchCustomerPage.fillMemberInfo(memberPerson,stylist,isValid);
    }

    public void createMember(boolean isValid){
        SearchCustomerPage onSearchCustomerPage = pageStore.get(SearchCustomerPage.class);
        onSearchCustomerPage.addMember(isValid);
    }

    public void addStylist(SalesPersonDetails salesPersonDetails, String searchTerm, Role role) {
        SelectStylistPage onSelectStylistPage = pageStore.get(SelectStylistPage.class);
        //if the stylist already exists, just set role
        try {
            onSelectStylistPage.changeRole(salesPersonDetails,role);
        }
        catch(AssertionError assertionError)
        {
            onSelectStylistPage.searchesFor(salesPersonDetails, searchTerm);
            BasePage.checkUniqueError("Assign " + salesPersonDetails.getFirstName() + " Stylist a role");
            Assert.assertTrue(onSelectStylistPage.getNextButton("Stylist").isEnabled() == false);
            onSelectStylistPage.changeRole(salesPersonDetails, role);
        }
    }

    public void removeStylist (SalesPersonDetails salesPersonDetails){
        SelectStylistPage onSelectStylistPage = pageStore.get(SelectStylistPage.class);
        WebElement stylist = onSelectStylistPage.isStylist(salesPersonDetails);
        stylist.findElements(By.tagName("button")).get(0).click();
    }

    public void edits(String fieldHeader){
        SelectStylistPage onSelectStylistPage = pageStore.get(SelectStylistPage.class);
        onSelectStylistPage.editField(fieldHeader);
    }

    public void addsItems(PurchaseItem [] UPCs){
        ScanItemsPage onScanItemsPage = pageStore.get(ScanItemsPage.class);
        onScanItemsPage.scanItems (UPCs);
    }

    public void addsItems(List<PurchaseItem> purchaseItems, OrderType orderType){
        ScanItemsPage onScanItemsPage = pageStore.get(ScanItemsPage.class);
        onScanItemsPage.scanItems (purchaseItems,orderType);
    }

    public void contiuesFrom(String headerText){
        BasePage.waitForPageLoaded(ExpectedConditions.visibilityOf(BasePage.getNextButton(headerText)));
        BasePage.getNextButton(headerText).click();

    }

    public void itemsInCart( int numItems){
        Assert.assertEquals(BasePage.getResultCount("Items", By.className("Arrange--withGutter")),numItems);
    }

    public void completesPickupOrder(){
        SelectDeliveryMethodPage onSelectDeliveryMethodPage = pageStore.get(SelectDeliveryMethodPage.class);
        Assert.assertFalse(onSelectDeliveryMethodPage.getCheckOutButton().isEnabled());
        onSelectDeliveryMethodPage.pickUpButton.click();
        onSelectDeliveryMethodPage.waitForPageLoaded("https://pos.stagingclub.com/purchase/select-payment");
        onSelectDeliveryMethodPage.getNextButton("Payment").click();
        onSelectDeliveryMethodPage.waitForPageLoaded("https://pos.stagingclub.com/purchase/submission");
        onSelectDeliveryMethodPage.waitForPageLoaded(ExpectedConditions.elementToBeClickable(onSelectDeliveryMethodPage.getCheckOutButton()));
        Assert.assertTrue(onSelectDeliveryMethodPage.getCheckOutButton().isEnabled());
        onSelectDeliveryMethodPage.getCheckOutButton().click();
    }


    public void completesOrder(ReceiptType receiptType) {
        ReceiptPage onReceiptPage = pageStore.get(ReceiptPage.class);
        onReceiptPage.waitForPageLoaded("https://pos.stagingclub.com/receipt");
        switch (receiptType) {
            case EMAIL:
                onReceiptPage.emailReceiptButton.click();
                onReceiptPage.waitForPageLoaded(onReceiptPage.emailed);
                Assert.assertTrue(onReceiptPage.emailed.isDisplayed());
                break;
            case PRINT:
                onReceiptPage.printReceiptButton.click();

                break;
            case DONE:
                onReceiptPage.doneButton.click();
                onReceiptPage.waitForPageLoaded("https://pos.stagingclub.com/login");
                break;
        }

    }

    public void selectsDeliveryMethod(DeliveryMethod deliveryMethod){
        switch (deliveryMethod){
            case PICKUP:
                break;
            case SHIP:
                break;
        }
    }
}
