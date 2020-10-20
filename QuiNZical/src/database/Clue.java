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
	private List<String> _multiAnswers = new ArrayList<String>();
	private List<String> _possibleAnswers = new ArrayList<String>();
	private List<List<String>> _multiAnswersWithPossibilities = new ArrayList<List<String>>();
	private ClueType _clueTypeEnum;
	/*
	 * ClueType enum to simplify clue type usage.
	 */
	enum ClueType {
		WHAT_IS("What is"),
		WHAT_ARE("What are"),
		WHO_IS("Who is"),
		WHO_ARE("Who are"),
		HOW_IS("How is"),
		HOW_ARE("How are"),
		WHERE_IS("Where is"),
		WHERE_ARE("Where are"),
		WHEN_IS("When is"),
		WHEN_ARE("When are"),
		WHY_IS("Why is"),
		WHY_ARE("Why are");
		
		private String _type;
		
		/**
		 * Constructor to allow String values for the enum.
		 * @param type
		 */
		ClueType(String type) {
			this._type = type;
		}
		
		/**
		 * getType is used to return the String value.
		 * @return the clue type for the enum value.
		 */
		public String getType() {
			return _type;
		}
	}
	
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
		setupClassType(clueType);
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
		setupClassType(clueType);
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
		setupClassType(clueType);
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
	
	/**
	 * This is used to setup the ClueType enum and String, so that it can be used for the game.
	 * @param clueType
	 */
	private void setupClassType(String clueType) {
		for (ClueType type: ClueType.values()) {
			if (type.getType().equalsIgnoreCase(clueType.replaceAll("\\?", "").trim())) {
				_clueTypeEnum = type;
				_clueType = _clueTypeEnum.getType();
			}
		}
	}
	
	/**
	 * check is used to check if the answer the user gave for the question is correct or not. 
	 * It utilises the splitting in splitAnswer, and follows the same splitting criteria to check 
	 * if the answer is correct or not. If multiple answers are needed, all answers need to be present. 
	 * If there are various possible answers, only one possibility needs to match. If there is only 
	 * one answer, it needs to match. It accounts for cases and blank spaces as well.
	 * @param answer
	 * @return if the answer given for the clue is correct or not
	 */
	public Boolean check(String answer) {
		if (answer.replaceAll(" ", "").equalsIgnoreCase(_answer.replaceAll(" ", ""))) {
			return true;
		}
		else if  (_possibleAnswers.stream().anyMatch(answer::equalsIgnoreCase)) {
			return true;
		}
		else {
			String [] answerArray;
			if (answer.contains(",")) {
				answerArray = answer.split(",");
			}
			else if (answer.contains("/")) {
				answerArray = answer.split("/");
			}
			else {
				answerArray = answer.split(" ");
			}
			for (String s: _possibleAnswers) {
				if (answer.replaceAll(" ", "").equalsIgnoreCase(s.replaceAll(" ", ""))) {
					return true;
				}
				else {
					for (String ans : answerArray) {
						if (ans.replaceAll(" ", "").equalsIgnoreCase(s.replaceAll(" ", ""))) {
							return true;
						}
					}
				}
			}
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
	
	/**
	 * The splitAnswer method allows the answer for the clue to be split in case there are different 
	 * possible answers or multiple answers to the clue. It uses a criteria for it, answers with a "," 
	 * are split into multiple answers, all of which need to be correct. Otherwise, answers with a "/" 
	 * are split into possibleAnswers, only one of which marks the clue as correct if it matches. Then, 
	 * for clues with both "," and "/" it is split into a combination of possible multiple answers.
	 * @param answer
	 */
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
						if (!_multiAnswersWithPossibilities.get(j).contains(multiAnswerArray[i])) {
							_multiAnswersWithPossibilities.get(j).add(multiAnswerArray[i]);
						}
					}
				}
			}
			_possibleAnswers.add(answer);
			_multiAnswers = Arrays.asList(answer.split(","));	
		}
		else if(answer.contains(",")) {
			_multiAnswers = Arrays.asList(answer.split(","));
			_possibleAnswers.add(answer);
			_multiAnswersWithPossibilities.add(_possibleAnswers);
		}
		else if(answer.contains("/")) {
			_possibleAnswers = Arrays.asList(answer.split("/"));
			_multiAnswers.add(answer);
			_multiAnswersWithPossibilities.add(_possibleAnswers);
		}
		else {
			_possibleAnswers.add(answer);
			_multiAnswers.add(answer);
			_multiAnswersWithPossibilities.add(_possibleAnswers);
		}
	}
	
	/**
	 * giveValue is used to assign a value to the clue.
	 * @param value
	 */
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
	 * @return if the question is answered
	 */
	public Boolean isAnswered() {
		return _answered;
	}
	
	/**
	 * showValue returns the value as a String.
	 * @return value string
	 */
	public String showValue() {
		return _valueString;
	}
	
	/**
	 * returnValue returns the value as a int for calculations.
	 * @return value 
	 */
	public int returnValue() {
		return _value;
	}
	
	/**
	 * showClue returns the question.
	 * @return clue/question
	 */
	public String showClue() {
		return _clue;
	}
	
	/**
	 * showAnswer returns answer for checking.
	 * @return answer
	 */
	public String showAnswer() {
		return _answer;
	}
	
	/**
	 * showCategory returns the category the clue comes under.
	 * @return category name
	 */
	public String showCategory() {
		return _category.toString();
	}
	
	/**
	 * getCategory returns the category the clue belongs to.
	 * @return category
	 */
	public Category getCategory() {
		return _category;
	}
	
	
	/**
	 * showClueType returns the clue type of the clue.
	 * @return clue type
	 */
	public String showClueType() {
		return _clueType;
	}
	
}
