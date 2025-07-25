package pages;

import configuration.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "a[href='/login']")
    private WebElement loginFromNavBar;
    public WebElement getLoginFromNavBar() {
        return loginFromNavBar;
    }
}