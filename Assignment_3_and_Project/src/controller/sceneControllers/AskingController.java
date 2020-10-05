package controller.sceneControllers;

import java.io.IOException;

import com.jfoenix.controls.JFXToggleButton;

import audio.Speaker;
import controller.PracticeModuleController;
import controller.PrimaryController;
import database.Category;
import database.Clue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import service.AnswerQuestionService;
import service.FXMLService;

public class AskingController extends Controller {
	private Clue _clue;
	private boolean audioFinished = true;
	@SuppressWarnings("unused")
	private Category _category;
	private boolean _practiceMode;
	
	public void initClue(Category category, Clue clue, boolean practiceMode) {		
		_clue = clue;
		_category = category;
		_practiceMode = practiceMode;	
		if (_practiceMode) {
			textToggle.setDisable(true);
			}
		showQuestionTextCheck();
		playAudio();
	}
	
	@FXML 
	public TextField usrAnsField;
	
	@FXML
	private Label questionField;
	
	@FXML
	private JFXToggleButton textToggle;
	
	@FXML
	private Slider speedSlider;

	@FXML
	private void handleSubmitButtonAction() {
		if (usrAnsField.getText() == null || usrAnsField.getText().trim().isEmpty()) {
		     // Usr has not entered text, do nothing
		} else {
			checkQuestion(usrAnsField.getText());
		}
	}
	
	@FXML 
	private void handleDontKnow() {
		checkQuestion("");
	}
			
	private void checkQuestion(String usrAns) {
		AnswerQuestionService service = new AnswerQuestionService();
		service.setAns(usrAns, _clue.showAnswer(), _practiceMode);
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

	            @Override 
	            public void handle(WorkerStateEvent t) {
	            	if(!_practiceMode) { 
		            	try {
		            		PrimaryController.getInstance().update(_clue, (boolean) t.getSource().getValue());
		            		speakAnswerResult((boolean) t.getSource().getValue());
		        		} catch (Exception e) {
							e.printStackTrace();
						}
	            	} else {
	            		PracticeModuleController.getInstance().currentAttempts++;
	            		Alert answerAlert = new Alert(AlertType.INFORMATION);
	            		answerAlert.setContentText("You have had " + PracticeModuleController.getInstance().currentAttempts + " attempt(s)");
	            		usrAnsField.clear();
	            		
	            		if ((boolean) t.getSource().getValue()) {
	            			answerAlert.setTitle("CORRECT!");
	            			answerAlert.setHeaderText("Congratulations! That's right!");
	            			answerAlert.showAndWait();
	            			speakAnswerResult(true);
	            			PrimaryController.app.addNewScene(FXMLService.FXMLNames.PRACTICESELECTOR);
		            	} else {
		            		answerAlert.setTitle("INCORRECT!");
		            		answerAlert.setHeaderText("Uh Oh! That wasn't it. Try again!");
		            		if (PracticeModuleController.getInstance().currentAttempts == 2) {
		            			usrAnsField.setPromptText(_clue.showAnswer().substring(0, 1));
		            		} else if(PracticeModuleController.getInstance().currentAttempts >= 3) {
		            			usrAnsField.setPromptText(_clue.showAnswer());
		            			speakAnswerResult(false);
		            		}
		            		answerAlert.showAndWait();
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

	@FXML
	private void playAudio() {
		if(audioFinished) {
			audioFinished = false;
			Thread th = new Thread(new Task<Void>() {
				protected Void call() throws IOException {
					Speaker.questionWithNZVoice(_clue, speedSlider.getValue());
						return null;
		        }
				protected void succeeded() {
					audioFinished = true;
				}
			});
			th.start();
		}
	}
	
	private void speakAnswerResult(boolean correct) {
		Thread th = new Thread(new Task<Void>() {
			protected Void call() throws IOException {
				audioFinished = false;
				if (correct) {
					Speaker.correctWithNZVoice(_clue, speedSlider.getValue());
				} else {
					Speaker.incorrectWithNZVoice(_clue, speedSlider.getValue());
				}
				return null;
	        }
			protected void succeeded() {
				audioFinished = true;
			}
		});
		th.start();

	}
	
	@FXML 
	private void showQuestionTextCheck() {
		if (textToggle.isSelected() || _practiceMode) {
			questionField.setText(_clue.showClue());
		} else {
			questionField.setText("Please listen for your hint.");
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