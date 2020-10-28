 package controller.sceneControllers;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXToggleButton;

import audio.Speaker;
import controller.PracticeModuleController;
import controller.PrimaryController;
import database.Category;
import database.Clue;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import service.AnswerQuestionService;
import service.FXMLService;
import javafx.util.Duration;

public class AskingController extends Controller {
	private Clue clue;
	private boolean practiceMode;
	private Integer timer = 30;
	private boolean internationalMode;
	private boolean timerStarted = false;
	private boolean submitted = false;
	
	@FXML 
	public TextField usrAnsField;
	
	@FXML
	private Label questionField;
	
	@FXML
	private Label questionType;
	
	@FXML
	private JFXToggleButton textToggle;
	
	@FXML
	private Slider speedSlider;
	
	@FXML
	private JFXNodesList macrons;
	
	@FXML
	private StackPane stack;
	
	private Text timeText = new Text(Integer.toString(timer));
	private Circle timerCircle;
	private Circle timerDot;
	private Timeline timeline = new Timeline();
	private PathTransition path;
	
	private JFXNodesList macronsLower = new JFXNodesList();
	private JFXNodesList macronsUpper = new JFXNodesList();
	

	private String[] lowerVowels = {"ā", "ē", "ī", "ō", "ū"};
	private String[] upperVowels = {"Ā", "Ē", "Ī", "Ō", "Ū"};
	
	/**
	 * Adds macrons once the FXML has loaded
	 */
	@FXML 
	private void initialize() {
		addMacronButtons();	
	}
	
	/**
	 * Initialises the clue to be asked. 
	 * @param category, the category the clue comes from
	 * @param clue, the clue to be asked
	 * @param practiceMode, if it is a practice mode
	 * @param internationalMode, if it is the international mode.
	 */
	public void initClue(Category inputCategory, Clue inputClue, boolean inputPracticeMode, boolean inputInternationalMode) {		
		clue = inputClue;
		practiceMode = inputPracticeMode;
		internationalMode = inputInternationalMode;
		if (practiceMode) {
			textToggle.setDisable(true);
			}
		questionType.setText(inputClue.showClueType());
		showQuestionTextCheck();
		playAudio();
		setupTimer();
		PrimaryController.getInstance().setAnswered(clue);
	}
	
	/**
	 * Sets up the on screen timer, with the default value of 30s.
	 */
	private void setupTimer() {
		timerDot = new Circle(5, Color.LIGHTGREEN);
		
		timerCircle = new Circle(30, Color.GREEN);
		timerCircle.setStroke(Color.WHITE);
		timerCircle.setStrokeWidth(3);
		
		StackPane sp = new StackPane(timerCircle, timerDot);
		sp.setRotate(-90);
		

		if (practiceMode) {
			timeText.setText("!");
			timerDot.setVisible(false);
		}
		
		timeText.setBoundsType(TextBoundsType.VISUAL);
		timeText.setFont(PrimaryController.titleFont);
		timeText.setFill(Color.WHITE);
		
		path = new PathTransition(Duration.millis(1000), timerCircle, timerDot);
		path.setCycleCount(timer);
		path.play();
		path.pause(); // Added due to bug in JavaFX
		 
		stack.getChildren().addAll(sp, timeText);
	}
	
	/**
	 * Begins the timer. Called once the question has finished being read aloud
	 */
	private void startTimer() {
		        timeline.setCycleCount(Timeline.INDEFINITE);
		        timeline.getKeyFrames().add(
		                new KeyFrame(Duration.seconds(1),
		                  new EventHandler<ActionEvent>() {
		                    // KeyFrame event handler
		                    @Override
		                	public void handle(ActionEvent event) {
		                    	boolean timerFinished = !decrementTimerAndSetText();
		                        if (timerFinished) {
		                            timeline.stop();
		                            checkQuestion(usrAnsSafety());
		                        }
		                      }
		                }));
		        
				path.play();
				timeline.playFromStart();
	}
	
	/**
	 * Stops the timer.
	 */
	private void stopTimer() {
		timeline.stop();
	}

	/**
	 * Decrements the value on the timer and updates the
	 * timer text accordingly.
	 * @return true if there is still time in the timer
	 */
	private boolean decrementTimerAndSetText() {
		timer--;
		timeText.setText(Integer.toString(timer));
		return timer > 0;
	}

	/**
	 * Checks that the user has indeed submitted an answer
	 * to the question, and replaces it with an empty string if not
	 * @return the usr answer, either an empty string or thier answer.
	 */
	private String usrAnsSafety() {
		String usrAns = usrAnsField.getText();
		if (usrAnsField.getText() == null || usrAnsField.getText().trim().isEmpty()) {
		    usrAns = "";
		}
		return usrAns;
	}
	
	/**
	 * Handles the submit button. Checks that the
	 * user has added text, does nothing if not.
	 */
	@FXML
	private void handleSubmitButtonAction() {
		if (usrAnsSafety().isEmpty()) {
			// do nothing
		} else {
			submitted = true;
			checkQuestion(usrAnsField.getText());
		}
	}

	// Allows user to submit by using the enter key
	@FXML
	private void lookForEnter(KeyEvent keyEvent) {
		if (keyEvent.getCode() == KeyCode.ENTER && !submitted) { //used to ensure user doesn't push enter multiple times
			handleSubmitButtonAction();
		}
	}
	
	/**
	 * Called by the dont know button to
	 * allow the user to skip the question
	 */
	@FXML 
	private void handleDontKnow() {
		checkQuestion("");
	}
			
	/**
	 * Calls the answer question service to check the user's
	 * answer
	 * @param usrAns, the string of the user's answer.
	 */
	private void checkQuestion(String usrAns) {
		stopTimer();

		AnswerQuestionService service = new AnswerQuestionService();
		service.setAns(usrAns, clue, practiceMode, internationalMode);
		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

	            @Override 
	            public void handle(WorkerStateEvent t) {
	            	if(!practiceMode) { 
		            	try {
		            		PrimaryController.getInstance().update(clue, (boolean) t.getSource().getValue());
		            		speakAnswerResult((boolean) t.getSource().getValue());
		        		} catch (Exception e) {
							e.printStackTrace();
						}
	            	} else {
	            		PracticeModuleController.getInstance().currentAttempts++; // increases the number of attempts the user has had
	            		Alert answerAlert = new Alert(AlertType.INFORMATION);
	            		answerAlert.setContentText("You have had " + PracticeModuleController.getInstance().currentAttempts + " attempt(s)");
	            		usrAnsField.clear();
	            		
	            		if ((boolean) t.getSource().getValue()) {
	            			answerAlert.setTitle("CORRECT!");
	            			answerAlert.setHeaderText("Congratulations! That's right!");
	            			answerAlert.showAndWait().ifPresent(response -> {
		            		    if (response == ButtonType.OK) {
		            		    	PrimaryController.getInstance().stopAudio();
		            		    }
		            		});
	            			if (PracticeModuleController.getInstance().currentAttempts < 3) {
	            				speakAnswerResult(true);
	            			}
	            			PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.PRACTICESELECTOR);
		            	} else {
		            		answerAlert.setTitle("INCORRECT!");
		            		answerAlert.setHeaderText("Uh Oh! That wasn't it. Try again!");
		            		if (PracticeModuleController.getInstance().currentAttempts == 2) {
		            			usrAnsField.setPromptText(clue.showAnswer().substring(0, 1));
		            		} else if(PracticeModuleController.getInstance().currentAttempts >= 3) { // shows the user the answer after 3 attempts
		            			usrAnsField.setPromptText(clue.showAnswer());
		            			answerAlert.setContentText("Correct Answer: " + clue.showAnswer());
		            			if(PracticeModuleController.getInstance().currentAttempts == 3) {
		            				speakAnswerResult(false);
		            			}
		            		} 
		            		answerAlert.showAndWait().ifPresent(response -> {
		            		    if (response == ButtonType.OK) {
		            		    	PrimaryController.getInstance().stopAudio();
		            		    }
		            		});
		            	}
	            	}
	            }
		});
		service.start();
		}


	/**
	 * Plays the question audio, and starts the timer upon completion
	 */
	@FXML
	private void playAudio() {
		Thread th = new Thread(new Task<Void>() {
			protected Void call() throws IOException {
				Speaker.question(clue, speedSlider.getValue());
					return null;
	        }
			protected void succeeded() {
				if(!practiceMode && !timerStarted) {
					timerStarted = true; // prevents bug where pushing the audio button speeds up the timer.
					startTimer();
				}
			}
		});
		th.start(); 
	}
	
	/**
	 * Speaks the result of the user's attempt (correct or incorrect)
	 * @param correct, whether or not the user was correct.
	 */
	private void speakAnswerResult(boolean correct) {
		Thread th = new Thread(new Task<Void>() {
			protected Void call() throws IOException {
				if (correct) {
					Speaker.correct(clue, speedSlider.getValue());
				} else {
					Speaker.incorrect(clue, speedSlider.getValue());
				}
				return null;
	        }
		});
		th.start();

	}
	
	/**
	 * Toggles whether the clue text should be shown.
	 */
	@FXML 
	private void showQuestionTextCheck() {
		if (textToggle.isSelected() || practiceMode) {
			questionField.setText(clue.showClue());
		} else {
			questionField.setText("Please listen for your hint.");
		}
	}

	/**
	 * Adds the macron buttons to screen, allowing the user to use these in their answer
	 */
	private void addMacronButtons() {
		JFXButton macronTitleButton = new JFXButton("Ā/ā");
		macronTitleButton.setButtonType(JFXButton.ButtonType.RAISED);
		macronTitleButton.getStyleClass().addAll("animated-option-button");
		macrons.addAnimatedNode(macronTitleButton);
		
		JFXButton macronLowerButton = new JFXButton("ā");
		macronLowerButton.setButtonType(JFXButton.ButtonType.RAISED);
		macronLowerButton.getStyleClass().addAll("animated-option-button", "animated-option-sub-button");
		macronsLower.addAnimatedNode(macronLowerButton);

		JFXButton macronUpperButton = new JFXButton("Ā");
		macronUpperButton.setButtonType(JFXButton.ButtonType.RAISED);
		macronUpperButton.getStyleClass().addAll("animated-option-button", "animated-option-sub-button");
		macronsUpper.addAnimatedNode(macronUpperButton);
		
			List<String[]> vowelLists = Arrays.asList(lowerVowels, upperVowels);
			List<JFXNodesList> nodeLists = Arrays.asList(macronsLower, macronsUpper);
			for (int i = 0; i<2; i++) {
				for (String c : vowelLists.get(i)) {
					JFXButton macronButton = new JFXButton(c);
					macronButton.setButtonType(JFXButton.ButtonType.RAISED);
					macronButton.getStyleClass().addAll("animated-option-button", "animated-option-sub-button-2");
					
					macronButton.setOnAction(new EventHandler<ActionEvent>() {
						@Override public void handle(ActionEvent e) {
							usrAnsField.setText(usrAnsField.getText() + macronButton.getText());
						}
					});
					nodeLists.get(i).addAnimatedNode(macronButton);
				}
			}
			
			for(JFXNodesList nodeList : nodeLists) {
				nodeList.getStyleClass().addAll("animated-option-button", "animated-option-sub-button");
				nodeList.setRotate(-90); // right
				nodeList.setSpacing(10d);
				macrons.addAnimatedNode(nodeList);
			}
		
		macrons.setRotate(180); // up
		macrons.setSpacing(10d);
	}
	
	
	/**
	 * Unused helper method inherited from the super class.
	 */
	@Override
	public void init() {}

	/**
	 * Unused helper method inherited from the super class.
	 */
	@Override
	public void updateTextIndividual() {}
}