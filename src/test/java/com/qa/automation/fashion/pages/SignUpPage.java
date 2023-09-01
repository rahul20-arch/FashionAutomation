package com.qa.automation.fashion.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpPage {
	@FindBy(id = "menu-item-50")
	public static WebElement myAccount;
	
	@FindBy(id = "reg_email")
	public static WebElement emailAddress;
	
	@FindBy(id = "reg_password")
	public static WebElement password;
	
	@FindBy(xpath = "//p[contains(@class,'woocomerce-FormRow')]//input[@type='submit']")
	public static WebElement submitButton;
	
	@FindBy(xpath = "//ul[@class='woocommerce-error']/")
	public static WebElement errorMessage;
	
	@FindBy(xpath = "//div[@class='woocommerce-MyAccount-content']/p[1]")
	public static WebElement registerCofirm;
}
