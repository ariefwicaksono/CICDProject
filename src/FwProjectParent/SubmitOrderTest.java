package FwProjectParent;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import FwProject.Checkout;
import FwProject.LandingPage;
import FwProject.ProductCatalogue;
import abstractComponent.AbstractComponent;

public class SubmitOrderTest {
	
	@Test(dataProvider = "getData")
	public void submitOrder(String email, String password, String productName) {
		WebDriver driver = new ChromeDriver();
		Actions a = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		String countryName = "indo";
		
		// Object for landingpage, so it can pass driver to LandingPage class
		LandingPage landpage = new LandingPage(driver);
		
		//Go to url
		landpage.goTo();
		
		//Login
		landpage.loginApp(email, password);
		
		//to get access to all product and wait for all item appear	
		//To select item and return first one using findFirst() | findElement used to get into title, so it can get the title text
		ProductCatalogue pc = new ProductCatalogue(driver);
		List<WebElement> products = pc.getProductList();
		
		//To click buy button on product 
		//Explicit wait to animation and toast message, so it can click cart button
		pc.addProductToCart(productName);
		
		
		AbstractComponent ac = new AbstractComponent(driver);
		ac.goToCartPage();
		
		//To validate if there is ZARA product or not
		//anyMatch is To check if any product from stream is have equals text to productZara
		Checkout co = new Checkout(driver, a);
		
		Boolean match = co.cartValidation(productName);
		Assert.assertTrue(match);
		co.goToCheckout();
		
		//To input country 
		co.inputCountry(countryName);
		
		//Validate order message n Get order ID
		co.submitOrder();
		
		//Validate order message
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
		co.getOrderId();
		
		driver.close();
	}
	
	@DataProvider
	public Object[][] getData() {
		return new Object[][] {{"usertest123@mail.com","@Test123", "ZARA COAT 3"}, {"arieftest123@mail.com","@Test1234", "IPHONE 13 PRO"}};
	}

}
