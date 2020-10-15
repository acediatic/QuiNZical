package controller;

import java.io.File;
import java.util.ArrayList;

import application.QuiNZical;
import controller.sceneControllers.Controller;
import database.Category;
import database.CategoryExtractor;
import database.Clue;
import database.WinningsExtractor;
import javafx.scene.text.Font;

public class PrimaryController {
	public static Double _currentFontSize = 1.8;
	public static QuiNZical app;
	
	private WinningsExtractor winExtractor = new WinningsExtractor();
	private CategoryExtractor  catExtractor = new CategoryExtractor();
	
	public Controller currentController;
	public static String path;
	public static File categoriesFolder;
	public static Font titleFont;
	
	private static PrimaryController singleton = null;
	
	private ArrayList<Category> _categories;
	
	private PrimaryController() {
		String fullPath = (new File((new File(System.getProperty("java.class.path"))).getAbsolutePath())).getAbsolutePath();
		String [] relevantPath = fullPath.split(System.getProperty("path.separator"));
		path = (new File(relevantPath[0])).getParentFile().getAbsolutePath();
		String categoriesPath = (new File(relevantPath[0])).getParentFile().getParentFile().getAbsolutePath();
		categoriesFolder = new File(categoriesPath + "/categories");
		
		
		try {
			_categories = catExtractor.getCategories();
			getWinnings();
			titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/homeFont.ttf"), 40);
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
	
	public static PrimaryController getInstance() {
		if (singleton == null) {
			singleton = new PrimaryController();
		}
		return singleton;
	}
	
	public void update(Clue clue, Boolean correct){
		try {
			//Updating in the Model as well.
			clue.answered();
			if (correct) {
				winExtractor.updateWinningFile(clue);
			}
			catExtractor.markQuestionAnswered(clue);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	/**
	 * reset() utilises to deleteDir to delete the winnings and history files, to remove the users progress and reset the game.
	 * @throws Exception 
	 */
	public void reset()  {
		try {
			winExtractor.resetWinnings();
			catExtractor.resetCategories();
			_categories = catExtractor.getCategories();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getWinnings() {
		String winnings = "0";
		try {
			winnings = winExtractor.getWinnings();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return winnings;
	}
	
	public ArrayList<Category> getCategories() {
		return _categories;
	}

	public Category getCategory(int index) {
		return _categories.get(index);
	}
}

