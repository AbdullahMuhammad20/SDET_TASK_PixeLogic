package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Account
{
    WebDriver driver;

    // define locators to interact with them
    By welcomeText = By.xpath("//*[@style='margin-left: 17px']");
    By userNameDropDown = By.xpath("//li[contains(@class, 'd-none')]");
    By logout      =  By.xpath("//a[contains(., 'Logout')]");


    public Account(WebDriver driver)
    {
        this.driver = driver;
    }

    // function to get locators and interact with them
    public String getWelcomeText()
    {
        return driver.findElement(welcomeText).getText();
    }
    public void   clickLogout()
    {
        driver.findElement(userNameDropDown).click();
        driver.findElement(logout).click();
    }

}
