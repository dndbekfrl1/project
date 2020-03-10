import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class cgv {

	static String url = "jdbc:mysql://localhost/Movie";
	static String user = "root";
	static String pw = "jina0925";
	static String sql = "";
	static PreparedStatement pstmt = null;
	static Connection conn = null;
	static ResultSet rs = null;
	
	static ArrayList<cgv_db> cgv;
	
	private static String mTitle;
	

	@SuppressWarnings("static-access")
	public cgv(String title) {
		this.setmTitle(title);
		
	}

	static void seleniumCGV() {
		int score;
		// TODO Auto-generated method stub
		String DRIVER_ID = "webdriver.chrome.driver";
		String DRIVER_PATH = "/Users/jina/Jinakorit/JAVA_jina/java/JavaProject1/MovieRecommand/chromedriver";
		System.setProperty(DRIVER_ID, DRIVER_PATH);
		WebDriver driver = new ChromeDriver();
		String url = "http://www.cgv.co.kr/movies/";

		ArrayList<cgv_db> Cgv = new ArrayList<cgv_db>();

		try {
			driver.get(url);
			List<WebElement> Title = driver
					.findElements(By.cssSelector("div.sect-movie-chart > ol > li > div.box-contents >a"));
			for (WebElement e : Title) {
				if (e.getText().equals(mTitle)) {
					e.click();

					List<WebElement> comment1 = driver
							.findElements(By.cssSelector("div.wrap-persongrade > ul > li> div.box-comment>p"));

					WebElement goodegg = driver.findElement(By.cssSelector("div.wrap-persongrade > ul > li> div.box-contents > ul > li > a > span.egg-icon.good"));
					List<WebElement> eggicon=driver.findElements(By.cssSelector(
							"div.wrap-persongrade > ul > li> div.box-contents > ul > li > a > span.egg-icon"));
					
					int gegg=0;
					
					Thread.sleep(3000);
					
					
					System.out.println("[cgv 관람평]");
					String review;
					for (int i1 = 0; i1 < comment1.size(); i1++) {
						// System.out.println(comment1.get(i1).getText());
						review = comment1.get(i1).getText();
						System.out.println(review);
				
						
						if(eggicon.get(i1).getAttribute("class").equals(goodegg.getAttribute("class"))) {

							cgv_db data = new cgv_db(review, mTitle, "good-egg");
							Cgv.add(i1, data);
							gegg++;
							
						}else {
							cgv_db data = new cgv_db(review, mTitle, "bad-egg");
							Cgv.add(i1, data);
						}

					}

					saveTodb(Cgv);
					Thread.sleep(3000);

					score = gegg;

					megabox MBox = new megabox(score, mTitle);
					megabox.seleniumMB();

					break;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static void saveTodb(ArrayList<cgv_db> cgv) {// 데이터베이스 저
		System.out.println(cgv.get(0).getReview());
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pw);

			for (int i = 0; i < cgv.size(); i++) {

				sql = "insert into CGV(title,review,egg) values(?,?,?);";
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, cgv.get(i).getTitle());
				pstmt.setString(2, cgv.get(i).getReview());
				pstmt.setString(3, cgv.get(i).getEgg());

				pstmt.executeUpdate();

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getmTitle() {
		return mTitle;
	}

	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}
}
