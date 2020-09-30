package application;

import static java.lang.Math.pow;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class QuestionBoardController implements Controller {
	private Stage _stage;
	private double _currentFontSize = 1.8;
	
	private void initalise() {	}
	
	public void initData(Stage stage) {
		_stage = stage;
	}
	
	@FXML
	private void continueButtonAction() {

		
		
		Platform.runLater(new Runnable() {
            @Override public void run() {
                tempTester.setScene(homeScene);
                
            }
        });
	}

	public void updateText(Stage stage, Number oldVal, Number newVal) {
		Double newVald = newVal.doubleValue();
		Double oldVald = oldVal.doubleValue();
		
		if(!oldVald.isNaN() && !newVald.isNaN()) {
			double ratio = newVal.doubleValue() / oldVal.doubleValue();
			_currentFontSize = _currentFontSize * ratio;
			
			GridPane gp = (GridPane) stage.getScene().getRoot();
			
			gp.setStyle("-fx-font-size: " + _currentFontSize + "em; -fx-padding: "+ _currentFontSize*10);
			gp.setVgap(pow(2,_currentFontSize));
			gp.setHgap(pow(2,_currentFontSize));
		}
	}
	
}