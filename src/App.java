import java.time.LocalDate;

public class App {          // тестване
    public static void main(String[] args) {
        Room room = new Room(213, 2, 3, false, true);
        System.out.println(room.toString());
        Reservation reservation = new Reservation(room.getNumber(), LocalDate.parse("2013-12-02"), LocalDate.parse("2013-12-13"), "Davidovi", 3);
        System.out.println(reservation.toString());
    }
}
