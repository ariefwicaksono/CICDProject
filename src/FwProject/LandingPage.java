package FwProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import abstractComponent.AbstractComponent;

public class LandingPage extends AbstractComponent {

	// driver initializion here so driver can be access to all public class
	WebDriver driver;

	// constructor for webdriver from SubmitOrderTest class
	public LandingPage(WebDriver driver) {
		//super is used for sending driver to parent class (AbstractComponent)
		super(driver);
		// intitialization for pass driver value to public class
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	//@FindBy is Method from PageFactory
	@FindBy(id = "userEmail") //id locator
	WebElement userEmail;
	
	@FindBy(id = "userPassword")
	WebElement userPassword;
	
	@FindBy(id = "login")
	WebElement loginBtn;
	
	@FindBy(css = "[class*=flyInOut]")
	WebElement errorMessage;
	
	
	public void loginApp(String email, String password) {
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		loginBtn.click();
	}
	
	public String getErrorMessage() {
		webElementToAppear(errorMessage);
		return errorMessage.getText();
		
	}
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	
}
