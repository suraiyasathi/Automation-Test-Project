package configuration;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utilities.Data;
import java.time.Duration;
import java.util.NoSuchElementException;

public class BaseClass {
    public static WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }
    @BeforeSuitepublic void beforeSuite() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(Data.BASE_URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains(Data.TITLE));

    }

    @AfterSuitepublic void afterSuite() {
        if (driver != null) {
            driver.quit();
        }
    }

    // This method set a dynamic wait for an element until it visible
    public static void waitForElementToBeVisible(WebElement element, int holdInSecond) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(holdInSecond))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(WebDriverException.class)
                .ignoring(TimeoutException.class);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

}