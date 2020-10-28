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

/**
 * Controls the QuestionBoard, allowing the user
 * to select from the NZ grid.
 * @author Adam and Osama
 */
public class QuestionBoardController extends Controller {	
	
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
	
	/**
	 * Sets up the question board, adding all categories and
	 * their respective available clue values.
	 */
	public void initialize() {
		lblList = Arrays.asList(cat1,cat2,cat3,cat4,cat5);
		int catCounter = 0;
		for (Label cat : lblList) {
			cat.setText(PrimaryController.getInstance().getCategories().get(catCounter).toString());
			cat.setWrapText(true);
			catCounter++;
		}
		
		if(PrimaryController.getInstance().getNumberCompletedCategories() < PrimaryController.getInstance().getCategories().size()) {
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

	
	/**
	 * Determines the clue pressed by its location in the gridpane.
	 * and begins asking the user that clue.
	 * @param e, the action event fired by the button pushed
	 */
	@FXML
	private void buttonPressed(ActionEvent e) {

		Node node = (Node) e.getSource();
		Integer nodeCol = GridPane.getColumnIndex(node);
		Integer nodeRow = GridPane.getRowIndex(node)-1;
		
		AskQuestionService askQuestion = new AskQuestionService();
			askQuestion.setCatAndClue(nodeCol, nodeRow);
			askQuestion.start();
		}
	
	
	/**
	 * Takes the user back to the homescreen
	 */
	@FXML
	private void back() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.HOMESCREEN);
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