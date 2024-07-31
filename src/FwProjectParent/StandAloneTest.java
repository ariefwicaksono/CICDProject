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

import FwProject.LandingPage;

public class StandAloneTest {
	
	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/client");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Actions a = new Actions(driver);
		driver.manage().window().maximize();
		String productZara = "ZARA COAT 3";
		
		//Login
		driver.findElement(By.id("userEmail")).sendKeys("usertest123@mail.com");
		driver.findElement(By.id("userPassword")).sendKeys("@Test123");
		driver.findElement(By.id("login")).click();
		
		//to get access to all product and wait for all item appear
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		//To select item and return first one using findFirst() | findElement used to get into title, so it can get the title text
		WebElement prod = products.stream().filter(product->product.findElement(By.cssSelector("b"))
				.getText().equals(productZara)).findFirst().orElse(null);
		
		//To click buy button on product 
		prod.findElement(By.cssSelector(".card button:last-of-type")).click();
		
		//Explicit wait to animation and toast message, so it can click cart button
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		//To validate if there is ZARA product or not
		//anyMatch is To check if any product from stream is have equals text to productZara
		List<WebElement> productInCart = driver.findElements(By.cssSelector(".cartSection h3"));
		
		Boolean match = productInCart.stream().anyMatch(productCart->productCart.getText().equalsIgnoreCase(productZara));
		Assert.assertTrue(match);
		
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//To input country 
		driver.findElement(By.cssSelector("[placeholder='Select Country']")).sendKeys("indo");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		driver.findElement(By.xpath("//button[@type='button']")).click();
		
		//Used Actions to click button. Because btn is moving, so ordinary click is not applicable and always get Click Intercepted
		a.moveToElement(driver.findElement(By.cssSelector(".action__submit"))).click().build().perform();
		
		//Validate order message
		String confirmMessage = driver.findElement(By.className("hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
		//Get order ID
		String orderId = driver.findElement(By.cssSelector("label[class='ng-star-inserted']")).getText();
		System.out.println("Order ID : " + orderId);
		driver.close();
	}

}
