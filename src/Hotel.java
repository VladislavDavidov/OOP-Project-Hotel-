import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class Hotel {
    List<Room> rooms = new ArrayList<>();
    List<Reservation> reservations = new ArrayList<>();


    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public void checkIn (int roomNumber, LocalDate from, LocalDate to, String note, int guests) {
        for (Room room : rooms) {
            if (roomNumber == room.getNumber() && !room.isOccupied()) {
                reservations.add(new Reservation(roomNumber, from, to, note, guests));
                room.setOccupied(true);
                System.out.println("Room number " + roomNumber + " has been occupied.");
                return;
            }
        }
    }

    public void setData(List<Room> rooms, List<Reservation> reservations) {
        this.rooms = rooms;
        this.reservations = reservations;
    }
}
