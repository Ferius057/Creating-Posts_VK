import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Menu extends Connection {
    public static final String COLOR_RESET = "\u001B[0m";
    public static final String COLOR_BLACK = "\u001B[30m";
    public static final String COLOR_RED = "\u001B[31m";
    public static final String COLOR_GREEN = "\u001B[32m";
    public static final String COLOR_YELLOW = "\u001B[33m";
    public static final String COLOR_BLUE = "\u001B[34m";
    public static final String COLOR_PURPLE = "\u001B[35m";
    public static final String COLOR_CYAN = "\u001B[36m";
    public static final String COLOR_WHITE = "\u001B[37m";

    public void mainMenu() throws Throwable {
        StringBuilder mainChoice = new StringBuilder(COLOR_WHITE + "Привет!");
        mainChoice.append(COLOR_CYAN + "\nЭто " + COLOR_PURPLE + "«Creating Posts».");
        mainChoice.append(COLOR_CYAN + "\nСкрипт который может создавать записи на стене.");
        System.out.print(mainChoice.toString());
        startSelection();
    }

    public void startSelection() throws Throwable {
        ScriptRun scriptRun = new ScriptRun();
        while (true) {
            StringBuilder choice = new StringBuilder("\n");
            choice.append(COLOR_GREEN + "\n[1] - Запуск скрипта.");
            choice.append(COLOR_GREEN + "\n[2] - Связь с автором.");
            choice.append(COLOR_GREEN + "\n[3] - GitHub.");
            choice.append(COLOR_BLUE + "\nВыбери пункт: ");
            System.out.print(choice.toString());
            Scanner functionSelection = new Scanner(System.in);
            String selected = functionSelection.nextLine();
            System.out.println(COLOR_BLACK + "--------------------------------------");
            switch (selected) {
                case "1":
                    System.out.println(COLOR_CYAN + "Подключение...");
                    Thread.sleep(300);
                    scriptRun.scriptRun();
                    break;
                case "2": contactTheAuthor(); break;
                case "3": github(); break;
                default: System.out.println(COLOR_RED + "ERROR: Можно только писать 1,2,3."); break;
            }
            System.out.print(COLOR_BLACK + "--------------------------------------");
        }
    }

    public void contactTheAuthor() {
        System.out.println(COLOR_PURPLE + "Связаться с автором можно только через VK." +
                COLOR_YELLOW + "\nVK: vk.com/ferius_057");
    }

    public void github() {
        System.out.println(COLOR_YELLOW + "https://github.com/Ferius057/Creating-Posts_VK.");
    }
}
