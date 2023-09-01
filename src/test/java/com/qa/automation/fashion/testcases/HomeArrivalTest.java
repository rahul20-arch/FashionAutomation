package com.qa.automation.fashion.testcases;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.qa.automation.fashion.base.DriverInitialization;
import com.qa.automation.fashion.login.LoginTest;
import com.qa.automation.fashion.pages.TestCasePage;

public class HomeArrivalTest extends DriverInitialization {
	static TestCasePage testPg = new TestCasePage();
	public static boolean shop;
	public static boolean home;
	public static boolean btnEnable = false;
	public static boolean btnVisible = false;

	@Test
	public static void homeArrival() throws InterruptedException, IOException {
		PageFactory.initElements(driver, testPg);
		LoginTest.run();
		shop = true;
		if (shop) {
			buttonClick(TestCasePage.shop, "SHOP NAVIGATION");
			home = true;
		}
		if (home) {
			buttonClick(TestCasePage.home, "HOME BUTTON");
		}
	}

	public static void buttonClick(WebElement button, String heading) {

		WebDriverWait wait = new WebDriverWait(driver, 2);
		try {
			wait.until(ExpectedConditions.elementToBeClickable(button));
			btnVisible = button.isDisplayed();
			btnEnable = button.isEnabled();
		} catch (Exception e) {

			e.printStackTrace();
		}
		if (btnVisible) {
			if (btnEnable) {
				button.click();
				testCase = extent.createTest("CHECKING " + heading);
				testCase.log(Status.INFO, heading + " BUTTON IS DISPLAYED,ENABLED AND CLICKED");
				testCase.log(Status.PASS, "TEST PASS");
			} else {
				testCase = extent.createTest("CHECKING " + heading);
				testCase.log(Status.INFO, heading + " BUTTON IS NOT ENABLED");
				testCase.log(Status.FAIL, "TEST FAIL");
			}
		} else {
			testCase = extent.createTest("CHECKING " + heading);
			testCase.log(Status.INFO, heading + " BUTTON IS NOT DISPLAYED");
			testCase.log(Status.FAIL, "TEST FAIL");
		}

	}
}
