package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import application.GameMainController;
import controller.Controller;
import database.Memory_maker;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

public class HomeController implements Controller {
	@FXML 
	private JFXHamburger _hamMenu;
	
	@FXML
	private Text mainTxt;
	
	@FXML
	private JFXDrawer _drawer;
	
	@FXML
	private JFXButton _beginBtn;
	
	@FXML
	private HBox _hbox;
	
	@FXML
	private void initialize() {	
		try {
			_drawer.setDefaultDrawerSize(300);
			_drawer.setResizeContent(true);
			_drawer.setResizableOnDrag(true);

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/fxml/homeDrawer.fxml"));
			VBox drawerContent = loader.load();

			_drawer.setSidePane(drawerContent); 
			for (Node node : drawerContent.getChildren()) {
				Button btn = (Button) node;
				btn.setFont(GameMainController.titleFont);
			}
			
		} catch(IOException e1) {
			e1.printStackTrace();
		}
			HamburgerSlideCloseTransition burgerTask = new HamburgerSlideCloseTransition(_hamMenu);
        burgerTask.setRate(-1);
        _hamMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            burgerTask.setRate(burgerTask.getRate() * -1);
            burgerTask.play();
            if (_drawer.isOpened()){
            	_drawer.close();
            } else {
            	_drawer.open();
            }
        }); 
        
        JFXRippler rippler = new JFXRippler(_beginBtn);
        _hbox.getChildren().add(rippler);
        
        mainTxt.setFont(GameMainController.titleFont);
        
	}
	
	public void init() {
		Stage currentStage = GameMainController.app.getStage();
		currentStage.heightProperty().addListener((obs, oldVal, newVal) -> {
			if(currentStage.getScene() != null) {
				updateText(oldVal, newVal);
			}
		});
	}
	
	@FXML
	private void drawerOpen() {
		_drawer.getStyleClass().clear();
		_drawer.getStyleClass().add("drawerOpen");
	}
	
	@FXML
	private void drawerClose() {
		_drawer.getStyleClass().clear();
		_drawer.getStyleClass().add("drawer");
	}
	
	@FXML
	private void startGame(ActionEvent e) {	
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("questionBoard.fxml"));
			Scene scene = loader.load();
			GameMainController.currentController = (loader.getController());
			
			Platform.runLater(new Runnable() {
	            @Override public void run() {
	               GameMainController.app.setScene(scene); 
	            }
	        });
		
		} catch (IOException e1) {
			e1.printStackTrace();
		}			
	}
	
	public void updateText(Number oldVal, Number newVal) {
		Double newVald = newVal.doubleValue();
		Double oldVald = oldVal.doubleValue();
		
		if(!oldVald.isNaN() && !newVald.isNaN()) {
			double ratio = newVal.doubleValue() / oldVal.doubleValue();
			GameMainController._currentFontSize = GameMainController._currentFontSize * ratio;
			
			Parent root = GameMainController.app.getStage().getScene().getRoot();
			
			root.setStyle("-fx-font-size: " + GameMainController._currentFontSize + "em");		
		}
	}
	
}