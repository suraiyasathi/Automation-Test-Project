package pages;

import configuration.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "input[data-qa='login-email']")private WebElement emailField;
    public WebElement getEmailField() {
        return emailField;
    }

    @FindBy(css = "input[placeholder='Password']")private WebElement passwordField;
    public WebElement getPasswordField() {
        return passwordField;
    }

    @FindBy(css = "button[data-qa='login-button']")private WebElement loginButton;
    public WebElement getLoginButton() {
        return loginButton;
    }

    @FindBy(css = "#header > div > div > div > div.col-sm-8 > div > ul > li:nth-child(10) > a")private WebElement profile;
    public WebElement getProfile() {
        return profile;
    }
}