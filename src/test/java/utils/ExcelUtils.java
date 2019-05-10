package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Clase de utilerias para la lectura de archivo de Excel
 * 
 * @author Ricardo Romero
 * @version 1.0.0
 * @date 18/12/18
 */
public class ExcelUtils {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static FileInputStream ExcelFile;
	private static String Path;
	private static String SheetName;

	public static void setExcelFile(String path, String sheetName) throws Exception {
		try {
			Path = new String(path);
			SheetName = new String(sheetName);
			ExcelFile = new FileInputStream(Path);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch (Exception e) {
			throw (e);
		}
	}

	public static Object[][] getTableArraySelected(String sTestCaseName, int[] cols) throws Exception {
		String[][] tabArray = null;
		try {
			int[] containslRows = getRowContainsTestCase(sTestCaseName, 0);
			int totalCols = cols.length;
			tabArray = new String[containslRows.length][totalCols];
			for (int ci = 0; ci < containslRows.length; ci++) {
				int iTestCaseRow = containslRows[ci];
				int cj = 0;
				for (int col = 0; col < cols.length; col++, cj++) {
					int startCol = cols[col];
					tabArray[ci][cj] = getCellData(iTestCaseRow, startCol);
				}
			}
		} catch (FileNotFoundException e) {
			Log.debug("No existe el archivo de Excel");
			e.printStackTrace();
		} catch (IOException e) {
			Log.debug("No se puede leer la hoja de Excel");
			e.printStackTrace();
		}
		return (tabArray);
	}

	public static Object[][] getTableArray(int iTestCaseRow) throws Exception {
		String[][] tabArray = null;
		try {
			int startCol = 1;
			int ci = 0, cj = 0;
			int totalRows = 1;
			int totalCols = getColUsed();
			tabArray = new String[totalRows][totalCols];
			for (int j = startCol; j <= totalCols; j++, cj++) {
				tabArray[ci][cj] = getCellData(iTestCaseRow, j);
			}
		} catch (FileNotFoundException e) {
			Log.debug("No existe el archivo de Excel");
			e.printStackTrace();
		} catch (IOException e) {
			Log.debug("No se puede leer la hoja de Excel");
			e.printStackTrace();
		}
		return (tabArray);
	}

	public static String getCellData(int RowNum, int ColNum) throws Exception {
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getStringCellValue();
			return CellData;
		} catch (Exception e) {
			return "";
		}
	}

	public static String getTestCaseName(String sTestCase) throws Exception {
		String value = sTestCase;
		try {
			int posi = value.indexOf("@");
			value = value.substring(0, posi);
			posi = value.lastIndexOf(".");
			value = value.substring(posi + 1);
			return value;
		} catch (Exception e) {
			throw (e);
		}
	}

	public static int[] getRowContainsTestCase(String sTestCaseName, int colNum) throws Exception {
		int[] tabArray = null;
		int ii = 0;
		try {
			int rowCount = ExcelUtils.getRowUsed() + 1;
			for (int i = 0; i < rowCount; i++) {
				if (ExcelUtils.getCellData(i, colNum).equalsIgnoreCase(sTestCaseName)) {
					ii++;
				}
			}
			tabArray = new int[ii];
			ii = 0;
			for (int i = 0; i < rowCount; i++) {
				if (ExcelUtils.getCellData(i, colNum).equalsIgnoreCase(sTestCaseName)) {
					tabArray[ii] = i;
					ii++;
				}
			}
			return tabArray;
		} catch (Exception e) {
			throw (e);
		}
	}

	public static int getRowContains(String sTestCaseName, int colNum) throws Exception {
		int i;
		try {
			int rowCount = ExcelUtils.getRowUsed();
			for (i = 0; i < rowCount; i++) {
				if (ExcelUtils.getCellData(i, colNum).equalsIgnoreCase(sTestCaseName)) {
					break;
				}
			}
			return i;
		} catch (Exception e) {
			throw (e);
		}
	}

	public static int getRowUsed() throws Exception {
		try {
			int RowCount = ExcelWSheet.getLastRowNum();
			return RowCount;
		} catch (Exception e) {
			Log.debug(e.getMessage());
			throw (e);
		}
	}

	public static int getColUsed() throws Exception {
		try {
			XSSFRow row = null;
			row = ExcelWSheet.getRow(0);
			int colCount = row.getLastCellNum() - 1;
			return colCount;
		} catch (Exception e) {
			Log.debug(e.getMessage());
			throw (e);
		}
	}
}
