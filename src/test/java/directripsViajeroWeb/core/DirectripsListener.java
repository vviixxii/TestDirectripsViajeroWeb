package directripsViajeroWeb.core;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import utils.Log;
import utils.Utils;

/**
 * Clase Listener para monitoreo de eventos de las pruebas
 * 
 * @author Ricardo Romero
 * @version 1.0.0
 * @date 17/01/19
 */
public class DirectripsListener extends BaseTest implements ITestListener {

	private Document document = null;
	PdfPTable successTable = null;
	PdfPTable failTable = null;
	PdfPTable skippedTable = null;
	private HashMap<Integer, Throwable> throwableMap = null;
	private int nbExceptions = 0;
	private String fileScreenShot = null;
	private ArrayList<String> fileScreenShotArray = new ArrayList<>();

	private String propertyFilePath = "config//config.properties";
	private Properties prop = new Properties();
	private String reportTitle = null;
	private String reportClass = null;
	private String reportMethod = null;
	private String reportTimems = null;
	private String reportException = null;
	private String reportScreenshot = null;

	private String reportPage = null;
	private String reportLogoFile = null;
	private String reportPassedTests = null;
	private String reportFailedTests = null;
	private String reportStacktrace = null;
	private String reportSkippedTable = null;

	final int NUM_LINES_STACKTRACE = 5;

	public DirectripsListener() {
		this.document = new Document();
		this.throwableMap = new HashMap<Integer, Throwable>();
		try {
			prop.load(new FileInputStream(propertyFilePath));
			reportTitle = prop.getProperty("report.title");
			reportClass = prop.getProperty("report.class");
			reportMethod = prop.getProperty("report.method");
			reportTimems = prop.getProperty("report.timems");
			reportException = prop.getProperty("report.exception");
			reportScreenshot = prop.getProperty("report.screenshot");
			reportPage = prop.getProperty("report.page");
			reportLogoFile = prop.getProperty("report.logoFile");
			reportPassedTests = prop.getProperty("report.passedTests");
			reportFailedTests = prop.getProperty("report.failedTests");
			reportStacktrace = prop.getProperty("report.stacktrace");
			reportSkippedTable = prop.getProperty("report.skippedTable");
		} catch (IOException e) {
			Log.error("El archivo config.properties no se encontró");
		}
	}

	public void onTestSuccess(ITestResult result) {
		Log.info("onTestSuccess(" + result + ")");
		if (successTable == null) {
			this.successTable = new PdfPTable(new float[] { .3f, .3f, .1f });
			this.successTable.setWidthPercentage(100f);
			Paragraph p = new Paragraph("");
			p.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell(p);
			cell.setColspan(3);
			cell.setBackgroundColor(Color.GREEN);
			this.successTable.addCell(cell);
			cell = new PdfPCell(new Paragraph(reportClass, new Font(Font.HELVETICA, 8, Font.NORMAL)));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.successTable.addCell(cell);
			cell = new PdfPCell(new Paragraph(reportMethod, new Font(Font.HELVETICA, 8, Font.NORMAL)));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.successTable.addCell(cell);
			cell = new PdfPCell(new Paragraph(reportTimems, new Font(Font.HELVETICA, 8, Font.NORMAL)));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.successTable.addCell(cell);
		}
		PdfPCell cell = new PdfPCell(new Paragraph(Utils.extraerTexto(result.getTestClass().getName()),
				FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
		this.successTable.addCell(cell);
		cell = new PdfPCell(new Paragraph(result.getMethod().getMethodName(),
				FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
		this.successTable.addCell(cell);
		long timeDuration = (result.getEndMillis() - result.getStartMillis());
		DecimalFormat myFormatter = new DecimalFormat("#,###");
		String output = myFormatter.format(timeDuration);
		cell = new PdfPCell(new Paragraph(output, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
		this.successTable.addCell(cell);
	}

	public void onTestFailure(ITestResult result) {
		Log.info("onTestFailure(" + result + ")");
		Log.debug("user.dir -> " + System.getProperty("user.dir"));
		fileScreenShot = System.getProperty("user.dir") + "/" + reportScreenshot + (new Random().nextInt() + ".png");
		Log.debug("fileScreenShot -> " + fileScreenShot);
		try {
			takeSnapShot(fileScreenShot);
		} catch (Exception e) {
			fileScreenShot = null;
		}
		if (this.failTable == null) {
			this.failTable = new PdfPTable(new float[] { .1f, .1f, .1f, .3f, .4f });
			this.failTable.setWidthPercentage(100f);
			Paragraph p = new Paragraph("");
			PdfPCell cell = new PdfPCell(p);
			cell.setColspan(5);
			cell.setBackgroundColor(Color.RED);
			this.failTable.addCell(cell);
			cell = new PdfPCell(new Paragraph(reportClass, new Font(Font.HELVETICA, 8, Font.NORMAL)));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.failTable.addCell(cell);
			cell = new PdfPCell(new Paragraph(reportMethod, new Font(Font.HELVETICA, 8, Font.NORMAL)));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.failTable.addCell(cell);
			cell = new PdfPCell(new Paragraph(reportTimems, new Font(Font.HELVETICA, 8, Font.NORMAL)));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.failTable.addCell(cell);
			cell = new PdfPCell(new Paragraph(reportException, new Font(Font.HELVETICA, 8, Font.NORMAL)));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.failTable.addCell(cell);
			cell = new PdfPCell(new Paragraph(reportScreenshot, new Font(Font.HELVETICA, 8, Font.NORMAL)));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.failTable.addCell(cell);
		}
		PdfPCell cell = new PdfPCell(new Paragraph(Utils.extraerTexto(result.getTestClass().getName()),
				FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
		this.failTable.addCell(cell);
		cell = new PdfPCell(new Paragraph(result.getMethod().getMethodName(),
				FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
		this.failTable.addCell(cell);
		long timeDuration = (result.getEndMillis() - result.getStartMillis());
		DecimalFormat myFormatter = new DecimalFormat("#,###");
		String output = myFormatter.format(timeDuration);
		cell = new PdfPCell(new Paragraph(output, FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
		this.failTable.addCell(cell);

		Throwable throwable = result.getThrowable();
		if (throwable != null) {
			this.throwableMap.put(new Integer(throwable.hashCode()), throwable);
			this.nbExceptions++;
			Chunk imdb = new Chunk("[SCREEN SHOT]", new Font(Font.HELVETICA, 6, Font.UNDERLINE));
			imdb.setAction(new PdfAction("file:///" + fileScreenShot));
			Set<Integer> keys = this.throwableMap.keySet();
			assert keys.size() == this.nbExceptions;
			StringBuffer sb = new StringBuffer();
			for (Integer key : keys) {
				Throwable throwableStack = this.throwableMap.get(key);
				StackTraceElement[] elems = throwableStack.getStackTrace();
				Integer index = 0;
				for (StackTraceElement ste : elems) {
					if (index <= NUM_LINES_STACKTRACE) {
						sb.append(ste.toString()).append("\n");
					}
					index++;
				}
			}
			Paragraph excep = new Paragraph(
					new Chunk(throwable.toString() + "\n\n" + reportStacktrace + ":\n" + sb.toString(),
							new Font(Font.HELVETICA, 5, Font.NORMAL)).setLocalGoto("" + throwable.hashCode()));
			cell = new PdfPCell(excep);
			this.failTable.addCell(cell);
		}
		if (fileScreenShot != null && fileScreenShot.length() > 0) {
			fileScreenShotArray.add(fileScreenShot);
			Image img = null;
			try {
				img = Image.getInstance(fileScreenShot);
				this.failTable.addCell(img);
			} catch (BadElementException e) {
				this.failTable.addCell("");
			} catch (FileNotFoundException e) {
				this.failTable.addCell("");
			} catch (MalformedURLException e) {
				this.failTable.addCell("");
			} catch (IOException e) {
				this.failTable.addCell("");
			}
		} else {
			this.failTable.addCell("");
		}
	}

	public void onTestSkipped(ITestResult result) {
		Log.info("onTestSkipped(" + result + ")");
		this.document.open();
		if (skippedTable == null) {
			this.skippedTable = new PdfPTable(new float[] { .3f, .3f });
			this.skippedTable.setWidthPercentage(100f);
			Paragraph p = new Paragraph("");
			p.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell(p);
			cell.setColspan(2);
			cell.setBackgroundColor(Color.YELLOW);
			this.skippedTable.addCell(cell);
			cell = new PdfPCell(new Paragraph(reportClass, new Font(Font.HELVETICA, 8, Font.NORMAL)));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.skippedTable.addCell(cell);
			cell = new PdfPCell(new Paragraph(reportMethod, new Font(Font.HELVETICA, 8, Font.NORMAL)));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.skippedTable.addCell(cell);
		}
		PdfPCell cell = new PdfPCell(new Paragraph(Utils.extraerTexto(result.getTestClass().getName()),
				FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
		this.skippedTable.addCell(cell);
		cell = new PdfPCell(new Paragraph(result.getMethod().getMethodName(),
				FontFactory.getFont(FontFactory.HELVETICA, 6, Font.NORMAL)));
		this.skippedTable.addCell(cell);
	}

	public void onStart(ITestContext context) {
		Log.info("onStart(" + context + ")");
		try {
			PdfWriter.getInstance(this.document,
					new FileOutputStream(context.getSuite().getName() + LocalDateTime.now() + ".pdf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.document.open();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		String strDate = sdf.format(date);
		HeaderFooter header = new HeaderFooter(
				new Phrase(reportTitle, FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD)), false);
		header.setBorder(Rectangle.NO_BORDER);
		header.setAlignment(Element.ALIGN_LEFT);
		document.setHeader(header);
		HeaderFooter footer = new HeaderFooter(
				new Phrase(reportPage, FontFactory.getFont(FontFactory.COURIER, 6, Font.NORMAL)), true);
		footer.setBorder(Rectangle.NO_BORDER);
		footer.setAlignment(Element.ALIGN_RIGHT);
		document.setFooter(footer);
		Paragraph pInit = new Paragraph(" ");
		Image img = null;
		try {
			pInit.setSpacingBefore(150f);
			pInit.setSpacingAfter(15f);
			this.document.add(pInit);
			img = Image.getInstance(reportLogoFile);
			img.scaleAbsolute(297f, 193f);
			img.setAlignment(Element.ALIGN_CENTER);
			this.document.add(img);
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Paragraph p1 = new Paragraph(reportTitle, new Font(Font.TIMES_ROMAN, 20, Font.BOLD));
		p1.setAlignment(Element.ALIGN_CENTER);
		Paragraph p2 = new Paragraph(context.getSuite().getName().toUpperCase(),
				new Font(Font.TIMES_ROMAN, 20, Font.BOLD));
		p2.setAlignment(Element.ALIGN_CENTER);
		Paragraph p3 = new Paragraph(strDate, new Font(Font.TIMES_ROMAN, 16, Font.BOLD));
		p3.setAlignment(Element.ALIGN_CENTER);
		try {
			p1.setSpacingBefore(50f);
			p1.setSpacingAfter(15f);
			p2.setSpacingAfter(15f);
			p3.setSpacingBefore(50f);
			p3.setSpacingAfter(15f);
			this.document.add(p1);
			this.document.add(p2);
			this.document.add(p3);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
	}

	public void onFinish(ITestContext context) {
		Log.info("onFinish(" + context + ")");
		this.document.newPage();
		try {
			if (this.successTable != null) {
				Log.info("Added success table");
				Paragraph p = new Paragraph(reportPassedTests, new Font(Font.HELVETICA, 16, Font.BOLD));
				try {
					this.document.add(p);
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				this.successTable.setSpacingBefore(15f);
				this.document.add(this.successTable);
				this.successTable.setSpacingBefore(15f);
				this.document.newPage();
			}
			if (this.skippedTable != null) {
				Log.info("Added skipped table");
				Paragraph p = new Paragraph(reportSkippedTable, new Font(Font.HELVETICA, 16, Font.BOLD));
				try {
					this.document.add(p);
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				this.skippedTable.setSpacingBefore(15f);
				this.document.add(this.skippedTable);
				this.skippedTable.setSpacingBefore(15f);
				this.document.newPage();
			}
			if (this.failTable != null) {
				Log.info("Added fail table");
				Paragraph p = new Paragraph(reportFailedTests, new Font(Font.HELVETICA, 16, Font.BOLD));
				try {
					this.document.add(p);
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				this.failTable.setSpacingBefore(15f);
				this.document.add(this.failTable);
				this.failTable.setSpacingAfter(15f);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		this.document.close();
		for (int i = 0; i < fileScreenShotArray.size(); i++) {
			String strFile = fileScreenShotArray.get(i);
			if (strFile.indexOf(reportScreenshot) >= 0) {
				File file = new File(strFile);
				file.delete();
			}
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		Log.info("onTestStart(" + result + ")");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		Log.info("onTestFailedButWithinSuccessPercentage(" + result + ")");
	}

	@SuppressWarnings("unused")
	private void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception {
		Log.debug("webdriver -> " + webdriver);
		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		Log.debug("scrShot -> " + scrShot);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(fileWithPath);
		FileUtils.copyFile(SrcFile, DestFile);
	}
}
