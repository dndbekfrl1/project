import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class megabox {

	private static int score;
	private static String mTitle;
	@SuppressWarnings("static-access")
	public megabox(int score, String mTitle) {
		this.score=score;
		this.mTitle=mTitle;
	}

	private static void saveToDB(ArrayList<megabox_db> megabox) { // 데이터베이스에 저장
		// TODO Auto-generated method stub
		String url = "jdbc:mysql://localhost/Movie";
		String user = "root";
		String pw = "jina0925";
		String sql = "";
		PreparedStatement pstmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pw);

			for (int i = 0; i < megabox.size(); i++) {

				sql = "insert into Megabox(title,review,point) values(?,?,?);";
				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, megabox.get(i).getTitle());
				pstmt.setString(2, megabox.get(i).getReview());
				pstmt.setString(3, megabox.get(i).getPoint());

				int result = pstmt.executeUpdate();

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void seleniumMB() {
		// TODO Auto-generated method stub
		String DRIVER_ID = "webdriver.chrome.driver"; 
		String DRIVER_PATH = "/Users/jina/Jinakorit/JAVA_jina/java/JavaProject1/MovieRecommand/chromedriver";
		System.setProperty(DRIVER_ID, DRIVER_PATH);
		WebDriver driver = new ChromeDriver();
		String base_url = "https://www.megabox.co.kr/movie";

		

		ArrayList<megabox_db> Megabox = new ArrayList<megabox_db>(); // 메가박스 데이터 저장
		ArrayList<pointStack> point_stk = new ArrayList<pointStack>();// 관람 포인트 저장

		try {
			driver.get(base_url);
			List<WebElement> element = driver.findElements(By.cssSelector("div.tit-area>p.tit"));
			List<WebElement> btn = driver.findElements(By.cssSelector("div.movie-list-info>img"));// 영화 포스터 태
			// 찾을 영화
			
			int i = 0;
			for (WebElement e1 : element) {

				Object tmp = e1.getText();
				// 찾을려는 영화 제목이 일치할 때
				if (tmp.equals(mTitle)) {
					btn.get(i).click();
					System.out.println(tmp);
					Thread.sleep(3000);
					// cgv 평점 우수일때,
					if (score >= 5) {
						System.out.println("영화를 추천합니다.");
						WebElement point = driver.findElement(By.cssSelector("div.col>dl>dd"));
						System.out.println("관람 포인트 : "+point.getText());
						
						List<WebElement> user_point=driver.findElements(By.cssSelector("div.col>dl>dd"));
						List<WebElement> review = driver.findElements(By.className("story-txt"));
						// review크롤링
						int r = 0;

						for (int i1=0;i1<review.size();i1++) {
							// 리뷰 스택에 저장 
							megabox_db data=new megabox_db(mTitle,review.get(i1).getText(),user_point.get(i1).getText());
							Megabox.add(r, data);
							r++;
						}
						
						saveToDB(Megabox);

					}
					// cgv 평점 나쁠때,
					else {
						// List<WebElement> review=driver.findElements(By.className("story-txt"));
						System.out.println("영화를 추천하지 않습니다.");
						List<WebElement> point = driver.findElements(By.cssSelector("div.story-recommend>em"));
						
						// 별로인 감상 포인트
						String direct = "연출";
						String actor = "배우";
						String story = "스토리";
						String ost = "OST";
						String visual = "영상미";
						int d = 0, a = 0, s = 0, o = 0, v = 0;

						point_stk.add(0, new pointStack(direct, d));
						point_stk.add(1, new pointStack(actor, a));
						point_stk.add(2, new pointStack(story, s));
						point_stk.add(3, new pointStack(ost, o));
						point_stk.add(4, new pointStack(visual, v));

						for (WebElement e : point) {
							String obj = e.getText();
							if (obj.equals(direct))
								point_stk.get(0).setScore(d++);
							else if (obj.equals(actor))
								point_stk.get(1).setScore(a++);
							else if (obj.equals(story))
								point_stk.get(2).setScore(s++);
							else if (obj.equals(ost))
								point_stk.get(3).setScore(o++);
							else if (obj.equals(visual))
								point_stk.get(4).setScore(v++);
						}
						Collections.sort(point_stk);
						System.out.println("별로인 관람 포인트 : "+point_stk.get(0).getField() + " " + point_stk.get(1).getField());

						List<WebElement> user_point=driver.findElements(By.cssSelector("div.col>dl>dd"));
						List<WebElement> review = driver.findElements(By.className("story-txt"));
						// review크롤링
						int r = 0;
						for (int i1=0;i1<review.size();i1++) {
							// 리뷰 스택에 저장 
							megabox_db data=new megabox_db(mTitle,review.get(i1).getText(),user_point.get(i1).getText());
							Megabox.add(r, data);
							r++;
						}
						saveToDB(Megabox);

					}
					break;
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
