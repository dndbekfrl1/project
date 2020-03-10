
public class cgv_db {
	
	private String review;
	private String title;
	private String egg;
	
	public cgv_db(String review, String title, String egg){
		this.review=review;
		this.title=title;
		this.egg=egg;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEgg() {
		return egg;
	}

	public void setEgg(String egg) {
		this.egg = egg;
	}
}
