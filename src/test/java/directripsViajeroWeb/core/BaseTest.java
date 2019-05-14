package directripsViajeroWeb.core;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

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

//	public static void initialization() {
//		System.setProperty("webdriver.chrome.driver", "libChrome//chromedriver"); //LOCAL
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("start-maximized"); // open Browser in maximized mode
//		options.addArguments("disable-infobars"); // disabling infobars
//		options.addArguments("--disable-extensions"); // disabling extensions
//		options.addArguments("--disable-gpu"); // applicable to windows os only
//		options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
//		options.addArguments("--no-sandbox"); // Bypass OS security model
//		options.setExperimentalOption("useAutomationExtension", false);
//		driver = new ChromeDriver(options);

//		System.setProperty("webdriver.chrome.driver",
//				"//home//jenkinsserver//node_modules//chromedriver//lib//chromedriver//chromedriver");
//		ChromeOptions options = new ChromeOptions();
//		options.addArguments("start-maximized"); // open Browser in maximized mode
//		options.addArguments("disable-infobars"); // disabling infobars
//		options.addArguments("--disable-extensions"); // disabling extensions
//		options.addArguments("--disable-gpu"); // applicable to windows os only
//		options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
//		options.addArguments("--no-sandbox"); // Bypass OS security model
//		// options.addArguments("--headless"); // No GUI
//		options.setExperimentalOption("useAutomationExtension", false);
//		driver = new ChromeDriver(options);
//	}

	public static void initialization(String browser) {
		if (browser.equalsIgnoreCase("FF")) {
			System.setProperty("webdriver.gecko.driver", "libGecko//geckodriver"); // LOCAL
			FirefoxOptions options = new FirefoxOptions().addPreference("geo.enabled", true)
					.addPreference("geo.provider.use_corelocation", true).addPreference("geo.prompt.testing", true)
					.addPreference("geo.prompt.testing.allow", true);
			driver = new FirefoxDriver(options);
		} else if (browser.equalsIgnoreCase("CH")) {
//			System.setProperty("webdriver.chrome.driver", "libChrome//chromedriver"); // LOCAL
//			ChromeOptions options = new ChromeOptions();
//			options.addArguments("start-maximized"); // open Browser in maximized mode
//			options.addArguments("disable-infobars"); // disabling infobars
//			options.addArguments("--disable-extensions"); // disabling extensions
//			options.addArguments("--disable-gpu"); // applicable to windows os only
//			options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
//			options.addArguments("--no-sandbox"); // Bypass OS security model
//			options.setExperimentalOption("useAutomationExtension", false);
//			driver = new ChromeDriver(options);

			System.setProperty("webdriver.chrome.driver", 
					"//home//jenkinsserver//node_modules//chromedriver//lib//chromedriver//chromedriver");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized"); // open Browser in maximized mode
			options.addArguments("disable-infobars"); // disabling infobars
			options.addArguments("--disable-extensions"); // disabling extensions
			options.addArguments("--disable-gpu"); // applicable to windows os only
			options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
			options.addArguments("--no-sandbox"); // Bypass OS security model
			// options.addArguments("--headless"); // No GUI
			options.setExperimentalOption("useAutomationExtension", false);
			driver = new ChromeDriver(options);
		}
	}

	public void takeSnapShot(String fileWithPath) throws Exception {
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		Log.debug("scrShot -> " + scrShot);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(fileWithPath);
		FileUtils.copyFile(SrcFile, DestFile);
	}
}
