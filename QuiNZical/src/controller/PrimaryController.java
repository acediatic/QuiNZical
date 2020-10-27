package controller;

import java.io.File;
import java.util.ArrayList;

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

public class PrimaryController {
	public static Double _currentFontSize = 1.8;
	public static Font titleFont;

	public static String pathQuiNZical;
	public static File categoriesFolder;

	private static PrimaryController singleton = null;
	
	private WinningsExtractor winExtractor = new WinningsExtractor();
	private CategoryExtractor  catExtractor = new CategoryExtractor();
	
	private QuiNZical app;
  
	private ArrayList<Category> _incorrectCategories;
	private ArrayList<User> _scorers;
	private ArrayList<Category> _categories;
  
	private Category internationalCat; 
	private boolean _internationalEnabled = false;

	public Controller currentController;
	
	private String winnings;
	
	private PrimaryController() {
		String binPath = (new File(System.getProperty("java.class.path"))).getAbsolutePath().split(File.pathSeparator)[0];
		String fullPath = new File(binPath).getParentFile().getAbsolutePath();
		System.out.println(fullPath);
		pathQuiNZical = fullPath;
		categoriesFolder = new File(fullPath + "/categories");

		try {
			_categories = catExtractor.getCategories();
			getWinnings();
			checkForInternational();
			titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/QuiNZicalFont.ttf"), 40);
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}
	
	public Category getInternationalCat() {
		if (internationalCat == null) {
			internationalCat = catExtractor.getInternational();
		}
		return internationalCat;
	}

	public void setInternationalCat(Category internationalCat) {
		this.internationalCat = internationalCat;
	}
	
	public void setStageListener() {
		Stage currentStage = app.getStage();
		currentStage.heightProperty().addListener((obs, oldVal, newVal) -> {
			if(currentStage.getScene() != null && this.currentController != null) {
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
	
	public void update(Clue clue, Boolean correct){
		Thread th = new Thread() {
			@Override
			public void run() {
				try {
					if (correct) {
						winnings = Integer.toString(Integer.parseInt(winnings) + Integer.parseInt(clue.showValue()));
						winExtractor.updateWinningFile(clue).toString();
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
			_internationalEnabled = false;
			addNewScene(FXMLService.FXMLNames.HOMESCREEN);
			IncorrectClueExtractor.resetIncorrect();
		} catch (Exception e) {
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
	
	public ArrayList<Category> getAllIncorrect() {
		return _incorrectCategories;
	}
	
	public ArrayList<User> getScoreboard() {
		return _scorers;
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
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				app.setLoadScreen();
			}
			
		});
	}

	private void enableInternational() {
		_internationalEnabled = true;
	}
	
	public boolean internationalEnabled() {
		return _internationalEnabled;
	}
	
	private void checkForInternational() {
		if(getNumberCompletedCategories() >= 2) {
			enableInternational();
		}
	}
	
	public int getNumberCompletedCategories() {
		int noCompletedCats = 0;
		for (Category c : getCategories()) {
			if(c.allAnswered()) {
				noCompletedCats++;
			}
		}
		return noCompletedCats;
	}
	
	public void setIncorrect () {
		_incorrectCategories = (ArrayList<Category>) IncorrectClueExtractor.getIncorrect();
	}
	
	public void setScoreboard () {
		_scorers = (ArrayList<User>) ScoreboardExtractor.extractScoreBoard();
	}

	public void stopAudio() {
		Thread th = new Thread() {
			@Override
			public void run() {
				Speaker.cleanUp();
			}
		};
		th.start();
	}
	
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
}

