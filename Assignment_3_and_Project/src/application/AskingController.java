package application;

import database.Clue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import database.Memory_maker;

public class AskingController {
	private Clue _clue;
	
	private void initalise() {}
	
	public void initData(Clue clue) {
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
			boolean correct = false;
			String usrAnsStripped = usrAns.getText().strip();
			String actualAns = _clue.showAnswer().strip();
			
			if (usrAnsStripped.equalsIgnoreCase(actualAns)) {
				correct = true;
				// load correct scene
				System.out.println("loading correct scene");
			} else {
				//load incorrect scene
				System.out.println("loading incorrect scene");
			}
			
			try {
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
}