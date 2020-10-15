package controller.sceneControllers;


import java.io.IOException;

import controller.PrimaryController;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import service.FXMLService;
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
		PrimaryController.app.addNewScene(FXMLService.FXMLNames.HOMESCREEN);
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

