package controller.sceneControllers;

import static java.lang.Math.pow;

import controller.PrimaryController;
import database.Category;
import database.Clue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import service.AskQuestionInternationalService;
import service.FXMLService;

/**
 * Controls the International Question Board, 
 * allowing the user to select from the 
 * international grid.
 * @author Adam and Osama
 */
public class InternationalQuestionBoardController extends Controller {		
	@FXML 
	private GridPane gp;
	
	@FXML
	private Label internationalCatLbl;
	
	/**
	 * Sets up the question board, adding the international
	 * category and its respective available clue values.
	 */
	public void initialize() {
		
		internationalCatLbl.setFont(PrimaryController.titleFont); 
		Category internationalCategory = PrimaryController.getInstance().getInternationalCat();

		if (internationalCategory.allAnswered()) {
			PrimaryController.getInstance().setLoadScreen();
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

	/**
	 * Determines the clue pressed by its location in the gridpane.
	 * and begins asking the user that clue.
	 * @param e, the action event fired by the button pushed
	 */
	@FXML
	private void buttonPressed(ActionEvent e) {

		Node node = (Node) e.getSource();
		Integer nodeCol = 0;
		Integer nodeRow = GridPane.getRowIndex(node)-1;
		
		AskQuestionInternationalService askQuestion = new AskQuestionInternationalService();
			askQuestion.setCatAndClue(nodeCol, nodeRow);
			askQuestion.start();
		}
	
	
	/**
	 * Updates text size when the window is resized, by adjusting the 
	 * size of the root.
	 */
	public void updateTextIndividual() {
		Double currentFontSize = PrimaryController.currentFontSize;
		gp.setStyle("-fx-font-size: " + currentFontSize + "em; -fx-padding: "+ currentFontSize*10);
		gp.setVgap(pow(2,currentFontSize));
		gp.setHgap(pow(2,currentFontSize));
	}
	
	/**
	 * Unused helper method inherited from the super class.
	 */
	@Override
	public void init() {}
}