package application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class IncorrectAnswerController implements Controller {
	private Stage _stage;
	
	private void initalise() {}
	
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
	
	public Double updateText(Stage stage, Double currentFontSize, Number oldVal, Number newVal) {
		Double newVald = newVal.doubleValue();
		Double oldVald = oldVal.doubleValue();
		
		if(!oldVald.isNaN() && !newVald.isNaN()) {
			double ratio = newVal.doubleValue() / oldVal.doubleValue();
			currentFontSize = currentFontSize * ratio;
			
			GridPane gp = (GridPane) stage.getScene().getRoot();
			
			gp.setStyle("-fx-font-size: " + currentFontSize + "em");
			
			return currentFontSize;
		}
	}

	// Allows user to submit by using the enter key
	@FXML
	private void lookForEnter(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.ENTER) {
			continueButtonAction();
		}
	}
}