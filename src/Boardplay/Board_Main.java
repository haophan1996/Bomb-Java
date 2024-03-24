package Boardplay; 
import Object.BaseUI;
import Object.GameSetting;
import Object.Minesweeper;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox; 

public class Board_Main extends BaseUI{   
    private Minesweeper ms;
    private Board_Controller boardController;

    @FXML
    private VBox root; 

    @FXML
    private VBox boardPane; 

    @FXML
    protected void onMouseEntered(MouseEvent event) {  
        super.onMouseEntered(event); 
    }

    @FXML
    protected void onMouseExited(MouseEvent event) { 
        super.onMouseExited(event);
    } 

    public void initialize() {
        if (boardPane != null) { 
            ms = new Minesweeper(GameSetting.get_size_c(),GameSetting.get_size_r(), GameSetting.get_bomb());  
            boardController = new Board_Controller(ms); 
            boardPane.getChildren().add(boardController);

            if (root != null) {
            setRoot(root);
            root.setStyle("-fx-background-color: rgba(0, 0, 139, 0.5);"); 
        }    
        }     
    } 

    public void board_Controller_restart(){
        boardController.reinit_game();
    }
} 