package stepDefinition;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import FwProject.Checkout;
import FwProject.LandingPage;
import FwProject.ProductCatalogue;
import TestComponent.BaseTest;
import abstractComponent.AbstractComponent;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImple extends BaseTest{
	
	public LandingPage landingPage;
//	public ProductCatalogue prodCatalogue;
//	public AbstractComponent abstractComp;
//	public Checkout checkout ;
//	public ProductCatalogue prodCatalogue = new ProductCatalogue(driver);
	
	@Given ("I landed on Ecommerce page") 
	public void I_landed_on_Ecommerce_page () throws IOException {
		landingPage = launchApplication();
	}
	
	@Given ("^I logged in with username (.+) and password (.+)$")
	public void logged_in_username_password(String username, String password) {
		landpage.loginApp(username, password);
	}
	
	@When ("^I add product (.+) to Cart$")
	public void i_add_product_to_cart(String productName) {
		ProductCatalogue prodCatalogue = new ProductCatalogue(driver);
		List<WebElement> products = prodCatalogue.getProductList();
		prodCatalogue.addProductToCart(productName);
	}
	
	@And("^Checkout product (.+) and submit the order$")
	public void checkout_product_and_submit_order(String productName) {
		AbstractComponent abstractComp = new AbstractComponent(driver);
		Actions a = new Actions(driver);
		Checkout checkout = new Checkout(driver, a);
		
		abstractComp.goToCartPage();
		Boolean match = checkout.cartValidation(productName);
		Assert.assertTrue(match);
		checkout.goToCheckout(); 
		checkout.inputCountry("indo");
		checkout.submitOrder();
	}
	
	@Then("{string} message is displayed on ConfirmationPage") // {string} is used to catch message directly
	public void message_displayed_confirmationPage(String string) {
		Actions a = new Actions(driver);
		Checkout checkout = new Checkout(driver, a);
		
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		checkout.getOrderId();
		driver.close();
	}
	
	@Then("{string} message is displayed")
	public void incorrect_message_displayed(String errorMessage) {
		Assert.assertEquals(landpage.getErrorMessage(),errorMessage);
		driver.close();
	}
	

}
