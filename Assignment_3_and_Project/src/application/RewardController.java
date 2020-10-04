package application;


import java.io.IOException;

import controller.Controller;
import database.DataExtractor;
import database.Memory_maker;
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
		usrWinnings.setFont(GameMainController.titleFont);
		usrWinnings.setText("$" + DataExtractor.winnings());
		usrWinnings.setFill(Color.WHITE);
		allDoneTxt.setFont(GameMainController.titleFont);
		allDoneTxt.setFill(Color.WHITE);
	}
	
	@FXML
	private void back() {
		Thread th = new Thread(new Task<Void>() {
			protected Void call() throws IOException {
					Memory_maker.reset();
					return null;
	            }
        	});
		th.start();
		GameMainController.app.addNewScene(FXMLService.FXMLNames.HOMESCREEN);
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

