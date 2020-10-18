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

public class QuestionBoardController extends Controller {	
	public void init() {}
	
	@FXML 
	private GridPane gp;
	
	@FXML
	private Label cat1;
	
	@FXML
	private Label cat2;
	
	@FXML
	private Label cat3;
	
	@FXML
	private Label cat4;
	
	@FXML
	private Label cat5;
	
	private List<Label> lblList;
	
	public void initialize() {
		lblList = Arrays.asList(cat1,cat2,cat3,cat4,cat5);
		int catCounter = 0;
		for (Label cat : lblList) {
			cat.setText(PrimaryController.getInstance().getCategories().get(catCounter).toString());
			cat.setWrapText(true);
			catCounter++;
		}
		
		int noCompletedCats = 0;
		for (Category c : PrimaryController.getInstance().getCategories()) {
			if(c.allAnswered()) {
				noCompletedCats++;
			}
		}
		
		if(noCompletedCats <= 2) {
			PrimaryController.getInstance().enableInternational();
		}
		
		if(noCompletedCats < PrimaryController.getInstance().getCategories().size()) {
			int catIndex = -1;
			for (Category cat : PrimaryController.getInstance().getCategories()) {
				catIndex++;
				int clueIndex = 0; //starts at zero as in gridpane q's begin at row (index) 1
				Boolean lowestClueFound = false;
				for (Clue clue : cat.getAllClues()) {
					clueIndex++;
					ObservableList<Node> childrens = gp.getChildren();
					for (Node node : childrens) {
						Integer colIndex = GridPane.getColumnIndex(node);
						Integer rowIndex = GridPane.getRowIndex(node);
						if (colIndex == null) {
							colIndex = 0;
						} 
						
						if (rowIndex == null) {
							rowIndex = 0;
						} 
						
						if (rowIndex == clueIndex && colIndex == catIndex) {
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
		} else {
			PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.GAMECOMPLETE);
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