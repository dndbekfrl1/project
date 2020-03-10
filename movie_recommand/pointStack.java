
@SuppressWarnings("rawtypes")
public class pointStack implements Comparable<pointStack>{
	String field;
	int score;
	
	public pointStack(String field,int score) {
		this.field=field;
		this.score=score;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(pointStack o) {
		// TODO Auto-generated method stub
		
		if(score>=o.getScore()) {
			return 1;
		}else if(score<o.getScore()){
			return -1;
		}
		return 0;
	}
}
