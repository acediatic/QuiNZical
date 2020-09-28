package application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class QuestionBoardController implements Controller {
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

	// Allows user to submit by using the enter key
	@FXML
	private void buttonHover() {
		
	}
}