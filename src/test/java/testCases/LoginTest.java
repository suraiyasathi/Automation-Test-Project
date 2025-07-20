package testCases;

import configuration.BaseClass;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import utilities.Data;

@Epic("Login")
@Feature("Login to your account")
public class LoginTest extends BaseClass {
    static HomePage homePage;
    static LoginPage loginPage;
    @Test(description = "Verify user can login successfully with valid credentials.")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login with valid credentials")
    @Owner("Suraiya Akter")
    @Link(name = "Story Link", url = "....")
    public static void loginTest() {
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        waitForElementToBeVisible(homePage.getLoginFromNavBar(), 10);
        homePage.getLoginFromNavBar().click();
        waitForElementToBeVisible(loginPage.getEmailField(), 10);
        loginPage.getEmailField().click();
        loginPage.getEmailField().sendKeys(Data.EMAIL);
        loginPage.getPasswordField().click();
        loginPage.getPasswordField().sendKeys(Data.PASSWORD);
        loginPage.getLoginButton().click();
        waitForElementToBeVisible(loginPage.getProfile(), 10);
        Assert.assertEquals(loginPage.getProfile().getText(), Data.LOGGED_IN_TEXT, "Invalid Credentials");
    }
}