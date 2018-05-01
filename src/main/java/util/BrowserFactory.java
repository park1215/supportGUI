package util;

import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author Sean Park
 * This factory class processes login and returns a browser driver object depending on the input parameters from properties file.
 * It is preferred to have parameters in the properties file, to avoid hard coded values in source code.
 */

public class BrowserFactory 
{
	static WebDriver driver;
	//absolute path is C:\Users\spark\workspace\SupportPortal\resources
	static String driverPath = ".\\resources\\drivers\\"; 
	static String propertiesPath = ".\\resources\\data\\supportPortal.properties\\";
	static String baseUrl = "https://ordermgmt.test.exede.net/PublicGUI-SupportGUI/v1/login.xhtml";
	
	public static WebDriver startBrowser(String browser, String url) throws IOException
	{
		if (browser.equalsIgnoreCase("firefox"))
		{
			System.out.println("*******************");
			
			System.out.println("Launching Firefox browser");
			
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver.exe");
			
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			//capabilities.setCapability("firefox_binary","C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
			//capabilities.setCapability("firefox_binary", C:\\Users\\spark\\MozillaFirefox\\firefox.exe
			//capabilities.setCapability("marionette", false);
			capabilities.setCapability("gecko", true);
			
			capabilities.setBrowserName("firefox");
			capabilities.setPlatform(Platform.ANY);
			
			driver = new FirefoxDriver();
		}

//		else if (prop.getProperty("browser").equalsIgnoreCase("chrome"))
		else if (browser.equalsIgnoreCase("chrome"))
		{
			
			System.out.println("*************************************");
			System.out.println("Launching Chrome browser...");
			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");

			Map<String, Object> prefs = new HashMap<String, Object>();

			// To Turns off multiple download warning
			prefs.put("profile.default_content_settings.popups", 0);

			prefs.put("profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1);

			// Turns off download prompt
			prefs.put("download.prompt_for_download", false);

			prefs.put("credentials_enable_service", false);
			// To Stop Save password propmts

			prefs.put("password_manager_enabled", false);

			ChromeOptions options = new ChromeOptions();

			options.addArguments("disable-extensions");

			options.addArguments("chrome.switches", "--disable-extensions");
			// To Disable any browser notifications
			options.addArguments("--disable-notifications");
			// To disable yellow strip info bar which prompts info messages
			options.addArguments("disable-infobars");

			options.setExperimentalOption("prefs", prefs);

			options.addArguments("--test-type");

			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);

//			try {
//				driver = BrowserFactory.startBrowser(browser, url);
//			}
//
//			catch (IOException e) {
//				// ToDO Auto-generated catch block
//				System.out.println("Browser not strated correctly...");
//				e.printStackTrace();
//			}

			System.out.println("Chrome browser successfully started...");

			driver = new ChromeDriver();
		}
		else 
		{
			System.out.println("*******************");
			System.out.println("Launching IE browser");
			System.setProperty("webdriver.ie.driver", driverPath+"IEDriverServer.exe");
			//On IE 7 or higher on Windows Vista or Windows 7, you must set the Protected Mode settings for each zone to be the same value.
			//This is a possible workaround for protected Mode
			//*********************************************************************************************************
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			ieCapabilities.setCapability("ensureCleanSession", true);
			//**********************************************************************************************************
			driver = new InternetExplorerDriver();
		}

		driver.manage().window().maximize();

		driver.get(url);
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		return driver;
	}
}
