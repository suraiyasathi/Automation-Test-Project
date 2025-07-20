# Automation-Test-Project
A practical automation project that enhances Selenium-based testing with advanced debugging capabilities - helping testers identify, log, and resolve test failures faster and more effectively.



---



## 📁 Project Overview



This framework automates login functionality on the [Automation Exercise](https://www.automationexercise.com/) site using:

- Selenium WebDriver

- TestNG

- Allure Reporting

- Smart logging with screenshots on failure



---



## 📦 Install Dependencies



### ✅ Prerequisites

Make sure the following are installed on your system:

- Java 17+

- Maven

- Allure CLI



---



## 🧪 Running the Tests



Run the following command to clean and execute your TestNG suite:

```bash

mvn clean test

```



After execution, you'll find:

- 📸 **Screenshots** in `target/screenshots/`

- 🧾 **Test Logs** in `target/test-output/test-log.txt`

- 📊 **Allure Results** in `target/allure-results/`



To generate the Allure report:

```bash

allure serve target/allure-results

```



---



## 🔍 Key Features



- ✅ Logs success, skip, and failure status of every test method.

- 📸 Captures **screenshots automatically** on test failure.

- 🧾 Stores **clear failure reasons** (e.g., "Element not found") in the log file.

- 🧹 Automatically clears old screenshots and logs on each run.

- 🔄 Simple, no external listener or utility class—**all in one BaseClass**.



---




## 🚀 How It All Works Together



Each test runs under a structured lifecycle:

- The browser launches at the suite start.

- Each method logs its outcome (pass, fail, skip) in `test-log.txt`.

- On failure, a screenshot is captured, stored, and linked to Allure.

- Everything resets cleanly for each test run under `target/`.



This setup makes it easier to identify bugs faster, integrate into CI/CD pipelines, and build more resilient test suites without unnecessary complexity.



---



## ✅ Example Test Scenario



**Automated Login Validation on Automation Exercise:**

- Visit login page

- Enter valid credentials

- Validate successful login



Only relevant and necessary code is maintained for clarity and readability.



---



## 🔗 Reference



Read the full article: [Test Smarter, Not Harder: Detailed Failure Debugging in Selenium](https://qabrains.com/test-smarter-not-harder-detailed-failure-debugging-in-selenium)
