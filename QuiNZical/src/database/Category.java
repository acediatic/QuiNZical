package database;

import java.util.ArrayList;
import java.util.List;

/**
 * The Category class is each category for the 'Quinzical' game, representing a stored category in the database.
 * It contains a list of all the clues in it.
 * @author Osama Kashif (Team 22)
 * @author Adam Sinclair (Team 22)
 */
public class Category {
	private List<Clue> clues = new ArrayList<Clue>();
	private String name;
	
	/**
	 * Initialises with category name.
	 * @param nameToAdd
	 */
	public Category(String nameToAdd) {
		name = nameToAdd;
	}
	
	/**
	 * Initialises with category name and a list of Clues given to it.
	 * @param nameToAdd
	 * @param clues
	 */
	public Category(String nameToAdd, List<Clue> clues) {
		name = nameToAdd;
		this.clues = clues;
	}
	
	/**
	 * addClue allows Clues to be added to the list.
	 * @param clue
	 */
	public void addClue(Clue clue) {
		clues.add(clue);
	}
	
	/**
	 * getClue returns the Clue at a certain index.
	 * @param index
	 * @return
	 */
	public Clue getClue(int index) {
		return clues.get(index);
	}
	
	/**
	 * getAllClues returns the list of Clues.
	 * @return
	 */
	public List<Clue> getAllClues() {
		return clues;
	}
	
	/**
	 * getAllUnansweredClues returns all the unanswered Clue in the list.
	 * @return
	 */
	public List<Clue> getAllUnasnweredClues() {
		List<Clue> unanswered = new ArrayList<Clue>();
		for (Clue clueBeingTested : clues) {
			if (!clueBeingTested.isAnswered()) {
				unanswered.add(clueBeingTested);
			}
		}
		return unanswered;
	}
	
	/**
	 * numberOfClues returns the number of Clues for the category.
	 * @return
	 */
	public int numberOfClues() {
		return clues.size();
	}
	
	/**
	 * allAnswered returns true if there are no Clues in Category or all have Clues in 
	 * it have been answered, otherwise false.
	 * @return
	 */
	public Boolean allAnswered() {
		for (Clue clueBeingTested : clues) {
			if (!clueBeingTested.isAnswered()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * categoryName returns the name of the category.
	 * Generally used for visual needs in the application.
	 * @return
	 */
	public String categoryName() {
		return name;
	}

	/**
	 * indexOf returns the index of a particular Clue in the Clue list stored in the Category.
	 * @param clue
	 * @return
	 */
	public int indexOf(Clue clue) {
		return clues.indexOf(clue);
	}
	
	/**
	 * getRandomClues gets 5 questions from the list of clues in it, or all the clues in random order 
	 * if they are less than 5.
	 * @return List of CLues which are randomly ordered.
	 */
	public List<Clue> getRandomClues() {
		List<Clue> randomClues = new ArrayList<Clue>();
		List<Integer> done = new ArrayList<Integer>(); 
		//while ((randomClues.size() < clues.size()) && (randomClues.size() < 5)) {
		while (randomClues.size() < 5) {
				int randomIndex = (int)(Math.random() * clues.size());
				if (!done.contains(randomIndex)) {
					randomClues.add(clues.get(randomIndex));
					done.add(randomIndex);
				}
		}
		return randomClues;
	}
	
	/**
	 * getOneRandomClue returns one random clue in the category
	 * @return randomClue
	 */
	public Clue getOneRandomClue() {
		int randomIndex = (int)(Math.random() * clues.size());
		Clue randomClue = clues.get(randomIndex);
		return randomClue;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
