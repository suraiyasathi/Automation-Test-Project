package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

public class FormFiller {
    WebDriver driver;

    public FormFiller(WebDriver driver) {
        this.driver = driver;
    }

    public void fillForm(LinkedHashMap<String, String> inputData, List<WebElement> fields) {
        for (WebElement field : fields) {
            String name = field.getAttribute("name");
            String type = field.getAttribute("type");
            String tag = field.getTagName();
            String role = field.getAttribute("role");
            String value = inputData.get(name);

            if (value == null) continue;

            try {
                boolean isInput = "input".equalsIgnoreCase(tag);

                if ((isInput && (
                        type == null ||
                                type.equalsIgnoreCase("text") ||
                                type.equalsIgnoreCase("email") ||
                                type.equalsIgnoreCase("password") ||
                                type.equalsIgnoreCase("tel") ||
                                type.equalsIgnoreCase("number") ||
                                type.equalsIgnoreCase("date") ||
                                type.equalsIgnoreCase("search")
                )) || "textarea".equalsIgnoreCase(tag)) {
                    field.click();
                    field.clear();
                    field.sendKeys(value);

                } else if (isInput) {
                    if ("radio".equalsIgnoreCase(type)) {
                        if (field.getAttribute("value").equalsIgnoreCase(value)) {
                            field.click();
                        }
                    } else if ("checkbox".equalsIgnoreCase(type)) {
                        boolean shouldCheck = value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes");
                        if (shouldCheck != field.isSelected()) {
                            field.click();
                        }
                    } else if ("file".equalsIgnoreCase(type)) {
                        String filePath = value.contains(Data.PROJECT_ROOT) ?
                                value.replace(Data.PROJECT_ROOT, PathConstants.PROJECT_ROOT) : value;

                        File file = new File(filePath);
                        if (!file.exists()) {
                            throw new RuntimeException("Upload file not found: " + file.getAbsolutePath());
                        }

                        field.sendKeys(value);
                    }

                } else if ("select".equalsIgnoreCase(tag) ||
                        "combobox".equalsIgnoreCase(role) ||
                        "listbox".equalsIgnoreCase(role)) {
                    try {
                        new Select(field).selectByVisibleText(value);
                    } catch (Exception e) {
                        field.click();
                        field.sendKeys(value);
                        field.sendKeys(Keys.ENTER);
                    }
                }

            } catch (Exception e) {
                System.out.println("Error handling field: " + name + " => " + e.getMessage());
            }
        }
    }
}