package application;
	
import java.util.List;

import database.Category;
import database.Clue;
import database.Memory_maker;
import database.ModuleModel;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private static Stage _currentStage;
	
	private static ModuleModel _data = ModuleModel.getInstance();
	
	/**
	 * getStage returns the stage to other classes, to allow them to set the stage.
	 * @return currentStage
	 */
	public static Stage getStage() {
		_currentStage.setTitle("QuiNZical");
        return _currentStage;
    }
	
	public static ModuleModel getData() {
		return _data;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			_currentStage = primaryStage;
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
			Scene scene = new Scene(root,700,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			_currentStage.setScene(scene);
			_currentStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		testing();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void testing () {
		List<Category> testingHistoryCategories = _data.getGameCategories();
		System.out.println("Testing initial random categories and clues before updating");
		for (Category c:testingHistoryCategories) {
			System.out.println(c.categoryName());
			for (Clue cl: c.getAllClues()) {
				System.out.println(cl.showClue()+","+cl.showClueType()+","+cl.showAnswer()+","+cl.showValue()+","+cl.isAnswered());
			}
		}
		System.out.println("Testing again after updating");
		try {
			Memory_maker.update(testingHistoryCategories.get(1).getClue(2), testingHistoryCategories.get(1), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Memory_maker.update(testingHistoryCategories.get(2).getClue(3), testingHistoryCategories.get(2), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Testing initial random categories and clues before updating");
		for (Category c:testingHistoryCategories) {
			System.out.println(c.categoryName());
			for (Clue cl: c.getAllClues()) {
				System.out.println(cl.showClue()+","+cl.showClueType()+","+cl.showAnswer()+","+cl.showValue()+","+cl.isAnswered());
			}
		}
		System.out.println("Resetting again now");
		Memory_maker.reset();
	}
}
