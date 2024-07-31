package FwProject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import abstractComponent.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {

	// driver initializion here so driver can be access to all public class
	WebDriver driver;

	// constructor for webdriver from StandAloneTest class
	public ProductCatalogue(WebDriver driver) {

		// intitialization for pass driver value to public class
		// super is always need to define from child to parent class(use it while extends class)
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// Created from this List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
	// @FindBy is Method from PageFactory
	@FindBy(css = ".mb-3") // css locator
	List<WebElement> products;

	@FindBy(css = ".ng-animating") // css locator
	WebElement spinner;
	
	By productBy = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".card button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");
	
	public List<WebElement> getProductList() {
		elementToAppear(productBy);
		return products;
	}

	public WebElement getProductByName(String productName) {
		WebElement prod = getProductList().stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
				.orElse(null);
		return prod;
	}

	public void addProductToCart(String productName) {
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		elementToAppear(toastMessage);
		elementToDissappear(spinner);
	}
}
