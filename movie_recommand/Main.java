import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

	static String DRIVER_ID = "webdriver.chrome.driver";
	static String DRIVER_PATH = "/Users/jina/Jinakorit/JAVA_jina/java/JavaProject1/MovieRecommand/chromedriver";

	@SuppressWarnings("static-access")
	public static void main(String[] args) {

		System.setProperty(DRIVER_ID, DRIVER_PATH);
		WebDriver driver = new ChromeDriver();

		String mTitle; // 검색할 영화
		Scanner sc = new Scanner(System.in);

		try {
			driver.get("http://www.cgv.co.kr/movies/");

			List<WebElement> Title = driver
					.findElements(By.cssSelector("div.sect-movie-chart > ol > li > div.box-contents >a"));

			System.out.println("******* 현재 상영중인 영화 *******");
			for (WebElement e : Title) {
				System.out.println(e.getText());
			}
			System.out.println("*****************************");
			System.out.println("원하는 영화를 입력해주세요.");

			mTitle = sc.nextLine();
			cgv cgv_data = new cgv(mTitle);
			cgv_data.seleniumCGV();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
