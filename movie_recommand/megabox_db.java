
/**
 * @author jina
 *
 */
public class megabox_db {
	private String title;
	private String review;
	private String point;
	
	public megabox_db(String title, String review, String point) {
		this.title=title;
		this.review=review;
		this.point=point;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}
	
}
