package com.qa.opencart.Factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {
	
	public WebDriver driver;
	public Properties prop;
	
	public static String highlightEle;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	
	private static final Logger log = LogManager.getLogger(DriverFactory.class);
	
	public OptionsManager optionsManager;	
	
	/** This method is init the driver on the basis of browser...
	 * 
	 * @param browserName
	 * @return
	 */
	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
//		System.out.println("browser name :" + browserName);
		log.info("browser name :" + browserName);
		
//		ChainTestListener.log("Running Browser :" + browserName);//chaintestlistener will give logs only for tests. it won't give log for browser.
		
		highlightEle = prop.getProperty("highlight");
		optionsManager = new OptionsManager(prop);
		
		switch(browserName.trim().toLowerCase()) {
		case "chrome":
//			driver = new ChromeDriver();
//			tlDriver.set(new ChromeDriver());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
//			driver = new FirefoxDriver();
//			tlDriver.set(new FirefoxDriver());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
//			driver = new EdgeDriver();
//			tlDriver.set(new EdgeDriver());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;
		case "safari":
//			driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;
		default:
//			System.out.println(AppError.INVALID_BROWSER_MESG + " : " + browserName);
			log.error(AppError.INVALID_BROWSER_MESG + " : " + browserName);
			throw new FrameworkException(" === INVALID BROWSER ===");
		}
		
//		driver.manage().deleteAllCookies();
//		driver.manage().window().maximize();
//		driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
//		driver.get(prop.getProperty("url"));
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		
		return getDriver();
		
		//we never create WebDriver 'static' becoz it doesn't support parallel execution. So avoid static here.
	}
	
	/**
	 * this is used to get the local copy of the driver anytime
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	
	/** This method is init the prop with properties file....
	 * 
	 * @return
	 */
	
	//mvn clean install -Denv="qa" --> this is the code using in maven to run in qa env.
	//mvn clean install ---> not passing any environment name.
	//mvn clean install -Denv="dev" -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml
	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;
		
		String envName = System.getProperty("env");//we have to give env. var. name used in maven --> "env" 
		log.info("Env name ====>" + envName);
		try {
			if(envName == null) {
				log.warn("no env.. is passed, hence running test cases on QA environment...by default..");
				ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
			}
			else {
				switch(envName.trim().toLowerCase()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/config.qa.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/config.stage.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/config.uat.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/config.dev.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;
				default:
					log.error("Env value is invalid..plz pass the right env value..");
					throw new FrameworkException("==== INVALID ENVIRONMENT====");		
				}
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	/**
	 * takescreenshot
	 */

	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		return srcFile;
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

	}

}
