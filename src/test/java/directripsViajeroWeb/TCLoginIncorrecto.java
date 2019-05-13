package directripsViajeroWeb;

import org.testng.Assert;
import org.testng.annotations.*;

import directripsViajeroWeb.core.BaseTest;
import directripsViajeroWeb.core.DirectripsDataProvider;
import directripsViajeroWeb.core.DirectripsListener;
import utils.Log;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;

/**
 * TestCase: Login incorrecto
 * 
 * @author Ricardo Romero
 * @version 1.0.0
 * @date 23/04/19
 */
@Listeners(DirectripsListener.class)
public class TCLoginIncorrecto extends BaseTest {

	@BeforeMethod
	public void setUp() {
		initialization();
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
		Log.startTestCase(this.getClass().getName());
	}
	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}

	@Test(dataProvider = "minimo", dataProviderClass = DirectripsDataProvider.class)
	public void testLoginIncorrecto(String... params) throws Exception {
		try {
			driver.get(params[0]);

			Log.info("Quita el msg de uso de cookies");
			driver.findElement(By.xpath(".//*[@id=\"mobile-view\"]/app-root/cookies-advice/div/div[1]/a")).click();

			Log.info("Presiona boton iniciar sesion");
			driver.findElement(By.xpath(".//*[@id=\"registro_cuenta\"]/div/div/button[2]")).click();

			Thread.sleep(5000);
			driver.findElement(By.name("email")).click();
			driver.findElement(By.name("email")).clear();
			driver.findElement(By.name("email")).sendKeys(params[1]);
			driver.findElement(By.name("password")).click();
			driver.findElement(By.name("password")).clear();
			driver.findElement(By.name("password")).sendKeys(params[2]);
			Thread.sleep(5000);

			Log.info("Presiona boton iniciar sesion nuevamente");
			driver.findElement(By.xpath(".//*[@id=\"registro_cuenta\"]/div/div/button[1]")).click();

			Thread.sleep(2000);

			Log.info("Verifica que no salga la exception Nombre de usuario o contraseña incorrectos");
			Boolean flag = driver.findElements(By.xpath(".//*[@id=\"registro_cuenta\"]")).isEmpty();
			if (!flag) {
				WebElement item = driver.findElement(By.xpath(".//*[@id=\"registro_cuenta\"]/div/div/div[1]"));
				String msgErr = item.getText();
				Log.debug("msgErr -> " + msgErr);
				assertTrue(msgErr.equals("Nombre de usuario o contraseña incorrectos"),
						"Nombre de usuario o contraseña incorrectos");
			}

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