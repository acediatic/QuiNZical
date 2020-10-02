package application;

import java.io.IOException;

import database.Clue;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import database.Memory_maker;

public class AskingController implements Controller {
	private Clue _clue;
	private Stage _stage;
	
	private void initalize() {}
	
	public void initClue(Clue clue) {		
		_clue = clue;
		questionField.setText(_clue.showClue());
	}
	
	public void updateText(Number oldVal, Number newVal) {
		Double newVald = newVal.doubleValue();
		Double oldVald = oldVal.doubleValue();
		
		if(!oldVald.isNaN() && !newVald.isNaN()) {
			double ratio = newVal.doubleValue() / oldVal.doubleValue();
			GameMainController._currentFontSize = GameMainController._currentFontSize * ratio;
			
			Node node = GameMainController.app.getStage().getScene().getRoot();
			
			node.setStyle("-fx-font-size: " + GameMainController._currentFontSize + "em");
		}
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
			String usrAnsStripped = usrAns.getText().strip();
			String actualAns = _clue.showAnswer().strip();
			boolean correct = false;
			if (usrAnsStripped.equalsIgnoreCase(actualAns)) {
				correct = true;
				loader = new FXMLLoader(getClass().getResource("incorrectAnswerScene.fxml"));				
			} else {
				loader = new FXMLLoader(getClass().getResource("correctAnswerScene.fxml"));
			}
			
			try {
				runLater(loader);
				System.out.println("Updating");
				Memory_maker.update(_clue, _clue.getCategory(), correct);
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
		Platform.runLater(new Runnable() {
            @Override public void run() {
                _stage.setScene(scene);
            }
        });
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}