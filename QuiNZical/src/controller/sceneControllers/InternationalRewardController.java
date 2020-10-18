package controller.sceneControllers;

import controller.PrimaryController;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import service.FXMLService;
public class InternationalRewardController extends Controller {
	@FXML
	private Text allDoneTxt;
	
	@FXML
	private void initialize() {	
		allDoneTxt.setFont(PrimaryController.titleFont);
		allDoneTxt.setFill(Color.WHITE);
		allDoneTxt.setWrappingWidth(500);
	}
	
	@FXML
	private void back() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.HOMESCREEN);
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

