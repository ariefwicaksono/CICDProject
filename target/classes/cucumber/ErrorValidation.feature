
@tag
Feature: Login Error Validation
  I want to use this template for my feature file
 check more outcomes

  @ErrorValidation
  Scenario Outline: Positive Login Error Validation
    Given I landed on Ecommerce page
    When I logged in with username <username> and password <password>
    Then "Incorrect email or password." message is displayed

    Examples: 
     	| username 			 				| password 			| 
      | usertest123@mail.com 	|    @Tes 			| 
      | arieftest123@mail.com |    @Tes				| 
