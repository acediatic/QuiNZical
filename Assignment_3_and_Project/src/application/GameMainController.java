package application;

import java.io.File;
import java.io.IOException;

import controller.Controller;
import database.Model;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameMainController {
	public static Double _currentFontSize = 1.8;
	public static QuiNZical app;
	
	public static Controller currentController;
	public static String path;
	private static Model _model;
	public static Font titleFont;
	

	public static Model getModel() {
		return _model;
	}
	
	private static GameMainController singleton = null;
	
	private GameMainController() {
		String fullPath = (new File((new File(System.getProperty("java.class.path"))).getAbsolutePath())).getAbsolutePath();
		String [] relevantPath = fullPath.split(System.getProperty("path.separator"));
		path = (new File(relevantPath[0])).getParentFile().getAbsolutePath();
		_model = Model.getInstance();
		
		
		try {
			titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/titleFont.ttf"), 40);
		} catch (Exception e) {	}
	}
	
	public static GameMainController getInstance() {
		if (singleton == null) {
			singleton = new GameMainController();
		}
		return singleton;
	}
}

