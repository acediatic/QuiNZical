package controller.sceneControllers;

import controller.PracticeModuleController;
import controller.PrimaryController;
import database.Category;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import service.AskPracticeQuestionService;
import service.FXMLService;

public class PracticeCatSelector extends Controller {
	@FXML
	private ComboBox<Category> combo;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	private void initialize() {
		combo.getItems().addAll(PracticeModuleController.getInstance().getAllCategories());
		combo.setEditable(false);
		combo.setButtonCell(new ListCell(){

	        @Override
	        protected void updateItem(Object item, boolean empty) {
	            super.updateItem(item, empty); 
	            if(empty || item==null){
	                // styled like -fx-prompt-text-fill:
	                setStyle("-fx-text-fill: white; -fx-font-size: 1.25em; -fx-font-style: italic");
	            }
	        }	

	    });
		}

	@FXML 
	private void selectedCategory() {
		Category chosenCat = combo.getValue();
		AskPracticeQuestionService askQuestion = new AskPracticeQuestionService();
		askQuestion.setCategory(chosenCat);
		PracticeModuleController.getInstance().currentAttempts = 0;
		askQuestion.start();
	}
		
	@FXML
	private void back() {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.HOMESCREEN);		
	}
	
	@Override
	public void updateTextIndividual() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}