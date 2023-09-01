package com.qa.automation.fashion.base;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverInitialization {
	public static WebDriver driver;
	public static ExtentReports extent;
	public static ExtentHtmlReporter htmlreporter;
	public static ExtentTest testCase;
	public static Properties prop = new Properties();
	public static FileReader fr;
	static int groupid = 0;
	static String names = "";

	@BeforeSuite
	public void main() throws IOException {

		if (driver == null) {
//			fr = new FileReader("D:\\privilege\\privilege WorkSpace\\Automation-fashion\\src\\test\\resources\\Configure\\Config.properties");
			fr = new FileReader("./config.properties");
//			fr=new FileReader("C:\\Users\\Invicta\\eclipse-workspace\\QA-QDMS-Automation\\QA-QDMS-AUTOMATION\\src\\test\\resources\\configure\\config.properties");
//			fr = new FileReader("./config.properties");
//			fr = new FileReader("/home/invicta/ITAF_Uploaded_Files/test project qdms/config.properties");
			prop.load(fr);
		}
		if (prop.getProperty("browser").equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
//			System.setProperty("webdriver.chsrome.driver","C:\\Users\\Invicta\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (prop.getProperty("browser").equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (prop.getProperty("browser").equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		driver.manage().window().maximize();
		driver.get(prop.getProperty("testurl"));
		ExtentReport(names, groupid);
	}

	@AfterSuite
	public void closeChrome() throws IOException {
		extent.flush();

		// Store the report content in the database
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Statement statement = null;

		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(
					"jdbc:postgresql://" + DriverInitialization.prop.getProperty("ip") + ":"
							+ DriverInitialization.prop.getProperty("port") + "/"
							+ DriverInitialization.prop.getProperty("database"),
					DriverInitialization.prop.getProperty("username"),
					DriverInitialization.prop.getProperty("password"));
		} catch (Exception e) {
		}
//		 INSERT INTO execution_history (test_grouping_id, report) VALUES (?,?);
//		 INSERT INTO group_report (group_id, report_name) VALUES (?,?);
		String insertQuery = "INSERT INTO execution_history (created_at,updated_at,test_grouping_id, report_name) VALUES (current_timestamp,current_timestamp, ?,?);";
		try {
			preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setInt(1, groupid);
			preparedStatement.setString(2, names);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		driver.quit();
	}

	public static void ExtentReport(String name, int no) {
		names = name;
		groupid = no;
		extent = new ExtentReports();
//		String aa="D:\\Quality Data Management System\\"+name+".html";
		String aa = "./" + name + ".html";
		System.out.println(aa);
		htmlreporter = new ExtentHtmlReporter(aa);
		extent.attachReporter(htmlreporter);

	}

}
