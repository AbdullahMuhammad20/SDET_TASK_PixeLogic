package pages;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.testng.Assert;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;

public class Register {

    WebDriver driver;

    public Register(WebDriver driver)
    {
        this.driver = driver;
    }

    // get locators form DOM
    By first_name = By.name("firstname");
    By last_name = By.name("lastname");
    By phone = By.name("phone");
    By email = By.name("email");
    By password = By.name("password");
    By confirm_pass = By.name("confirmpassword");
    By btn_signUp = By.xpath("//button[contains(.,'Sign Up')]");
    By alert_Dialog = By.className("resultsignup");

    // String text to use in validation alert message
    String First_Name_Alert = "First Name should start with capital letter";
    String Last_Name_Alert = "Last Name should start with capital letter";
    String First_Last_Name_Matches = "First Name and Last Name should not be matched";
    String Mobile_Number_Alert = "Please Enter Valid Number";
    String Email_Format_Alert = "The Email field must contain a valid email address.";
    String Email_Existing_Alert = "Email Already Exists.";
    String Password_Format_Alert = "Please Enter Invalid Password Format";
    String Password_Max_Length_Alert = "The Password field must be a limit of 8 characters in length.";



    /*
    //  get data from register page items to check all
    public String getFirstName()
    {
        return driver.findElement(first_name).getText();
    }
    public String getLastName()
    {
        return driver.findElement(last_name).getText();
    }
    public String getPhone()
    {
        return driver.findElement(phone).getText();
    }
    public String getEmail()
    {
        return driver.findElement(email).getText();
    }
    public String getPassword() { return driver.findElement(password).getText(); }
    public String getConfirmPassword()
    {
        return driver.findElement(confirm_pass).getText();
    }
    */


    // Functions to find locators and interact with them
    public void setFirst_name(String FirstName)
    {
        driver.findElement(first_name).sendKeys(FirstName);
    }
    public void setLast_name(String LastName)
    {
        driver.findElement(last_name).sendKeys(LastName);
    }
    public void setPhone(String Phone)
    {
        driver.findElement(phone).sendKeys(Phone);
    }
    public void setEmail(String Email) { driver.findElement(email).sendKeys(Email); }
    public void setPassword(String Password)
    {
        driver.findElement(password).sendKeys(Password);
    }
    public void setConfirm_pass(String ConfPassword)
    {
        driver.findElement(confirm_pass).sendKeys(ConfPassword);
    }
    public void clickRegister()
    {
        driver.findElement(btn_signUp).sendKeys(Keys.ENTER);
    }


    // functions to help me assert message in alert dialog
    private boolean assertAlertElementIsDisplay(By element)
    {
        boolean status = driver.findElement(element).isDisplayed();
        if (status)
            return  true;
        else
            return false;
    }

    private void assertAlertMessageIsDisplay(String expected_Alert_Message)
    {

        String Actual_Alert_Message = driver.findElement(alert_Dialog).getText();
        if(assertAlertElementIsDisplay(alert_Dialog))
        {
            Assert.assertEquals(Actual_Alert_Message,expected_Alert_Message);
        }
        else
        {
            Assert.assertTrue(!assertAlertElementIsDisplay(alert_Dialog),"Alert Message Is Not Displayed");
        }
    }

    // tests to check validation of register page items
    public void registerWithInvalid_First_Name()
    {
        RegisterAccount("ahmed","Omar","01145612426","ahmed111@gmail.com","Asd123456","Asd123456");
        assertAlertMessageIsDisplay(First_Name_Alert);
    }
    public void registerWithInvalid_Last_Name()
    {
        RegisterAccount("Ahmed","omar","01145612426","ahmed112@gmail.com","Asd123456","Asd123456");
        assertAlertMessageIsDisplay(Last_Name_Alert);
    }
    public void registerWith_First_Last_Name_Matche()
    {
        RegisterAccount("ahmed","Omar","01145612426","ahmed113@gmail.com","Asd123456","Asd123456");
        assertAlertMessageIsDisplay(First_Last_Name_Matches);
    }
    public void registerWith_Invalid_Mobile()
    {
        RegisterAccount("ahmed","Omar","Ahm222###","ahmed114@gmail.com","Asd123456","Asd123456");
        assertAlertMessageIsDisplay(Mobile_Number_Alert);
    }
    public void registerWith_Invalid_Email_Format()
    {
        RegisterAccount("ahmed","Omar","01145612426","ahmed157","Asd123456","Asd123456");
        assertAlertMessageIsDisplay(Email_Format_Alert);
    }
    public void registerWith_Invalid_Email_Existing()
    {
        RegisterAccount("ahmed","Omar","01145612426","ahmed115@gmail.com","Asd123456","Asd123456");
        assertAlertMessageIsDisplay(Email_Existing_Alert);
    }

    public void registerWith_Invalid_Max_limit_Password()
    {
        RegisterAccount("ahmed","Omar","01145612426","ahmed116@gmail.com","1234567891010","Asd123456");
        assertAlertMessageIsDisplay(Password_Max_Length_Alert);
    }
    public void registerWith_Invalid_Passwords_Format()
    {
        RegisterAccount("ahmed","Omar","01145612426","ahmed117@gmail.com","147","Asd123456");
        assertAlertMessageIsDisplay(Password_Format_Alert);
    }




    // Function to get Response and save content in text file
    private void getResponse()
    {
        String currentURL = driver.getCurrentUrl();
        LogEntries logs = driver.manage().logs().get("performance");
        int status = -1;

        for(Iterator<LogEntry> it = logs.iterator(); it.hasNext();)
        {
            LogEntry entry = it.next();
            try
            {
                JSONObject json = new JSONObject(entry.getMessage());
                JSONObject message = json.getJSONObject("message");
                String method = message.getString("method");

                if (method != null && "Network.responseReceived".equals(method))
                {
                    JSONObject params = message.getJSONObject("params");
                    JSONObject response = params.getJSONObject("response");
                    String messageUrl = response.getString("url");

                    if (currentURL.equals(messageUrl))
                    {
                        status = response.getInt("status");
                        PrintStream apiResponse;

                        try
                        {
                            apiResponse = new PrintStream(new FileOutputStream("API_GET_REQ_RESPONSE.txt"));
                            apiResponse.println("returned response for " + messageUrl + ": " + status);
                            apiResponse.println("\nstatus code: " + status);
                            apiResponse.println("headers: " + response.get("headers"));
                            apiResponse.println(json.toString());
                            apiResponse.println("");
                            System.setOut(apiResponse);
                        }
                        catch (FileNotFoundException Fex)
                        {
                            Fex.printStackTrace();
                        }
                    }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

    }


    // Set data into items get from external method
    public void RegisterAccount(String First_name,String Last_name,String Phone,String Email,String Password,String Confirm_Pass)
    {
        this.setFirst_name(First_name);
        this.setLast_name(Last_name);
        this.setPhone(Phone);
        this.setEmail(Email);
        this.setPassword(Password);
        this.setConfirm_pass(Confirm_Pass);
        this.clickRegister();

        // Save Response after Register
        getResponse();
    }




}
