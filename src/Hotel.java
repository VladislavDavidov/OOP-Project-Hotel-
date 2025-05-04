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

    public void availability(LocalDate date){
        for (Reservation reservation : reservations) {
            if (!reservation.getFrom().equals(date) || (date.isAfter(reservation.getFrom()) && date.isBefore(reservation.getTo()))) {
                System.out.println("Room " + reservation.getRoom() + " is available");
            }
            else{
                System.out.println("Room " + reservation.getRoom() + " is NOT available");
            }
        }
    }

    public void checkOut(int number) {
        for (Room room : rooms) {
            if (number == room.getNumber() && room.isOccupied()) {
                room.setOccupied(false);
                for (int i = reservations.size() - 1; i >= 0; i--) {
                    if (reservations.get(i).getRoom() == number) {
                        reservations.remove(i);
                    }
                }
            }
            System.out.println("Checked out room " + number);
            return;
        }
        System.out.println("Room " + number + " has been checked out");
    }


    public void setData(List<Room> rooms, List<Reservation> reservations) {
        this.rooms = rooms;
        this.reservations = reservations;
    }


    @Override
    public String toString() {
        return "Hotel{" +
                "rooms=\n" + rooms +
                "\n, reservations=\n" + reservations +
                '}';
    }
}
