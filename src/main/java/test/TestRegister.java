package test;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.ITestResult;
import org.testng.annotations.*;

import pages.Account;
import pages.Login;
import pages.Register;
import utility.UtilityClass;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static org.testng.Assert.assertEquals;

public class TestRegister
{

    private WebDriver driver;
    private WebDriverWait wait;
    private ExtentReports report;
    private ExtentTest logger;
    private Register  objRegister;
    private Account   objAccount;
    private Login     objLogin;

    String BASE_URL = "https://www.phptravels.net/register";


    // Data pass to use in register function, Also we can use "DATA DRIVEN" feature
    String FIRST_NAME = "Abdullah";
    String LAST_NAME = "Muhammed";
    String PHONE = "01145612428";
    String EMAIL = "Abdullah23@gmail.com";
    String PASSWORD = "Asd123456+++";
    String CONFIRM_PASS = "Asd123456+++";


    @BeforeSuite
    public void setupSuite()
    {
        ExtentHtmlReporter extent  =
                new ExtentHtmlReporter(
                        new File(System.getProperty("user.dir") + "/Reports/RegisterDemo"+ UtilityClass.getCurrentDateTime()+".html"));
        report =  new ExtentReports();
        report.attachReporter(extent);

    }

    @BeforeClass
    public void setup()
    {
        if (driver == null)
        {
            String chromePath = System.getProperty("user.dir") + "\\resources\\chromedriver.exe";
            System.setProperty("webdriver.chrome.driver",chromePath);
              //driver = new ChromeDriver();
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--incognito");
        DesiredCapabilities cap =  DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY,options);

        LoggingPreferences logPref = new LoggingPreferences();
        logPref.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability("goog:loggingPrefs",logPref);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.navigate().to(BASE_URL);
        objRegister =  new Register(driver);
        objAccount  = new Account(driver);
        objLogin    = new Login(driver);
    }

    @BeforeMethod
    public void navigateToRegisterPage()
    {

        driver.navigate().to(BASE_URL);
    }

    @Test
    public void testFirstNameValidation() { objRegister.registerWithInvalid_First_Name(); }

    @Test
    public void testLastNameValidation() { objRegister.registerWithInvalid_Last_Name(); }

    @Test
    public void testFirstLastValidation() { objRegister.registerWith_First_Last_Name_Matche(); }

    @Test
    public void testMobileValidation() { objRegister.registerWith_Invalid_Mobile(); }

    @Test
    public void testEmailFormatValidation() { objRegister.registerWith_Invalid_Email_Format(); }

    @Test
    public void testEmailExistingValidation() { objRegister.registerWith_Invalid_Email_Existing(); }

    @Test
    public void testPasswordLimitValidation() { objRegister.registerWith_Invalid_Max_limit_Password(); }

    @Test
    public void testPasswordFormatValidation() { objRegister.registerWith_Invalid_Passwords_Format(); }

    @Test
    public void registration()
    {
        logger =  report.createTest("Register to PHPTravels");
        logger.info("Starting Page");

        objRegister.RegisterAccount(FIRST_NAME,LAST_NAME,PHONE,EMAIL,PASSWORD,CONFIRM_PASS);
        wait = new WebDriverWait(driver , 20);
        wait.until(ExpectedConditions.titleContains("My Account"));

        logger.pass("Register success");

        // get Welcome text to assert if user register is success after insert registration data
        // Compare between welcome text  + first + last name And first + last is passed before
        assertEquals(objAccount.getWelcomeText(),"Hi, "+ FIRST_NAME + " " + LAST_NAME);

        logger.pass("Assert if Register success");
    }

    @Test(dependsOnMethods = {"registration"})
    public void loginWithRegisterData()
    {

        logger =  report.createTest("Login to PHPTravels");
        logger.info("Starting Page");
        // logout from account page and redirect to login
        objAccount.clickLogout();

        logger.info("After logout success");

        // login action from login page
        objLogin.loginWithRegisteredData(EMAIL,PASSWORD);

        logger.info("login success");
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.titleContains("My Account"));


        // Compare between welcome text  + first + last name And first + last is passed before
        assertEquals(objAccount.getWelcomeText(),"Hi, "+ FIRST_NAME + " " + LAST_NAME);
        logger.pass("Assert if login success");
    }

    @AfterMethod
    public void tearDownMethod(ITestResult result) throws IOException
    {
        // Also we can use multi case here if test is "passed" or "skip" or anu case we can take screenshot also
        if (ITestResult.FAILURE == result.getStatus())
        {
            logger.fail("Test Failed" , MediaEntityBuilder.createScreenCaptureFromPath(UtilityClass.captureScreenshot(driver)).build());
        }
        /*
        else if (ITestResult.SUCCESS == result.getStatus())
        {
            //System.out.println("Hello Before write in report");
            logger.pass("Test Passed", MediaEntityBuilder.createScreenCaptureFromPath(UtilityClass.captureScreenshot(driver)).build());
           // System.out.println("Hello After write in report and Take Screen");
        }
         */
        report.flush();

        if (driver.getCurrentUrl().equalsIgnoreCase("https://www.phptravels.net/account/"))
        {
            objAccount.clickLogout();
        }
    }

    @AfterClass
    public void closeSetup()
    {
        driver.quit();
    }
}
