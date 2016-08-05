package POS_scenarios;

import domain.SalesPersonDetails;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.PageStore;
import spec.SalesPerson;

/**
 * Created by jovana on 6/14/16.
 */
public class LoginScenarios_Valid extends BaseScenario{

    @BeforeMethod(alwaysRun = true)
    public void setup(){
        SalesPersonDetails salesPersonDetails = new SalesPersonDetails("jovana@trunkclub.com", "password", "CHTK0101A01", "Jovana", "Florus", true);
        pageStore = new PageStore();
        salesPerson = new SalesPerson(pageStore,salesPersonDetails, SalesPerson.App.POS);
        driver = pageStore.getDriver();
        driver.get("https://pos.stagingclub.com");
    }

    @Test
    public void testThatLoginWithValidCredentialsSucceeds(){
        salesPerson.choosesToLogin();
        salesPerson.logsInWithCredentials();
        salesPerson.isLoggedIn(true);
    }

    @Test
    public void testThatLogoutSucceeds(){
        salesPerson.logsIn();
        salesPerson.logsOut();
        salesPerson.isLoggedIn(false);
    }
}
