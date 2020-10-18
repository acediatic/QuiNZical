package controller.sceneControllers;

import static java.lang.Math.pow;

import java.util.Arrays;
import java.util.List;

import controller.PrimaryController;
import database.Category;
import database.Clue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import service.AskQuestionService;
import service.FXMLService;

public class InternationalQuestionBoardController extends Controller {	
	public void init() {}
	
	@FXML 
	private GridPane gp;
	
	@FXML
	private Label internationalCatLbl;
	
	public void initialize() {
		
		internationalCatLbl.setFont(PrimaryController.titleFont);
		Category internationalCategory = PrimaryController.getInstance().getInternationalCat();
		
		if (internationalCategory.allAnswered()) {
			PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.INTERNATIONALFINISHED);
		} else { 
			int clueIndex = 0; //starts at zero as in gridpane q's begin at row (index) 1
			Boolean lowestClueFound = false;
			for (Clue clue : internationalCategory.getAllClues()) {
				clueIndex++;
				ObservableList<Node> childrens = gp.getChildren();
				for (Node node : childrens) {
					Integer rowIndex = GridPane.getRowIndex(node);
					
					if (rowIndex == null) {
						rowIndex = 0;
					} 
					
					if (rowIndex == clueIndex) {
						if (clue.isAnswered()) {
							node.setVisible(false);
						}
						else if (!lowestClueFound) {
							node.setDisable(false);
							lowestClueFound = true;
						} 
						break;
			        }
			    }
			}
		}
	}

	
	@FXML
	private void buttonPressed(ActionEvent e) {

		Node node = (Node) e.getSource();
		Integer nodeCol = GridPane.getColumnIndex(node);
		Integer nodeRow = GridPane.getRowIndex(node)-1;
		
		AskQuestionService askQuestion = new AskQuestionService();
			askQuestion.setCatAndClue(nodeCol, nodeRow);
			askQuestion.start();
		}
	
	public void updateTextIndividual() {
		Double currentFontSize = PrimaryController._currentFontSize;
		gp.setStyle("-fx-font-size: " + currentFontSize + "em; -fx-padding: "+ currentFontSize*10);
		gp.setVgap(pow(2,currentFontSize));
		gp.setHgap(pow(2,currentFontSize));
	}
}