package bg.tu_varna.sit.b1.f23621673;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileHandler се грижи за отваряне, записване и зареждане на резервации от/към файл.
 * Работи с файлове чрез сериализация на списък от Reservation обекти.
 * Поддържа и команди за работа с файловата система: open, close, save, saveas.
 */

public class FileHandler {
    private String filePath;
    private boolean isFileLoaded = false;

    public boolean isFileLoaded() {
        return isFileLoaded;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Зарежда списък от резервации от текущо зададения файл.
     *
     * @return списък с резервации или празен списък, ако няма данни
     */
    public List<Reservation> loadReservations() {
        List<Reservation> reservations = new ArrayList<>();
        File file = new File(filePath);
        if (file.exists() && file.length() > 0) { // Check if file exists and is not empty
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                reservations = (List<Reservation>) ois.readObject();
                System.out.println("Successfully loaded reservations from " + file.getName());
                isFileLoaded = true;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading reservations: " + e.getMessage());

                return new ArrayList<>();
            }
        } else {
            System.out.println("File " + file.getName() + " does not exist or is empty. Starting with no reservations.");
            isFileLoaded = true;
        }
        return reservations;
    }

    /**
     * Записва предоставения списък с резервации в текущия файл.
     */
    public void saveReservations(List<Reservation> reservations) {
        if (isFileLoaded && filePath != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
                oos.writeObject(reservations);
                System.out.println("Successfully saved reservations to " + new File(filePath).getName());
            } catch (IOException e) {
                System.out.println("Error saving reservations: " + e.getMessage());
            }
        } else {
            System.out.println("No file is currently open to save reservations.");
        }
    }

    /**
     * Отваря файл, като задава пътя и маркира файла като зареден.
     */
    public void openFile(String path) {
        this.filePath = path;
        this.isFileLoaded = true;
        System.out.println("Set file path to: " + new File(path).getName());
    }

    /**
     * Затваря текущия файл и премахва зададения път.
     */
    public void closeFile() {
        if (isFileLoaded) {
            System.out.println("Successfully closed " + new File(filePath).getName());
            filePath = null;
            isFileLoaded = false;
        } else {
            System.out.println("No file is currently open.");
        }
    }

    /**
     * Отпечатва помощна информация с всички поддържани команди.
     */
    public void printHelp() {
        System.out.println("""
                The following commands are supported:
                open <file>           sets the file to be used for reservations (e.g., "myhotel.dat")
                close                 closes currently opened file
                save                  saves the current reservations to the opened file
                saveas <file>         saves the current reservations to <file>
                checkin <roomNumber> <fromDate> <toDate> <guestName> [guests] - checks in a guest
                checkout <roomNumber> - checks out a room (removes active reservations for today)
                availability [date]   lists available rooms for a given date (default: today)
                report <fromDate> <toDate> - generates a usage report for rooms
                find <beds> <fromDate> <toDate> - finds an available room with specified beds
                find! <beds> <fromDate> <toDate> - finds an available room, or suggests urgent action
                unavailable <roomNumber> <fromDate> <toDate> <note> - marks a room as unavailable
                help                  prints this information
                exit                  exits the program
                """);
    }
}
