package testCases;

import configuration.BaseClass;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utilities.CookieManager;
import utilities.Data;
import utilities.EnvReader;

@Epic("Login")
@Feature("Login to your account")
public class LoginTest extends BaseClass {
    static HomePage homePage;
    static LoginPage loginPage;
    static CookieManager cookieManager;
    @Test(description = "Verify user can login successfully with valid credentials.")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login with valid credentials")
    @Owner("Suraiya Akter")
    @Link(name = "Story Link", url = "....")
    public static void loginTest() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        cookieManager = new CookieManager(driver);
        boolean loaded = cookieManager.loadCookiesIfValid();

        if (loaded) {
            driver.navigate().refresh();
        } else {
            waitForElementToBeVisible(homePage.getLoginFromNavBar(), 10);
            homePage.getLoginFromNavBar().click();
            waitForElementToBeVisible(loginPage.getEmailField(), 10);
            loginPage.getEmailField().click();
            loginPage.getEmailField().sendKeys(EnvReader.get("EMAIL"));
            loginPage.getPasswordField().click();
            loginPage.getPasswordField().sendKeys(EnvReader.get("PASSWORD"));
            loginPage.getLoginButton().click();
            cookieManager.saveCookies();
        }
        waitForElementToBeVisible(loginPage.getProfile(), 10);
        Assert.assertEquals(loginPage.getProfile().getText(), Data.LOGGED_IN_TEXT, "Invalid Credentials");
    }
}