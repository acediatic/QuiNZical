package application;

import controller.Controller;
import database.Category;
import database.Clue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import service.AnswerQuestionService;
import database.Memory_maker;

public class AskingController extends Controller {
	private Clue _clue;
	private Category _category;
	
	private void initalize() {}
	
	public void initClue(Category category, Clue clue) {		
		_clue = clue;
		_category = category;
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
			checkQuestion(usrAns.getText());
		}
	}
	
	@FXML 
	private void handleDontKnow() {
		checkQuestion("");
	}
			
	private void checkQuestion(String usrAns) {
		AnswerQuestionService service = new AnswerQuestionService();
		service.setAns(usrAns, _clue.showAnswer());
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

	            @Override 
	            public void handle(WorkerStateEvent t) {
	            	try {
						Memory_maker.update(_clue, _category, (boolean) t.getSource().getValue());
					} catch (Exception e) {
						e.printStackTrace();
					}
	            }
	        });
		 
		 service.start();
		}

	// Allows user to submit by using the enter key
	@FXML
	private void lookForEnter(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.ENTER) {
			handleSubmitButtonAction();
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTextIndividual() {
		// TODO Auto-generated method stub
		
	}
}