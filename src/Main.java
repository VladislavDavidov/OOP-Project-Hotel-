import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String command = input.nextLine();
        Menu menu = new Menu();
        while (!command.equals("exit")) {
            try {
                menu.displayMenu(command);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
