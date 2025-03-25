import java.io.File;
import java.io.IOException;

public class Menu {
    public void displayMenu(String command) throws IOException {
        File file = new File("hotel.txt");

        switch (command)
        {
            case "open":
                try {

                    if (file.createNewFile()) {
                        System.out.println("File created: " + file.getName());
                    }
                    else {
                        System.out.println("File couldn't be created");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("File couldn't be created");
                }
                break;

            case "exit":
                if (file.delete()) {
                    System.out.println("File deleted: " + file.getName());
                }
                else {
                    System.out.println("File couldn't be deleted");
                }
                break;


        }
    }
}
