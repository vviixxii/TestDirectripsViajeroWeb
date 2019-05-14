package directripsViajeroWeb;

import org.testng.Assert;
import org.testng.annotations.*;

import directripsViajeroWeb.core.BaseTest;
import directripsViajeroWeb.core.DirectripsDataProvider;
import directripsViajeroWeb.core.DirectripsListener;
import utils.Log;

import static org.testng.Assert.assertFalse;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * TestCase: Búsqueda por Hotel 2
 * 
 * @author Ricardo Romero
 * @version 1.0.0
 * @date 25/04/19
 */
@Listeners(DirectripsListener.class)
public class TCBusquedaPorHotel2 extends BaseTest {

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

	@Test(dataProvider = "busquedas", dataProviderClass = DirectripsDataProvider.class)
	public void testBusquedaPorHotel2(String... params) throws Exception {
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
			Log.info("Escribe datos del usuario");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email"))).sendKeys(params[1]);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password"))).sendKeys(params[2]);

			Thread.sleep(2000);
			Log.info("Presiona boton iniciar sesion nuevamente");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(".//*[@id=\"registro_cuenta\"]/div/div/button[1]"))).click();

			Thread.sleep(5000);
			Log.info("Click en la busqueda");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(".//*[@id=\"fake_search\"]/ul[1]/li/form/input"))).click();

			Thread.sleep(5000);
			Log.info("Escribe el hotel a buscar");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-search"))).sendKeys(params[3]);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-search"))).sendKeys(Keys.ENTER);

			Thread.sleep(5000);
			Log.info("Verifica que se encuentren resultados");
			WebElement item = driver.findElement(By.xpath(".//*[@id=\"wrapper\"]/div/div[3]/div/div/div[1]/div"));
			if (item != null) {
				String msgErr = item.getText();
				if (msgErr.length() != 0)
					Log.debug("msgErr -> " + msgErr);
				assertFalse(msgErr.equals("No se encontraron resultados"), "No se encontraron resultados");
			}

			Log.info("Selecciona el primer hotel de la lista desplegada");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(".//*[@id=\"wrapper\"]/div/div[3]/div/div/div[2]/ul/li[8]")))
					.click();

			Thread.sleep(5000);
			Log.info("Regresa a la lista consultada");
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id=\"wrapper\"]/div/nav/div[1]/a")))
					.click();

			Thread.sleep(5000);
			Log.info("Regresa a pagina principal");
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(".//*[@id=\"wrapper\"]/div/div[2]/nav/div/a"))).click();

			Thread.sleep(5000);
			Log.info("Presiona el boton de Perfil");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id=\"header\"]/nav/div/ul/li[4]")))
					.click();

			Thread.sleep(5000);
			Log.info("Presiona Cerrar sesion");
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath(".//*[@id=\"mobile-view\"]/app-root/home/account/div/ul/li[3]/span/label"))).click();

			Thread.sleep(1000);
			Log.info("Termina correctamente");
			Assert.assertTrue(true);
		} catch (TimeoutException e) {
			Log.info("No existe el elemento buscado ..... " + e.getMessage());
			Assert.assertTrue(false, e.getMessage());
		} 
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		Log.endTestCase(this.getClass().getName());
	}
}