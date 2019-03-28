package Database;

import JavaFX.Login;
import Game.GameEngine;

public class User {

    public static int getElo(int user_id){
        DBOps db = new DBOps();
        return Integer.parseInt(db.exQuery("SELECT ELOrating FROM User WHERE user_id = " + user_id+ "; ", 1).get(0));
    }

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

    public static void updateElo(int user_id, int elo){
                DBOps db = new DBOps();
                db.exUpdate("UPDATE User SET ELOrating = " + elo + " WHERE user_id = " + user_id + ";");
                db.exUpdate("INSERT INTO userElo VALUES(" + user_id + ", DEFAULT, " +  elo + ");");
                System.out.println("updated ELO for user " + user_id);
    }
    public static void updateEloByGame(int game_id) {
        System.out.println("started updating ELO");

        Thread t = new Thread(new Runnable() {
            public void run() {
                int user_id1 = Game.getUser_id1(game_id);
                int user_id2 = Game.getUser_id2(game_id);
                int result = Game.getResult(game_id);

                int ELOuser1 = getElo(user_id1);
                int ELOuser2 = getElo(user_id2);
                int a = 0;
                if(result == user_id2){
                    a = 1;
                }else if(result == 0){
                    a = 2;
                }
                int [] elo = GameEngine.getElo(ELOuser1, ELOuser2, a);
                System.out.println(" white ELO: "+ elo[0] + " black ELO: " + elo[1]);
                updateElo(user_id1, elo[0]);
                updateElo(user_id2, elo[1]);

            }
        });
        t.start();
    }
}
