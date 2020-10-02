package application;

import database.ModuleModel;
import javafx.stage.Stage;

public class GameMainController {

	public static Double _currentFontSize = 1.8;
	private Controller _currentController;
	private static ModuleModel _data = ModuleModel.getInstance();
	private static Stage _currentStage;
	
	/**
	 * getStage returns the stage to other classes, to allow them to set the stage.
	 * @return currentStage
	 */
	public static Stage getStage() {
		_currentStage.setTitle("QuiNZical");
        return _currentStage;
    }
	
	public static ModuleModel getData() {
		return _data;
	}
	
	private static GameMainController singleton = null;
	
	private GameMainController() {}
	
	public static GameMainController getInstance() {
		if (singleton == null) {
			singleton = new GameMainController();
			
			return singleton;
		}
	}
}
