package database;

/**
 * The Clue class is used to store a clue, its value, and correct answer.
 * It can then be used to return those values.
 * @author Adam Sinclair (Team 22)
 * @author Osama Kashif (Team 22)
 *
 */
public class Clue {

	private int _value;
	private String _valueString;
	private String _clue;
	private String _clueType;
	private Category _category;
	private String _answer;
	private Boolean _answered = false;
	
	/**
	 * The Clue constructor is used to initialise the question object using
	 * String values from the line in a file in the categories folder.
	 * @param value
	 * @param clue
	 * @param answer
	 */
	public Clue(String value, String clue, String answer) {
		_valueString = value;
		_value = Integer.parseInt(_valueString);
		_clue = clue;
		_answer = answer;
	}
	
	/**
	 * The Clue constructor is used to initialise the question object using
	 * String values from the line in a file in the database folder.
	 * @param category
	 * @param clue
	 * @param clueType
	 * @param answer
	 */
	public Clue(Category category, String clue, String clueType, String answer) {
		_category = category;
		_clue  = clue;
		_clueType = clueType;
		_answer = answer;
	}
	
	/**
	 * The Clue constructor is used to initialise the question object using
	 * String values from the line in a file in the database folder.
	 * @param category
	 * @param clue
	 * @param clueType
	 * @param answer
	 * @param value
	 */
	public Clue(Category category, String clue, String clueType, String answer, String value) {
		_category = category;
		_clue = clue;
		_clueType = clueType;
		_answer = answer;
		_valueString = value;
		_value = Integer.parseInt(_valueString);
	}
	
	/**
	 * The Clue constructor is used to initialise the question object using
	 * String values from the line in a file in the database folder.
	 * @param category
	 * @param clue
	 * @param clueType
	 * @param answer
	 * @param value
	 * @param answered
	 */
	public Clue(Category category, String clue, String clueType, String answer, String value, String answered) {
		_category = category;
		_clue = clue;
		_clueType = clueType;
		_answer = answer;
		_valueString = value;
		_value = Integer.parseInt(_valueString);
		if (answered.equals("true")) {
			_answered = true;
		}
		else {
			_answered = false;
		}
	}
	
	public void giveValue(String value) {
		_value = Integer.parseInt(value);
		_valueString = value;
	}
	
	/**
	 * answered marks the question as answered //Not used much, but left for possible future implementations.
	 */
	public void answered() {
		_answered = true;
	}
	
	/**
	 * Returns if the question has been answered or not.
	 * @return
	 */
	public Boolean isAnswered() {
		return _answered;
	}
	
	/**
	 * showValue returns the value as a String.
	 * @return
	 */
	public String showValue() {
		return _valueString;
	}
	
	/**
	 * returnValue returns the value as a double for calculations.
	 * @return
	 */
	public int returnValue() {
		return _value;
	}
	
	/**
	 * showClue returns the question.
	 * @return
	 */
	public String showClue() {
		return _clue;
	}
	
	/**
	 * showAnswer returns answer for checking.
	 * @return
	 */
	public String showAnswer() {
		return _answer;
	}
	
	/**
	 * showCategory returns the category the clue comes under.
	 * @return
	 */
	public String showCategory() {
		return _category.toString();
	}
	
	public Category getCategory() {
		return _category;
	}
	
	
	/**
	 * showClueType returns the clue type of the clue.
	 * @return
	 */
	public String showClueType() {
		return _clueType;
	}
	
}
