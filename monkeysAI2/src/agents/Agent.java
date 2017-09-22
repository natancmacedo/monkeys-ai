package agents;

public class Agent {

    private Position place;
    private String name;
    private int index;

    public Position getPlace() {
        return place;
    }

    public void setPlace(Position place) {
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Agent() {
        java.util.Random random = new java.util.Random();
        int x = random.nextInt(Florest.getMapSizeX());
        int y = random.nextInt(Florest.getMapSizeY());

        this.setPlace(new Position(x, y));

        Florest.getMap()[this.getPlace().getX()][this.getPlace().getY()] = this;
    }

    public void Move() {
        java.util.Random random = new java.util.Random();

        int x = random.nextInt(Florest.getMap().length);
        int y = random.nextInt(Florest.getMap()[0].length);

        Position newPosition = new Position(x, y);

        while (Florest.getMap()[newPosition.getX()][newPosition.getY()] != null) {
            x = random.nextInt(Florest.getMap().length);
            y = random.nextInt(Florest.getMap()[0].length);
            newPosition = new Position(x, y);
        }

        Florest.getMap()[getPlace().getX()][getPlace().getY()] = null;
        Florest.getMap()[newPosition.getX()][newPosition.getY()] = this;

        setPlace(newPosition);
    }

}
