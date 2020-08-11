import com.vk.api.sdk.exceptions.ApiCaptchaException;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ScriptRun extends Menu {
    private static String token;
    private static String id;
    private static int postIdStr;
    private static int postOwner;
    private static boolean postOwnerBoll;
    private static int postPieces;
    private static String postText;
    private static int postKD;
    private static final String comYourPage = "ваша страница - ";
    private static int captcha;
    public void scriptRun() throws Throwable {
        new Connection();
        Scanner tokenSc = new Scanner(System.in);
        Scanner idSc = new Scanner(System.in);
        do {
            System.out.print(COLOR_PURPLE + "Введи токен страницы: " + COLOR_RED);
            token = tokenSc.nextLine();
            if (token.equals("exit")) {
                System.out.println(COLOR_BLACK + "--------------------------------------");
                Menu menu = new Menu();
                menu.startSelection();
            }
            token = token.replace(" ","");
            if (token.isEmpty()) System.out.println(COLOR_RED + "ERROR: токен не может быть пустым.");
        } while (token.isEmpty());
        do {
            System.out.print(COLOR_PURPLE + "Введи id страницы (только цифры): ");
            id = idSc.nextLine();
            id = id.replace(" ","");
            if (id.isEmpty()) System.out.println(COLOR_RED + "ERROR: id не может быть пустым.");
        } while (id.isEmpty());
        System.out.println(COLOR_CYAN + "Проверка...");
        try {
            connection(token,Integer.parseInt(id));
        } catch (NumberFormatException e) {
            System.out.println(COLOR_RED + "ERROR: Проверь токен и id, попробуй ещё раз");
            System.out.println(COLOR_GREEN + "Или напиши в токен \"" + COLOR_RED + "exit" + COLOR_GREEN + "\" для выхода в главное меню.");
            System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
            scriptRun();
        }
        for (int i = 3; i > 0; i--) {
            Thread.sleep(ThreadLocalRandom.current().nextInt(500, 900));
            System.out.println(i + "...");
        }
        String method = vk.users().get(actor).executeAsString();
        if (method.contains("\":" + id + ",\"")) { }
        else {
            System.out.println(COLOR_RED + "ERROR: Проверь токен и id, попробуй ещё раз");
            System.out.println(COLOR_GREEN + "Или напиши в токен \"" + COLOR_RED + "exit" + COLOR_GREEN + "\" для выхода в главное меню.");
            System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
            scriptRun();
        }
        if (method.contains("error")) {
            System.out.println(COLOR_RED + "ERROR: Проверь токен и id, попробуй ещё раз");
            System.out.println(COLOR_GREEN + "Или напиши в токен \"" + COLOR_RED + "exit" + COLOR_GREEN + "\" для выхода в главное меню.");
            System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
            scriptRun();
        } else {
            System.out.println(COLOR_YELLOW + "Успешно подключено." + COLOR_BLACK + "\n--------------------------------------------------------------------------");
        }
        start();
    }

    public void start() throws Throwable {
        Scanner postIdStrSc = new Scanner(System.in);
        Scanner postOwnerSc = new Scanner(System.in);
        Scanner postPiecesSc = new Scanner(System.in);
        Scanner postTextSc = new Scanner(System.in);
        Scanner postKDSc = new Scanner(System.in);
        Scanner checkCorrectnessSc = new Scanner(System.in);
        Scanner captchaSc = new Scanner(System.in);
        try {
            System.out.println(COLOR_PURPLE + "Напиши в id страницы \"" + COLOR_RED + "100" + COLOR_PURPLE + "\" для выхода в главное меню.");
            System.out.print(COLOR_GREEN + "Поставь в начале \"-\" и id сообщество(для спама в посте сообщества).\nВведи id страницы где будут публиковаться посты {0 - твоя страница}: ");
            postIdStr = postIdStrSc.nextInt();
            if (postIdStr == 100) {
                System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
                Menu menu = new Menu();
                menu.startSelection();
            }
            if (postIdStr < 0) {
                System.out.print(COLOR_GREEN + "Если это не твое сообщество пиши \"" + COLOR_YELLOW + 0 + COLOR_GREEN + "\"." +
                        "\nЕсли это твое сообщество делать пост от страницы или сообщество?" +
                        "\n0 - От страницы.\n1 - От сообщество.\n ---> ");
                postOwner = postOwnerSc.nextInt();
                switch (postOwner) {
                    case 0: postOwnerBoll = false; break;
                    case 1: postOwnerBoll = true; break;
                    default:
                        System.out.println(COLOR_RED + "Неверный выбор.");
                        System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
                        start();break;
                }
            } else {
                postOwnerBoll = false;
            }
            System.out.print("Введи колличество постов: ");
            postPieces = postPiecesSc.nextInt();
            System.out.print("Введи текст: ");
            postText = postTextSc.nextLine();
            System.out.print("1000мс - 1сек\nВведи задержку в мс {не меньше 900мс, не больше 10000мс}: ");
            postKD = postKDSc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(COLOR_RED + "ERROR: Unknown error.");
            System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
            start();
        }
        /*--------------------------------------------------------------------------------*/
        if (postPieces < 1 || postPieces > 2000000000) {
            System.out.println(COLOR_RED + "ERROR: Колличество комментариев маленькое или большое.");
            System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
            start();
        } else if (postText.isEmpty()) {
            System.out.println(COLOR_RED + "ERROR: Текст комментария не может быть пустым.");
            System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
            start();
        } else if (postKD < 900 || postKD > 10000) {
            System.out.println(COLOR_RED + "ERROR: Задержка не может быть меньше 900мс, и больше 10000мс.");
            System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
            start();
        }
        /*--------------------------------------------------------------------------------*/
        System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
        StringBuilder sb = new StringBuilder(COLOR_BLUE + "Проверьте правильность.");
        if (postIdStr == 0) {
            postIdStr = Integer.parseInt(id);
            sb.append(COLOR_BLUE + "\nid страницы постинга: " + COLOR_PURPLE + comYourPage + postIdStr);
        } else {
            sb.append(COLOR_BLUE + "\nid страницы постинга: " + COLOR_PURPLE + postIdStr);
        }
        sb.append(COLOR_BLUE + "\nКолличество постов: " + COLOR_PURPLE + postPieces);
        sb.append(COLOR_BLUE + "\nТекст поста: " + COLOR_PURPLE + postText);
        sb.append(COLOR_BLUE + "\nЗарержка: " + COLOR_PURPLE + postKD + "мс.");
        sb.append(COLOR_CYAN + "\n1 - все верно\n2 - заполнить заново.");
        System.out.print(sb + "\nВсе правильно?: ");
        int checkCorrectness = checkCorrectnessSc.nextInt();
        System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
        if (checkCorrectness == 1) {
            for (int i = 1; i < postPieces + 1; i++) {
                String request = vk.wall().post(actor).ownerId(postIdStr).fromGroup(postOwnerBoll).message(postText).executeAsString();
                if (request.contains("error")) {
                    if (request.contains("14") & request.contains("Captcha needed")) {
                        System.out.println(COLOR_RED + "ERROR: Появилась капча.(если капчи очень много вам стоит вручную сделать коммент и ввести капчу, или же подождать определенное кол-во времени)\n1 - Продолжить.\n2 - выход.");
                        System.out.print("Продолжить?: ");
                        captcha = captchaSc.nextInt();
                        if (captcha == 1) {
                            System.out.println("Продолжение...\nAnti-Captcha KD 15 sek.");
                            for (int c = 15; c > 0; c--) {
                                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2000));
                                System.out.println(c + "...");
                            }
                        } else {
                            System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
                            start();
                        }
                        i = i - 1;
                        continue;
                    } else if (request.contains("219") & request.contains("Advertisement post was recently added")) {
                        System.out.println(COLOR_RED + "Рекламный пост уже недавно публиковался.");
                        start();
                    } else if (request.contains("214") & request.contains("Access to adding post denied")) {
                        System.out.println(COLOR_RED + "Публикация запрещена. Превышен лимит на число публикаций в сутки, либо на указанное время уже запланирована другая запись, либо для текущего пользователя недоступно размещение записи на этой стене..");
                        start();
                    } else if (request.contains("222") & request.contains("Hyperlinks are forbidden")) {
                        System.out.println(COLOR_RED + "Запрещено размещать ссылки..");
                        start();
                    } else if (request.contains("220") & request.contains("Too many recipients")) {
                        System.out.println(COLOR_RED + "Слишком много получателей.");
                        start();
                    } else if (request.contains("224") & request.contains("Too many ads posts")) {
                        System.out.println(COLOR_RED + "Слишком много рекламных постов.");
                        start();
                    } else {
                        System.out.println(COLOR_RED + "ERROR: Unknown error.");
                        System.out.println(COLOR_BLACK + "--------------------------------------------------------------------------");
                        start();
                    }
                }
                System.out.println(COLOR_YELLOW + "Пост " + COLOR_WHITE + i + COLOR_YELLOW + " отправлен.");
                Thread.sleep(postKD);
            }
            System.out.println(COLOR_BLUE + "Все посты были отправлены." + COLOR_BLACK + "\n--------------------------------------------------------------------------");
        }
        start();
    }
}
