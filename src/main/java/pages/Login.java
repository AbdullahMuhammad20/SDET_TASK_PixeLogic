package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login
{
    WebDriver driver;

    // define locators
    By email = By.name("username");
    By password = By.name("password");
    By btn_login = By.xpath("//button[@type='submit' and contains (class, signupbtn) and contains(text() ,'Login')]");

    public Login(WebDriver driver)
    {
        this.driver = driver;
    }


    // function to get locators and interact with them
    public void setEmail(String Email)
    {
        driver.findElement(this.email).sendKeys(Email);
    }
    public void setPassword(String Password)
    {
        driver.findElement(this.password).sendKeys(Password);
    }
    public void clickLogin()
    {
        driver.findElement(this.btn_login).click();
    }

    public void loginWithRegisteredData(String Email,String Password)
    {
        this.setEmail(Email);
        this.setPassword(Password);
        clickLogin();
    }
}
