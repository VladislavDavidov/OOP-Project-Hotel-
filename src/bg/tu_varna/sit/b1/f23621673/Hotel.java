package bg.tu_varna.sit.b1.f23621673;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Hotel е основният клас, който управлява списъка със стаи и резервации.
 * Поддържа логика за настаняване, освобождаване, търсене и справки.
 * Използва FileHandler за зареждане и запис на резервациите.
 */

public class Hotel implements Serializable {
    private List<Room> rooms;
    private List<Reservation> reservations;
    private FileHandler fileHandler;

    public Hotel() {
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.fileHandler = new FileHandler();
        loadRooms();
    }

    /**
     * Зарежда списъка с резервации от файл чрез FileHandler.
     */
    public void loadReservationsFromFile() {
        if (fileHandler.getFilePath() != null) {
            reservations = fileHandler.loadReservations();
        } else {
            System.out.println("No file path set. Cannot load reservations.");
        }
    }

    /**
     * Записва текущите резервации в зададения файл.
     */
    public void saveReservationsToFile() {
        if (fileHandler.isFileLoaded()) {
            fileHandler.saveReservations(reservations);
        } else {
            System.out.println("No file is currently open to save.");
        }
    }

    /**
     * Записва текущите резервации в нов файл и възстановява стария път.
     */
    public void saveReservationsAsNewFile(String newPath) {
        String originalPath = fileHandler.getFilePath();
        fileHandler.setFilePath(newPath);
        fileHandler.saveReservations(reservations);
        fileHandler.setFilePath(originalPath);
    }


    /**
     * Зарежда списъка със стаи по подразбиране.
     */
    public void loadRooms() {
        rooms.add(new Room(100, 2));
        rooms.add(new Room(101, 3));
        rooms.add(new Room(102, 4));
        rooms.add(new Room(103, 1));
        rooms.add(new Room(104, 2));
        System.out.println("Rooms loaded.");
    }

    /**
     * Добавя нова резервация (check-in), ако стаята е свободна в избрания период.
     */
    public void checkin(String args) {
        String[] parts = args.split(" ", 5);

        if (parts.length < 4) {
            System.out.println("Usage: checkin <roomNumber> <fromDate> <toDate> <guestName> [guests]");
            return;
        }


        try {
            int roomNumber = Integer.parseInt(parts[0]);
            LocalDate from = LocalDate.parse(parts[1]);
            LocalDate to = LocalDate.parse(parts[2]);
            String guestName = parts[3];

            int guests = (parts.length == 5) ? Integer.parseInt(parts[4]) : -1;


            Room room = null;
            for (Room r : rooms) {
                if (r.getNumber() == roomNumber) {
                    room = r;
                    break;
                }
            }

            if (room == null) {
                System.out.println("Room " + roomNumber + " does not exist.");
                return;
            }

            if (guests == -1) {
                guests = room.getBeds();
            } else if (guests > room.getBeds()) {
                System.out.println("Exceeds room capacity (" + room.getBeds() + " beds).");
                return;
            }

            if (from.isAfter(to)) {
                System.out.println("From date cannot be after To date.");
                return;
            }

            for (Reservation r : reservations) {
                if (r.getRoomNumber() == roomNumber && r.overlaps(from, to)) {
                    System.out.println("Room " + roomNumber + " is not available in this period.");
                    return;
                }
            }

            reservations.add(new Reservation(roomNumber, from, to, guestName, guests, false));
            System.out.println("Checked in Room " + roomNumber + " with Guest: " + guestName + " from " + from + " to " + to);

        } catch (NumberFormatException e) {
            System.out.println("Invalid room number or guests. Please use integers.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    /**
     * Освобождава стая, премахвайки всички активни (не "unavailable") резервации.
     */
    public void checkout(String roomStr) {
        try {
            int roomNumber = Integer.parseInt(roomStr);

            boolean removed = reservations.removeIf(r -> r.getRoomNumber() == roomNumber && !r.isUnavailable());
            if (removed) {
                System.out.println("Checked out room " + roomNumber + ". All active reservations for this room have been removed.");

            } else {
                System.out.println("No active reservations found for room " + roomNumber + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid room number. Please provide an integer.");
        }
    }

    /**
     * Показва всички налични стаи за конкретна дата.
     */
    public void availability(String dateStr) {

        LocalDate date = (dateStr == null || dateStr.isEmpty()) ? LocalDate.now() : LocalDate.parse(dateStr);


        Set<Integer> occupiedRoomNumbers = new HashSet<>();


        for (Reservation r : reservations) {
            if (r.overlaps(date, date)) {
                occupiedRoomNumbers.add(r.getRoomNumber());
            }
        }

        System.out.println("Available rooms for " + date + ":");
        boolean foundAvailable = false;
        for (Room room : rooms) {

            if (!occupiedRoomNumbers.contains(room.getNumber())) {
                System.out.println("Room " + room.getNumber() + " (Beds: " + room.getBeds() + ")");
                foundAvailable = true;
            }
        }


        if (!foundAvailable) {
            System.out.println("No rooms available on this date.");
        }
    }

    /**
     * Генерира справка за използването на стаи в зададен период.
     */
    public void report(String args) {
        String[] parts = args.split(" ");
        if (parts.length != 2) {
            System.out.println("Usage: report <fromDate> <toDate>");
            return;
        }
        try {
            LocalDate from = LocalDate.parse(parts[0]);
            LocalDate to = LocalDate.parse(parts[1]);

            if (from.isAfter(to)) {
                System.out.println("From date cannot be after To date.");
                return;
            }


            Map<Integer, Long> usage = new HashMap<>();


            for (Room room : rooms) {
                usage.put(room.getNumber(), 0L);
            }

            for (Reservation r : reservations) {

                if (!r.isUnavailable() && r.overlaps(from, to)) {
                    LocalDate start = r.getFromDate().isBefore(from) ? from : r.getFromDate();
                    LocalDate end = r.getToDate().isAfter(to) ? to : r.getToDate();

                    long days = ChronoUnit.DAYS.between(start, end) +1;

                    usage.put(r.getRoomNumber(), usage.getOrDefault(r.getRoomNumber(), 0L) + days);
                }
            }

            System.out.println("Room usage report from " + from + " to " + to + ":");
            usage.forEach((roomNumber, days) -> System.out.println("Room " + roomNumber + ": " + days + " days occupied"));

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    /**
     * Намира първата свободна стая със зададения брой легла.
     */
    public void find(String args) {
        String[] parts = args.split(" ");
        if (parts.length != 3) {
            System.out.println("Usage: find <beds> <fromDate> <toDate>");
            return;
        }
        try {
            int beds = Integer.parseInt(parts[0]);
            LocalDate from = LocalDate.parse(parts[1]);
            LocalDate to = LocalDate.parse(parts[2]);


            if (from.isAfter(to)) {
                System.out.println("From date cannot be after To date.");
                return;
            }

            Room foundRoom = null;

            List<Room> suitableRooms = new ArrayList<>();
            for (Room room : rooms) {
                if (room.getBeds() >= beds) {
                    suitableRooms.add(room);
                }
            }

            suitableRooms.sort(Comparator.comparingInt(Room::getBeds));

            for (Room currentRoom : suitableRooms) {
                boolean isAvailable = true;

                for (Reservation reservation : reservations) {

                    if (reservation.getRoomNumber() == currentRoom.getNumber() && reservation.overlaps(from, to)) {
                        isAvailable = false;
                        break;
                    }
                }

                if (isAvailable) {
                    foundRoom = currentRoom;
                    break;
                }
            }

            if (foundRoom != null) {
                System.out.println("Found available room: " + foundRoom.getNumber() + " (Beds: " + foundRoom.getBeds() + ")");
            } else {
                System.out.println("No available room found matching criteria.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid number of beds. Please provide an integer.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    /**
     * Намира свободна стая за важен гост.
     */
    public void findUrgent(String args) {
        String[] parts = args.split(" ");
        if (parts.length != 3) {
            System.out.println("Usage: find! <beds> <fromDate> <toDate>");
            return;
        }
        try {
            int beds = Integer.parseInt(parts[0]);
            LocalDate from = LocalDate.parse(parts[1]);
            LocalDate to = LocalDate.parse(parts[2]);

            if (from.isAfter(to)) {
                System.out.println("From date cannot be after To date.");
                return;
            }

            Optional<Room> freeRoom = rooms.stream()
                    .filter(r1 -> r1.getBeds() >= beds)
                    .filter(r1 -> reservations.stream().noneMatch(r -> r.getRoomNumber() == r1.getNumber() && r.overlaps(from, to)))
                    .findFirst();

            if (freeRoom.isPresent()) {
                System.out.println("Free room found: " + freeRoom.get().getNumber() + " (Beds: " + freeRoom.get().getBeds() + ")");
                return;
            }

            System.out.println("No direct availability. Consider rearranging bookings manually (simulation needed or manual intervention).");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number of beds. Please provide an integer.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    /**
     * Отбелязва стая като недостъпна за конкретен период.
     */
    public void unavailable(String args) {
        String[] parts = args.split(" ", 4);
        if (parts.length < 4) {
            System.out.println("Usage: unavailable <roomNumber> <fromDate> <toDate> <note>");
            return;
        }
        try {
            int roomNumber = Integer.parseInt(parts[0]);
            LocalDate from = LocalDate.parse(parts[1]);
            LocalDate to = LocalDate.parse(parts[2]);
            String note = parts[3];

            boolean roomExists = false;
            for (Room r : rooms) {
                if (r.getNumber() == roomNumber) {
                    roomExists = true;
                    break;
                }
            }

            if (!roomExists) {
                System.out.println("Room " + roomNumber + " does not exist.");
                return;
            }

            if (from.isAfter(to)) {
                System.out.println("From date cannot be after To date.");
                return;
            }

            boolean overlapsExistingReservation = false;

            for (Reservation r : reservations) {

                if (r.getRoomNumber() == roomNumber && r.overlaps(from, to) && !r.isUnavailable()) {
                    overlapsExistingReservation = true;
                    break;
                }
            }

            if (overlapsExistingReservation) {
                System.out.println("Warning: This unavailable period overlaps with existing guest reservations for room " + roomNumber + ".");
                System.out.println("Please confirm if you want to proceed by re-entering the command (or consider manual adjustments).");
            }

            reservations.add(new Reservation(roomNumber, from, to, note, 0, true));
            System.out.println("Room " + roomNumber + " marked as unavailable from " + from + " to " + to + " with note: \"" + note + "\".");

        } catch (NumberFormatException e) {
            System.out.println("Invalid room number. Please provide an integer.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public FileHandler getFileHandler() {
        return fileHandler;
    }
}
