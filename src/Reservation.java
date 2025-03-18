import java.time.LocalDate;

public class Reservation {
    private int room;
    private LocalDate from;
    private LocalDate to;
    private String note;
    private int guests;

    public Reservation(int room, LocalDate from, LocalDate to, String note, int guests) {
        this.room = room;
        this.from = from;
        this.to = to;
        this.note = note;
        this.guests = guests;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    @Override
    public String toString() {          //тестване
        return "Reservation{" +
                "room=" + room +
                ", from=" + from +
                ", to=" + to +
                ", note='" + note + '\'' +
                ", guests=" + guests +
                '}';
    }
}
