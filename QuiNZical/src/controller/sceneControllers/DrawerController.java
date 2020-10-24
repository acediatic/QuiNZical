package controller.sceneControllers;

import java.io.IOException;

import controller.PrimaryController;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import service.FXMLService;

public class DrawerController extends Controller {
	@FXML
	private VBox homeDrawer;
	
	@FXML
	private void startGame(ActionEvent e) {	
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.QUESTIONBOARD);	
	}
	
	@FXML
	private void viewScore() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.WINNINGS);	
	}
	
	@FXML
	private void practice() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.PRACTICESELECTOR);	
	}

	@FXML
	private void leaderboard() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.LEADERBOARD);
	}
	
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

	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTextIndividual() {
		// TODO Auto-generated method stub
		
	}
	
}