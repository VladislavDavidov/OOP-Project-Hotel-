import java.io.*;
import java.util.List;

public class FileManager {
    public static void saveToFile(String filePath, List<Room> rooms, List<Reservation> reservations) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath))) {
            out.writeObject(rooms);
            out.writeObject(reservations);
        }
    }

    public static Wrapper loadFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath))) {
            List<Room> rooms = (List<Room>) in.readObject();
            List<Reservation> reservations = (List<Reservation>) in.readObject();
            return new Wrapper(rooms, reservations);
        }
    }
}
