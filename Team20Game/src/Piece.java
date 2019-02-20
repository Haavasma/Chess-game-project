public abstract class Piece {
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

    public String toString() {
        return getNotation() + "" + ((char) (getX() + 'A')) + "" + (getY() + 1);
    }
}