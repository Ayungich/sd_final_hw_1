import com.google.inject.Guice;
import com.google.inject.Injector;
import finance.command.*;
import finance.di.FinanceModule;
import finance.importexport.DataImporterFactory;
import finance.service.FinanceManager;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FinanceApp {

    // Главный логгер
    private static final Logger logger = Logger.getLogger(FinanceApp.class.getName());

    public static void main(String[] args) {
        // Создаем DI-контейнер и получаем экземпляр finance.service.FinanceManager через Guice
        Injector injector = Guice.createInjector(new FinanceModule());
        FinanceManager manager = injector.getInstance(FinanceManager.class);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Ваш выбор: ");
            String choice = scanner.nextLine();
            Command command;
            switch (choice) {
                case "1":
                    command = new TimeMeasureCommandDecorator(new CreateAccountCommand(manager, scanner));
                    break;
                case "2":
                    command = new TimeMeasureCommandDecorator(new EditAccountCommand(manager, scanner));
                    break;
                case "3":
                    command = new TimeMeasureCommandDecorator(new DeleteAccountCommand(manager, scanner));
                    break;
                case "4":
                    command = new TimeMeasureCommandDecorator(new CreateCategoryCommand(manager, scanner));
                    break;
                case "5":
                    command = new TimeMeasureCommandDecorator(new EditCategoryCommand(manager, scanner));
                    break;
                case "6":
                    command = new TimeMeasureCommandDecorator(new DeleteCategoryCommand(manager, scanner));
                    break;
                case "7":
                    command = new TimeMeasureCommandDecorator(new CreateOperationCommand(manager, scanner));
                    break;
                case "8":
                    command = new TimeMeasureCommandDecorator(new EditOperationCommand(manager, scanner));
                    break;
                case "9":
                    command = new TimeMeasureCommandDecorator(new DeleteOperationCommand(manager, scanner));
                    break;
                case "10":
                    command = new TimeMeasureCommandDecorator(new ListAccountsCommand(manager));
                    break;
                case "11":
                    command = new TimeMeasureCommandDecorator(new ListCategoriesCommand(manager));
                    break;
                case "12":
                    command = new TimeMeasureCommandDecorator(new ListOperationsCommand(manager));
                    break;
                case "13":
                    command = new TimeMeasureCommandDecorator(new AnalyticsCommand(manager, scanner));
                    break;
                case "14":
                    command = new TimeMeasureCommandDecorator(new ExportDataCommand(manager, scanner));
                    break;
                case "15":
                    DataImporterFactory importerFactory = injector.getInstance(DataImporterFactory.class);
                    command = new TimeMeasureCommandDecorator(new ImportDataCommand(manager, scanner, importerFactory));
                    break;
                case "16":
                    command = new TimeMeasureCommandDecorator(new RecalcBalanceCommand(manager, scanner));
                    break;
                case "0":
                    logger.info("Завершение работы приложения.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор.");
                    continue;
            }
            try {
                command.execute();
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Ошибка при выполнении команды", ex);
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("====== Финансовый учет ======");
        System.out.println("1.  Создать счет");
        System.out.println("2.  Редактировать счет");
        System.out.println("3.  Удалить счет");
        System.out.println("4.  Создать категорию");
        System.out.println("5.  Редактировать категорию");
        System.out.println("6.  Удалить категорию");
        System.out.println("7.  Создать операцию");
        System.out.println("8.  Редактировать операцию");
        System.out.println("9.  Удалить операцию");
        System.out.println("10. Показать список счетов");
        System.out.println("11. Показать список категорий");
        System.out.println("12. Показать список операций");
        System.out.println("13. Аналитика");
        System.out.println("14. Экспорт данных");
        System.out.println("15. Импорт данных");
        System.out.println("16. Пересчитать баланс счета");
        System.out.println("0.  Выход");
    }
}