package listeners;

import configuration.BaseClass;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TestListener implements ITestListener {
    private WebDriver driver;
    private static final Path logFile = Paths.get("target", "test-log.txt");
    private BufferedWriter writer;

    // Clear log file before suite
    @Override
    public void onStart(ITestContext context) {
        try {
            Files.createDirectories(logFile.getParent());
            Files.deleteIfExists(logFile);
            writer = Files.newBufferedWriter(logFile, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            logToFile("Test Suite started at " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Close writer after suite
    @Override
    public void onFinish(ITestContext context) {
        try {
            logToFile("Test Suite finished at " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logToFile(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        String msg = "TEST STARTED: " + result.getMethod().getMethodName();
        Reporter.log(msg, true);
        logToFile(msg);
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        String msg = "TEST PASSED: " + result.getMethod().getMethodName();
        Reporter.log(msg, true);
        logToFile(msg);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String msg = "TEST FAILED: " + result.getMethod().getMethodName();
        Reporter.log(msg, true);
        logToFile(msg);

        Throwable cause = result.getThrowable();
        if (cause != null) {
            String causeMsg = "Cause: " + cause.getMessage();
            Reporter.log(causeMsg, true);
            logToFile(causeMsg);
        }

        Object testClass = result.getInstance();
        driver = ((BaseClass) testClass).getDriver();

        attachScreenshotToAllure(result.getMethod().getMethodName());
    }


    @Override
    public void onTestSkipped(ITestResult result) {
        String msg = "TEST SKIPPED: " + result.getMethod().getMethodName();
        Reporter.log(msg, true);
        logToFile(msg);
    }


    public void attachScreenshotToAllure(String testName) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            saveScreenshotToFile(testName, screenshot);
            Allure.addAttachment("Failure Screenshot - " + testName, new ByteArrayInputStream(screenshot));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveScreenshotToFile(String testName, byte[] screenshot) throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        Path destination = Paths.get("target", "screenshots", testName + "_" + timestamp + ".png");
        Files.createDirectories(destination.getParent());
        Files.write(destination, screenshot);
    }

}