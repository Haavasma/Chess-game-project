package Database;

import JavaFX.Login;

public class User {

    public static void updateGamesPlayed(){
        Thread t = new Thread(new Runnable() {
            public void run() {
                DBOps db = new DBOps();
                int gamesPlayed = Integer.parseInt(db.exQuery("SELECT COUNT(game_id) FROM Game WHERE (user_id1 = "
                        + Login.userID +" OR user_id2 = " + Login.userID+ ") AND result IS NOT NULL;",1).get(0));
                db.exUpdate("UPDATE User SET gamesPlayed = " + gamesPlayed + " WHERE user_id = " + Login.userID);
            }
        });
        t.start();
    }

    public static void updateGamesWon(){
        Thread t = new Thread(new Runnable() {
            public void run() {
                DBOps db = new DBOps();
                int gamesWon = Integer.parseInt(db.exQuery("SELECT COUNT(game_id) FROM Game WHERE result ="
                        + Login.userID + ";",1).get(0));
                System.out.println(gamesWon);
                db.exUpdate("UPDATE User SET gamesWon = " + gamesWon + " WHERE user_id = " + Login.userID);
            }
        });
        t.start();
    }
    public static void updateGamesLost(){
        Thread t = new Thread(new Runnable() {
            public void run() {
                DBOps db = new DBOps();
                int gamesLost = Integer.parseInt(db.exQuery("SELECT COUNT(game_id) FROM Game WHERE (user_id1 = "
                        + Login.userID +" OR user_id2 = " + Login.userID+ ") AND result != " + Login.userID,1).get(0));
                db.exUpdate("UPDATE User SET gamesLost = " + gamesLost + " WHERE user_id = " + Login.userID);
            }
        });
        t.start();
    }

    public static void updateUser(){
        updateGamesPlayed();
        updateGamesLost();
        updateGamesWon();
    }
}