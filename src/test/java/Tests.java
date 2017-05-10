import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mikhail_Churakov on 5/2/2017.
 */
public class Tests {
    public static String gecDrPath = "C:\\GeckoDriver\\geckodriver.exe";
    public static String testHost = "https://www.avito.ru/";
    public String screenPath = "C:\\temp\\testscreen.png";
    public String userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 8_3 like Mac OS X) AppleWebKit/600.1.4 (KHTML, like Gecko) FxiOS/1.0 Mobile/12F69 Safari/600.1.4";

    @Test
    // Point 1.1 of Homework
    public void testMobileIphone() {
        System.setProperty("webdriver.gecko.driver", gecDrPath);
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("general.useragent.override", userAgent);
        WebDriver driver = new FirefoxDriver(profile);
        driver.manage().window().setSize(new Dimension(375, 667));
        driver.navigate().to(testHost);
        driver.findElement(By.xpath("//div[@class='promo-app-caption promo-app-caption_app promo-app-caption_app-ios']"));
        driver.close();
    }
    @Test
    // Point 1.2 of Homework
    public void testAvito() {
        String desiredCar = "ВАЗ 2101";
        System.setProperty("webdriver.gecko.driver", gecDrPath);
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        driver.navigate().to(testHost);
        driver.findElement (By.id("region_653240")).click();
        driver.findElement(By.xpath("//a[@class='js-catalog-counts__link'][@title='Транспорт в Санкт-Петербурге']")).click();
        driver.findElement(By.xpath("//a[@class='js-catalog-counts__link'][@title='Автомобили в Санкт-Петербурге']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("search")));
        driver.findElement(By.id("search")).click();
        driver.findElement(By.id("search")).sendKeys(desiredCar);
        driver.findElement(By.xpath("//input[@class='search button button-origin'][@value='Найти']")).click();
        driver.findElement(By.xpath("//div[@class='catalog-filters-sort form-select form-select_btn-white']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='catalog-filters-sort form-select form-select_btn-white']//option[@value='2']")));
        driver.findElement(By.xpath("//div[@class='catalog-filters-sort form-select form-select_btn-white']//option[@value='1']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='js-catalog_before-ads']//div[1]//div[@class='about']")));
        driver.findElement(By.xpath("//div[@class='js-catalog_before-ads']//div[1]//h3[@class='title item-description-title']//a[contains(text(),'" + desiredCar + "')]"));
        driver.close();
    }
    @Test
    // Point 1.3 of Homework
    public void makeScreen() {
        System.setProperty("webdriver.gecko.driver", gecDrPath);
        WebDriver driver = new FirefoxDriver();
        driver.navigate().to(testHost);
        TakesScreenshot sc = (TakesScreenshot)driver;
        File screensFile = sc.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screensFile, new File(screenPath));
        } catch (IOException ex) {
            System.out.println("The test cannot save the screen");
        }
        driver.close();
    }
}
