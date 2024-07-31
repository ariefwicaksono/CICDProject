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

public class OrderPage extends AbstractComponent {
	
	WebDriver driver;
	public OrderPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	

	@FindBy (css = "tr td:nth-child(3)")
	List<WebElement> productOrders;
	
	public Boolean verifyOrderDisplay(String productName) {
		Boolean match = productOrders.stream().anyMatch(po->po.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	

}
