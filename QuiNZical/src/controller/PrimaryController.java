package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import application.QuiNZical;
import audio.Speaker;
import controller.sceneControllers.Controller;
import database.Category;
import database.CategoryExtractor;
import database.Clue;
import database.IncorrectClueExtractor;
import database.ScoreboardExtractor;
import database.User;
import database.WinningsExtractor;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import service.FXMLService;

/**
 * A singleton clsas for the primary controller for the QuiNZical application. 
 * Any calls to model or view are managed by this controller.  
 * @author Adam and Osama
 */
public class PrimaryController {
	public static Double currentFontSize = 1.8;
	public static Font titleFont;

	public static String pathQuiNZical;
	public static File categoriesFolder;

	private static PrimaryController singleton = null;
	
	private WinningsExtractor winExtractor = new WinningsExtractor();
	private CategoryExtractor  catExtractor = new CategoryExtractor();
	
	private QuiNZical app;
  
	private ArrayList<Category> incorrectCategories;
	private ArrayList<User> _scorers;
	private ArrayList<Category> _categories;
  
	private Category internationalCat; 
	private boolean internationalEnabled = false;

	public Controller currentController;
	
	private String winnings;
	
	public CountDownLatch latch = new CountDownLatch(1);
	
	/**
	 * A private primary constructor as it is a singleton class.
	 * Also calculates the appropriate path used by other classes.
	 */
	private PrimaryController() {
		String binPath = (new File(System.getProperty("java.class.path"))).getAbsolutePath().split(File.pathSeparator)[0];
		String fullPath = new File(binPath).getParentFile().getAbsolutePath();
		pathQuiNZical = fullPath;
		categoriesFolder = new File(fullPath + "/categories");
		titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/QuiNZicalFont.ttf"), 40);
	}
	
	/**
	 * Returns the singleton instance of the controller.
	 * It generates one if it doesn't yet exist. 
	 * @return the instance of the primary controller.
	 */
	public static PrimaryController getInstance() {
		if (singleton == null) {
			singleton = new PrimaryController();
		}
		return singleton;
	}
	
	/**
	 * Sets the game up, extracting categories, winnings, 
	 * and seeing if the international section is unlocked. 
	 */
	public void setupGame() {
		try {
			_categories = catExtractor.getCategories();
			getWinnings();
			checkForInternational();
		} catch (Exception e) {	
				e.printStackTrace();
		}
	}
	
	/**
	 * @return if the History directory exists. 
	 */
	public boolean historyExists() {
		return catExtractor.historyExists();
	}
	
	/**
	 * @return the international category instance
	 */
	public Category getInternationalCat() {
		if (internationalCat == null) {
			internationalCat = catExtractor.getInternational();
		}
		return internationalCat;
	}

	/**
	 * Sets the international category gamewide.
	 * @param internationalCat, the category that is the international category
	 */
	public void setInternationalCat(Category internationalCat) {
		this.internationalCat = internationalCat;
	}
	
	/**
	 * A legacy method to determine if the stage size has changed, in order to 
	 * update the text size accordingly. 
	 */
	public void setStageListener() {
		Stage currentStage = app.getStage();
		currentStage.heightProperty().addListener((obs, oldVal, newVal) -> {
			if(currentStage.getScene() != null && this.currentController != null) {
				currentController.updateText(oldVal, newVal);
			}
		});
	} 
	
	
	/**
	 * Sets a clue as being answered in the loaded model, but not the database.
	 * @param clue, to set as answered.
	 */
	public void setAnswered(Clue clue) {
		Thread th = new Thread() {
			@Override
			public void run() {
				try {
					catExtractor.markQuestionAnswered(clue);
					checkForInternational();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		th.start();
	}
	
	/**
	 * Updates the clue in the database
	 * @param clue. The clue to mark as answered. 
	 * @param correct. Whether or not the question was answered correctly.
	 */
	public void update(Clue clue, Boolean correct){
		Thread th = new Thread() {
			@Override
			public void run() {
				try {
					if (correct) {
						winnings = Integer.toString(Integer.parseInt(winnings) + Integer.parseInt(clue.showValue()));
						winExtractor.updateWinningFileCorrect(clue).toString();
					} else {
						IncorrectClueExtractor.addIncorrect(clue);
					}
					catExtractor.markQuestionAnswered(clue);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		th.start();
	}	
	
	/**
	 * reset() utilises to deleteDir to delete the winnings and history files, to remove the users progress and reset the game.
	 * @throws Exception 
	 */
	public void reset()  {
		try {
			winExtractor.resetWinnings();
			catExtractor.resetCategories();
			IncorrectClueExtractor.resetIncorrect();
			winnings = winExtractor.getWinnings();
			_categories = catExtractor.getCategories();
			internationalEnabled = false;
			addNewScene(FXMLService.FXMLNames.HOMESCREEN);
			IncorrectClueExtractor.resetIncorrect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the user's current winnings. Reads from
	 * RAM or otherwise extracts from the database.
	 * @return the users current winnings.
	 */
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
	
	/**
	 * @return all game categories
	 */
	public ArrayList<Category> getCategories() {
		return _categories;
	}

	/**
	 * Returns the category at a specified index
	 * @param index of the clue
	 * @return the category at that index. 
	 */
	public Category getCategory(int index) {
		return _categories.get(index);
	}
	
	/**
	 * @return all categories with incorrectly answered
	 * questions.
	 */
	public ArrayList<Category> getAllIncorrect() {
		return incorrectCategories;
	}
	
	/**
	 * @return the current user scores. 
	 */
	public ArrayList<User> getScoreboard() {
		return _scorers;
	}
	
	/**
	 * Calls the FXML service to set a scene on the main stage.
	 * @param fxml the enum FXML scene to set.
	 */
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

	/**
	 * Sets the JavaFX application of the primary controller.
	 * @param app
	 */
	public void setApp(QuiNZical app) {
		this.app = app;
	}

	/**
	 * Legacy method which updates the size of the root font text, for
	 * text scaling on resize.
	 */
	public void updateRoot() {
    	Parent root = app.getStage().getScene().getRoot();
		root.setStyle("-fx-font-size: " + PrimaryController.currentFontSize + "em");
	}

	/**
	 * Sets the passed scene on the main stage.
	 * @param scene. The scene to be set.
	 */
	public void setScene(Scene scene) {
		app.setScene(scene);
	}

	/**
	 * Activates the load screen, and places it on the main stage.
	 */
	public void setLoadScreen() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				app.setLoadScreen();
			}
			
		});
	}

	/**
	 * Enables the international section in RAM.
	 */
	private void enableInternational() {
		internationalEnabled = true;
	}
	
	/**
	 * Checks if the international section is enabled
	 * @return internationalEnabled, whether the international section is enabled.
	 */
	public boolean internationalEnabled() {
		return internationalEnabled;
	}
	
	/**
	 * Performs a check if the international section has been unlocked
	 */
	private void checkForInternational() {
		if(getNumberCompletedCategories() >= 2) {
			enableInternational();
		}
	}
	
	/**
	 * Determines the number of categories that have currently been completed.
	 * @return the number of completed categories
	 */
	public int getNumberCompletedCategories() {
		int noCompletedCats = 0;
		for (Category c : getCategories()) {
			if(c.allAnswered()) {
				noCompletedCats++;
			}
		}
		return noCompletedCats;
	}
	
	/**
	 * Populates incorrect categories from memory
	 */
	public void setIncorrect () {
		incorrectCategories = (ArrayList<Category>) IncorrectClueExtractor.getIncorrect();
	}
	
	/**
	 * Populates scoreboard values from memory.
	 */
	public void setScoreboard () {
		_scorers = (ArrayList<User>) ScoreboardExtractor.extractScoreBoard();
	}

	/**
	 * Kills any audio currently playing by running a new thread.
	 */
	public void stopAudio() {
		Thread th = new Thread() {
			@Override
			public void run() {
				Speaker.cleanUp();
			}
		};
		th.start();
	}
	
	/**
	 * Sets the app image in the task bar.
	 */
	public void setImage() {
		Thread th = new Thread() {
			@Override 
			public void run() {
				String iconUrlStr = getClass().getResource("/application/css/appIcon/quiNZical-Icon.png").toString();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						app.getStage().getIcons().add(new Image(iconUrlStr));
					}
				});
			}
		};
		th.start();
	}
	
	/**
	 * Extracts the current master categories (not including international)
	 * @return the master categories
	 */
	public ArrayList<Category> getMasterCategories() {
		ArrayList<Category> cats = new ArrayList<Category>();
		
		try {
			cats = catExtractor.getNZMasterCategories();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cats;
	}

	/**
	 * Sets gameCategories to be the game's categories, which are stored
	 * into memory.
	 * @param gameCategories, the game categories for this game.
	 */
	public void setCategories(ArrayList<Category> gameCategories) {
		catExtractor.setCategories(gameCategories);
	}
	
	/**
	 * Sets up the game with random categories.
	 */
	public void setRandomCategories() {
		catExtractor.setRandomCategories();
	}
}
