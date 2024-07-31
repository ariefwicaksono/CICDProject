
@tag
Feature: Purchase the order from ecommerce website
  I want to use this template for my feature file
	
	Background:
	Given I landed on Ecommerce page

  @Regression
  Scenario Outline: Positive Test of Submitting the order
    Given I logged in with username <username> and password <password>
    When I add product <productName> to Cart
    And Checkout product <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage 
    
    Examples: 
      | username 			 				| password 			| productName		  	|
      | usertest123@mail.com 	|    @Test123 	| ZARA COAT 3			 	|
      | arieftest123@mail.com |    @Test1234 	| IPHONE 13 PRO   	|
