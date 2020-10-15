package controller;

import java.util.ArrayList;

import database.Category;
import database.CategoryExtractor;
import javafx.scene.text.Font;

public class PracticeModuleController {
	public static Double _currentFontSize = 1.8;
	
	private CategoryExtractor  catExtractor = new CategoryExtractor();

	public static Font titleFont;
	
	private static PracticeModuleController singleton = null;
	
	public int currentAttempts;
	private ArrayList<Category> _categories;
	
	private PracticeModuleController() {		
		try {
			_categories = catExtractor.getMasterCategories();
			titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/homeFont.ttf"), 40);
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
	
	public static PracticeModuleController getInstance() {
		if (singleton == null) {
			singleton = new PracticeModuleController();
		}
		return singleton;
	}

	public static void showHint() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * reset() utilises to deleteDir to delete the winnings and history files, to remove the users progress and reset the game.
	 * @throws Exception 
	 */
	public void reset()  {
		try {
			singleton = new PracticeModuleController();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Category> getAllCategories() {
		return _categories;
	}

	public Category getCategory(int index) {
		return _categories.get(index);
	}

	public static void showAnswer() {
		// TODO Auto-generated method stub
		
	}


}

