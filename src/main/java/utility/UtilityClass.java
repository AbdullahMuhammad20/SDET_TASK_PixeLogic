package utility;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilityClass
{
    public static String captureScreenshot(WebDriver driver)
    {
        String screenshotPath =System.getProperty("user.dir") +"/Screenshots/PHPTravels"+getCurrentDateTime()+".png";
        try
        {
            TakesScreenshot ts =  (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            // using (org.apache.commons) dependency in POM.xml to get library to use this feature
            FileUtils.copyFile(source,new File(screenshotPath));

            System.out.println("Screenshot taken");
        }
        catch (Exception e)
        {
            System.out.println("Screenshot not taken -  "+e.getMessage());
        }
        return  screenshotPath;
    }

    public static String getCurrentDateTime()
    {
        DateFormat customFormat =  new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
        Date currentDate = new Date();
        return customFormat.format(currentDate);
    }
}
