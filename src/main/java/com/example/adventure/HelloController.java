package com.example.adventure;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class HelloController {

    @FXML
    public Polygon pHand;
    @FXML
    public Polygon pLeg;
    @FXML
    public Polygon scelSword;
    @FXML
    public Polygon scelHead;

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ImageView bgCity;
    @FXML
    private ImageView bgCity2;
    @FXML
    private ImageView player;
    @FXML
    private ImageView skeleton;
    @FXML
    private Label lblPause;
    @FXML
    private Label lblGameOver;

    // Ширина картинки
    private final int BG_WIDTH = 700;

    private ParallelTransition parallelTransition;
    public TranslateTransition enemyTransition;

    public static boolean right = false;
    public static boolean left = false;
    public static boolean jump = false;
    public static boolean isPause = false;

    private int plSpeed = 3;
    private int jumpDownSpeed = 5;

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {
            // Если еще не допрыгнул до верхней границы
            if(jump && player.getLayoutY() >= 70 ){
                player.setLayoutY(player.getLayoutY() - plSpeed);
                pHand.setLayoutY(pHand.getLayoutY() - plSpeed);
                pLeg.setLayoutY(pLeg.getLayoutY() - plSpeed);
            } else if (player.getLayoutY()<= 216) {
                jump = false;
                player.setLayoutY(player.getLayoutY() + jumpDownSpeed);
                pHand.setLayoutY(pHand.getLayoutY() + jumpDownSpeed);
                pLeg.setLayoutY(pLeg.getLayoutY() + jumpDownSpeed);
            }
            if(right && player.getLayoutX() < 300){
                player.setScaleX(-1.0);
                pHand.setScaleX(1.0);
                pLeg.setScaleX(1.0);
                player.setLayoutX(player.getLayoutX() + plSpeed);
                pHand.setLayoutX(player.getLayoutX() + plSpeed - 20);
                pLeg.setLayoutX(player.getLayoutX() + plSpeed - 15);
            }
            if(left && player.getLayoutX() > 28){
                player.setScaleX(1.0);
                pHand.setScaleX(-1.0);
                pLeg.setScaleX(-1.0);
                player.setLayoutX(player.getLayoutX() - plSpeed);
                pHand.setLayoutX(player.getLayoutX() - plSpeed - player.getFitWidth()/2 - 10);
                pLeg.setLayoutX(player.getLayoutX() - plSpeed);
            }
            // isPause нажата и надпись не отображается
            if(isPause && !lblPause.isVisible()){
                plSpeed = 0;
                jumpDownSpeed = 0;
                parallelTransition.pause();
                enemyTransition.pause();
                lblPause.setVisible(true);
            } // isPause отпущена и надпись отображается
            else if(!isPause && lblPause.isVisible()){
                plSpeed = 3;
                jumpDownSpeed = 5;
                parallelTransition.play();
                enemyTransition.play();
                lblPause.setVisible(false);
            }

            scelHead.setLayoutX(skeleton.getLocalToSceneTransform().getTx()-skeleton.getFitWidth() + 75);
            scelSword.setLayoutX(skeleton.getLocalToSceneTransform().getTx()-skeleton.getFitWidth() + 55);


            if(scelHead.getBoundsInParent().intersects(pHand.getBoundsInParent()) ||
                    scelSword.getBoundsInParent().intersects(pHand.getBoundsInParent()) ||
                    scelSword.getBoundsInParent().intersects(pLeg.getBoundsInParent()) ||
                    scelHead.getBoundsInParent().intersects(pLeg.getBoundsInParent()))
            {
                lblGameOver.setVisible(true);
                plSpeed = 0;
                jumpDownSpeed = 0;
                parallelTransition.pause();
                enemyTransition.pause();
            }
        }
    };
    @FXML
    void initialize() {
        TranslateTransition backgroundOneTransition = getTranslateTransition(bgCity);
        TranslateTransition backgroundTwoTransition = getTranslateTransition(bgCity2);
        parallelTransition = new ParallelTransition(backgroundOneTransition, backgroundTwoTransition);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();

        timer.start();

        enemyTransition = getEnemyTransition(skeleton, 3500, 200);
        enemyTransition.play();
    }

    private TranslateTransition getTranslateTransition(ImageView background){
        // Объект анимации
        TranslateTransition backgroundTransition =
                new TranslateTransition(Duration.millis(5000), background);
        // Начало старта анимации
        backgroundTransition.setFromX(0);
        // Движение влево
        backgroundTransition.setToX(BG_WIDTH * -1);
        // Как идет движение
        backgroundTransition.setInterpolator(Interpolator.LINEAR);

        return backgroundTransition;
    }

    private TranslateTransition getEnemyTransition(ImageView enemyGroup, double duration, int shift){
        TranslateTransition enemyTransition =
                new TranslateTransition(Duration.millis(duration), enemyGroup);
        enemyTransition.setFromX(shift);
        // Движение влево. Для врага
        enemyTransition.setToX(BG_WIDTH * -1 - shift);
        enemyTransition.setInterpolator(Interpolator.LINEAR);
        enemyTransition.setCycleCount(Animation.INDEFINITE);
        return enemyTransition;
    }
}
