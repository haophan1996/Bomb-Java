import java.io.IOException;
import java.net.URL;

import Object.ManageResources;
import Object.SceneManager;
import javafx.fxml.FXMLLoader; 
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {  
        ManageResources.load_resourcs();
        launch(args); 
    } 

    @Override
    public void start(Stage primaryStage) throws IOException {  
        URL resourceUrl = ManageResources.class.getResource("/resources/fxml/StartGameUI.fxml");
        if (resourceUrl != null){
            try{
                FXMLLoader loader = new FXMLLoader(resourceUrl);;
                SceneManager.setPrimaryStage(primaryStage);
                SceneManager.getPrimaryStage().setTitle("Minesweeper Game!!!");   
                VBox root = loader.load();
                StartGameUIController controller = loader.getController();
                Scene scene = new Scene(root,422, 460);  
                controller.initialize();
                SceneManager.pushScene(scene, true);  
                SceneManager.centerOnScreen(primaryStage);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        } else { 
            System.out.printf("StartGameUI.fxml not found %nProgram Stop");
            System.exit(0);
        }
    } 
 
}
