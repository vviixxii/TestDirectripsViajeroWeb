package directripsViajeroWeb;

import org.testng.Assert;
import org.testng.annotations.*;

import directripsViajeroWeb.core.BaseTest;
import directripsViajeroWeb.core.DirectripsDataProvider;
import directripsViajeroWeb.core.DirectripsListener;
import utils.Log;

import static org.testng.Assert.assertFalse;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;

/**
 * TestCase: Búsqueda por Hotel
 * 
 * @author Ricardo Romero
 * @version 1.0.0
 * @date 22/04/19
 */
@Listeners(DirectripsListener.class)
public class TCBusquedaPorHotel extends BaseTest {

//	@BeforeMethod
//	public void setUp() {
//		initialization();
//		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//		Log.startTestCase(this.getClass().getName());
//	}

	@AfterMethod
	public void teardown() {
		driver.quit();
	}

	@Test(dataProvider = "busquedas", dataProviderClass = DirectripsDataProvider.class)
	public void testBusquedaPorHotel(String... params) throws Exception {
		try { 
			String browser = params[params.length - 1];
			Log.info("Browser --> " + browser);
			initialization(browser);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			Log.startTestCase(this.getClass().getName());
			
			driver.get(params[0]);
			
			Log.info("Quita el msg de uso de cookies");
			driver.findElement(By.xpath(".//*[@id=\"mobile-view\"]/app-root/cookies-advice/div/div[1]/a/img")).click();

			Log.info("Presiona boton iniciar sesion");
			driver.findElement(By.xpath(".//*[@id=\"registro_cuenta\"]/div/div/button[2]")).click();

			Thread.sleep(5000);
			Log.info("Escribe datos del usuario");
			driver.findElement(By.name("email")).click();
			driver.findElement(By.name("email")).clear();
			driver.findElement(By.name("email")).sendKeys(params[1]);
			driver.findElement(By.name("password")).click();
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys(params[2]);

			Thread.sleep(2000);
			Log.info("Presiona boton iniciar sesion nuevamente");
			driver.findElement(By.xpath(".//*[@id=\"registro_cuenta\"]/div/div/button[1]")).click();

			Thread.sleep(5000);
			Log.info("Click en la busqueda");
			driver.findElement(By.xpath(".//*[@id=\"fake_search\"]/ul[1]/li/form/input")).click();

			Thread.sleep(5000);
			Log.info("Escribe el hotel a buscar");
			driver.findElement(By.id("input-search")).clear();
			driver.findElement(By.id("input-search")).sendKeys(params[3]);
			driver.findElement(By.id("input-search")).sendKeys(Keys.ENTER);

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
			driver.findElement(By.xpath(".//*[@id=\"wrapper\"]/div/div[3]/div/div/div[2]/ul/li[8]")).click();

			Thread.sleep(5000);
			Log.info("Regresa a la lista consultada");
			driver.findElement(By.xpath(".//*[@id=\"wrapper\"]/div/nav/div[1]/a/img")).click();

			Thread.sleep(5000);
			Log.info("Regresa a pagina principal");
			driver.findElement(By.xpath(".//*[@id=\"wrapper\"]/div/div[2]/nav/div/a/img")).click();

			Thread.sleep(5000);
			Log.info("Presiona el boton de Perfil");
			driver.findElement(By.xpath(".//*[@id=\"header\"]/nav/div/ul/li[4]")).click();

			Thread.sleep(5000);
			Log.info("Presiona Cerrar sesion");
			driver.findElement(By.xpath(".//*[@id=\"mobile-view\"]/app-root/home/account/div/ul/li[3]/span/label"))
					.click();

			Thread.sleep(1000);
			Log.info("Termina correctamente");
			Assert.assertTrue(true);
		} catch (NoSuchElementException e) {
			Log.info("No existe el elemento buscado ..... " + e.getMessage());
			Assert.assertTrue(false, e.getMessage());
		} 
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		Log.endTestCase(this.getClass().getName());
	}
}