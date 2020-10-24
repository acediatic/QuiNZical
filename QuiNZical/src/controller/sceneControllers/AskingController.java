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
	private Clue _clue;
	@SuppressWarnings("unused")
	private Category _category;
	private boolean _practiceMode;
	private Integer timer = 30;
	private boolean _internationalMode;
	private boolean _timerStarted = false;
	private boolean _submitted = false;
	
	public void initClue(Category category, Clue clue, boolean practiceMode, boolean internationalMode) {		
		_clue = clue;
		_category = category;
		_practiceMode = practiceMode;
		_internationalMode = internationalMode;
		if (_practiceMode) {
			textToggle.setDisable(true);
			}
		showQuestionTextCheck();
		playAudio();
		setupTimer();
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
	
	@FXML 
	private void initialize() {
		addMacronButtons();	
	}
	
	private void setupTimer() {
		timerDot = new Circle(5, Color.LIGHTGREEN);
		
		timerCircle = new Circle(30, Color.GREEN);
		timerCircle.setStroke(Color.WHITE);
		timerCircle.setStrokeWidth(3);
		
		StackPane sp = new StackPane(timerCircle, timerDot);
		sp.setRotate(-90);
		

		if (_practiceMode) {
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
	
	private void stopTimer() {
		timeline.stop();
	}
	// returns true if there is still time in the timer.
	private boolean decrementTimerAndSetText() {
		timer--;
		timeText.setText(Integer.toString(timer));
		return timer > 0;
	}

	private String usrAnsSafety() {
		String usrAns = usrAnsField.getText();
		if (usrAnsField.getText() == null || usrAnsField.getText().trim().isEmpty()) {
		    usrAns = "";
		}
		return usrAns;
	}
	
	@FXML
	private void handleSubmitButtonAction() {
		if (usrAnsSafety().isEmpty()) {
			// do nothing
		} else {
			_submitted = true;
			checkQuestion(usrAnsField.getText());
		}
	}
	
	@FXML 
	private void handleDontKnow() {
		checkQuestion("");
	}
			
	private void checkQuestion(String usrAns) {
		stopTimer();

		AnswerQuestionService service = new AnswerQuestionService();
		service.setAns(usrAns, _clue, _practiceMode, _internationalMode);
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
	            			if (PracticeModuleController.getInstance().currentAttempts < 3) {
	            				speakAnswerResult(true);
	            			}
	            			PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.PRACTICESELECTOR);
		            	} else {
		            		answerAlert.setTitle("INCORRECT!");
		            		answerAlert.setHeaderText("Uh Oh! That wasn't it. Try again!");
		            		if (PracticeModuleController.getInstance().currentAttempts == 2) {
		            			usrAnsField.setPromptText(_clue.showAnswer().substring(0, 1));
		            		} else if(PracticeModuleController.getInstance().currentAttempts >= 3) {
		            			usrAnsField.setPromptText(_clue.showAnswer());
		            			answerAlert.setContentText("Correct Answer: " + _clue.showAnswer());
		            			if(PracticeModuleController.getInstance().currentAttempts == 3) {
		            				speakAnswerResult(false);
		            			}
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
		if (keyEvent.getCode() == KeyCode.ENTER && !_submitted) { //used to ensure user doesn't push enter multiple times
			handleSubmitButtonAction();
		}
	}

	@FXML
	private void playAudio() {
		Thread th = new Thread(new Task<Void>() {
			protected Void call() throws IOException {
				Speaker.question(_clue, speedSlider.getValue());
					return null;
	        }
			protected void succeeded() {
				if(!_practiceMode && !_timerStarted) {
					_timerStarted = true; // prevents bug where pushing the audio button speeds up the timer.
					startTimer();
				}
			}
		});
		th.start(); 
	}
	
	private void speakAnswerResult(boolean correct) {
		Thread th = new Thread(new Task<Void>() {
			protected Void call() throws IOException {
				if (correct) {
					Speaker.correct(_clue, speedSlider.getValue());
				} else {
					Speaker.incorrect(_clue, speedSlider.getValue());
				}
				return null;
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
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTextIndividual() {
		// TODO Auto-generated method stub
		
	}
}