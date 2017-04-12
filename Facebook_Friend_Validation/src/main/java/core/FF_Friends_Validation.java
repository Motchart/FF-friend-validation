package core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class FF_Friends_Validation {
	// List -l="www.alegro.com|alegro.com","www.alegro.com|abc abc"
	@Parameter(names = { "-e", "--ffemail" }, description = "email")
	private static String em = null;

	// String -u=www.alegro.com
	@Parameter(names = { "-p", "--ffpassword" }, description = "Password")																									// true
	private static String pass = null;

	public static void main(String[] v) throws InterruptedException {
		new JCommander(new FF_Friends_Validation(), v);

		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);
		String driverPath = "./src/main/resources/geckodriver";
		System.setProperty("webdriver.gecko.driver", driverPath);
		String url = "https://www.facebook.com/";
		String expected_result = "44";
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("./src/main/resources/input.properties"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		WebDriver d = new FirefoxDriver();
		// d.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(d, 50);
		d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		d.get(url);
		try {
			WebElement email = wait
					.until(ExpectedConditions.elementToBeClickable(By.id(properties.getProperty("emailField"))));
			email.sendKeys(em);

			WebElement password = wait
					.until(ExpectedConditions.elementToBeClickable(By.id(properties.getProperty("passField"))));
			password.sendKeys(pass);

			WebElement login = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("login"))));
			login.click();

			// WebElement copyRight =
			// wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("copyRight"))));
			// copyRight.click();

			WebElement timeLine = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("Timeline"))));
			timeLine.click();

			String getNumber = d.findElement(By.xpath(properties.getProperty("nfriends"))).getAttribute("Value");
			
			String title = d.getTitle();
			System.out.println(title);


			WebElement settings = wait
					.until(ExpectedConditions.elementToBeClickable(By.id(properties.getProperty("dDown"))));
			settings.click();

			WebElement logOut = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("logOut"))));
			logOut.click();
			if (expected_result.equals(getNumber)) {
				System.out.println("Test Case ID:\t\tTC-01.01.01");
				System.out.println("Test Case Result:\t" + "PASS");
			} else {
				System.out.println("Test Case ID:\t\tTC-01.01.01");
				System.out.println("Test Case Result:\t" + "FAILED");
			}
		} catch (NoSuchElementException ignored) {
			System.out.println("element not found");
		}
		d.quit();

	}
}
