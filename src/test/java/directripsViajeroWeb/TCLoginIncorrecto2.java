package directripsViajeroWeb;

import org.testng.Assert;
import org.testng.annotations.*;

import directripsViajeroWeb.core.BaseTest;
import directripsViajeroWeb.core.DirectripsDataProvider;
import directripsViajeroWeb.core.DirectripsListener;
import utils.Log;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * TestCase: Login incorrecto 2
 * 
 * @author Ricardo Romero
 * @version 1.0.0
 * @date 25/04/19
 */
@Listeners(DirectripsListener.class)
public class TCLoginIncorrecto2 extends BaseTest {

	WebDriverWait wait;

//	@BeforeMethod
//	public void setUp() {
//		initialization();
//		Log.startTestCase(this.getClass().getName());
//	}

	@AfterMethod
	public void teardown() {
		driver.quit();
	}

	@Test(dataProvider = "minimo", dataProviderClass = DirectripsDataProvider.class, enabled = false)
	public void testLoginIncorrecto2(String... params) throws Exception {
		try {

			String browser = params[params.length - 1];
			initialization(browser);
			Log.startTestCase(this.getClass().getName());
			
			wait = new WebDriverWait(driver, 30);
			driver.get(params[0]);

			Log.info("Quita el msg de uso de cookies");
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(".//*[@id=\"mobile-view\"]/app-root/cookies-advice/div/div[1]/a"))).click();

			Log.info("Presiona boton iniciar sesion");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(".//*[@id=\"registro_cuenta\"]/div/div/button[2]"))).click();

			Thread.sleep(5000);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email"))).sendKeys(params[1]);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys(params[2]);

			Thread.sleep(5000);
			Log.info("Presiona boton iniciar sesion nuevamente");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(".//*[@id=\"registro_cuenta\"]/div/div/button[1]"))).click();

			Thread.sleep(2000);
			Log.info("Verifica que no salga la exception Nombre de usuario o contraseña incorrectos");
			Boolean flag = driver.findElements(By.xpath(".//*[@id=\"registro_cuenta\"]")).isEmpty();
			if (!flag) {
				WebElement item = driver.findElement(By.xpath(".//*[@id=\"registro_cuenta\"]/div/div/div"));
				String msgErr = item.getText();
				Log.debug("msgErr -> " + msgErr);
				assertTrue(msgErr.equals("Nombre de usuario o contraseña incorrectos"),
						"Nombre de usuario o contraseña incorrectos");
			}

			Thread.sleep(1000);
			Log.info("Termina correctamente");
			Assert.assertTrue(true);
		} catch (Exception e) {
			Log.info("Termina con error");
			Assert.assertTrue(false, e.getMessage());
		}
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		Log.endTestCase(this.getClass().getName());
	}
}