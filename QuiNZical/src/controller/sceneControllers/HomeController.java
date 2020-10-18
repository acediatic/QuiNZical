package controller.sceneControllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import controller.PrimaryController;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.FXMLService;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

@SuppressWarnings("unused")
public class HomeController extends Controller {
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
	private Button internationalButton;
	
	private HamburgerSlideCloseTransition burgerTask;
	
	@FXML
	private void initialize() {	
		Image globe = new Image(getClass().getClassLoader().getResourceAsStream("application/css/earth.png"));
		internationalButton.setGraphic(new ImageView(globe));
		internationalButton.getStyleClass().add("internationalButton");
		internationalButton.setVisible(PrimaryController.getInstance().internationalEnabled());
		
		internationalButton.setMinWidth(50);
		internationalButton.setMinHeight(50);
		
		_drawer.setDefaultDrawerSize(300);
		_drawer.setResizeContent(true);
		_drawer.setResizableOnDrag(true);
		
		Service<VBox> drawerService = new Service<VBox>() {

			@Override
			protected Task<VBox> createTask() {
				return new Task<VBox>() {
					protected VBox call() throws IOException {
						FXMLLoader loader = new FXMLLoader(FXMLService.FXMLNames.HOMEDRAWER.toURL());
						VBox drawerContent = loader.load();
						return drawerContent;
						
					}
				};
			}
		};
		
		drawerService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override 
            public void handle(WorkerStateEvent t) {
            	VBox drawerContent = (VBox)t.getSource().getValue();
            	_drawer.setSidePane(drawerContent);
            	for (Node node : drawerContent.getChildren()) {
    				Button btn = (Button) node;
    				btn.setFont(PrimaryController.titleFont);
    			}
            }
        });
		burgerTask = new HamburgerSlideCloseTransition(_hamMenu);
		drawerService.start();
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
        
        mainTxt.setFont(PrimaryController.titleFont);
	}
	
	@FXML
	private void internationalStart() throws IOException {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.INTERNATIONALBOARD);	
	}
	
	public void init() {}
	
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
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.QUESTIONBOARD);		
	}

	@Override
	public void updateTextIndividual() {
		// TODO Auto-generated method stub
		
	}
	
	public void closeMenu() {
		burgerTask.setRate(burgerTask.getRate() * -1);
        burgerTask.play();
        if (_drawer.isOpened()){
        	_drawer.close();
        } else {
        	_drawer.open();
        }
	}
}