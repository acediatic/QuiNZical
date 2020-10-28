package controller.sceneControllers;


import controller.PrimaryController;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import service.FXMLService;

/**
 * The controller for the international section
 * @author Adam and Osama
 *
 */
public class InternationalAnswerController extends Controller {
	
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
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.HOMESCREEN);
	}
	
	/**
	 * Loads the question Board
	 */
	@FXML 
	private void goQuestionBoard() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.INTERNATIONALBOARD);	
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
}