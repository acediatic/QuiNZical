package application;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import application.GameMainController;
import controller.Controller;
import database.DataExtractor;
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

public class DrawerController implements Controller {
	@FXML
	private void initialize() {	}
	
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
	
	@FXML
	private void viewScore() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("winningsScene.fxml"));
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
		System.out.println("Winnings: " + DataExtractor.winnings());
	}
	
	@FXML
	private void reset() {
		Memory_maker.reset();
	}
	
	@FXML private void quit() {
		Platform.exit();
	}

	@Override
	public void updateText(Number oldVal, Number newVal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
}