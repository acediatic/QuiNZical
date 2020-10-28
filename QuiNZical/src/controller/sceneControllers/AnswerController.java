package controller.sceneControllers;

import controller.PrimaryController;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import service.FXMLService;

/**
 * Controller for the Answer scenes (Correct/incorrect)
 * @author Adam and Osama
 */
public class AnswerController extends Controller {
	
	@FXML
	private Text winningsMsg;
	
	/**
	 * Fetches the user's winnings and displays on screen.
	 */
	@FXML
	private void initialize() {
		winningsMsg.setText("$" + PrimaryController.getInstance().getWinnings());
	}
	
	/**
	 * Loads the home screen
	 */
	@FXML
	private void goHome() {
		killAudio();
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.HOMESCREEN);
	}
	
	/**
	 * Loads the question Board
	 */
	@FXML 
	private void goQuestionBoard() {
		killAudio();
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.QUESTIONBOARD);	
	}	

	/**
	 * Unused method inherited from abstract class
	 */
	@Override
	public void init() {}

	/**
	 * Unused method inherited from abstract class
	 */
	@Override
	public void updateTextIndividual() {}
	
	/**
	 * Kills any audio playing.
	 */
	private void killAudio() {
		PrimaryController.getInstance().stopAudio();
	}
}