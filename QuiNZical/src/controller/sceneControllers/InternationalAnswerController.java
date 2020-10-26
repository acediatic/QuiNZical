package controller.sceneControllers;


import controller.PrimaryController;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import service.FXMLService;

public class InternationalAnswerController extends Controller {
	
	@FXML
	private Text winningsMsg;
	
	@FXML
	private void initialize() {
		winningsMsg.setText("$" + PrimaryController.getInstance().getWinnings());
		winningsMsg.setVisible(false);
	}
	
	@FXML
	private void goHome() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.HOMESCREEN);
	}
	
	@FXML 
	private void goQuestionBoard() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.INTERNATIONALBOARD);	
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