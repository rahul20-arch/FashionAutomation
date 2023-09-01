package com.qa.automation.fashion.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TestCasePage {
	
	@FindBy(xpath = "//li[@id='menu-item-40']/a")
	public static WebElement shop;
	
	@FindBy(xpath = "//nav/a[@href='https://practice.automationtesting.in']")
	public static WebElement home;
	
	
}
