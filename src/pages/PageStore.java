package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;


public class PageStore {

    WebDriver webDriver;
    List<Object> pages;

    public PageStore() {
/*
        final String USERNAME = "jovana";
        final String ACCESS_KEY = "c1a98d09-1b4b-4702-8577-860030f127e0";
        //final String url = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";

        final String url = "http://10.8.6.202:4444/wd/hub";


        DesiredCapabilities capabilty = DesiredCapabilities.chrome();
        try {
            webDriver = new RemoteWebDriver(new URL(url), capabilty);
        }catch (MalformedURLException badURL){
            System.out.println("check grid url: " + url );
        }
*/

        if (webDriver == null) {
            webDriver = new FirefoxDriver();
        }
        pages = new ArrayList<Object>();
    }

    public <T> T get(Class<T> clazz) {
        for (Object page : pages) {
            if (page.getClass() == clazz)
                return (T) page;
        }
        T page = PageFactory.initElements(webDriver, clazz);
        pages.add(page);
        return page;
    }

    public void destroy() {
        pages.clear();
        webDriver.quit();
    }

    public WebDriver getDriver() {
        return webDriver;
    }
}
