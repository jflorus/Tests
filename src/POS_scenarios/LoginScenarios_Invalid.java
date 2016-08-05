package POS_scenarios;

import domain.SalesPersonDetails;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.PageStore;
import spec.SalesPerson;

/**
 * Created by jovana on 6/14/16.
 */
public class LoginScenarios_Invalid extends BaseScenario{

    @BeforeMethod(alwaysRun = true)
    public void setup(){
        pageStore = new PageStore();
        SalesPersonDetails salesPersonDetailsInvalid = new SalesPersonDetails("jovana@trunkclub.com", "wrongpassword", "CHTK0101A01", "Jovana", "Florus", false);
        salesPerson = new SalesPerson(pageStore, salesPersonDetailsInvalid, SalesPerson.App.POS);
        driver = pageStore.getDriver();
        driver.get("https://pos.stagingclub.com");
    }

    @Test
    public void testThatLoginWithInvalidCredentialsFailsName(){
        SalesPersonDetails salesPersonDetailsInvalid = new SalesPersonDetails("jovana@trunkclub.com", "wrongpassword", "CHTK0101A01", "Jovana", "Florus", false);
        salesPerson = new SalesPerson(pageStore, salesPersonDetailsInvalid, SalesPerson.App.POS);

        salesPerson.choosesToLogin();
        salesPerson.logsInWithCredentials();
        salesPerson.isLoggedIn(false);
    }

    @Test
    public void testThatLoginWithInvalidCredentialsFailsPassword(){
        SalesPersonDetails salesPersonDetailsInvalid = new SalesPersonDetails("12345o98jovana@trunkclub.com", "password", "CHTK0101A01", "Jovana", "Florus", false);
        salesPerson = new SalesPerson(pageStore, salesPersonDetailsInvalid, SalesPerson.App.POS);

        salesPerson.choosesToLogin();
        salesPerson.logsInWithCredentials();
        salesPerson.isLoggedIn(false);
    }

    @Test
    public void testThatLoginWithInvalidCredentialsFailsTable(){
        SalesPersonDetails salesPersonDetailsInvalid = new SalesPersonDetails("jovana@trunkclub.com", "password", "CHTK02356tdf101A01", "Jovana", "Florus", false);
        salesPerson = new SalesPerson(pageStore, salesPersonDetailsInvalid, SalesPerson.App.POS);

        salesPerson.choosesToLogin();
        salesPerson.logsInWithCredentials();
        salesPerson.isLoggedIn(false);
    }
}
