package controller.sceneControllers;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import controller.PrimaryController;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import service.FXMLService;

/**
 * The controller for the main homepage.
 * @author Adam and Osama
 *
 */
public class HomeController extends Controller {
	@FXML 
	private JFXHamburger hamMenu;
	
	@FXML
	private Text mainTxt;
	
	@FXML
	private JFXDrawer drawer;
	
	@FXML
	private JFXButton beginBtn;
	
	@FXML
	private HBox hbox;
	
	@FXML
	private Button internationalButton;
	
	private HamburgerSlideCloseTransition burgerTask;
	
	/**
	 * Sets up the international section and hamburger drawer. 
	 */
	@FXML
	private void initialize() {	
		Image globe = new Image(getClass().getClassLoader().getResourceAsStream("application/css/earth.png"));
		internationalButton.setGraphic(new ImageView(globe));
		internationalButton.getStyleClass().add("internationalButton");
		internationalButton.setVisible(PrimaryController.getInstance().internationalEnabled());
		
		internationalButton.setMinWidth(50);
		internationalButton.setMinHeight(50);
		
		drawer.setDefaultDrawerSize(300);
		drawer.setResizeContent(true);
		drawer.setResizableOnDrag(true);
		
		// fetches the scene to go in the drawer.
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
		
		// sets the new scene in the drawer
		drawerService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override 
            public void handle(WorkerStateEvent t) {
            	VBox drawerContent = (VBox)t.getSource().getValue();
            	drawer.setSidePane(drawerContent);
            	for (Node node : drawerContent.getChildren()) {
    				Button btn = (Button) node;
    				btn.setFont(PrimaryController.titleFont);
    			}
            }
        });
		
		// Adds a listener to the hamburger menu, to close and open the drawer.
		burgerTask = new HamburgerSlideCloseTransition(hamMenu);
		drawerService.start();
        burgerTask.setRate(-1);
        hamMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            burgerTask.setRate(burgerTask.getRate() * -1);
            burgerTask.play();
            if (drawer.isOpened()){
            	drawer.close();
            } else {
            	drawer.open();
            }
        }); 

        JFXRippler rippler = new JFXRippler(beginBtn);
        hbox.getChildren().add(rippler);
        
        mainTxt.setFont(PrimaryController.titleFont);
	}
	
	/**
	 * Starts the international section
	 * @throws IOException
	 */
	@FXML
	private void internationalStart() throws IOException {
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.INTERNATIONALBOARD);	
	}
	
	/**
	 * Sets the style for the open drawer
	 */
	@FXML
	private void drawerOpen() {
		drawer.getStyleClass().clear();
		drawer.getStyleClass().add("drawerOpen");
	}
	
	/**
	 * Sets the style for the closed drawer.
	 */
	@FXML
	private void drawerClose() {
		drawer.getStyleClass().clear();
		drawer.getStyleClass().add("drawer");
	}
	
	/**
	 * Closes the game menu.
	 */
	public void closeMenu() {
		burgerTask.setRate(burgerTask.getRate() * -1);
        burgerTask.play();
        if (drawer.isOpened()){
        	drawer.close();
        } else {
        	drawer.open();
        }
	}
	
	/**
	 * Starts the game by loading the question board.
	 * @param e
	 */
	@FXML
	private void startGame() {	
		PrimaryController.getInstance().addNewScene(FXMLService.FXMLNames.QUESTIONBOARD);
	}

	/**
	 * An unused utility method from the superclass
	 */
	@Override
	public void updateTextIndividual() {}
	
	/**
	 * An unused utility method from the superclass
	 */
	public void init() {} 
}