package finance.command;

import finance.domain.Category;
import finance.domain.Operation;
import finance.service.FinanceManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalyticsCommand implements Command {

    private final FinanceManager manager;
    private final Scanner scanner;
    private final Logger logger = Logger.getLogger(AnalyticsCommand.class.getName());

    public AnalyticsCommand(FinanceManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Введите начальную дату (например, 2023-03-01T00:00): ");
            LocalDateTime from = LocalDateTime.parse(scanner.nextLine());
            System.out.print("Введите конечную дату (например, 2023-03-31T23:59): ");
            LocalDateTime to = LocalDateTime.parse(scanner.nextLine());
            double net = manager.calculateNetDifference(from, to);
            System.out.println("Чистая разница (доходы - расходы) за период: " + net);
            Map<Category, List<Operation>> grouped = manager.groupOperationsByCategory(from, to);
            System.out.println("Операции по категориям:");
            grouped.forEach((cat, ops) -> {
                System.out.println("Категория: " + cat.getName());
                ops.forEach(op -> System.out.println("   " + op));
            });
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка выполнения аналитики", e);
            System.out.println("Ошибка аналитики: " + e.getMessage());
        }
    }
}
