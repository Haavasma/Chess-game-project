package JavaFX.GameScene;

import Database.Game;
import Database.User;
import JavaFX.GameScene.ChessGame;
import JavaFX.GameScene.GameOverPopupBox;
import JavaFX.MainScene.MainScene;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DrawOfferPopupBox {
    static Stage window = new Stage();
    public static void Display() {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Draw Offer");
        //Labels
        Label titleLabel = new Label("Draw Offer");
        titleLabel.setFont(Font.font("Copperplate", 26));
        titleLabel.setStyle("-fx-font-weight: bold");
        titleLabel.setTextFill(Color.WHITE);

        Label textLabel = new Label("Your opponent has offered a draw!");
        textLabel.setFont(Font.font("Copperplate", 22));
        textLabel.setStyle("-fx-font-weight: bold");
        textLabel.setTextFill(Color.WHITE);
        MainScene.inDrawOffer = true;

        Button acceptDrawButton = new Button("Accept");
        acceptDrawButton.setOnAction(e -> {
            MainScene.inGame = false;
            ChessGame.isDone = true;
            Game.setResult(ChessGame.gameID, 0);
            User.updateEloByGame(ChessGame.gameID);
            GameOverPopupBox.Display();
            window.close();

        });
        Button declineDrawButton = new Button("Decline");
        declineDrawButton.setOnAction(e -> {
            MainScene.inDrawOffer = false;
            int result = Game.getResult(ChessGame.gameID);
            if (result == 2 || result == 1)
                Game.setResult(ChessGame.gameID, -1);
            window.close();
        });


        BorderPane windowLayout = new BorderPane();
        GridPane mainLayout = new GridPane();
        mainLayout.setHgap(35);
        mainLayout.setVgap(20);
        mainLayout.setPadding(new Insets(30, 40, 30, 40));
        mainLayout.add(titleLabel, 0, 0, 2, 1);
        mainLayout.add(textLabel, 0, 1, 2, 1);
        mainLayout.setHalignment(textLabel, HPos.CENTER);
        mainLayout.setHalignment(titleLabel, HPos.CENTER);

        GridPane bottomLayout = new GridPane();
        bottomLayout.getColumnConstraints().add(new ColumnConstraints(230));
        bottomLayout.getColumnConstraints().add(new ColumnConstraints(230));
        bottomLayout.setPadding(new Insets(0,50,15,50));
        bottomLayout.add(acceptDrawButton, 0,0);
        bottomLayout.setHalignment(acceptDrawButton, HPos.LEFT);
        bottomLayout.add(declineDrawButton, 1, 0);
        //bottomLayout.setHalignment(declineDrawButton, HPos.LEFT);
        windowLayout.setCenter(mainLayout);
        windowLayout.setBottom(bottomLayout);
        windowLayout.setStyle("-fx-background-color: #404144;");

        Scene scene = new Scene(windowLayout, 440, 360);
        window.setScene(scene);
        window.showAndWait();
    }
    public static void close(){
        window.close();
    }
}