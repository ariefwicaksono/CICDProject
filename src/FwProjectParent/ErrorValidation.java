package FwProjectParent;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import FwProject.Checkout;
import FwProject.LandingPage;
import FwProject.ProductCatalogue;
import TestComponent.BaseTest;
import abstractComponent.AbstractComponent;

public class ErrorValidation extends BaseTest {

	@Test(groups = {"errorHandling"}, retryAnalyzer=TestComponent.Retry.class)
	public void loginErrorValidation() throws IOException {
//		| not needed the object because "landpage" already exist in class BaseTest
		LandingPage landpage = launchApplication();
		// Login
		landpage.loginApp("usertest123@mail.com", "@123");
		Assert.assertEquals(landpage.getErrorMessage(),"Incorrect email or password#!@!@.");

	}

	@Test
	public void productErrorValidation() throws IOException {
		LandingPage landpage = launchApplication();
		String productName = "ZARA COAT 3";
		Actions a = new Actions(driver);

		landpage.loginApp("usertest123@mail.com", "@Test123");
		ProductCatalogue pc = new ProductCatalogue(driver);
		List<WebElement> products = pc.getProductList();
		pc.addProductToCart(productName);
		AbstractComponent ac = new AbstractComponent(driver);
		ac.goToCartPage();
		Checkout co = new Checkout(driver, a);
		Boolean match = co.cartValidation("zar");
		Assert.assertFalse(match);
	}

}
