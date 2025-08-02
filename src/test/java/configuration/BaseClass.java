package configuration;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utilities.Data;
import utilities.EnvReader;

import java.time.Duration;
import java.util.NoSuchElementException;

public class BaseClass {
    public static WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeSuite
    public void beforeSuite() {
        String browser = EnvReader.get("BROWSER", "chrome").toLowerCase();
        boolean isHeadless = EnvReader.getBoolean("HEADLESS", false);

        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                }
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "safari":
                // Safari doesn't support headless mode officially
                if (isHeadless) {
                    throw new UnsupportedOperationException("Safari does not support headless mode.");
        }
        driver = new SafariDriver();
        break;

        default:
        throw new IllegalArgumentException("Unsupported browser: " + browser);
    }

    driver.manage().window().maximize();
    driver.navigate().to(EnvReader.get("BASE_URL"));
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.titleContains(Data.TITLE));
}

    @AfterSuite
    public void afterSuite() {
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