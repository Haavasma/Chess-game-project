package JavaFX;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jdk.nashorn.api.tree.NewTree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static JavaFX.Login.*;
import static JavaFX.MainScene.showMainScene;
import static JavaFX.Settings.settingsScene;
import static JavaFX.Settings.showSettings;

@SuppressWarnings("Duplicates")
class UserProfile{
    static Scene userProfileScene;
    static Image avatar;
    static ImageView avatarImageview;


    static void showUserProfileScene(){
        GridPane mainLayout = new GridPane();
        avatar = new Image("Images/Avatars/" + AVATAR);

        String userTitle = "User Profile";
        Label title = new Label(userTitle);
        title.setFont(Font.font("Copperplate", 40));
        title.setStyle("-fx-font-weight: bold");
        title.setTextFill(Color.WHITE);

        String gamesInfoString = "User: " + USERNAME + "\nGames Played: " + gamesPlayed
                + "\nGames Won: " + gamesWon + "\nGames Lost: " + gamesLost
                + "\nElo-rating: " + ELOrating;
        Label gamesInfoLabel = new Label(gamesInfoString);
        gamesInfoLabel.setFont(Font.font("Copperplate", 25));
        gamesInfoLabel.setStyle("-fx-font-weight: bold");
        gamesInfoLabel.setTextFill(Color.WHITE);


        //Buttons
        Button backToMainButton = new Button("Back");
        Image imageBackToMain = new Image("Images/ButtonImages/ArrowLeft.png");
        ImageView imageViewBackToMain = new ImageView(imageBackToMain);
        imageViewBackToMain.setFitWidth(20);
        imageViewBackToMain.setFitHeight(20);
        backToMainButton.setGraphic(imageViewBackToMain);
        backToMainButton.setOnAction(e -> {
            showMainScene();
        });

        Button nextAvatar = new Button();
        Image imageNextAvatar = new Image("Images/ButtonImages/ArrowRight.png");
        ImageView imageViewNextAvatar = new ImageView(imageNextAvatar);
        imageViewNextAvatar.setFitWidth(30);
        imageViewNextAvatar.setFitHeight(30);
        nextAvatar.setGraphic(imageViewNextAvatar);
        nextAvatar.setOnAction(e -> {
            increaseAvatar();
            avatarImageview.setImage(new Image("Images/Avatars/" + AVATAR));
        });

        Button lastAvatar = new Button();
        Image imageLastAvatar = new Image("Images/ButtonImages/ArrowLeft.png");
        ImageView imageViewLastAvatar = new ImageView(imageLastAvatar);
        imageViewLastAvatar.setFitWidth(30);
        imageViewLastAvatar.setFitHeight(30);
        lastAvatar.setGraphic(imageViewLastAvatar);
        lastAvatar.setOnAction(e -> {
            decreaseAvatar();
            avatarImageview.setImage(new Image("Images/Avatars/" + AVATAR));
        });

        //Menubar
        Menu homeMenu = new Menu("Home");
        MenuItem homeMenuItem = new MenuItem("Go to Main Screen");
        homeMenuItem.setOnAction(e -> showMainScene());
        homeMenu.getItems().add(homeMenuItem);

        Menu gameMenu = new Menu("Game");
        MenuItem newGameItem = new MenuItem("New Game");
        newGameItem.setOnAction(e -> System.out.println("Launch new game"));
        MenuItem joinGameItem = new MenuItem("Join Game");
        joinGameItem.setOnAction(e -> System.out.println("Joining game"));
        gameMenu.getItems().addAll(newGameItem, joinGameItem);

        Menu userMenu = new Menu("User");
        MenuItem userProfileMenuItem = new MenuItem("User profile");
        userProfileMenuItem.setOnAction(e -> showUserProfileScene());
        userMenu.getItems().add(userProfileMenuItem);
        MenuItem logOutMenuItem = new MenuItem("Log out");
        logOutMenuItem.setOnAction(e -> {
            setAvatar(AVATAR);
            runLogin();
        });
        userMenu.getItems().add(logOutMenuItem);

        Menu settingsMenu = new Menu("Settings");
        MenuItem openSettings = new MenuItem("Go to settings");
        openSettings.setOnAction(e -> showSettings());
        settingsMenu.getItems().add(openSettings);

        Menu helpMenu = new Menu("Help");
        MenuItem howToLogIn = new MenuItem("How to log in");
        howToLogIn.setOnAction(e -> System.out.println("To log in you have to..."));
        helpMenu.getItems().add(howToLogIn);

        //Mainmenu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(homeMenu, gameMenu, userMenu, settingsMenu, helpMenu);

        
        //Now im going to code the centerPane, which have to consist of one GridPane, with 2x2 cols/rows. Col 0, row 0 will consist of the title with colspan 2, rowspan 1
        //Column 0, row 2 will have the buttons, and column 1, row 2 will have a sandobox chessboard

        //Right GridPane
        GridPane rightGrid = new GridPane();
        rightGrid.setVgap(40);
        rightGrid.setPadding(new Insets(50, 200, 100, 250));
        avatarImageview = new ImageView(avatar);
        avatarImageview.setFitHeight(250);
        avatarImageview.setFitWidth(250);
        rightGrid.add(avatarImageview, 1, 0, 3, 1);
        Label changeAvatarLabel = new Label("Change avatar");
        changeAvatarLabel.setFont(Font.font("Copperplate", 18));
        changeAvatarLabel.setStyle("-fx-font-weight: bold");
        changeAvatarLabel.setTextFill(Color.WHITE);
        rightGrid.add(changeAvatarLabel, 1, 1, 3, 1);
        rightGrid.setHalignment(changeAvatarLabel, HPos.CENTER);
        rightGrid.add(nextAvatar, 1, 1, 3, 1);
        rightGrid.setHalignment(nextAvatar, HPos.RIGHT);
        rightGrid.add(lastAvatar, 1, 1, 3, 1);
        rightGrid.setHalignment(lastAvatar, HPos.LEFT);
        rightGrid.add(gamesInfoLabel, 1, 2);

        //Left GridPane
        GridPane leftGrid = new GridPane();
        leftGrid.setPadding(new Insets(100, 10, 100, 100));
        Image eloImage = new Image("Images/elo.jpg");
        ImageView eloImageView= new ImageView(eloImage);
        eloImageView.setFitHeight(400);
        eloImageView.setFitWidth(550);
        leftGrid.add(eloImageView, 0, 0);

        //mainLayout
        mainLayout.setPadding(new Insets(20, 50, 20, 50));
        mainLayout.setHgap(20);
        mainLayout.setVgap(12);
        mainLayout.getColumnConstraints().add(new ColumnConstraints(550)); // column 0 is 400 wide
        mainLayout.getColumnConstraints().add(new ColumnConstraints(550)); // column 1 is 800 wide
        mainLayout.add(backToMainButton, 0, 0, 2, 1);
        mainLayout.setHalignment(backToMainButton, HPos.LEFT);
        mainLayout.add(title, 0, 0, 2, 1);
        mainLayout.setHalignment(title, HPos.CENTER);
        mainLayout.add(leftGrid, 0, 1);
        mainLayout.setHalignment(leftGrid, HPos.CENTER);
        mainLayout.add(rightGrid, 1,1);
        mainLayout.setHalignment(rightGrid, HPos.CENTER);

        //Set image as background
        BackgroundImage myBI= new BackgroundImage(new Image("Images/Backgrounds/Mahogny.jpg",1200,800,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        mainLayout.setBackground(new Background(myBI));

        
        BorderPane layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setCenter(mainLayout);
        userProfileScene = new Scene(layout, 1200, 800);
        Main.window.setScene(userProfileScene);
    }

    static void increaseAvatar(){
        if(AVATAR.equals("avatar1.jpg")) {
            AVATAR = "avatar2.png";
        }else if(AVATAR.equals("avatar2.png")) {
            AVATAR = "avatar3.png";
        }else if(AVATAR.equals("avatar3.png")) {
            AVATAR = "avatar4.jpg";
        }else if(AVATAR.equals("avatar4.jpg")) {
            AVATAR = "avatar5.png";
        }else if(AVATAR.equals("avatar5.png")) {
            AVATAR = "avatar6.png";
        }else if(AVATAR.equals("avatar6.png")) {
            AVATAR = "avatar7.png";
        }else if(AVATAR.equals("avatar7.png")) {
            AVATAR = "avatar8.png";
        }else if(AVATAR.equals("avatar8.png")) {
            AVATAR = "avatar9.png";
        }else if(AVATAR.equals("avatar9.png") || AVATAR.equals("avatar10.png")) {
            AVATAR = "avatar10.png";
        }
    }

    static void decreaseAvatar(){
        if(AVATAR.equals("avatar1.jpg") || AVATAR.equals("avatar2.png")) {
            AVATAR = "avatar1.jpg";
        }else if(AVATAR.equals("avatar3.png")) {
            AVATAR = "avatar2.png";
        }else if(AVATAR.equals("avatar4.jpg")) {
            AVATAR = "avatar3.png";
        }else if(AVATAR.equals("avatar5.png")) {
            AVATAR = "avatar4.jpg";
        }else if(AVATAR.equals("avatar6.png")) {
            AVATAR = "avatar5.png";
        }else if(AVATAR.equals("avatar7.png")) {
            AVATAR = "avatar6.png";
        }else if(AVATAR.equals("avatar8.png")) {
            AVATAR = "avatar7.png";
        }else if(AVATAR.equals("avatar9.png")) {
            AVATAR = "avatar8.png";
        }else if(AVATAR.equals("avatar10.png")) {
            AVATAR = "avatar9.png";
        }

    }

    static void setAvatar(String avatar){
        AVATAR = avatar;
        String url = "jdbc:mysql://mysql.stud.idi.ntnu.no:3306/martijni?user=martijni&password=wrq71s2w";
        try(Connection con = DriverManager.getConnection(url)) {
            Statement stmt = con.createStatement();
            //Insert into database
            String sqlQuery = "UPDATE User SET avatar = '" + avatar +"' WHERE username = '" + USERNAME + "';";
            stmt.executeUpdate(sqlQuery);
        }catch (Exception sq) {
            System.out.println("SQL-Feil: " + sq);
        }
    }



}
