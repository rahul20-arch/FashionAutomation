package com.qa.automation.fashion.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	
	@FindBy(id = "username")
	public static WebElement username;
	
	@FindBy(id = "password")
	public static WebElement password;
	
	@FindBy(xpath = "//p[@class='form-row']//input[contains(@class,'woocommerce-Button')]")
	public static WebElement login;

	@FindBy(xpath = "//ul[@class='woocommerce-error']/")
	public static WebElement errorMessage;
	
	@FindBy(xpath = "//div[@class='woocommerce-MyAccount-content']/p[1]")
	public static WebElement registerCofirm;
}
