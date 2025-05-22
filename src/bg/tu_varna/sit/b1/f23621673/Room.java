package bg.tu_varna.sit.b1.f23621673;

import java.io.Serializable;

/**
 * Room представя хотелска стая с номер, брой легла и състояние заетост.
 * Използва се в системата за определяне на капацитет и наличност.
 */

public class Room  implements Serializable{
    private int number;
    private int beds;
    private boolean isOccupied;
    private String note;

    public Room(int number, int beds) {
        this.number = number;
        this.beds = beds;
        this.isOccupied = false;
        this.note = "";
    }

    public int getNumber() {
        return number;
    }

    public int getBeds() {
        return beds;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void checkIn(String note) {
        this.isOccupied = true;
        this.note = note;
    }

    public void checkOut() {
        this.isOccupied = false;
        this.note = "";
    }

    @Override
    public String toString() {
        return "Room " + number + " (Beds: " + beds + ", Occupied: " + isOccupied + ")";
    }
}
