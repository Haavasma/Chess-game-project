package JavaFX;

import Database.DBOps;
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
import java.util.ArrayList;

import static JavaFX.Login.*;
import static JavaFX.MainScene.showMainScene;
import static JavaFX.Settings.settingsScene;
import static JavaFX.Settings.showSettings;
import static JavaFX.UserProfile.setAvatar;
import static JavaFX.UserProfile.showUserProfileScene;

@SuppressWarnings("Duplicates")
class FindUser{
    static Scene findProfileScene;
    static Image findAvatarImage;
    static ImageView findAvatarImageView;
    static Label gamesInfoLabel;
    static String gamesInfoString;

    //This will be changed by SQL-queries
    static String findUser_USERNAME;
    static String findUser_AvatarString;
    static int findUser_gamesPlayed;
    static int findUser_gamesWon;
    static int findUser_gamesLost;
    static int findUser_gamesRemis;
    static int findUser_ELOrating;

    static void showFindUserScene(){
        GridPane mainLayout = new GridPane();
        gamesInfoString = "";
        findAvatarImage = new Image("Images/Avatars/emptyAvatar.png");
        findAvatarImageView = new ImageView(findAvatarImage);

        String userTitle = "Find User";
        Label title = new Label(userTitle);
        title.setFont(Font.font("Copperplate", 40));
        title.setStyle("-fx-font-weight: bold");
        title.setTextFill(Color.WHITE);


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
        MenuItem findUserMenuItem = new MenuItem("Find User");
        findUserMenuItem.setOnAction(e -> showFindUserScene());
        userMenu.getItems().add(findUserMenuItem);
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
        gamesInfoLabel = new Label(gamesInfoString);
        gamesInfoLabel.setFont(Font.font("Copperplate", 25));
        gamesInfoLabel.setStyle("-fx-font-weight: bold");
        gamesInfoLabel.setTextFill(Color.WHITE);
        findAvatarImageView = new ImageView(findAvatarImage);
        findAvatarImageView.setFitHeight(250);
        findAvatarImageView.setFitWidth(250);
        rightGrid.add(findAvatarImageView, 0, 0);
        rightGrid.add(gamesInfoLabel, 0, 1);

        //Left GridPane
        GridPane leftGrid = new GridPane();
        leftGrid.setPadding(new Insets(30, 50, 20, 50));
        leftGrid.setHgap(10);
        leftGrid.setVgap(10);
        Label usernameLabel = new Label("Username: ");
        usernameLabel.setFont(Font.font("Copperplate", 30));
        usernameLabel.setStyle("-fx-font-weight: bold");
        usernameLabel.setTextFill(Color.WHITE);
        TextField searchField = new TextField();
        searchField.setPrefSize(200, 30);
        Label searchComment = new Label("");
        searchComment.setTextFill(Color.RED);
        Button searchButton = new Button("Search");
        searchButton.setPrefSize(100, 30);
        searchButton.setOnAction(e -> {
            if(searchForUser(searchField.getText())){
                setUserPane();
            } else {
                searchComment.setText("User doesn't exist");
            }
        });
        leftGrid.add(usernameLabel, 0,0,2,1);
        leftGrid.add(searchField, 0, 1);
        leftGrid.add(searchButton, 1, 1);
        leftGrid.add(searchComment, 0, 2, 2, 1);

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
        findProfileScene = new Scene(layout, 1200, 800);
        Main.window.setScene(findProfileScene);
    }

    static void setUserPane(){
        gamesInfoString = "User: " + findUser_USERNAME + "\nGames Played: " + findUser_gamesPlayed
                + "\nGames Won: " + findUser_gamesWon + "\nGames Lost: " + findUser_gamesLost +
                "\nRemis: " + findUser_gamesRemis + "\nElo-rating: " + findUser_ELOrating;
        gamesInfoLabel.setText(gamesInfoString);
        findAvatarImageView.setImage(new Image("Images/Avatars/" + findUser_AvatarString));
    }

    static boolean searchForUser(String username){
        DBOps connection = new DBOps();
        ArrayList<String> result = connection.exQuery("SELECT avatar, gamesPlayed, gamesWon, gamesLost, gamesRemis, ELOrating FROM User where username = '" + username + "';", 6);
        if(result.size() > 0) {
            findUser_USERNAME = username;
            findUser_AvatarString = result.get(0);
            findUser_gamesPlayed = Integer.parseInt(result.get(1));
            findUser_gamesWon = Integer.parseInt(result.get(2));
            findUser_gamesLost = Integer.parseInt(result.get(3));
            findUser_gamesRemis = Integer.parseInt(result.get(4));
            findUser_ELOrating = Integer.parseInt(result.get(5));
            return true;
        }
        return false;
    }



}