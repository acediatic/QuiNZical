package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import controller.Controller;
import database.Category;
import database.Clue;
import database2.CategoryExtractor;
import database2.WinningsExtractor;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameMainController {
	public static Double _currentFontSize = 1.8;
	public static QuiNZical app;
	
	private WinningsExtractor winExtractor = new WinningsExtractor();
	private CategoryExtractor  catExtractor = new CategoryExtractor();
	
	public static Controller currentController;
	public static String path;
	public static Font titleFont;
	
	private static GameMainController singleton = null;
	
	private ArrayList<Category> _categories;
	
	private GameMainController() {
		String fullPath = (new File((new File(System.getProperty("java.class.path"))).getAbsolutePath())).getAbsolutePath();
		String [] relevantPath = fullPath.split(System.getProperty("path.separator"));
		path = (new File(relevantPath[0])).getParentFile().getAbsolutePath();
		
		try {
			_categories = catExtractor.getCategories();
			getWinnings();
			titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/homeFont.ttf"), 40);
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
	
	public static GameMainController getInstance() {
		if (singleton == null) {
			singleton = new GameMainController();
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
			// TODO Auto-generated catch block
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

