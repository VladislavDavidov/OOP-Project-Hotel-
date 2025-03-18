public class Room {
    private int number;
    private int floor;
    private int beds;
    private boolean hasCableTV;
    private boolean isOccupied;

    public Room(int number, int floor, int beds, boolean hasCableTV, boolean isOccupied) {
        this.number = number;
        this.floor = floor;
        this.beds = beds;
        this.hasCableTV = hasCableTV;
        this.isOccupied = isOccupied;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public boolean isHasCableTV() {
        return hasCableTV;
    }

    public void setHasCableTV(boolean hasCableTV) {
        this.hasCableTV = hasCableTV;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    @Override
    public String toString() {          //тестване
        return "Room number " + number +
                " on floor " + floor +
                " has " + beds + " beds.\n" +
                "Is it occupied: " + isOccupied + "\n" +
                "Does it have a cable tv: " + hasCableTV;
    }
}
