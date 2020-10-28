package controller.sceneControllers;

import java.util.List;

import controller.PracticeModuleController;
import controller.PrimaryController;
import database.Category;
import database.IncorrectClueExtractor;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import service.AskPracticeQuestionService;
import service.FXMLService;

/**
 * Selection controller for the practice module
 * @author Adam and Osama
 *
 */
public class PracticeCatSelector extends Controller {
	@FXML
	private ComboBox<Category> comboPracticeCats;
	
	@FXML
	private ComboBox<Category> comboPrevClues;

	/**
	 * Sets up the practice category selector and 
	 * the incorrect category selector, if applicable.
	 */
	@FXML
	private void initialize() {
		comboPracticeCats.getItems().addAll(PracticeModuleController.getInstance().getAllCategories());
		comboPracticeCats.setEditable(false);
		comboPracticeCats.setButtonCell(new ListCell<Category>(){

	        @Override
	        protected void updateItem(Category item, boolean empty) {
	            super.updateItem(item, empty); 
	            if(empty || item==null){
	                // styled like -fx-prompt-text-fill:
	                setStyle("-fx-text-fill: white; -fx-font-size: 1.25em; -fx-font-style: italic");
	            }
	        }	

	    });
		
		List<Category> incorrectCats = IncorrectClueExtractor.getIncorrect();
		if (!(incorrectCats.size() <= 0)) {
			comboPrevClues.setVisible(true);
			comboPrevClues.getItems().addAll(incorrectCats);
			comboPrevClues.setEditable(false);
			comboPrevClues.setButtonCell(new ListCell<Category>(){

		        @Override
		        protected void updateItem(Category item, boolean empty) {
		            super.updateItem(item, empty); 
		            if(empty || item==null){
		                // styled like -fx-prompt-text-fill:
		                setStyle("-fx-text-fill: white; -fx-font-size: 1.25em; -fx-font-style: italic");
		            }
		        }	

		    });
		} else {
			comboPrevClues.setVisible(false);
		}
	}

	/**
	 * Fetches the selected category, and begins the 
	 * askPracticeQuestionService
	 */
	@FXML 
	private void selectedCategory() {
		ComboBox<Category> chosenCombo;
		if(comboPracticeCats.getValue() == null) {
			chosenCombo = comboPrevClues;
		} else {
			chosenCombo = comboPracticeCats;
		}
		
		Category chosenCat = chosenCombo.getValue();
		AskPracticeQuestionService askQuestion = new AskPracticeQuestionService();
		askQuestion.setCategory(chosenCat);
		PracticeModuleController.getInstance().currentAttempts = 0;
		askQuestion.start();
	}
		
	/**
	 * Takes the user back to the homescreen.
	 */
	@FXML
	private void back() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.HOMESCREEN);		
	}
	
	/**
	 * Unused helper method inherited from the super class.
	 */
	@Override
	public void init() {}

	/**
	 * Unused helper method inherited from the super class.
	 */
	@Override
	public void updateTextIndividual() {}
}