package controller.sceneControllers;

import java.io.IOException;

import controller.PrimaryController;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import service.FXMLService;

/**
 * The controller for the hamburger drawer on the homescreen
 * @author Adam and Osama
 *
 */
public class DrawerController extends Controller {
	@FXML
	private VBox homeDrawer;
	
	/**
	 * Begins the game by showing the questionboard for NZ
	 */
	@FXML
	private void startGame() {	
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.QUESTIONBOARD);	
	}
	
	/**
	 * Loads the user's score screen
	 */
	@FXML
	private void viewScore() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.WINNINGS);	
	}
	
	/**
	 * Loads the practice module
	 */
	@FXML
	private void practice() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.PRACTICESELECTOR);	
	}

	/**
	 * Loads the leaderboard
	 */
	@FXML
	private void leaderboard() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.LEADERBOARD);
	}
	
	/**
	 * Resets the game back to it's initial state. 
	 * Confirms that the user does indeed want to do this before proceeding.
	 */
	@FXML
	public void reset() {
		HomeController hmCtrl = (HomeController) PrimaryController.getInstance().currentController;
		hmCtrl.closeMenu();
		Alert resetAlert = new Alert(AlertType.CONFIRMATION);
		resetAlert.setTitle("WARNING!");
		resetAlert.setHeaderText("Reset?");
		resetAlert.setContentText("All progress to this point will be reset.");
		
		resetAlert.showAndWait().ifPresent(response -> {
		    if (response == ButtonType.OK) {
		    	Thread th = new Thread(new Task<Void>() {
		        	protected Void call() throws IOException {
		        		try {
							PrimaryController.getInstance().reset();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
		            }
	        	});
	            th.start();
		    }
		});
	}

	/**
	 * An usued utility method from the superclass
	 */
	public void init() {}

	/**
	 * An usued utility method from the superclass
	 */
	@Override
	public void updateTextIndividual() {}
	
}