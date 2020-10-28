package controller.sceneControllers;

import controller.PrimaryController;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import service.FXMLService;
/**
 * The controller for the international reward section
 * @author Adam and Osama
 */
public class InternationalRewardController extends Controller {
	@FXML
	private Text allDoneTxt;
	
	/**
	 * Updates the text to tell the user they're done, set in th
	 * colour and font of the game.
	 */
	@FXML
	private void initialize() {	
		allDoneTxt.setFont(PrimaryController.titleFont);
		allDoneTxt.setFill(Color.WHITE);
		allDoneTxt.setWrappingWidth(500);
	}
	
	/**
	 * Takes the user back to the home screen.
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

