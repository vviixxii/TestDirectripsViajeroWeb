package directripsViajeroWeb.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import utils.ExcelUtils;
import utils.Log;

/**
 * Clase Directrips Data Provider
 * 
 * @author Ricardo Romero
 * @version 1.0.0
 * @date 17/01/19
 */
public class DirectripsDataProvider {

	static String propertyFilePath = "config//config.properties";
	static Properties prop = new Properties();
	static String filePath = null;
	static String sheetFile = null;

	static {
		try {
			prop.load(new FileInputStream(propertyFilePath));
			filePath = prop.getProperty("filePath");
		} catch (IOException e) {
			Log.error("El archivo config.properties no se encontró.");
		}
	}

	@DataProvider(name = "busquedas")
	public static Object[][] getBusqueda(ITestContext context) throws Exception {
		int cols[] = new int[] { 1, 2, 3, 4 };
		Object[][] testObjArray = getTestObjArray(context, cols);
		return (testObjArray);
	}

	@DataProvider(name = "minimo")
	public static Object[][] getMinimo(ITestContext context) throws Exception {
		int cols[] = new int[] { 1, 2, 3 };
		Object[][] testObjArray = getTestObjArray(context, cols);
		return (testObjArray);
	}

	@DataProvider(name = "cambioPass")
	public static Object[][] getCambioPass(ITestContext context) throws Exception {
		int cols[] = new int[] { 1, 2, 3, 5 };
		Object[][] testObjArray = getTestObjArray(context, cols);
		return (testObjArray);
	}

	private static Object[][] getTestObjArray(ITestContext context, int cols[]) throws Exception {
		String tcName = context.getAllTestMethods()[0].getInstance().getClass().getSimpleName();
		sheetFile = tcName;
		ExcelUtils.setExcelFile(filePath, sheetFile);
		Object[][] testObjArray = ExcelUtils.getTableArraySelected(tcName, cols);
		Log.info("Parámetros leidos: " + testObjArray.length);
		if (testObjArray.length == 0)
			Log.error("No se leyó ningún datos del archivos Excel, favor de agregarlo.");
		return (testObjArray);
	}
}
