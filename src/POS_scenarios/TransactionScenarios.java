package POS_scenarios;

import domain.MemberPersonDetails;
import domain.PurchaseItem;
import domain.SalesPersonDetails;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import spec.MemberPerson;
import spec.SalesPerson;

import java.util.Random;

/**
 * Created by jovana on 6/16/16.
 */
public class TransactionScenarios extends BaseScenario{
        @BeforeMethod(alwaysRun = true)
    public void setup(){
        pageStore = new PageStore();
        SalesPersonDetails salesPersonDetails = new SalesPersonDetails("jovana@trunkclub.com", "password", "CHTK0101A01", "Jovana", "Florus", true);
        MemberPersonDetails memberPersonDetails = new MemberPersonDetails("Jovana", "Test","jovana@trunkclub.com", "password", "1234567890", "123 asdfg sdf","", "60654", "IL", "1245697452","123","123","123", true);
        memberPerson = new MemberPerson(pageStore, memberPersonDetails);
        salesPerson = new SalesPerson(pageStore,salesPersonDetails, SalesPerson.App.POS);
        driver = pageStore.getDriver();
        driver.get("https://pos.stagingclub.com/transaction-type-select");
    }

    @Test
    public void testTransactionTypeHeadingShows() {
        POSTransactionTypeSelectPage onPOSTransactionTypeSelectPage = pageStore.get(POSTransactionTypeSelectPage.class);
        salesPerson.isShopping(SalesPerson.OrderType.READYTOWEAR);
        Assert.assertTrue(onPOSTransactionTypeSelectPage.transactionType.getText().equalsIgnoreCase("Ready To Wear"));
    }

    @Test
    void testThatValidMemberIsFound(){
        salesPerson.isShopping(SalesPerson.OrderType.READYTOWEAR);
        salesPerson.searchesFor(memberPerson.memberPersonDetails);
        salesPerson.selectedMember(memberPerson.memberPersonDetails);
    }

    @Test
    void testThatExistingMemberCannotBeAdded(){
        SalesPersonDetails stylistDetails = new SalesPersonDetails("tara@trunkclub.com", "password", "CHTK0101A01", "Tara", "Jones", true);
        SalesPerson stylist = new SalesPerson(pageStore,stylistDetails, SalesPerson.App.POS);
        salesPerson.isShopping(SalesPerson.OrderType.READYTOWEAR);
        salesPerson.fillsInMemberInfo(memberPerson,stylist,false);
        salesPerson.createMember(false);
        BasePage.checkUniqueError("The email address provided - \"" + memberPerson.memberPersonDetails.getEmail() + "\" - already exists.", "Member");
    }

    @Test
    public void testThatMemberCreationRequiresNumberOf10Digits() {
        Random rand = new Random();
        int  n = rand.nextInt() + 1;
        String memberEmail = "jovana" + n + "@trunkclub.com";
        SalesPersonDetails stylistDetails = new SalesPersonDetails("tara@trunkclub.com", "password", "CHTK0101A01", "Tara", "Jones", true);
        SalesPerson stylist = new SalesPerson(pageStore,stylistDetails, SalesPerson.App.POS);
        MemberPersonDetails newMemberPersonDetails = new MemberPersonDetails("Jovana", "Florus",memberEmail, "password", "12345", "123 asdfg sdf","", "60654", "IL", "1245697452","123","123","123", true);
        MemberPerson newMemberPerson = new MemberPerson(pageStore, newMemberPersonDetails);
        salesPerson.isShopping(SalesPerson.OrderType.READYTOWEAR);
        salesPerson.fillsInMemberInfo(newMemberPerson,stylist,false);
        BasePage.checkUniqueError("Phone number must be 10 digits.", "Member");
    }

    @Test
    public void testCreatedMemberHasCorrectStylist() {
        Random rand = new Random();
        int  n = rand.nextInt() + 1;
        String memberEmail = "jovana" + n + "@trunkclub.com";
        SalesPersonDetails stylistDetails = new SalesPersonDetails("tara@trunkclub.com", "password", "CHTK0101A01", "Tara", "Jones", true);
        SalesPerson stylist = new SalesPerson(pageStore,stylistDetails, SalesPerson.App.POS);
        MemberPersonDetails newMemberPersonDetails = new MemberPersonDetails("Jovana", "Florus",memberEmail, "password", "1234567890", "123 asdfg sdf","", "60654", "IL", "1245697452","123","123","123", true);
        MemberPerson newMemberPerson = new MemberPerson(pageStore, newMemberPersonDetails);

        salesPerson.isShopping(SalesPerson.OrderType.READYTOWEAR);
        salesPerson.fillsInMemberInfo(newMemberPerson, stylist, true);
        salesPerson.createMember( true);
        Assert.assertTrue(0 == SelectStylistPage.getFirstStylist().getEmail().compareToIgnoreCase(stylist.salesPersonDetails.getEmail()));


    }

    @Test
    public void testThatValidMemberIsFoundEmail() {
        MemberPersonDetails memberPersonDetails = new MemberPersonDetails("jovana@trunkclub.com", "","jovana@trunkclub.com", "password", "1234567890", "123 asdfg sdf","", "60654", "IL", "1245697452","123","123","123", true);
        memberPerson = new MemberPerson(pageStore, memberPersonDetails);
        salesPerson.isShopping(SalesPerson.OrderType.READYTOWEAR);
        salesPerson.searchesFor(memberPerson.memberPersonDetails);
        salesPerson.selectedMember(memberPerson.memberPersonDetails);
    }

    @Test
    public void testThatStylistChangesWhenMemberChanges() {
        String firstStylist;
        String secondStylist;
        MemberPersonDetails newMemberPersonDetails = new MemberPersonDetails("suzanne gould","","","","","","","","","","","","",true);
        salesPerson.isShopping(SalesPerson.OrderType.READYTOWEAR);
        salesPerson.searchesFor(memberPerson.memberPersonDetails);
        salesPerson.selectedMember(memberPerson.memberPersonDetails);
        firstStylist = SelectStylistPage.getFirstStylist().getEmail();
        salesPerson.edits("Member");
        salesPerson.searchesFor(newMemberPersonDetails);
        secondStylist = SelectStylistPage.getFirstStylist().getEmail();
        Assert.assertFalse(firstStylist.equalsIgnoreCase(secondStylist));
    }

    @Test
    void testThatStylistCanBeEdited(){
        //test ready to wear
        salesPerson.isShopping(SalesPerson.OrderType.READYTOWEAR);
        salesPerson.searchesFor(memberPerson.memberPersonDetails);
        salesPerson.selectedMember(memberPerson.memberPersonDetails);

        SalesPersonDetails defaultSalesPerson = SelectStylistPage.getFirstStylist();

        //add a new stylist
        SalesPersonDetails additionalSalesPersonDetails = new SalesPersonDetails("Suzanne+5@trunkclub.com", "", "", "Suzanne", "Stylist", true);
        salesPerson.addStylist(additionalSalesPersonDetails, additionalSalesPersonDetails.getFullName(), SalesPerson.Role.ADDTIONAL);

        //change new stylist to primary
        salesPerson.addStylist(additionalSalesPersonDetails, additionalSalesPersonDetails.getFullName(), SalesPerson.Role.SHOPPING);
        SelectStylistPage.checkUniqueError("There can be only one Primary Stylist. You have 2 assigned", false,"Stylist");

        //
        salesPerson.addStylist(defaultSalesPerson, additionalSalesPersonDetails.getEmail(), SalesPerson.Role.ADDTIONAL);
        SelectStylistPage.checkUniqueError("", true,"Stylist");
        //remove a stylist
        //
    }


    @Test
    void testThatItemsCanBeAdded(){

        PurchaseItem[] UPCs = new PurchaseItem[]{ new PurchaseItem(new String []{"886732455147", "$278.00", "", ""}),
                                                   new PurchaseItem(new String []{"884926695263", "$107.20", "", ""})};
        salesPerson.isShopping(SalesPerson.OrderType.READYTOWEAR);
        salesPerson.searchesFor(memberPerson.memberPersonDetails);
        salesPerson.contiuesFrom("Stylist");
        ScanItemsPage.checkUniqueError("There are no products scanned");
        salesPerson.addsItems(UPCs);
        salesPerson.itemsInCart(UPCs.length);
        salesPerson.contiuesFrom("Items");
    }

    @Test
    void testThatStylistCanCompletePickUpOrder(){
        testThatItemsCanBeAdded();
        salesPerson.completesPickupOrder();
        salesPerson.completesOrder(SalesPerson.ReceiptType.EMAIL);
        salesPerson.completesOrder(SalesPerson.ReceiptType.PRINT);
        salesPerson.completesOrder(SalesPerson.ReceiptType.DONE);
    }

    @Test
    public void testThatUserLoginsInPerTransaction(){
        testThatItemsCanBeAdded();
        salesPerson.completesPickupOrder();
        salesPerson.completesOrder(SalesPerson.ReceiptType.EMAIL);
        salesPerson.completesOrder(SalesPerson.ReceiptType.DONE);
    }

    /*
    @Parameters({"UPCs"})
    @Test
    public void testThatCustomUPCsAreCorrect() {
                driver.get("https://pos.stagingclub.com/transaction-type-select");

        List<PurchaseItem> purchaseItems = new ArrayList<PurchaseItem>();
        String csvFile = "./src/resources/production/test.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                purchaseItems.add(new PurchaseItem(line.split(csvSplitBy)));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        SalesPersonDetails salesPersonDetails = new SalesPersonDetails("jovana@trunkclub.com", "password", "CHTK0101A01", "Jovana", "Florus", true);
        salesPerson = new SalesPerson(pageStore,salesPersonDetails, SalesPerson.App.POS);
        salesPerson.isShopping(SalesPerson.OrderType.CUSTOM);
        salesPerson.searchesFor(memberPerson.memberPersonDetails);
        salesPerson.contiuesFrom("Stylist");
        ScanItemsPage.checkUniqueError("There are no products scanned");
        salesPerson.addsItems(purchaseItems, SalesPerson.OrderType.CUSTOM);
    }*/
}
