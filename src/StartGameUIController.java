import java.io.IOException;
import java.net.URL;

import Boardplay.Board_Main;
import Object.BaseUI;
import Object.GameSetting;
import Object.ManageResources;
import Object.SceneManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene; 
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StartGameUIController extends BaseUI{
    @FXML
    private VBox root; 
    private Stage stage;

    @FXML
    private BorderPane customPane;

    private Scene gameScene; 
    private Board_Main board_main_Controller;
 
    public void initialize() {
        setStage(stage, root);   
        setMinSize(422, 497); 
        cancel_custom();
    } 
      

    private void showGame() { 
        if (gameScene == null) {
            URL resourceUrl = ManageResources.class.getResource("/resources/fxml/Board_Main.fxml");
            if (resourceUrl != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/fxml/Board_Main.fxml"));
                    VBox boardMainRoot = loader.load();  
                    board_main_Controller = loader.getController();
                    gameScene = new Scene(boardMainRoot, 422, 460);  
                    fadeInTransition(gameScene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.printf("Board_Main.fxml not found %nProgram Stop");
                System.exit(0);
            }
        } else { 
            board_main_Controller.board_Controller_restart();
        }  
        SceneManager.getPrimaryStage().setWidth(GameSetting.get_width());
        SceneManager.getPrimaryStage().setHeight(GameSetting.get_height());
        SceneManager.pushScene(gameScene, true);  
    } 

    @FXML
    protected void onClickClassic(){
        GameSetting.setClassic();
        showGame();
    }

    @FXML
    protected void onClickEasy(){
        GameSetting.setEasy();
        showGame();
    }

    @FXML
    protected void onClickMedium(){
        GameSetting.setMedium();
        showGame();
    }

    @FXML
    protected void onClickExpert(){
        GameSetting.setExpert();
        showGame();
    }

    @FXML
    protected void onClickCustom(){
        customPane.setDisable(false);
        customPane.setOpacity(1);
    }

    @FXML
    protected void cancel_custom(){
        customPane.setDisable(true);
        customPane.setOpacity(0);
    }

    @FXML
    private void quit() {
        super.quitApp();
    } 

    @FXML
    protected void onMouseEntered(MouseEvent event) { 
        super.onMouseEntered(event); 
    }

    @FXML
    protected void onMouseExited(MouseEvent event) { 
        super.onMouseExited(event);
    }
  
    private void fadeInTransition(Scene scene) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(600), scene.getRoot());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }
}
