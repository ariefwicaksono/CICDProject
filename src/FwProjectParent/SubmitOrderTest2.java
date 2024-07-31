package FwProjectParent;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.bidi.module.Input;
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
import FwProject.OrderPage;
import FwProject.ProductCatalogue;
import TestComponent.BaseTest;
import abstractComponent.AbstractComponent;

public class SubmitOrderTest2 extends BaseTest {
	
	String productName = "ZARA COAT 3";

	@Test(dataProvider = "getData", groups={"Purchase"})
	public void submitOrder(HashMap<String, String> input) throws IOException {
		LandingPage landpage = launchApplication();
		
		Actions a = new Actions(driver);
		
		
		//Login
		landpage.loginApp(input.get("email"), input.get("password"));
		
		//to get access to all product and wait for all item appear	
		//To select item and return first one using findFirst() | findElement used to get into title, so it can get the title text
		ProductCatalogue pc = new ProductCatalogue(driver);
		List<WebElement> products = pc.getProductList();
		
		//To click buy button on product 
		//Explicit wait to animation and toast message, so it can click cart button
		pc.addProductToCart(input.get("productName"));
		
		
		AbstractComponent ac = new AbstractComponent(driver);
		ac.goToCartPage();
		
		//To validate if there is ZARA product or not
		//anyMatch is To check if any product from stream is have equals text to productZara
		Checkout co = new Checkout(driver, a);
		
		Boolean match = co.cartValidation(input.get("productName"));
		Assert.assertTrue(match);
		co.goToCheckout();
		
		//To input country 
		co.inputCountry("indo");
		
		//Validate order message n Get order ID
		co.submitOrder();
		
		//Validate order message
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
		co.getOrderId();
	}
	
	//To verify zara coat 3 is displaying in orders page
	@Test(dependsOnMethods = {"submitOrder"})
	public void orderHistoryTest() throws IOException {
		LandingPage landpage = launchApplication();

		landpage.loginApp("usertest123@mail.com", "@Test123");
		OrderPage orderspage = landpage.goToOrderPage();
		Assert.assertTrue(orderspage.verifyOrderDisplay(productName));
	}
	
	@DataProvider
	public Object[][] getData() throws IOException {
		
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("email", "usertest123@mail.com");
//		map.put("password", "@Test123");
//		map.put("productName", "ZARA COAT 3");
//		
//		HashMap<String, String> map2 = new HashMap<String, String>();
//		map2.put("email", "arieftest123@mail.com");
//		map2.put("password", "@Test1234");
//		map2.put("productName", "IPHONE 13 PRO");
		
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\data\\PurschaseOrder.json");
		return new Object [][] {{data.get(0)},{data.get(1)}};
	}
}
