package agents;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        setX(x);
        setY(y);
    }

    public final int getX() {
        return x;
    }

    public final void setX(int value) {
        if (x >= Florest.getMapSizeX() && x < 0) {
            System.out.println("x inaceitável");
        } else {
            x = value;
        }
    }

    public final int getY() {
        return x;
    }

    public final void setY(int value) {
        if (y >= Florest.getMapSizeY() && y < 0) {
            System.out.println("y inaceitável");
        } else {
            y = value;
        }
    }

    @Override
    public String toString() {
        return "X: " + getX() + ", Y: " + getY();
    }
}
