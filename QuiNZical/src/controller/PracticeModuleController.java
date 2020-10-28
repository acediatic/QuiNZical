package controller;

import java.util.ArrayList;

import database.Category;
import database.CategoryExtractor;
import javafx.scene.text.Font;

/**
 * The singleton controller responsible for managing the practice
 * module. Any method calls between model and view run
 * through this controller for this section. 
 * @author Adam and Osama
 *
 */
public class PracticeModuleController {
	public static Double _currentFontSize = 1.8;
	public static Font titleFont;

	private static PracticeModuleController singleton = null;
	
	public int currentAttempts;
	
	private CategoryExtractor  catExtractor = new CategoryExtractor();
	private ArrayList<Category> _categories;
	
	
	/**
	 * Private constructor as it is a singleton. Tries to extract
	 * the categoires for the practice module, and set the font.
	 */
	private PracticeModuleController() {		
		try {
			_categories = catExtractor.getMasterCategories();
			titleFont = PrimaryController.titleFont;
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the current instance of the object, or 
	 * instantiates one if not.
	 * @return the instance of PracticeModuleController
	 */
	public static PracticeModuleController getInstance() {
		if (singleton == null) {
			singleton = new PracticeModuleController();
		}
		return singleton;
	}
	
	/**
	 * reset() utilises to deleteDir to delete the winnings and history files, to remove the users progress and reset the game.
	 * @throws Exception 
	 */
	public void reset()  {
		try {
			singleton = new PracticeModuleController();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Fetches all the categories available in the practice
	 * module.
	 * @return all categories available to be practiced
	 */
	public ArrayList<Category> getAllCategories() {
		return _categories;
	}

}

