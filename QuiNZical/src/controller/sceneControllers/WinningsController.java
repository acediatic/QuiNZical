package controller.sceneControllers;

import controller.PrimaryController;
import service.FXMLService;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * The controller for the Winnings screen
 * showing the user their current winnings.
 * @author Adam and Osama
 *
 */
public class WinningsController extends Controller {
	
	@FXML 
	private Text usrWinnings;
	
	@FXML
	private Text yourWinningsTxt;
	
	/**
	 * Sets up the winnings, fetching the current winnings
	 * and setting the style.
	 */
	@FXML
	private void initialize() {	
		usrWinnings.setFont(PrimaryController.titleFont);
		usrWinnings.setText("$" + PrimaryController.getInstance().getWinnings());
		usrWinnings.setFill(Color.GOLDENROD);
		yourWinningsTxt.setFont(PrimaryController.titleFont);
		yourWinningsTxt.setFill(Color.WHITE);
	}
	
	/**
	 * Takes the user back to the homescreen
	 */
	@FXML
	private void back() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.HOMESCREEN);
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