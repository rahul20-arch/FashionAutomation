/*Registration-Sign-in

1) Open the browser
2) Enter the URL “http://practice.automationtesting.in/”
3) Click on My Account Menu
4) Enter registered Email Address in Email-Address textbox
5) Enter your own password in password textbox
6) Click on Register button
7) User will be registered successfully and will be navigated to the Home page
 * 
 */
package com.qa.automation.fashion.signUp;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.qa.automation.fashion.base.DriverInitialization;
import com.qa.automation.fashion.pages.SignUpPage;

public class SignUpTest extends DriverInitialization {
	public static boolean signUp = false;
	static SignUpPage signUpPg = new SignUpPage();
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

	public static void signUp() throws InterruptedException, IOException {
		PageFactory.initElements(driver, signUpPg);
		WebDriverWait wait = new WebDriverWait(driver, 20);
//		try {
//			wait.until(ExpectedConditions.elementToBeClickable(SignUpPage.myAccount));
//		} catch (Exception e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//		
//		try {
//			SignUpPage.myAccount.click();
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
			XSSFSheet sheet = workbook.getSheet("signIn");
			int rowcount = sheet.getLastRowNum();
			for (int i = 0; i <= rowcount; i++) {
				XSSFRow row = sheet.getRow(i);

				uName = data.formatCellValue(row.getCell(0));
				pWord = data.formatCellValue(row.getCell(1));
				confirm = data.formatCellValue(row.getCell(2));
				try {
					wait.until(ExpectedConditions.visibilityOf(SignUpPage.emailAddress));
					display = SignUpPage.emailAddress.isDisplayed();
					enable = SignUpPage.emailAddress.isEnabled();
				} catch (Exception e1) {

					e1.printStackTrace();
				}

				if (display) {
					if (enable) {
						try {
							SignUpPage.emailAddress.sendKeys(uName);
							testCase = extent.createTest("EMAIL ADDRESS FIELD");
							testCase.log(Status.INFO, "EMAIL FIELD IS DISPLAYED AND ENABLED");
							testCase.log(Status.PASS, "TEST PASS");
						} catch (Exception e) {

							e.printStackTrace();
						}
					} else {
						testCase = extent.createTest("EMAIL ADDRESS FIELD");
						testCase.log(Status.INFO, "EMAIL FIELD IS NOT ENABLED");
						testCase.log(Status.FAIL, "TEST FAIL");
					}
				} else {
					testCase = extent.createTest("EMAIL ADDRESS FIELD");
					testCase.log(Status.INFO, "EMAIL FIELD IS NOT DISPLAYED");
					testCase.log(Status.FAIL, "TEST FAIL");
				}

				try {
					wait.until(ExpectedConditions.visibilityOf(SignUpPage.password));
					pwdD = SignUpPage.password.isDisplayed();
					pwdE = SignUpPage.password.isEnabled();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (pwdD) {
					if (pwdE) {
						try {
							SignUpPage.password.sendKeys(pWord);
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
					wait.until(ExpectedConditions.elementToBeClickable(SignUpPage.submitButton));
					SubButtonD = SignUpPage.submitButton.isDisplayed();
					SubButtonE = SignUpPage.submitButton.isEnabled();
				} catch (Exception e1) {

					e1.printStackTrace();
				}

				if (SubButtonD) {
					if (SubButtonE) {
						try {
							SignUpPage.submitButton.click();
							button = true;
							testCase = extent.createTest("CHECKING SUBMIT BUTTON");
							testCase.log(Status.INFO, "SUBMIT BUTTON IS DISPLAYED,ENABLED AND CLICKED");
							testCase.log(Status.PASS, "TEST PASS");
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						testCase = extent.createTest("CHECKING SUBMIT BUTTON");
						testCase.log(Status.INFO, "SUBMIT BUTTON IS NOT ENABLED");
						testCase.log(Status.FAIL, "TEST FAIL");
					}
				} else {
					testCase = extent.createTest("CHECKING SUBMIT BUTTON");
					testCase.log(Status.INFO, "SUBMIT BUTTON IS NOT DISPLAYED");
					testCase.log(Status.FAIL, "TEST FAIL");
				}

			}

		} catch (Exception e) {
			testCase = extent.createTest("Excel File Not Found");
			testCase.log(Status.INFO, "Excel File Not Found");
			testCase.log(Status.FAIL, "Excel File Not Found, So fail");
		}
	}

	public static void registeredConfirm() {
		WebDriverWait wait = new WebDriverWait(driver, 2);
		testCase = extent.createTest("CHECKING REGISTER CONFIRMATION");
		try {
			wait.until(ExpectedConditions.visibilityOf(SignUpPage.registerCofirm));
			Bconfirm = true;
		} catch (Exception e) {

			e.printStackTrace();
		}
		if (Bconfirm) {

			register = SignUpPage.registerCofirm.getText();
			System.out.println("////////////////////" + register);

			try {
				Assert.assertEquals(register, confirm);

				testCase.log(Status.INFO, "ACTUAL VALUE" + register);
				testCase.log(Status.INFO, "EXPECTED VALUE" + confirm);
				testCase.log(Status.PASS, "SUCESSFULLY REGISTERED");
			} catch (AssertionError e) {
				testCase = extent.createTest("CHECKING REGISTER CONFIRMATION AND SPELLING");
				testCase.log(Status.INFO, "ACTUAL VALUE" + register);
				testCase.log(Status.INFO, "EXPECTED VALUE" + confirm);

			}

		} else {
			try {
				error = SignUpPage.errorMessage.getText();
				System.out.println("Error message" + error);
				testCase.log(Status.INFO, "REGISTERED HAS FAIL");
				testCase.log(Status.INFO, "BECAUSE OF " + error);
				testCase.log(Status.FAIL, "TEST FAILED");
			} catch (Exception e) {

			}
		}

	}

	@Test
	public static void run() throws InterruptedException, IOException {
		testCase = extent.createTest("SIGNUP FUNCTION");
		signUp();
		if (button) {
			registeredConfirm();
		}
	}

}
