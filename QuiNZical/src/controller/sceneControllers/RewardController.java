package controller.sceneControllers;


import java.io.IOException;
import java.util.Optional;

import controller.PrimaryController;
import database.ScoreboardExtractor;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import service.FXMLService;
import service.FXMLService.FXMLNames;
public class RewardController extends Controller {
	
	@FXML 
	private Text usrWinnings;
	
	@FXML
	private Text allDoneTxt;
	
	@FXML
	private void initialize() {	
		usrWinnings.setFont(PrimaryController.titleFont);
		usrWinnings.setText("$" + PrimaryController.getInstance().getWinnings());
		usrWinnings.setFill(Color.WHITE);
		allDoneTxt.setFont(PrimaryController.titleFont);
		allDoneTxt.setFill(Color.WHITE);
	}
	
	@FXML
	private void back() {
		Thread th = new Thread(new Task<Void>() {
			protected Void call() throws IOException {
				PrimaryController.getInstance().reset();
					return null;
	            }
        	});
		th.start();
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.HOMESCREEN);
	}

	@FXML
	private void scoreboard() {
		TextInputDialog dialog = new TextInputDialog("Jeff");
		dialog.setTitle("Add to Leaderboard");
		dialog.setHeaderText("Get added to the Leaderboard!");
		dialog.setContentText("Please enter your preferred name:");

		// Traditional way to get the response value.
		Optional<String> userName = dialog.showAndWait();
		if (userName.isPresent()){
		    Thread th = new Thread(new Task<Void>() {
		    	protected Void call() throws IOException {
		    		ScoreboardExtractor.updateScoreBoardWithUsr(userName.get());
		    		return null;
		    	}
		    	@Override
                protected void succeeded() {
		    		PrimaryController.getInstance().addNewScene(FXMLNames.LEADERBOARD);
		    	}
		    });
		    th.start();
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

