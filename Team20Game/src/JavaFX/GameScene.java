package JavaFX;
import Database.Game;
import Database.User;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
//import sun.security.pkcs11.Secmod;


@SuppressWarnings("Duplicates")
public class GameScene {
    static Scene gameScene;
    static Timer yourTimer;
    static Timer opponentTimer;
    public static int yourTime = Game.getTime(ChessGame.gameID) * 60;
    static int opponentTime = Game.getTime(ChessGame.gameID)*60;
    static Label yourClock;
    static Label opponentClock;
    static boolean yourIncrement = true;
    static boolean opponentIncrement = true;

    static int increment = Game.getIncrement(ChessGame.gameID);
    public static ArrayList<String> allMoves = new ArrayList<>();
    public static ArrayList<String> whiteMoves = new ArrayList<>();
    public static ArrayList<String> blackMoves = new ArrayList<>();
    public static ArrayList<Integer> moveNumbers = new ArrayList<>();

    private static TableView table = new TableView();

    //Stats which will be initialized with DBOps while starting a game
    static String player1;
    static String player2;
    public static boolean remiOffered;
    public static Button offerDrawButton;

    static void updateMoves(){
        String s = allMoves.get(allMoves.size() - 1);
        String help = "0a";
        int letter = (int)s.charAt(1) - (int)help.charAt(0) + (int)(help.charAt(1));
        s = (char)(letter) + Character.toString(s.charAt(0));

        if (allMoves.size() % 2 == 0){
            blackMoves.add(s);
        }
        else{
            whiteMoves.add(s);
            moveNumbers.add((allMoves.size() + 1) / 2);
        }
    }

    static void showGameScene() {
        yourTimer = new Timer();
        opponentTimer = new Timer();
        yourClock = new Label(secToMinSec(yourTime));
        opponentClock = new Label(secToMinSec(opponentTime));


        yourClock.setFont(Font.font("Ubuntu", 30));
        yourClock.setStyle("-fx-font-weight: bold");
        yourClock.setTextFill(Color.WHITE);

        opponentClock.setFont(Font.font("Ubuntu", 30));
        opponentClock.setStyle("-fx-font-weight: bold");
        opponentClock.setTextFill(Color.WHITE);



        Label title = new Label("Recess Chess");
        title.setFont(Font.font("Georgia", 60));
        title.setStyle("-fx-font-weight: bold");
        title.setTextFill(Color.WHITE);



        int userid1 = Game.getUser_id1(ChessGame.gameID);
        int userid2 = Game.getUser_id2(ChessGame.gameID);

        player1 = User.getUsername(userid1) + " (" + User.getElo(userid1) + ")";
        player2 = User.getUsername(userid2) + " (" + User.getElo(userid2) + ")";

        //Now im going to code the centerPane, which have to consist of one GridPane, with 2x2 cols/rows. Col 0, row 0 will consist of the title with colspan 2, rowspan 1
        //Column 0, row 2 will have the buttons, and column 1, row 2 will have a sandobox chessboard

        //Left GridPane
        GridPane leftGrid = new GridPane();
        leftGrid.setPadding(new Insets(110, 0, 0, 0));
        Label chatLabel = new Label("Chat");
        chatLabel.setTextFill(Color.WHITE);
        chatLabel.setFont(Font.font("Georgia", 30));
        leftGrid.add(chatLabel, 0, 0);
        leftGrid.add(ChatFX.createChat(), 0, 1);

        GridPane centerGrid = new GridPane();
        centerGrid.setPadding(new Insets(70, 100, 100, 0));
        Parent chessGame = new ChessGame().setupBoard();
        centerGrid.add(chessGame, 0, 0);

        //Right GridPane
        GridPane rightGrid = new GridPane();
        rightGrid.setPadding(new Insets(70, 0, 50, 20));
        rightGrid.setHgap(10);
        rightGrid.setVgap(10);


        /*
        Label gameidLabel = new Label("GameID: " + ChessGame.gameID);
        gameidLabel.setFont(Font.font("Copperplate", 40));
        gameidLabel.setStyle("-fx-font-weight: bold");
        gameidLabel.setTextFill(Color.WHITE);
        rightGrid.add(gameidLabel, 0, 0);
        */
        //Label playersLabel = new Label(player1 + " vs " + player2);
        Label playerOne = new Label(player1);
        Label playerTwo = new Label(player2);
        //playersLabel.setFont(Font.font("Copperplate", 25));
        //playersLabel.setStyle("-fx-font-weight: bold");
        //playersLabel.setTextFill(Color.LIGHTSKYBLUE);
        //rightGrid.add(playersLabel, 0, 1);
        playerOne.setFont(Font.font("Copperplate", 25));
        playerOne.setStyle("-fx-font-weight: bold");
        playerOne.setTextFill(Color.LIGHTSKYBLUE);
        playerTwo.setFont(Font.font("Copperplate", 25));
        playerTwo.setStyle("-fx-font-weight: bold");
        playerTwo.setTextFill(Color.LIGHTSKYBLUE);

        if (Login.userID == userid1) {
            rightGrid.add(playerTwo, 0, 0);
            rightGrid.add(playerOne, 0, 4);

        } else {
            rightGrid.add(playerOne, 0, 0);
            rightGrid.add(playerTwo, 0, 4);
        }


        Label time1label = new Label(player1);
        time1label.setFont(Font.font("Copperplate", 40));
        time1label.setStyle("-fx-font-weight: bold");
        Label time2label = new Label(player2);
        time2label.setFont(Font.font("Copperplate", 40));
        time2label.setStyle("-fx-font-weight: bold");
        /*GameTimerFX player1Time = new GameTimerFX();
        GameTimerFX player2Time = new GameTimerFX();
        rightGrid.add(player1Time.startTime(Game.getTime(ChessGame.gameID), Game.getIncrement(ChessGame.gameID)), 0, 3);
        rightGrid.add(player1Time.startTime(Game.getTime(ChessGame.gameID), Game.getIncrement(ChessGame.gameID)), 0, 4);
        rightGrid.add(time1label, 1, 3);
        rightGrid.add(time2label, 1, 4);
        */


        //forfeitButton
        Button resignButton = new Button("Resign");
        resignButton.setStyle("-fx-background-color: #ff0000");
        resignButton.setTextFill(Color.WHITE);

        resignButton.setOnAction(e->{
            MainScene.inGame = false;
            ChessGame.isDone = true;
            Game.setResult(ChessGame.gameID, ChessGame.color?Game.getUser_id2(ChessGame.gameID):Game.getUser_id1(ChessGame.gameID));
            User.updateEloByGame(ChessGame.gameID);
            GameOverPopupBox.Display();
        });

        rightGrid.add(resignButton, 1, 2);

        offerDrawButton = new Button("Offer draw");
        offerDrawButton.setOnAction(e->{
            if(!remiOffered) {
                int a = ChessGame.color ? 1 : 2;
                Game.setResult(ChessGame.gameID, a);
                remiOffered = true;
                offerDrawButton.setText("Draw offered");
                offerDrawButton.setOpacity(0.5);
            }
        });
        rightGrid.add(offerDrawButton, 0, 2);

        if (yourTime != 0) {
            rightGrid.add(yourClock, 0, 3);
            rightGrid.add(opponentClock, 0, 1);
        }



        //mainLayout
        GridPane mainLayout = new GridPane();
        mainLayout.setPadding(new Insets(20, 50, 20, 50));
        mainLayout.setHgap(20);
        mainLayout.setVgap(12);
        mainLayout.getColumnConstraints().add(new ColumnConstraints(250));
        mainLayout.getColumnConstraints().add(new ColumnConstraints(675));
        mainLayout.getColumnConstraints().add(new ColumnConstraints(300));
        mainLayout.add(title, 0, 0, 3, 1);
        mainLayout.setHalignment(title, HPos.CENTER);
        mainLayout.add(leftGrid, 0, 1);
        mainLayout.setHalignment(leftGrid, HPos.CENTER);
        mainLayout.add(centerGrid, 1,1);
        mainLayout.setHalignment(centerGrid, HPos.CENTER);
        mainLayout.add(rightGrid, 2,1);
        mainLayout.setHalignment(rightGrid, HPos.CENTER);

        //mainLayout.setGridLinesVisible(true);

        //Set image as background
        BackgroundImage myBI= new BackgroundImage(new Image("Images/Backgrounds/darkwood.jpg",1200,1200,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        mainLayout.setBackground(new Background(myBI));


        BorderPane layout = new BorderPane();
        //layout.setTop(new WindowMenuBar().getWindowMenuBar());
        layout.setCenter(mainLayout);

        gameScene = new Scene(layout, 1450, 950);
        Main.window.setScene(gameScene);
        ChatFX.refresh();
    }

    public static String secToMinSec(int time) {
        if (time < 60) {
            return "00:"+time;
        }
        int min = (time % 3600) / 60;
        int sec = ((time % 3600) % 60);
        if (Math.floor(Math.log10((double)min)+1) < 2 && sec == 0) {
            return "0" + min + ":00";
        }
        if (Math.floor(Math.log10((double)min)+1) >= 2 && sec == 0) {
            return min + ":00";
        }
        if (Math.floor(Math.log10((double)min)+1) < 2 && Math.floor(Math.log10((double)sec)+1) < 2) {
            return "0" + min + ":0" + sec;
        }
        if (Math.floor(Math.log10((double)min)+1) >= 2 && Math.floor(Math.log10((double)sec)+1) < 2) {
            return min + ":0" + sec;
        }
        if (Math.floor(Math.log10((double)min)+1) < 2 && Math.floor(Math.log10((double)sec)+1) >= 2) {
            return "0" + min + ":" + sec;
        }
        return min + ":" + sec;
    }

    private static final int setInterval() {
        if (yourTime == 1)
            yourTimer.cancel();
        if (opponentTime == 1 ) {
            opponentTimer.cancel();
        }
        if (ChessGame.myTurn) {
            if (opponentIncrement) {
                opponentTime += increment + 1;
                opponentIncrement = false;
                yourIncrement = true;
            }
            return yourTime--;
        } else {
            if (yourIncrement) {
                yourTime += increment + 1;
                yourIncrement = false;
                opponentIncrement = true;
            }
            return opponentTime--;
        }

    }

    public static void refresh() {
       yourTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                serviceTimer();
            }
        }, 1000, 1000);
    }

    public static void serviceTimer() {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        //Background work
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (ChessGame.myTurn) {

                                        yourClock.setText(secToMinSec(setInterval()));
                                        opponentClock.setText(secToMinSec(opponentTime));
                                    } else {
                                        opponentClock.setText(secToMinSec(setInterval()));
                                        yourClock.setText(secToMinSec(yourTime));
                                    }

                                } finally {
                                    latch.countDown();
                                }
                            }
                        });
                        latch.await();
                        return null;
                    }
                };
            }
        };
        service.start();
    }
}