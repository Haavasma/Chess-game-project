package Pieces;

import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

public abstract class Piece{
    private boolean color;
    private int x;
    private int y;

    public Piece(boolean color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public boolean getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public abstract char getNotation();

    public abstract ImageView getImageView();

    public String toString() {
        return getNotation() + "" + ((char) (getX() + 'a')) + "" + (getY() + 1);
    }

    // Equals method for use in JUnit tests
    @Override
    public boolean equals(Object o){
        // If object is compared with itself
        if(o == this){
            return true;
        }

        if(!(o instanceof Piece)){
            return false;
        }

        if(this.getClass().equals(o.getClass())){
            if(this.getX() == ((Piece) o).getX() && this.getY() == ((Piece) o).getY()) {
                return true;
            }
        }

        return false;
    }

    //public abstract boolean move(int x, int y); //Takes chosen coordinates as arguments and compares them to the array returned from legalMove.

    //public abstract int[][] legalMove(int a, int b); //Takes the coordinates of the desired piece as arguments. Returns array of legal moves
}
