package application;

import java.io.File;

import database.ModuleModel;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameMainController {
	public static Double _currentFontSize = 1.8;
	public static QuiNZical app;
	
	public static Controller currentController;
	public static String path;
	private static ModuleModel _data;
	

	public static ModuleModel getData() {
		return _data;
	}
	
	private static GameMainController singleton = null;
	
	private GameMainController() {
		String fullPath = (new File((new File(System.getProperty("java.class.path"))).getAbsolutePath())).getAbsolutePath();
		String [] relevantPath = fullPath.split(System.getProperty("path.separator"));
		path = (new File(relevantPath[0])).getParentFile().getAbsolutePath();
		ModuleModel.getInstance();
	}
	
	public static GameMainController getInstance() {
		if (singleton == null) {
			singleton = new GameMainController();
		}
		return singleton;
	}
	
}
