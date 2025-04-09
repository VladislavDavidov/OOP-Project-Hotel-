import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Room room1 = new Room(120, 1, 3, false, false);
        Room room2 = new Room(220, 2, 2, true, false);
        Room room3 = new Room(320, 3, 4, true, false);
        Room room4 = new Room(420, 4, 2, false, false);
        hotel.addRoom(room1);
        hotel.addRoom(room2);
        hotel.addRoom(room3);
        hotel.addRoom(room4);


        Scanner input = new Scanner(System.in);
        String command;
        String file = "hotel.txt";
        do {
            command = input.nextLine();
            switch (command) {
                case "open":
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(" nova informacia");
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("File couldn't be created");
                    }
                    System.out.println("File created");
                    break;
                case "exit":
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid command");
            }
        } while (!command.equals("exit"));

    }
}
