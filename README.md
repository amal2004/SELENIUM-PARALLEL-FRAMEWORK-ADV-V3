# 🚀 Selenium Parallel Automation Framework Adv-V3

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
* 🧰 WebDriverManager integration
* 🧼 Clean driver lifecycle management
* 🧩 Custom exception handling

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
│   ├── DriverFactory.java
│   └── BrowserManager.java
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

### 📄 config.properties

```properties
base.url=http://localhost:5000
browser=edge
timeout=30
headless=false
```

---

## 🧠 Framework Architecture

The framework follows **Page Object Model (POM)** with **thread-safe parallel execution**.

---

### 🔹 Core Components

#### 📌 ConfigManager

* Loads configuration from `config.properties`
* Supports system property overrides
* Provides typed getters (`String`, `int`, `boolean`)
* Validates missing values

---

#### 📌 BrowserManager (NEW)

* Responsible for **creating WebDriver instances**
* Encapsulates browser-specific logic
* Uses `WebDriverManager` internally
* Supports:

  * Chrome
  * Firefox
  * Edge
* Handles browser options (headless, arguments)

```java
WebDriver driver = BrowserManager.createDriver(browser, headless);
```

---

#### 📌 DriverFactory (UPDATED)

* Manages **thread-safe WebDriver using ThreadLocal**
* Delegates driver creation to `BrowserManager`
* Applies global configurations:

  * Window maximize
  * Page load timeout
  * Disable implicit waits
* Handles driver lifecycle (init / quit)

```java
DriverFactory.initDriver("chrome");
WebDriver driver = DriverFactory.getDriver();
```

---

#### 📌 BasePage

* Common reusable Selenium methods:

  * click()
  * type()
  * getText()
  * wait utilities
  * navigation helpers

---

#### 📌 BaseTest

* Handles setup & teardown
* Initializes driver per test
* Captures screenshots on failure
* Ensures clean driver shutdown

---

#### 📌 Page Classes (RegisterPage)

* Encapsulates UI interactions
* Maintains locators and actions

---

#### 📌 ScreenshotUtil

* Captures failure screenshots
* Saves to:

```
target/screenshots/
```

---

#### 📌 BrowserType (Enum)

* Supported browsers:

  * CHROME
  * FIREFOX
  * EDGE
* Converts string → enum safely

---

#### 📌 FrameworkException

* Custom runtime exception
* Handles:

  * Invalid browser
  * Missing config
  * Framework errors

---

### 🔹 Architecture Flow

```
TestNG Test
   ↓
BaseTest
   ↓
DriverFactory (ThreadLocal)
   ↓
BrowserManager (Driver Creation)
   ↓
BasePage
   ↓
Page Classes
   ↓
Selenium WebDriver
```

---

## 🧪 Test Execution

### 📄 testng.xml

```xml
<suite name="ParallelRegistrationSuite" parallel="tests" thread-count="12">
```

### 🌐 Supported Browsers

* Chrome
* Firefox
* Edge

---

## 🧾 Sample Test Flow

1. Launch browser
2. Navigate to page
3. Fill form
4. Submit
5. Validate results
6. Capture screenshot on failure

---

## 🧪 Example Test Case

```java
@Test(dataProvider = "registrationData")
public void testRegistration(...) {

    RegisterPage page = new RegisterPage();

    page.open();
    page.selectGender(gender);
    page.fillForm(...);
    page.submit();

    Assert.assertTrue(page.isRegistrationSuccessful());
}
```

---

## 🚀 Running the Framework

### Clone Repo

```bash
git clone https://github.com/your-repo/selenium-parallel.git
```

### Start the Application Under Test

Download nopCommerce_4.80.9 and run Nop.Web.exe.
Ensure your application is running at:

```
http://localhost:5000/register
```

### Run Tests

```bash
mvn clean test
```

### Run with Browser

```bash
mvn test -Dbrowser=chrome
```

---

## 📸 Screenshot Feature

```
target/screenshots/testName_timestamp.png
```

---

## 🧰 Tech Stack

* Java
* Selenium WebDriver
* TestNG
* Maven
* WebDriverManager

---

## 👨‍💻 Author

Built for scalable, maintainable, and parallel Selenium automation by Amal W
