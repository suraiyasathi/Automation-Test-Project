package pages;

import configuration.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ContactUsPage extends BasePage {
    public ContactUsPage(WebDriver driver) {
        super(driver);
    }

    @FindAll({
            @FindBy(css = "input[placeholder='Name']"),
            @FindBy(css = "input[placeholder='Email']"),
            @FindBy(css = "input[placeholder='Subject']"),
            @FindBy(xpath = "//textarea[@id='message']"),
            @FindBy(css = "input[type='file']")
    })public List<WebElement> contactForm;

    @FindBy(css = "input[value='Submit']")private WebElement submitBtn;
    public WebElement getSubmitBtn() {
        return submitBtn;
    }

    @FindBy(css = "#contact-page > div.row > div.col-sm-8 > div > div.status.alert.alert-success")private WebElement successMessage;
    public WebElement getSuccessMessage() {
        return successMessage;
    }

    @FindBy(css = ".btn.btn-success")private WebElement homeBtn;
    public WebElement getHomeBtn(){
        return homeBtn;
    }
}