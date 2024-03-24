package Object;

import java.util.Stack;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SceneManager { 
    private static Stack<Scene> sceneStack = new Stack<>();
    private static Stage primaryStage;   
 

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Stack<Scene> getSceneStack() {
        return sceneStack;
    }

    public static void pushScene(Scene scene, boolean setScene) { 
        sceneStack.push(scene);
        if (setScene) {
            setScene(scene);
        }
    }
    

    public static Scene popScene() {
        if (!sceneStack.isEmpty()) {
            return sceneStack.pop();
        }
        return null;
    }

    public static void setScene(Scene scene) { 
        if (primaryStage != null) {
            primaryStage.setScene(scene);  
            if (sceneStack.size() == 1){
                primaryStage.setWidth(422);
                primaryStage.setHeight(497);
            }
            centerOnScreen(primaryStage);
            primaryStage.show();   
            //     primaryStage.setMaximized(true);  
            // else primaryStage.setMaximized(false);  
        }
    }  

    public static void centerOnScreen(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        double stageWidth = stage.getWidth();
        double stageHeight = stage.getHeight();

        double x = (screenWidth - stageWidth) / 2;
        double y = (screenHeight - stageHeight) / 2;

        stage.setX(x);
        stage.setY(y);
    }
}
