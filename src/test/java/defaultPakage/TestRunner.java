package defaultPakage;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.aventstack.extentreports.Status;
import com.qa.automation.fashion.base.DriverInitialization;


public class TestRunner {
	public static String groupName = "";
	public static int groupid = 0;
//	static String config="C:\\Users\\Invicta\\eclipse-workspace\\QA-QDMS-Automation\\QA-QDMS-AUTOMATION\\src\\test\\resources\\configure\\config.properties";
	static String config = "./config.properties";
	public static int projectid = 0;

	public static void main(String[] args) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd HH-mm-ss");
		String formattedDateTime = currentDateTime.format(formatter);
		try {
			if (DriverInitialization.driver == null) {
				System.out.println(">>>>>>>>>>>>>>>" + config);
				DriverInitialization.fr = new FileReader(config);
				DriverInitialization.prop.load(DriverInitialization.fr);
			}
		} catch (Exception e) {
		}

		java.sql.Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println(DriverInitialization.prop.getProperty("ip"));
			System.out.println(DriverInitialization.prop.getProperty("port"));
			System.out.println(DriverInitialization.prop.getProperty("database"));
			System.out.println(DriverInitialization.prop.getProperty("username"));
			System.out.println(DriverInitialization.prop.getProperty("password"));
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(
					"jdbc:postgresql://" + DriverInitialization.prop.getProperty("ip") + ":"
							+ DriverInitialization.prop.getProperty("port") + "/"
							+ DriverInitialization.prop.getProperty("database"),
					DriverInitialization.prop.getProperty("username"), DriverInitialization.prop.getProperty("password"));
//			connection=DriverManager.getConnection("jdbc:postgresql://"+DriverInitialization.prop.getProperty("ip")+":"+DriverInitialization.prop.getProperty("port")+"/"+DriverInitialization.prop.getProperty("database"),DriverInitialization.prop.getProperty("username"),DriverInitialization.prop.getProperty("password"));
			System.out.println("444444444444444");
		} catch (Exception e) {
			System.out.println("****************11111111");
		}
		ResultSet rs = null;
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		List<Class<?>> testClasses = new ArrayList<Class<?>>();
		PreparedStatement preparedStatement = null;
		if (connection != null) {
			System.out.println("!!!!!!!!!!!");

			String insertQuerys = "SELECT id FROM project WHERE code=?";
			try {
				preparedStatement = connection.prepareStatement(insertQuerys);
				String projectCode = DriverInitialization.prop.getProperty("code");
				preparedStatement.setString(1, projectCode);
				ResultSet result = preparedStatement.executeQuery();

				while (result.next()) {
					projectid = result.getInt("id");
					}
				}catch (Exception e) {
					// TODO: handle exception
				}
				
				
			String insertQuery = "SELECT id FROM test_grouping WHERE execution_status='true' AND project_id=(SELECT id FROM project WHERE code=?)";
			try {
				preparedStatement = connection.prepareStatement(insertQuery);
				String projectCode = DriverInitialization.prop.getProperty("code");
				System.out.println(projectCode + "******************");
				preparedStatement.setString(1, projectCode);
				ResultSet result = preparedStatement.executeQuery();

				while (result.next()) {
					int id = result.getInt("id");
					arrayList.add(id);
					System.out.println("ID from test_grouping: " + id);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

			if (arrayList.isEmpty() || arrayList==null) {
				System.out.println("array list is empty");
				DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String formattedDateTimes = currentDateTime.format(formatters);
				int x = 0;
				try {
					String Querys = "SELECT id FROM test_grouping WHERE date_trunc('second', updated_at) = ?";
					preparedStatement = connection.prepareStatement(Querys);
					Timestamp targetTimestamp = Timestamp.valueOf(formattedDateTimes);
					preparedStatement.setTimestamp(1, targetTimestamp);
					ResultSet resultSets = preparedStatement.executeQuery();
					while (resultSets.next()) {
						x = Integer.valueOf(resultSets.getString("id"));
						System.out.println(resultSets.getString("id"));
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				try {
					String Querys = "UPDATE test_grouping SET execution_status='false' WHERE test_grouping.id = ?";
					preparedStatement = connection.prepareStatement(Querys);
					preparedStatement.setInt(1, x);
					preparedStatement.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}

				try {
					String Querys = "DELETE FROM executed_test_case WHERE test_grouping_id = ?";
					preparedStatement = connection.prepareStatement(Querys);
					preparedStatement.setInt(1, x);
					preparedStatement.executeUpdate(); // Use executeUpdate() for delete queries
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
				System.out.println("array list is empty 1111111111111111");
			}
			System.out.println(arrayList + "*******************");
			for (int i : arrayList) {
				groupid = i;
				System.out.println(groupid + "***************");
//					
				try {
					String Query = "SELECT test_grouping.name FROM test_grouping WHERE test_grouping.execution_status='true' AND id=?";
					preparedStatement = connection.prepareStatement(Query);
					preparedStatement.setInt(1, i);
					ResultSet resultSet = preparedStatement.executeQuery();

					while (resultSet.next()) {
						groupName = resultSet.getString("name");
						String aa = groupName + " " + formattedDateTime;
						System.out.println(aa + "******************************");
						DriverInitialization.ExtentReport(aa, groupid);
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
				try {
					String Querys = "SELECT test_cases.name FROM executed_test_case JOIN test_cases ON executed_test_case.test_cases_id = test_cases.id WHERE executed_test_case.test_grouping_id = ?";
					preparedStatement = connection.prepareStatement(Querys);
					preparedStatement.setInt(1, i);
					ResultSet resultSet = preparedStatement.executeQuery();

					while (resultSet.next()) {
						System.out.println(resultSet.getString("name"));
						try {
							Class<?> clazz = Class.forName(resultSet.getString("name"));
							testClasses.add(clazz);
						} catch (Exception e) {
							System.out.println(resultSet.getString("name") + " Not Found");
							DriverInitialization.testCase = DriverInitialization.extent
									.createTest("Class Not Found in Jar");
							DriverInitialization.testCase.log(Status.INFO, "Class Not Found in Your Jar");
							DriverInitialization.testCase.log(Status.WARNING, "So Check the Class Name");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
				TestNG testNG = new TestNG();

				int currentIndex = 0;
				String insertProgress = "INSERT INTO progress_bar (executed_test_case_count,total_no_of_test_cases, project_id, test_grouping_id) VALUES (?,?,?,?)";
				try {
					  preparedStatement = connection.prepareStatement(insertProgress);
					preparedStatement.setInt(3,  projectid);
				    preparedStatement.setInt(4, groupid);
				    preparedStatement.setInt(2,  testClasses.size());
				    preparedStatement.setInt(1, currentIndex);
				    preparedStatement.executeUpdate();
				} catch (Exception e) {
				    e.printStackTrace();
				    System.out.println(e.getMessage());
				}
				for (Class<?> testClass : testClasses) {

					int a = testClasses.size();
					int b = currentIndex;

					XmlSuite suite = new XmlSuite();
					suite.setName("SingleTestClassSuite" + currentIndex);

					XmlTest test = new XmlTest(suite);
					test.setName("SingleTestClassTest" + currentIndex);

					List<XmlClass> classes = new ArrayList<XmlClass>();
					classes.add(new XmlClass(testClass));
					test.setXmlClasses(classes);

					List<XmlSuite> suites = new ArrayList<XmlSuite>();
					suites.add(suite);
					testNG.setXmlSuites(suites);
					testNG.run();
					currentIndex++;
					String updateProgress = "UPDATE progress_bar SET executed_test_case_count = ? WHERE test_grouping_id = ?";
					try {
						 preparedStatement = connection.prepareStatement(updateProgress);
						    preparedStatement.setInt(1, currentIndex);
						    preparedStatement.setInt(2, groupid);
						    preparedStatement.executeUpdate();
					}catch (Exception e) {
						System.out.println(e.getMessage());
					}
					
					
				}
				
				
//				try {
//					String Querys = "UPDATE test_grouping SET execution_status='false' WHERE test_grouping.id = ?";
//					preparedStatement = connection.prepareStatement(Querys);
//					preparedStatement.setInt(1, i);
//					preparedStatement.executeUpdate();
//				} catch (Exception e) {
//					e.printStackTrace();
//					System.out.println(e.getMessage());
//				}
//
//				try {
//					String Querys = "DELETE FROM executed_test_case WHERE test_grouping_id = ?";
//					preparedStatement = connection.prepareStatement(Querys);
//					preparedStatement.setInt(1, i);
//					preparedStatement.executeUpdate(); // Use executeUpdate() for delete queries
//				} catch (Exception e) {
//					e.printStackTrace();
//					System.out.println(e.getMessage());
//				}
//			
//			
//				try {
//					String Querys = "DELETE FROM progress_bar WHERE test_grouping_id = ?";
//					preparedStatement = connection.prepareStatement(Querys);
//					preparedStatement.setInt(1, groupid);
//					preparedStatement.executeUpdate(); // Use executeUpdate() for delete queries
//				} catch (Exception e) {
//					e.printStackTrace();
//					System.out.println(e.getMessage());
//				}
			}
			System.out.println(">>>>>>>>>>>>>>>>>>");
		} else {
			System.out.println("*********");
			DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDateTimes = currentDateTime.format(formatters);
			int x = 0;
			try {
				String Querys = "SELECT id FROM test_grouping WHERE date_trunc('second', updated_at) = ?";
				preparedStatement = connection.prepareStatement(Querys);
				Timestamp targetTimestamp = Timestamp.valueOf(formattedDateTimes);
				preparedStatement.setTimestamp(1, targetTimestamp);
				ResultSet resultSets = preparedStatement.executeQuery();
				while (resultSets.next()) {
					x = Integer.valueOf(resultSets.getString("id"));
					System.out.println(resultSets.getString("id"));
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			try {
				String Querys = "UPDATE test_grouping SET execution_status='false' WHERE test_grouping.id = ?";
				preparedStatement = connection.prepareStatement(Querys);
				preparedStatement.setInt(1, x);
				preparedStatement.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

			try {
				String Querys = "DELETE FROM executed_test_case WHERE test_grouping_id = ?";
				preparedStatement = connection.prepareStatement(Querys);
				preparedStatement.setInt(1, x);
				preparedStatement.executeUpdate(); // Use executeUpdate() for delete queries
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}

	}

}
