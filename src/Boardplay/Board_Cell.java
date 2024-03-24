package Boardplay;  
import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node; 
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
 
import Enum.MPOS;
import Enum.Status;
import Object.ManageResources;

public class Board_Cell extends StackPane { 
    private static final Color SQUARE_COLOR = Color.rgb(150, 180, 210, 0.7); 
    private static final Color BORDER_COLOR = Color.rgb(0, 0, 255, 0.5);
    private static final Color ENTER_COLOR = Color.rgb(255, 182, 193, 0.5);
    private static final Color FLAG_COLOR = Color.rgb(255, 255, 150, 0.5);
    private static final Color CHCKED_COLOR = Color.rgb(255, 255, 255, 0.7);
    private static final Color BOMB_COLOR = Color.rgb(255, 0, 0, 0.7);  
    private static final double ARC_RADIUS = 5.0;
    private int row;
    private int column; 
    private Status status;
    private ImageView imageView;  
    private Rectangle rectangle;
    private ClickCallback clickCallback;  

    public Board_Cell(int column, int row, ClickCallback clickCallback) {
        super(); 
        this.column = column;
        this.row = row;
        this.status = Status.EMPTY; 
        this.clickCallback = clickCallback;

        rectangle = new Rectangle(40, 40);
        rectangle.setStroke(BORDER_COLOR);
        rectangle.setFill(SQUARE_COLOR);
        rectangle.setStrokeWidth(0.5);
        rectangle.setArcWidth(ARC_RADIUS);
        rectangle.setArcHeight(ARC_RADIUS);

        imageView = new ImageView();
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
  
        this.getChildren().addAll(rectangle, imageView);

        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) handleLeftMouseClick();
            else handleRightMouseClick();
        });
        setOnMouseEntered(event -> handleMouseEntered());
        setOnMouseExited(event -> handleMouseExited());
    }

    private void handleLeftMouseClick() {  
        if (clickCallback != null) 
            clickCallback.onCellClick(column, row, MPOS.LEFT_CLICK); 
    }

    private void handleRightMouseClick() { 
        if (clickCallback != null) 
            clickCallback.onCellClick(column, row, MPOS.RIGHT_CLICK);
    }

    public interface ClickCallback {
        void onCellClick(int column, int row, MPOS mpos);
    }

    private void playBombClickAnimation() { 
        imageView.setScaleX(1.0);
        imageView.setScaleY(1.0);

        // Stop any ongoing animation
        imageView.getTransforms().clear();

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), imageView);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.5);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();
    }

    private void playFadeInClickAnimation() { 
        imageView.setOpacity(0.0); 
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(100), imageView); 
        fadeTransition.setToValue(1.0); 
        fadeTransition.play();  
        applyShakingAnimation(imageView);
    }

    private void applyShakingAnimation(Node e) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.1), e);
        rotateTransition.setFromAngle(-5);
        rotateTransition.setToAngle(5);
        rotateTransition.setCycleCount(3); // Number of times the shaking motion repeats

        // Reset the rotation angle to 0 after the animation
        rotateTransition.setOnFinished(event -> imageView.setRotate(0));

        rotateTransition.play();
    }
    
    private void handleMouseEntered() {
        if (this.status == Status.EMPTY || this.status == Status.BOMB || this.status == Status.BOMB_UNDER_FLAG)
            setCustomFill(ENTER_COLOR); 
    }

    private void handleMouseExited() {
        if (this.status == Status.EMPTY || this.status == Status.BOMB || this.status == Status.BOMB_UNDER_FLAG)
            setCustomFill(SQUARE_COLOR); 
    }

    private void setCustomFill(Color color){
        rectangle.setFill(color); 
    }

    private void setImage(String path) { 
        try { 
            imageView.setImage(ManageResources.getImage(path));
        } catch (Exception e) {
            System.out.printf("File not found: %s %n", path);
        }
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status; 
        switch (status) {
            case BOMB_EXP: //When user click, bomb show up
                setImage("BOMB_IMG");
                setCustomFill(BOMB_COLOR);
                playBombClickAnimation();
                break; 
            case FLAG, BOMB_UNDER_FLAG: //Show flag when user right click
                setImage("FLAG_IMG");
                setCustomFill(FLAG_COLOR);
                break; 
            case BOMB_UNDER_FLAG_END_ROUND: 
                setImage("FLAG_IMG");
                setCustomFill(BOMB_COLOR);
                break;  
            case CHECKED: // when user click, when there is 0 bomb
                setImage("BLANK_IMG"); 
                setCustomFill(CHCKED_COLOR);
                break; 
            case ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT: 
                setImage(status.toString());
                setCustomFill(CHCKED_COLOR);
                playFadeInClickAnimation();
                break;
            default: //Init cell - Default
                setImage("BLANK_IMG");
                setCustomFill(SQUARE_COLOR);
                break;
        }
    } 
}
