package POS_scenarios;

import domain.MemberPersonDetails;
import domain.SalesPersonDetails;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.PageStore;
import spec.MemberPerson;
import spec.SalesPerson;

import java.io.File;
import java.io.IOException;


public class BaseScenario {

    WebDriver driver;
    SalesPerson salesPerson;
    MemberPerson memberPerson;
    PageStore pageStore;

    @BeforeMethod(alwaysRun = true)
      public void setup(){
        pageStore = new PageStore();
        SalesPersonDetails salesPersonDetails = new SalesPersonDetails("jovana@trunkclub.com", "password", "CHTK0101A01", "Jovana", "Florus", true);
        MemberPersonDetails memberPersonDetails = new MemberPersonDetails("Jovana", "Florus","jovana@trunkclub.com", "password", "1234567890", "123 asdfg sdf","", "60654", "IL", "1245697452","123","123","123", true);
        memberPerson = new MemberPerson(pageStore, memberPersonDetails);
        salesPerson = new SalesPerson(pageStore,salesPersonDetails, SalesPerson.App.GENERIC);
        driver = pageStore.getDriver();
        driver.get("www.stagingclub.com");
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult result){
        captureScreenShotInCaseOfFailure(result);
        pageStore.destroy();

    }
    protected void captureScreenShotInCaseOfFailure(ITestResult result) {
        if (result.isSuccess()) {
            return;
        }
        String screenShotFolder = "screenshots";
        File screenShotSourceFile = ((TakesScreenshot) pageStore.getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            createFolder(screenShotFolder);
            String fileName = result.getMethod().getMethodName();
            File screenShotTargetFile = getTargetFile(screenShotFolder, fileName, "png");
            FileUtils.copyFile(screenShotSourceFile, screenShotTargetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFolder(String folderName) throws IOException {
        if (!(new File(folderName).exists())) new File(folderName).mkdir();
    }

    private File getTargetFile(String folderName, String fileName, String ext) throws IOException {
        String rootPath = new File(".").getCanonicalPath();
        String fullPath = String.format("%s//%s//%s.%s", rootPath, folderName, fileName, ext);
        return new File(fullPath);
    }

}
