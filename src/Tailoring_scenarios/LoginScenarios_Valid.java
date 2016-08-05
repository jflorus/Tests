package Tailoring_scenarios;

import Tailoring_scenarios.BaseScenario;
import domain.SalesPersonDetails;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.PageStore;
import spec.SalesPerson;

/**
 * Created by jovana on 6/14/16.
 */
public class LoginScenarios_Valid extends BaseScenario {

    @BeforeMethod(alwaysRun = true)
    public void setup(){
        SalesPersonDetails salesPersonDetails = new SalesPersonDetails("jovana@trunkclub.com", "password", "CHTK0101A01", "Jovana", "Florus", true);
        pageStore = new PageStore();
        salesPerson = new SalesPerson(pageStore,salesPersonDetails, SalesPerson.App.TAILORING);
        driver = pageStore.getDriver();
        driver.get("https://tailoring.stagingclub.com");


    }
    @Test
    public void testThatLoginWithValidCredentialsSucceeds(){
        given(salesPerson).choosesToLogin();
        when(salesPerson).logsInWithCredentials();
        then(salesPerson).isLoggedIn(true);
    }

    @Test
    public void testThatLogoutSucceeds(){
        given(salesPerson).logsIn();
        when (salesPerson).logsOut();
        then (salesPerson).isLoggedIn(false);
    }

    @Test
    public void testThatLinksNavigateToProperURLS(){

    }
}
