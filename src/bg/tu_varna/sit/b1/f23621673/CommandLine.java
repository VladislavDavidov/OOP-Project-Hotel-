package bg.tu_varna.sit.b1.f23621673;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * CommandLine е главният потребителски интерфейс, базиран на команден ред,
 * който позволява взаимодействие с хотелската система.
 * Поддържа команди за отваряне/затваряне на файлове, запис, справки, резервации и др.
 * Използва Map от команди към действия за по-ясна структура.
 */

public class CommandLine {
    private Scanner scanner;
    private Hotel hotel;
    private Map<String, Consumer<String>> commands;

    public CommandLine() {
        this.scanner = new Scanner(System.in);
        this.hotel = new Hotel();
        this.commands = new HashMap<>();

        commands.put("open", this::openFile);
        commands.put("close", arg -> {
            hotel.getFileHandler().closeFile();
            hotel.getReservations().clear();
        });
        commands.put("save", arg -> hotel.saveReservationsToFile());
        commands.put("saveas", this::saveAsFile);
        commands.put("help", arg -> hotel.getFileHandler().printHelp());
        commands.put("exit", arg -> {
            System.out.println("Exiting the program. Remember to save your changes.");
            System.exit(0);
        });

        commands.put("checkin", this::checkin);
        commands.put("checkout", this::checkout);
        commands.put("availability", this::availability);
        commands.put("report", this::report);
        commands.put("find", this::find);
        commands.put("find!", this::findUrgent);
        commands.put("unavailable", this::unavailable);
    }

    /**
     * Отваря файл чрез FileHandler и зарежда резервации.
     */
    private void openFile(String argument) {
        hotel.getFileHandler().openFile(argument);
        if (hotel.getFileHandler().isFileLoaded()) {
            hotel.loadReservationsFromFile();
        }
    }

    /**
     * Записва резервациите в нов файл.
     */
    private void saveAsFile(String argument) {
        hotel.saveReservationsAsNewFile(argument);
    }

    private void checkin(String argument) {
        hotel.checkin(argument);
    }

    private void checkout(String argument) {
        hotel.checkout(argument);
    }

    private void availability(String argument) {
        hotel.availability(argument);
    }

    private void report(String argument) {
        hotel.report(argument);
    }

    private void find(String argument) {
        hotel.find(argument);
    }

    private void findUrgent(String argument) {
        hotel.findUrgent(argument);
    }

    private void unavailable(String argument) {
        hotel.unavailable(argument);
    }

    /**
     * Проверява дали дадена команда изисква отворен файл.
     */
    private boolean requiresFileLoaded(String command) {
        return List.of("checkin", "checkout", "availability", "report", "find", "find!", "unavailable").contains(command);
    }

    public void run() {
        System.out.println("Welcome to the CLI Hotel Management System. Type 'help' for available commands.");
        System.out.println("Please 'open' a file first to load/save reservations.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue;

            String[] parts = input.split(" ", 2);
            String command = parts[0].toLowerCase();
            String argument = parts.length > 1 ? parts[1].replace("\"", "") : "";

            Consumer<String> action = commands.get(command);
            if (action != null) {

                if (requiresFileLoaded(command) && !hotel.getFileHandler().isFileLoaded()) {
                    System.out.println("No file is currently open. Please 'open' a file first.");
                } else {
                    action.accept(argument);
                }
            } else {
                System.out.println("Unknown command. Type 'help' to see available commands.");
            }
        }
    }
}
