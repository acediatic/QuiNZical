package database;

public class User implements Comparable<User> {
	
	private String _name;
	private int _score = 0;
	private Integer _comparableScore = (Integer)_score;
	
	public User(String name) {
		_name = name;
	}
	
	public String getName() {
		return _name;
	}
	
	public int reportScore() {
		return _score;
	}
	
	public Integer reportComparableScore() {
		return _comparableScore;
	}
	
	public void incrementScore(int added) {
		_score = _score + added;
		_comparableScore = (Integer)_score;
	}

	@Override
	public int compareTo(User user) {
		return this._comparableScore.compareTo(user.reportComparableScore());
	}

}
