import java.util.List;

public class Wrapper {
    private List<Room> rooms;
    private List<Reservation> reservations;

    public Wrapper(List<Room> rooms, List<Reservation> reservations) {
        this.rooms = rooms;
        this.reservations = reservations;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
