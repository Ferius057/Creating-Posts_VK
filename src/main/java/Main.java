import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

public class Main {
    public static void main(String[] args) throws Throwable {
        clearScreen();
        Menu menu = new Menu();
        menu.mainMenu();
    }

    private static void clearScreen() {
        System.out.print("\033\143");
    }
}