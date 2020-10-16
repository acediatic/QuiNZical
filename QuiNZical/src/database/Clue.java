package database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	private List<String> _multiAnswers;
	private List<String> _possibleAnswers;
	private List<List<String>> _multiAnswersWithPossibilities;
	
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
		splitAnswer(_answer);
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
		splitAnswer(_answer);
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
		splitAnswer(_answer);
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
		splitAnswer(_answer);
		_valueString = value;
		_value = Integer.parseInt(_valueString);
		if (answered.equals("true")) {
			_answered = true;
		}
		else {
			_answered = false;
		}
	}
	
	public Boolean check(String answer) {
		if (answer.replaceAll(" ", "").equalsIgnoreCase(_answer.replaceAll(" ", ""))) {
			return true;
		}
		//list.stream().anyMatch("search_value"::equalsIgnoreCase) //https://stackoverflow.com/questions/15824733/option-to-ignore-case-with-contains-method
		//else if (_possibleAnswers.contains(_answer)) {
		else if (_possibleAnswers.stream().anyMatch(answer::equalsIgnoreCase)) {
			return true;
		}
		else {
			for (String s: _possibleAnswers) {
				if (answer.replaceAll(" ", "").equalsIgnoreCase(s.replaceAll(" ", ""))) {
					return true;
				}
			}
			String [] answerArray = answer.split(",");
			int multiMatch = 0;
			for (String s : _multiAnswers) {
				for (String ans: answerArray) {
					if (ans.replaceAll(" ", "").equalsIgnoreCase(s.replaceAll(" ", ""))) {
						multiMatch++;
						break;
					}
				}
			}
			if (multiMatch == _multiAnswers.size()) {
				return true;
			}
			
			multiMatch = 0;
			List<String> checkedAnswers = new ArrayList<String>();
			for (List<String> list: _multiAnswersWithPossibilities) {
				for (String s : list) {
					for (String ans: answerArray) {
						if (ans.replaceAll(" ", "").equalsIgnoreCase(s.replaceAll(" ", "")) & !checkedAnswers.contains(s)) {
							multiMatch++;
							checkedAnswers.add(s);
							break;
						}
					}
				}
			}
			if (multiMatch == _multiAnswers.size()) {
				return true;
			}

		}
		return false;
	}
	
	private void splitAnswer(String answer) {
		if (answer.contains(",") & answer.contains("/")) {
			String[] multiAnswerArray = answer.split(",");
			int maxVariations = 0;
			for (int i = 0; i < multiAnswerArray.length; i++) {
				if (multiAnswerArray[i].contains("/")) {
					if (multiAnswerArray[i].split("/").length > maxVariations) {
						maxVariations = multiAnswerArray[i].split("/").length;
					}
				}
			}
			for (int i = 0; i < maxVariations; i++) {
				_multiAnswersWithPossibilities.add(new ArrayList<String>());
			}
			for (int i = 0; i < multiAnswerArray.length; i++) {
				if (multiAnswerArray[i].contains("/")) {
					if (multiAnswerArray[i].split("/").length < maxVariations) {
						for (int j = 0; j < multiAnswerArray[i].split("/").length; j++) {
							_multiAnswersWithPossibilities.get(j).add(multiAnswerArray[i].split("/")[j]);
						}
						for (int j = multiAnswerArray[i].split("/").length; j < maxVariations; j++) {
							_multiAnswersWithPossibilities.get(j).add(multiAnswerArray[i].split("/")[0]);
						}
					}
					else {
						for (int j = 0; j < multiAnswerArray[j].split("/").length; j++) {
							_multiAnswersWithPossibilities.get(j).add(multiAnswerArray[i].split("/")[j]);
						}
					}
				}
				else {
					for (int j = 0; j < maxVariations; j++) {
						_multiAnswersWithPossibilities.get(j).add(multiAnswerArray[j]);
					}
				}
			}
			
		}
		else if(answer.contains(",")) {
			_multiAnswers = Arrays.asList(answer.split(","));
		}
		else if(answer.contains("/")) {
			_possibleAnswers = Arrays.asList(answer.split("/"));
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
