package controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.QuiNZical;
import audio.Speaker;
import controller.sceneControllers.Controller;
import database.Category;
import database.CategoryExtractor;
import database.Clue;
import database.WinningsExtractor;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import service.FXMLService;

public class PrimaryController {
	public static Double _currentFontSize = 1.8;
	public static Font titleFont;

	public static String path;
	public static File categoriesFolder;

	private static PrimaryController singleton = null;
	
	private WinningsExtractor winExtractor = new WinningsExtractor();
	private CategoryExtractor  catExtractor = new CategoryExtractor();
	
	private QuiNZical app;
	
	private ArrayList<Category> _categories;
	private Category internationalCat; 
	
	private boolean _internationalEnabled = false;
	
	public Category getInternationalCat() {
		return internationalCat;
	}

	public void setInternationalCat(Category internationalCat) {
		this.internationalCat = internationalCat;
	}

	public Controller currentController;
	
	private String winnings;
	
	private PrimaryController() {
		String fullPath = (new File((new File(System.getProperty("java.class.path"))).getAbsolutePath())).getAbsolutePath();
		String [] relevantPath = fullPath.split(System.getProperty("path.separator"));
		path = (new File(relevantPath[0])).getParentFile().getAbsolutePath();
		String categoriesPath = (new File(relevantPath[0])).getParentFile().getParentFile().getAbsolutePath();
		categoriesFolder = new File(categoriesPath + "/categories");
		
		try {
			_categories = catExtractor.getCategories();
			getWinnings();
			titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/QuiNZicalFont.ttf"), 40);
		} catch (Exception e) {	
			e.printStackTrace();
		} 
	}
	
	public void setStageListener() {
		Stage currentStage = app.getStage();
		currentStage.heightProperty().addListener((obs, oldVal, newVal) -> {
			if(currentStage.getScene() != null) {
				currentController.updateText(oldVal, newVal);
			}
		});
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
		if (winnings == null) {
			try {
				winnings = winExtractor.getWinnings();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 		
		return winnings;
	}
	
	public ArrayList<Category> getCategories() {
		return _categories;
	}

	public Category getCategory(int index) {
		return _categories.get(index);
	}
	
	public void addNewScene(FXMLService.FXMLNames fxml) {
		 FXMLService service = new FXMLService();
         service.setFXML(fxml);
		 service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	            @Override 
	            public void handle(WorkerStateEvent t) {
	            	Scene scene = (Scene) t.getSource().getValue();
	            	setScene(scene);
	            }
	     });
		 service.start();
	}

	public void setApp(QuiNZical app) {
		this.app = app;
	}

	public void updateRoot() {
    	Parent root = app.getStage().getScene().getRoot();
		root.setStyle("-fx-font-size: " + PrimaryController._currentFontSize + "em");
	}

	public void setScene(Scene scene) {
		app.setScene(scene);
	}

	public void setLoadScreen() {
		app.setLoadScreen();
	}

	public void enableInternational() {
		_internationalEnabled = true;
	}
	
	public boolean internationalEnabled() {
		return _internationalEnabled;
	}
}

