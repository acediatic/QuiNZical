package database;

import javafx.beans.property.SimpleStringProperty;

/**
 * The User class is made to allow users to record their score and come on the scoreboard.
 * @author Osama Kashif (Team 22)
 * @author Adam Sinclair (Team 22)
 *
 */
public class User implements Comparable<User> {
	
	private final SimpleStringProperty _name;
	private int _score = 0;
	private final SimpleStringProperty _scoreString;
	//private String _scoreString = Integer.toString(_score);
	private Integer _comparableScore = (Integer)_score;
	
	
	/**
	 * This constructor for the User class just assigns the user name.
	 * @param name
	 */
	public User(String name) {
		_name = new SimpleStringProperty(name);
		_scoreString = new SimpleStringProperty("");
	}
	
	/**
	 * This constructor for the User class assigns the user name, score, and associated score values for 
	 * calculations and comparison. The input score is a String.
	 * @param name
	 * @param score
	 */
	public User(String name, String score) {
		_name = new SimpleStringProperty(name);
		_scoreString = new SimpleStringProperty(score);
		_comparableScore = (Integer)_score;
	}
	
	/**
	 * This constructor for the User class assigns the user name, score, and associated score values for 
	 * calculations and comparison. The input score is an int.
	 * @param name
	 * @param score
	 */
	public User(String name, int score) {
		_name = new SimpleStringProperty(name);
		_score = score;
		_scoreString = new SimpleStringProperty(Integer.toString(score));
		_comparableScore = (Integer)_score;
	}
	
	/**
	 * getName returns the username.
	 * @return username
	 */
	public String getName() {
		return _name.get();
	}
	
	/**
	 * reportScore returns the String score of the user stored.
	 * @return user score
	 */
	public String getScore() {
		return _scoreString.get();
	}
	/**
	 * reportCalculableScore returns the users score as an int.
	 * @return user score
	 */
	public int reportCalculableScore() {
		return _score;
	}
	
	/**
	 * reportComparableScore is used in association with compareTo to allow sorting of users by score. 
	 * It returns the score an Integer.
	 * @return user score
	 */
	private Integer reportComparableScore() {
		return _comparableScore;
	}
	
	/**
	 * incrementScore is used to update the score of the user on answering a question.
	 * @param added
	 */
	public void incrementScore(int added) {
		_score = _score + added;
		_comparableScore = (Integer)_score;
	}
	
	/**
	 * overrideScore is used to override the score the user has. It can be used at the 
	 * end of the game to save the score on file once instead of saving it on the file per turn.
	 * @param score
	 */
	public void overrideScore(int score) {
		_score = score;
		_scoreString.set(Integer.toString(score));
		_comparableScore = (Integer)_score;
	}

	/**
	 * compareTo is a method in Comparable, and it is being overridden here so lists of User can be sorted.
	 */
	@Override
	public int compareTo(User user) {
		return this._comparableScore.compareTo(user.reportComparableScore());
	}

}
