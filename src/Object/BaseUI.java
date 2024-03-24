package Object;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor; 
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class BaseUI { 
    protected Stage stage;
    protected VBox root;

    public void setStage(Stage stage, VBox root) {
        this.stage = stage;
        this.root = root; 
    }

    public void setRoot(VBox root) {
         this.root = root;
    }
 
    protected void onMouseEntered(MouseEvent event) {
        root.setCursor(new ImageCursor(ManageResources.getImage("CURSOR_IMG")));
    }

    protected void onMouseExited(MouseEvent event) {
        root.setCursor(Cursor.DEFAULT);
    }

    protected void quitApp(){
        System.exit(0);
    }


    protected void setMinSize(double minWidth, double minHeight) {
        if (stage != null) {
            stage.setMinWidth(minWidth);
            stage.setMinHeight(minHeight);
        }
    }

}
