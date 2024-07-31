package FwProject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import abstractComponent.AbstractComponent;

public class Checkout extends AbstractComponent {
	
	WebDriver driver;
	Actions a;
	public Checkout(WebDriver driver, Actions a) {
		super(driver);
		this.driver=driver;
		this.a=a;
		PageFactory.initElements(driver, this);
	}
	

	@FindBy (css = "[placeholder='Select Country']")
	WebElement selectCountry;
	
	@FindBy (xpath = "//button[@type='button']")
	WebElement clickCountry;
	
	@FindBy (className= "hero-primary")
	WebElement confirmText;
	
	@FindBy (css= "label[class='ng-star-inserted']")
	WebElement orderIdText;
	
	@FindBy (css= ".totalRow button")
	WebElement checkOutBtn;
	
	@FindBy(css = ".cartSection h3") // css locator
	List<WebElement> productsInCart;
	
	@FindBy (css= ".action__submit")
	WebElement submitOrderBtn;
	
	By countryList = By.cssSelector(".ta-results");
	
	public Boolean cartValidation(String productName) {
		Boolean match = productsInCart.stream().anyMatch(productCart->productCart.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	public void goToCheckout() {
		checkOutBtn.click();
	}
	
	public void inputCountry(String countryName) {
		selectCountry.sendKeys(countryName);
		elementToAppear(countryList);
		clickCountry.click();
	}
	
	public void submitOrder() {
		//Checkout
		a.moveToElement(submitOrderBtn).click().build().perform();		
	}
	
	public void getOrderId() {
		//Get order ID
		String orderId = orderIdText.getText();
		System.out.println(orderId);
	}

}
