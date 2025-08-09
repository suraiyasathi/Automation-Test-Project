package testCases;

import configuration.BaseClass;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ContactUsPage;
import pages.HomePage;
import utils.Data;
import utils.JsonDataProvider;
import utils.FormFiller;

import java.util.LinkedHashMap;

@Epic("Automation Exercise")
@Feature("Contact Us Form")
public class ContactFormTest extends BaseClass {
    HomePage homePage;
    ContactUsPage contactUsPage;
    FormFiller formFiller;

    @Test(dataProvider = "jsonDataProvider", dataProviderClass = JsonDataProvider.class, description = "Fill Contact Us form and submit successfully")
    @Story("Valid Contact Us form submission")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Suraiya Akter")
    @Link(name = "Story Link", url = "....")
    @Description("This test verifies that user can fill the Contact Us form, attach a file, and submit successfully.")
    public void contactFormTest(LinkedHashMap<String, String> inputData) {
        homePage = new HomePage(driver);
        contactUsPage = new ContactUsPage(driver);
        formFiller = new FormFiller(driver);

        Allure.step("Click on 'Contact Us' from Nav Bar");
        clickOnContactUs();
        Allure.step("Fill the contact form");
        fillUpContactUsForm(inputData);
        Allure.step("Submit the contact form");
        submitForm();
        Allure.step("Verify the success message");
        verifySuccessMessage();
        Allure.step("Get Back to Home page");
        getBackToHomePage();

    }

    public void clickOnContactUs()
    {
        waitForElementToBeVisible(homePage.getContactUsFromNavBar(), 10);
        homePage.getContactUsFromNavBar().click();
    }

    public void fillUpContactUsForm(LinkedHashMap<String, String> inputData) {
        formFiller.fillForm(inputData, contactUsPage.contactForm);
    }

    public void submitForm() {
        contactUsPage.getSubmitBtn().click();
        driver.switchTo().alert().accept(); // Alert confirmation
    }

    public void verifySuccessMessage() {
        Assert.assertEquals(contactUsPage.getSuccessMessage().getText(), Data.CONTACT_US_SUBMIT_SUCCESS_MESSAGE, "Message mismatched");
    }

    public void getBackToHomePage() {
        contactUsPage.getHomeBtn().click();
    }
}