package bg.tu_varna.sit.b1.f23621673;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Reservation представя информация за резервация на стая в хотела.
 * Съдържа номер на стая, дати на престой, име на госта, брой гости и флаг за недостъпност.
 * Използва се от класа Hotel за всички резервационни действия.
 */

public class Reservation implements Serializable {
    private int roomNumber;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String guestName;
    private int guests;
    public boolean isUnavailable;

    public Reservation(int roomNumber, LocalDate fromDate, LocalDate toDate, String guestName, int guests, boolean isUnavailable) {
        this.roomNumber = roomNumber;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.guestName = guestName;
        this.guests = guests;
        this.isUnavailable = isUnavailable;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public boolean isUnavailable() {
        return isUnavailable;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getGuests() {
        return guests;
    }

    @Override
    public String toString() {
        return "Reservation: Room " + roomNumber +
                " from " + fromDate + " to " + toDate +
                " (Guest: " + guestName + ", Unavailable: " + isUnavailable + ")";
    }


    public boolean overlaps(LocalDate start, LocalDate end) {
        return !(end.isBefore(fromDate) || start.isAfter(toDate));
    }
}
