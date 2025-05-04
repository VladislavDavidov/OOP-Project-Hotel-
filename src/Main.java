import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
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

        String currentFilePath = null;
        boolean fileLoaded = false;


        String file = "hotel.txt";
        do {
            command = input.nextLine();
            switch (command) {
                case "open": // trqbwa promqna
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(hotel.toString());
                    } catch (IOException e) {
                        System.out.println("File couldn't be created");
                    }
                    System.out.println("File created");
                    break;


                case "checkin":
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        System.out.println("Enter room number: ");
                        int roomNo = Integer.parseInt(input.nextLine());
                        System.out.println("Enter from: ");
                        LocalDate from = LocalDate.parse(input.nextLine());
                        System.out.println("Enter to: ");
                        LocalDate to = LocalDate.parse(input.nextLine());
                        System.out.println("Enter note: ");
                        String note = input.nextLine();
                        System.out.println("Enter guests: ");
                        int guests = Integer.parseInt(input.nextLine());
                        hotel.checkIn(roomNo, from, to, note, guests);
                        writer.write(hotel.toString());
                    }catch (IOException e){
                        System.out.println(e.getMessage());
                    }
                    break;


                case "help" :
                    System.out.println("The following commands are supported:");
                    System.out.println("open <file> \t\topens <file>");
                    System.out.println("close \t\tcloses currently opened file");
                    System.out.println("save \t\tsaves the currently open file");
                    System.out.println("saveas <file> \t\tsaves the currently open file in <file>");
                    System.out.println("help \t\tprints this information");
                    System.out.println("exit \t\texits the program");
                    break;


                //case "save":


                //case "saveas":


                case "exit":
                    System.out.println("Exiting program...");
                    break;


                default:
                    System.out.println("Invalid command");
            }
        } while (!command.equals("exit"));

    }
}
