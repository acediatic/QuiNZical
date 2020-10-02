package application;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import application.Controller;
import application.QuiNZical;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

public class HomeController implements Controller {
	private Stage _currentStage = QuiNZical.getStage();
	
	@FXML 
	private JFXHamburger _hamMenu;
	
	@FXML
	private JFXDrawer _drawer;
	
	@FXML
	private JFXButton _beginBtn;
	
	@FXML
	private HBox _hbox;
	
	@FXML
	private void initialize() {	
		
		_currentStage.heightProperty().addListener((obs, oldVal, newVal) -> {
			if(_currentStage.getScene() != null) {
				updateText(oldVal, newVal);
			}
		});
		
		HamburgerSlideCloseTransition burgerTask = new HamburgerSlideCloseTransition(_hamMenu);
        burgerTask.setRate(-1);
        _hamMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            burgerTask.setRate(burgerTask.getRate() * -1);
            burgerTask.play();
        });
        
        JFXRippler rippler = new JFXRippler(_beginBtn);
        _hbox.getChildren().add(rippler);
	}

	
	public void initData(Stage stage) {
		_stage = stage;
	}
	
	@FXML
	private void buttonPressed(ActionEvent e) {
		Node node = (Node) e.getSource();
		GridPane gp = (GridPane) node.getParent();
		int nodeRow = gp.getRowIndex(node);
		int nodeCol = gp.getColumnIndex(node);
		
		
		// Access objects
		
		

		
		
		Platform.runLater(new Runnable() {
            @Override public void run() {
                Quizical.setScene(homeScene);
                
            }
        });
	}

	
	
	
	
	public void updateText(Number oldVal, Number newVal) {
		Double newVald = newVal.doubleValue();
		Double oldVald = oldVal.doubleValue();
		
		if(!oldVald.isNaN() && !newVald.isNaN()) {
			double ratio = newVal.doubleValue() / oldVal.doubleValue();
			QuiNZical._currentFontSize = QuiNZical._currentFontSize * ratio;
			
			Parent root = _currentStage.getScene().getRoot();
			
			root.setStyle("-fx-font-size: " + QuiNZical._currentFontSize + "em");		
		}
	}
	
}