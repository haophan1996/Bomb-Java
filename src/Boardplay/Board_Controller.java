package Boardplay;
import Object.GameSetting;
import Object.ManageResources;
import Object.Minesweeper;
import Object.SceneManager;
import Enum.MPOS;
import Enum.RoundStatus;
import Enum.Status;
   
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration; 

public class Board_Controller extends javafx.scene.layout.GridPane {   
    private Minesweeper ms;
    private Board_Cell[][] cells;
    private RoundStatus round;
    private Alert alert;
    private ButtonType mainGame,viewBoard,playAgainBTN,cancelButton;

    public Board_Controller(Minesweeper ms) {
        this.ms = ms;
        this.round = RoundStatus.INGAME;
        this.cells = new Board_Cell[ms.getSize_c()][ms.getSize_r()]; 
        setcells();
    }

    private void setcells(){
        for (int r = 0; r < ms.getSize_r(); r++) {
            for (int c = 0; c < ms.getSize_c(); c++) {
                Board_Cell cell = new Board_Cell(c, r, (column, row, MPOS) -> callback(column, row, MPOS));  
                cell.setStatus(ms.getStatus(c, r));
                this.add(cell, c, r);

                cellAppearAnimation(cell);

                setMargin(cell, new Insets(0.2));
                cells[c][r] = cell;
            } 
        } 
    }

    private void cellAppearAnimation(Board_Cell cell) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), cell);
        scaleTransition.setFromX(0.0);
        scaleTransition.setToX(1.0);
        scaleTransition.setFromY(0.0);
        scaleTransition.setToY(1.0);

        // Move the cell downwards
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), cell);
        translateTransition.setByY(cell.getHeight());

        scaleTransition.setOnFinished(event -> {
            // Cleanup resources when the animation is finished
            scaleTransition.stop();
            translateTransition.stop();
        });

        // Play both animations in parallel
        scaleTransition.play();
        translateTransition.play();
    } 

    private void callback(int column, int row, MPOS mpos){  
        if (this.round == RoundStatus.VIEWBOARD){
            checkGame();
            return;
        }  
        
        if (this.round != RoundStatus.INGAME) return;  
        
        onMouseClickSound(column,row,mpos);
        
        
        ms.setStatusAt(column, row, mpos);
        
        this.round = ms.getRoundStatus();  
        updateUI();

        if (this.round == RoundStatus.LOSE)
            if (ms.getStatus(column, row) == Status.BOMB) 
                cells[column][row].setStatus(Status.BOMB_EXP);

        checkGame();  
    }

    private void checkGame(){
        if (this.round == RoundStatus.WIN){
            showRoundStatusCondition("Woohoo!", "You Win!");
        } else if (this.round == RoundStatus.LOSE){
            showRoundStatusCondition("Mine hit!", "You lose!");
        } else if (this.round == RoundStatus.VIEWBOARD) {
            showRoundStatusCondition("Play Again!", "!!!!");
        }
    }

    private void updateUI() {  
        for (int r = 0; r < ms.getSize_r(); r++)  
            for (int c = 0; c < ms.getSize_c(); c++){  
                if (ms.getStatus(c, r) != cells[c][r].getStatus())   
                    cells[c][r].setStatus(ms.getStatus(c, r));
                 
                if (this.round == RoundStatus.VIEWBOARD) {
                    if (ms.getStatus(c, r) == Status.BOMB) 
                        cells[c][r].setStatus(Status.BOMB_EXP);  
                    if (ms.getStatus(c, r) == Status.BOMB_UNDER_FLAG)
                        cells[c][r].setStatus(Status.BOMB_UNDER_FLAG_END_ROUND);  
                } 
            } 
    }

    private void showRoundStatusCondition(String header, String contentText) {
        alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Notice");
        alert.setHeaderText(header);
        alert.setContentText(contentText);

        mainGame = new ButtonType("Main");
        viewBoard = new ButtonType("View Board");    
        playAgainBTN = new ButtonType("Play Again");
        cancelButton = new ButtonType("Exit");

        alert.getButtonTypes().setAll(playAgainBTN, viewBoard, mainGame, cancelButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == playAgainBTN) {
                this.round = RoundStatus.INGAME; 
                ms.restart();
                updateUI();
            } else if (buttonType == cancelButton) { 
                System.exit(0); 
            } else if (buttonType == viewBoard) {   
                this.round = RoundStatus.VIEWBOARD;
                if (this.ms.getRoundStatus() == RoundStatus.LOSE) 
                    updateUI();
                this.ms.setRoundStatus(this.round);  
            } else { 
                this.getChildren().clear();
                this.cells = null;
                SceneManager.popScene();  
                Scene poppedScene = SceneManager.popScene(); 
                System.gc();
                if (poppedScene != null) { 
                    SceneManager.pushScene(poppedScene, true);
                }  
            }
        }); 

    }

    public void reinit_game(){ 
        this.round = RoundStatus.INGAME;   
        this.ms.setSize_C_R(GameSetting.get_size_c(), GameSetting.get_size_r(), GameSetting.get_bomb());  
        this.ms.restart();   
        this.cells = new Board_Cell[ms.getSize_c()][ms.getSize_r()];
        setcells();
        updateUI(); 
    }

    private void onMouseClickSound(int c, int r,MPOS mpos){
        if (mpos == MPOS.LEFT_CLICK) {
            if (cells[c][r].getStatus() == Status.BOMB) 
                playSoundEffect("BOMB__EXPL_SND"); 
            else if (cells[c][r].getStatus() == Status.EMPTY)
                playSoundEffect("LEFT_CLICK_SND"); 
            
        } else if (mpos== MPOS.RIGHT_CLICK) 
            playSoundEffect("RIGHT_CLICK_SND");  
    }

    private void playSoundEffect(String soundFile) { 
        try { 
            ManageResources.getSound(soundFile).setFramePosition(0);
            ManageResources.getSound(soundFile).start();
         } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
} 