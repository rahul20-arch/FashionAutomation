package com.qa.automation.fashion.login;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.qa.automation.fashion.base.DriverInitialization;
import com.qa.automation.fashion.pages.LoginPage;
import com.qa.automation.fashion.pages.SignUpPage;
import com.qa.automation.fashion.pages.LoginPage;

public class LoginTest extends DriverInitialization{
	public static boolean signUp = false;
	static LoginPage loginPg = new LoginPage();
	public static boolean display = false;
	public static boolean enable = false;
	public static String confirm = null;
	public static String uName = null;
	public static String pWord = null;
	public static String register = null;
	public static String error = null;
	public static boolean Bconfirm;
	public static boolean button;
	public static boolean SubButtonD;
	public static boolean SubButtonE;
	public static boolean pwdE;
	public static boolean pwdD;
	
	public static void login() {

		PageFactory.initElements(driver, loginPg);
		WebDriverWait wait = new WebDriverWait(driver, 20);
//		try {
//			wait.until(ExpectedConditions.elementToBeClickable(LoginPage.myAccount));
//		} catch (Exception e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//		
//		try {
//			LoginPage.myAccount.click();
//			testCase = extent.createTest("CHECKING MY ACCOUNT BUTTON");
//			testCase.log(Status.INFO, "MY ACCOUNT BUTTON IS FOUND");
//			testCase.log(Status.PASS, "TEST PASS");
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

		XSSFWorkbook workbook = null;

		try {

			FileInputStream file = new FileInputStream(prop.getProperty("data"));
			workbook = new XSSFWorkbook(file);
			DataFormatter data = new DataFormatter();
			XSSFSheet sheet = workbook.getSheet("login");
			int rowcount = sheet.getLastRowNum();
			for (int i = 0; i <= rowcount; i++) {
				XSSFRow row = sheet.getRow(i);

				uName = data.formatCellValue(row.getCell(0));
				pWord = data.formatCellValue(row.getCell(1));
				confirm = data.formatCellValue(row.getCell(2));
				try {
					wait.until(ExpectedConditions.visibilityOf(LoginPage.username));
					display = LoginPage.username.isDisplayed();
					enable = LoginPage.username.isEnabled();
				} catch (Exception e1) {

					e1.printStackTrace();
				}

				if (display) {
					if (enable) {
						try {
							LoginPage.username.sendKeys(uName);
							testCase = extent.createTest("USERNAME FIELD");
							testCase.log(Status.INFO, "USERNAME FIELD IS DISPLAYED AND ENABLED");
							testCase.log(Status.PASS, "TEST PASS");
						} catch (Exception e) {

							e.printStackTrace();
						}
					} else {
						testCase = extent.createTest("USERNAME FIELD");
						testCase.log(Status.INFO, "EMAIL FIELD IS NOT ENABLED");
						testCase.log(Status.FAIL, "TEST FAIL");
					}
				} else {
					testCase = extent.createTest("USERNAME FIELD");
					testCase.log(Status.INFO, "EMAIL FIELD IS NOT DISPLAYED");
					testCase.log(Status.FAIL, "TEST FAIL");
				}

				try {
					wait.until(ExpectedConditions.visibilityOf(LoginPage.password));
					pwdD = LoginPage.password.isDisplayed();
					pwdE = LoginPage.password.isEnabled();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (pwdD) {
					if (pwdE) {
						try {
							LoginPage.password.sendKeys(pWord);
							testCase = extent.createTest("PASSWORD FIELD");
							testCase.log(Status.INFO, "PASSWORD FIELD IS DISPLAYED AND ENABLED");
							testCase.log(Status.PASS, "TEST PASS");
						} catch (Exception e) {

							e.printStackTrace();
						}
					} else {
						testCase = extent.createTest("PASSWORD FIELD");
						testCase.log(Status.INFO, "PASSWORD FIELD IS NOT ENABLED");
						testCase.log(Status.FAIL, "TEST FAIL");
					}
				} else {
					testCase = extent.createTest("PASSWORD FIELD");
					testCase.log(Status.INFO, "PASSWORD FIELD IS NOT DISPLAYED");
					testCase.log(Status.FAIL, "TEST FAIL");
				}
				try {
					wait.until(ExpectedConditions.elementToBeClickable(LoginPage.login));
					SubButtonD = LoginPage.login.isDisplayed();
					SubButtonE = LoginPage.login.isEnabled();
				} catch (Exception e1) {

					e1.printStackTrace();
				}

				if (SubButtonD) {
					if (SubButtonE) {
						try {
							LoginPage.login.click();
							button = true;
							testCase = extent.createTest("CHECKING LOGIN BUTTON");
							testCase.log(Status.INFO, "LOGIN BUTTON IS DISPLAYED,ENABLED AND CLICKED");
							testCase.log(Status.PASS, "TEST PASS");
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						testCase = extent.createTest("CHECKING LOGIN BUTTON");
						testCase.log(Status.INFO, "SUBMIT BUTTON IS NOT ENABLED");
						testCase.log(Status.FAIL, "TEST FAIL");
					}
				} else {
					testCase = extent.createTest("CHECKING LOGIN BUTTON");
					testCase.log(Status.INFO, "LOGIN BUTTON IS NOT DISPLAYED");
					testCase.log(Status.FAIL, "TEST FAIL");
				}

			}

		} catch (Exception e) {
			testCase = extent.createTest("Excel File Not Found");
			testCase.log(Status.INFO, "Excel File Not Found");
			testCase.log(Status.FAIL, "Excel File Not Found, So fail");
		}
	
		
	}
	public static void loginConfirm() {
		WebDriverWait wait = new WebDriverWait(driver, 2);
		testCase = extent.createTest("CHECKING LOGIN CONFIRMATION");
		try {
			wait.until(ExpectedConditions.visibilityOf(LoginPage.registerCofirm));
			Bconfirm = true;
		} catch (Exception e) {

			e.printStackTrace();
		}
		if (Bconfirm) {

			register = LoginPage.registerCofirm.getText();
			System.out.println("////////////////////" + register);

			try {
				Assert.assertEquals(register, confirm);

				testCase.log(Status.INFO, "ACTUAL VALUE" + register);
				testCase.log(Status.INFO, "EXPECTED VALUE" + confirm);
				testCase.log(Status.PASS, "SUCESSFULLY LOGIN");
			} catch (AssertionError e) {
				testCase = extent.createTest("CHECKING LOGIN CONFIRMATION AND SPELLING");
				testCase.log(Status.INFO, "ACTUAL VALUE" + register);
				testCase.log(Status.INFO, "EXPECTED VALUE" + confirm);

			}

		} else {
			try {
				error = LoginPage.errorMessage.getText();
				System.out.println("Error message" + error);
				testCase.log(Status.INFO, "LOGIN HAS FAIL");
				testCase.log(Status.INFO, "BECAUSE OF " + error);
				testCase.log(Status.FAIL, "TEST FAILED");
			} catch (Exception e) {

			}
		}

	}
	@Test
	public static void run() throws InterruptedException, IOException {
		testCase = extent.createTest("LOGIN TEST FUNCTION");
		login();
		if (button) {
			loginConfirm();
		}
	}

}
