package JavaFX;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static JavaFX.Login.*;
import static JavaFX.MainScene.showMainScene;

@SuppressWarnings("Duplicates")
class UserProfile{
    static Scene userProfileScene;
    static Image avatar;
    static ImageView avatarImageview;


    static void showUserProfileScene(){
        GridPane mainScreen = new GridPane();
        avatar = new Image("Images/Avatars/" + AVATAR + ".jpg");
        String userTitle = "User: " + USERNAME;
        Label title = new Label(userTitle);
        title.setFont(Font.font("Calibri", 40));
        title.setStyle("-fx-font-weight: bold");
        title.setTextFill(Color.WHITE);

        //Buttons
        Button backToMain = new Button("Back");
        backToMain.setOnAction(e -> {
            showMainScene();
        });

        Button nextAvatar = new Button();
        Image imageNextAvatar = new Image("Images/ButtonImages/ArrowRight.jpg");
        ImageView imageViewNextAvatar = new ImageView(imageNextAvatar);
        imageViewNextAvatar.setFitWidth(30);
        imageViewNextAvatar.setFitHeight(30);
        nextAvatar.setGraphic(imageViewNextAvatar);
        nextAvatar.setOnAction(e -> {
            increaseAvatar();
            avatarImageview.setImage(new Image("Images/Avatars/" + AVATAR + ".jpg"));
        });

        Button lastAvatar = new Button();
        Image imageLastAvatar = new Image("Images/ButtonImages/ArrowLeft.jpg");
        ImageView imageViewLastAvatar = new ImageView(imageLastAvatar);
        imageViewLastAvatar.setFitWidth(30);
        imageViewLastAvatar.setFitHeight(30);
        lastAvatar.setGraphic(imageViewLastAvatar);
        lastAvatar.setOnAction(e -> {
            decreaseAvatar();
            avatarImageview.setImage(new Image("Images/Avatars/" + AVATAR + ".jpg"));
        });


        //layout
        mainScreen.setPadding(new Insets(20, 20, 20, 20));
        mainScreen.setHgap(20);
        mainScreen.setVgap(12);
        mainScreen.add(backToMain, 0, 0);
        mainScreen.setHalignment(backToMain, HPos.CENTER);
        mainScreen.add(title, 1, 1, 3, 1);
        mainScreen.setHalignment(title, HPos.CENTER);
        avatarImageview = new ImageView(avatar);
        avatarImageview.setFitHeight(200);
        avatarImageview.setFitWidth(200);
        mainScreen.add(avatarImageview, 1, 2, 3, 1);
        mainScreen.add(nextAvatar, 1, 3, 3, 1);
        mainScreen.setHalignment(nextAvatar, HPos.RIGHT);
        mainScreen.add(lastAvatar, 1, 3, 3, 1);
        mainScreen.setHalignment(lastAvatar, HPos.LEFT);






        //Set backgroudn to a hex-color-value
        mainScreen.setStyle("-fx-background-color: #000000;");

        userProfileScene = new Scene(mainScreen, 800, 800);
        Main.window.setScene(userProfileScene);
    }

    static void increaseAvatar(){
        if(AVATAR.equals("avatar1")) {
            AVATAR = "avatar2";
        }else if(AVATAR.equals("avatar2")) {
            AVATAR = "avatar3";
        }else if(AVATAR.equals("avatar3")) {
            AVATAR = "avatar4";
        }else if(AVATAR.equals("avatar4")) {
            AVATAR = "avatar5";
        }else if(AVATAR.equals("avatar5")) {
            AVATAR = "avatar6";
        }else if(AVATAR.equals("avatar6")) {
            AVATAR = "avatar7";
        }else if(AVATAR.equals("avatar7")) {
            AVATAR = "avatar8";
        }else if(AVATAR.equals("avatar8")) {
            AVATAR = "avatar9";
        }else if(AVATAR.equals("avatar9") || AVATAR.equals("avatar10")) {
            AVATAR = "avatar10";
        }
    }

    static void decreaseAvatar(){
        if(AVATAR.equals("avatar1") || AVATAR.equals("avatar2")) {
            AVATAR = "avatar1";
        }else if(AVATAR.equals("avatar3")) {
            AVATAR = "avatar2";
        }else if(AVATAR.equals("avatar4")) {
            AVATAR = "avatar3";
        }else if(AVATAR.equals("avatar5")) {
            AVATAR = "avatar4";
        }else if(AVATAR.equals("avatar6")) {
            AVATAR = "avatar5";
        }else if(AVATAR.equals("avatar7")) {
            AVATAR = "avatar6";
        }else if(AVATAR.equals("avatar8")) {
            AVATAR = "avatar7";
        }else if(AVATAR.equals("avatar9")) {
            AVATAR = "avatar8";
        }else if(AVATAR.equals("avatar10")) {
            AVATAR = "avatar9";
        }

    }

    static void setAvatar(String avatar){
        AVATAR = avatar;
        String url = "jdbc:mysql://mysql.stud.idi.ntnu.no:3306/martijni?user=martijni&password=wrq71s2w";
        try(Connection con = DriverManager.getConnection(url)) {
            Statement stmt = con.createStatement();
            //Insert into database
            String sqlQuery = "UPDATE User SET avatar = '" + avatar +"' WHERE username = '" + USERNAME + "';";
            int rowsAffected = stmt.executeUpdate(sqlQuery);
        }catch (Exception sq) {
            System.out.println("SQL-Feil: " + sq);
        }
    }



}
