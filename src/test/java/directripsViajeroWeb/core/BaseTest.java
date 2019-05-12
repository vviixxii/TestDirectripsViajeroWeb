package directripsViajeroWeb.core;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.Log;

/**
 * Clase base para la creación de los Test Case
 * 
 * @author Ricardo Romero
 * @version 1.0.0
 * @date 07/01/19
 */
public class BaseTest {

	public static WebDriver driver;
	String propertyFilePath = "config//config.properties";
	protected Properties prop = new Properties();

	public static void initialization() {
		//System.setProperty("webdriver.chrome.driver", "libChrome//chromedriver"); LOCAL
		System.setProperty("webdriver.chrome.driver", "//home//jenkinsserver//node_modules//chromedriver//lib//chromedriver//chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized"); // open Browser in maximized mode
		options.addArguments("disable-infobars"); // disabling infobars
		options.addArguments("--disable-extensions"); // disabling extensions
		options.addArguments("--disable-gpu"); // applicable to windows os only
		options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
		options.addArguments("--no-sandbox"); // Bypass OS security model
		//driver = new ChromeDriver();
		driver = new ChromeDriver(options);
	}
	
	public void takeSnapShot(String fileWithPath) throws Exception {
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		Log.debug("scrShot -> " + scrShot);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(fileWithPath);
		FileUtils.copyFile(SrcFile, DestFile);
	}
}
