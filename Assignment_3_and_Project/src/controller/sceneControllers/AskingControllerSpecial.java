package controller.sceneControllers;

import java.io.IOException;
import java.util.Optional;

import controller.PracticeModuleController;
import controller.PrimaryController;
import database.Category;
import database.Clue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import service.AnswerQuestionService;
import service.FXMLService;

public class AskingControllerSpecial extends Controller {
	private Clue _clue;
	private Category _category;
	private boolean practiceMode;
	
	private void initalize() {}
	
	public void initClue(Category category, Clue clue) {		
		_clue = clue;
		_category = category;
		practiceMode = true;
		if (practiceMode) {
			questionField.setText(clue.showClue());
		} else {
			questionField.setText("Please listen for your hint");
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
			checkQuestion(usrAns.getText());
		}
	}
	
	@FXML 
	private void handleDontKnow() {
		checkQuestion("");
	}
			
	private void checkQuestion(String usrAnsStr) {
		AnswerQuestionService service = new AnswerQuestionService();
		service.setAns(usrAnsStr, _clue.showAnswer());
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

	            @Override 
	            public void handle(WorkerStateEvent t) {
	            	if(!practiceMode) { 
		            	try {
		            		Thread th = new Thread(new Task<Void>() {
		        	        	protected Void call() throws IOException {
		        	        		try {
		        	        			PrimaryController.getInstance().update(_clue, (boolean) t.getSource().getValue());
		        					} catch (Exception e) {
		        						// TODO Auto-generated catch block
		        						e.printStackTrace();
		        					}
		        					return null;
		        	            }
		                	});
		                    th.start();
						} catch (Exception e) {
							e.printStackTrace();
						}
	            	} else {
	            		Alert resetAlert = new Alert(AlertType.INFORMATION);
	            		
	            		if ((boolean) t.getSource().getValue()) {
	            			resetAlert.setTitle("CORRECT!");
	            			resetAlert.setHeaderText("Congratulations! That's right!");
	            			PrimaryController.app.addNewScene(FXMLService.FXMLNames.PRACTICESELECTOR);
		            	} else {
		            		resetAlert.setTitle("INCORRECT!");
		            		resetAlert.setHeaderText("Uh Oh! That wasn't it. Try again!");
		            		if (PracticeModuleController.getInstance().currentAttempts++ == 2) {
		            			usrAns.setPromptText(_clue.showAnswer().substring(0, 1));
		            		} else if(PracticeModuleController.getInstance().currentAttempts <= 3) {
		            			usrAns.setPromptText(_clue.showAnswer());
		            		}
	            		resetAlert.showAndWait();
		            	}
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