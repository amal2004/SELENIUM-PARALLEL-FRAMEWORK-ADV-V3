# 🚀 Selenium Automation Framework Parallel ADV V3

A scalable, thread-safe Selenium Test Automation Framework built using Java, TestNG, and WebDriverManager, designed for parallel execution across multiple browsers (Chrome, Firefox, Edge).

---

## 📌 Features

* ⚡ Parallel test execution using TestNG
* 🌐 Cross-browser support (Chrome, Firefox, Edge)
* 🧵 Thread-safe WebDriver using `ThreadLocal`
* ⚙️ Config-driven execution (`config.properties`)
* 🧪 Page Object Model (POM) architecture
* 📸 Automatic screenshot capture on test failure
* 🔄 Data-driven testing using TestNG DataProvider
* 🧰 WebDriverManager integration (optional)
* 🧼 Clean driver lifecycle management
* 🧩 Custom exception handling for framework stability

---

## 🏗️ Project Structure

```
com.amalw.parallel
│
├── base
│   └── BaseTest.java
│
├── config
│   └── ConfigManager.java
│
├── driver
│   └── DriverFactory.java
│
├── enums
│   └── BrowserType.java
│
├── exceptions
│   └── FrameworkException.java
│
├── pages
│   ├── BasePage.java
│   └── RegisterPage.java
│
├── tests
│   └── RegistrationTest.java
│
└── utils
    └── ScreenshotUtil.java
```

---

## ⚙️ Supported Properties

The framework uses a `config.properties` file for external configuration.

### 📄 config.properties

```
base.url=http://localhost:5000
browser=edge
timeout=30
```

### 🧾 Property Reference

| Key      | Type   | Description                |
| -------- | ------ | -------------------------- |
| base.url | String | Application under test URL |
| browser  | String | chrome / firefox / edge    |
| timeout  | int    | Default timeout in seconds |

### 🔁 Override via Command Line

```
mvn test -Dbrowser=chrome -Dtimeout=20
```

System properties take priority over `config.properties`.

---

## 🧠 Framework Architecture

The framework follows the **Page Object Model (POM)** with thread-safe execution.

### 🔹 Core Components

#### 📌 ConfigManager

* Loads configuration from `config.properties`
* Supports system property overrides
* Provides typed getters (`String`, `int`, `boolean`)
* Validates missing configuration

#### 📌 DriverFactory

* Uses `ThreadLocal<WebDriver>` for parallel execution
* Supports Chrome, Firefox, Edge
* Optional WebDriverManager integration
* Handles headless mode
* Applies global driver configuration

#### 📌 BasePage

* Reusable Selenium methods:

  * click()
  * type()
  * getText()
  * wait utilities
  * navigation helpers

#### 📌 BaseTest

* Initializes WebDriver before each test
* Cleans up driver after execution
* Captures screenshots on failure

#### 📌 Page Classes (RegisterPage)

* Contains locators and page actions
* Encapsulates UI interactions

#### 📌 ScreenshotUtil

* Captures screenshots
* Stores in:

```
target/screenshots/
```

#### 📌 BrowserType (Enum)

* Supported browsers:

  * CHROME
  * FIREFOX
  * EDGE

#### 📌 FrameworkException

* Custom runtime exception for framework errors

---

### 🔹 Architecture Flow

```
TestNG Test
   ↓
BaseTest (Setup / Teardown)
   ↓
DriverFactory (ThreadLocal WebDriver)
   ↓
BasePage (Reusable Actions)
   ↓
Page Classes (RegisterPage)
   ↓
Selenium WebDriver
```

---

## 🧪 Test Execution

Tests are executed using TestNG XML configuration.

### 📄 testng.xml

```
<suite name="ParallelRegistrationSuite" parallel="tests" thread-count="12">

    <test name="ChromeTests">
        <parameter name="browser" value="chrome" />
        <classes>
            <class name="com.amalw.parallel.tests.RegistrationTest"/>
        </classes>
    </test>

    <test name="FirefoxTests">
        <parameter name="browser" value="firefox" />
        <classes>
            <class name="com.amalw.parallel.tests.RegistrationTest"/>
        </classes>
    </test>

    <test name="EdgeTests">
        <parameter name="browser" value="edge" />
        <classes>
            <class name="com.amalw.parallel.tests.RegistrationTest"/>
        </classes>
    </test>

</suite>
```

### 🌐 Supported Browsers (Parallel Execution)

* Chrome
* Firefox
* Edge

---

## 🧾 Sample Test Flow

1. Launch browser
2. Navigate to registration page
3. Fill form using test data
4. Submit form
5. Validate success message
6. Capture screenshot on failure

---

## 🧪 Example Test Case

```java
@Test(dataProvider = "registrationData")
public void testRegistration(String firstName, String lastName,
                             String gender, String company,
                             String password, String conPassword) {

    String email = UUID.randomUUID() + "@example.com";

    RegisterPage registerPage = new RegisterPage();

    registerPage.open();
    registerPage.selectGender(gender);

    registerPage.fillForm(firstName, lastName, email, company, password, conPassword);
    registerPage.submit();

    Assert.assertTrue(registerPage.isRegistrationSuccessful(), "Registration failed!");
    Assert.assertEquals(registerPage.getConfirmationMessage(),
            "Your registration completed");
}
```

---

## 🚀 Running the Framework

### 📥 Clone Repository

```
git clone https://github.com/your-repo/selenium-parallel.git
```

### ▶️ Run Tests

```
mvn clean test
```

### 🌐 Run with Specific Browser

```
mvn test -Dbrowser=chrome
```

### ⚙️ Run via TestNG XML

```
mvn clean test -DsuiteXmlFile=testng.xml
```

---

## 📸 Screenshot Feature

Screenshots are automatically captured on test failure:

```
target/screenshots/testName_timestamp.png
```

---

## 🧰 Tech Stack

* Java 17+
* Selenium WebDriver 4+
* TestNG
* Maven
* WebDriverManager
* Page Object Model (POM)

---

## ❗ Custom Exception Handling

```java
public class FrameworkException extends RuntimeException
```

Used for:

* Missing configuration
* Invalid browser values
* Framework-level errors

---

## 🔥 Future Enhancements

* Allure / Extent Reports
* CI/CD integration (GitHub Actions / Jenkins)
* Docker execution support
* Retry mechanism for flaky tests
* API + UI combined framework

---

## 👨‍💻 Author

Built for scalable, maintainable, and parallel Selenium automation execution. By Amal W
