package application;

import java.io.IOException;

import database.Clue;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import database.Memory_maker;

public class AskingController {
	private Clue _clue;
	private Stage _stage;
	
	private void initalise() {}
	
	public void initData(Stage stage, Clue clue) {
		_stage = stage;
		
		_clue = clue;
		questionField.setText(_clue.showClue());
	}
	
	@FXML 
	public TextField usrAns;
	
	@FXML
	private Label questionField;

	@FXML
	private void handleSubmitButtonAction() {
		if (usrAns.getText() == null || usrAns.getText().trim().isEmpty()) {
		     // Usr has not entered text, do nothing
		} else {
			FXMLLoader loader;
			boolean correct = false;
			String usrAnsStripped = usrAns.getText().strip();
			String actualAns = _clue.showAnswer().strip();
			 
			if (usrAnsStripped.equalsIgnoreCase(actualAns)) {
				correct = true;
				loader = new FXMLLoader(getClass().getResource("incorrectAnswerScene.fxml"));				
			} else {
				loader = new FXMLLoader(getClass().getResource("correctAnswerScene.fxml"));
			}
			
			try {
				runLater(loader);
				System.out.println("Updating");
				//Memory_maker.update(_clue, _clue.showCategory(), correct);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	// Allows user to submit by using the enter key
	@FXML
	private void lookForEnter(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.ENTER) {
			handleSubmitButtonAction();
		}
	}
	
	private void runLater(FXMLLoader loader) throws IOException {
		Scene scene;
		scene = loader.load();			
		Controller controller = loader.getController();
		controller.initData(_stage);
		Platform.runLater(new Runnable() {
            @Override public void run() {
                _stage.setScene(scene);
            }
        });
	}
}