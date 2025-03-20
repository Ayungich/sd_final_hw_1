package finance.command;

import finance.domain.Category;
import finance.domain.DomainFactory;
import finance.service.FinanceManager;
import finance.types.CategoryType;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateCategoryCommand implements Command {

    private final FinanceManager manager;
    private final Scanner scanner;
    private final Logger logger = Logger.getLogger(CreateCategoryCommand.class.getName());

    public CreateCategoryCommand(FinanceManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Введите название категории: ");
            String name = scanner.nextLine();
            if (name == null || name.trim().isEmpty())
                throw new IllegalArgumentException("Название не может быть пустым");
            System.out.print("Введите тип категории (INCOME/EXPENSE): ");
            String typeStr = scanner.nextLine().trim().toUpperCase();
            CategoryType type = CategoryType.valueOf(typeStr);
            Category category = DomainFactory.createCategory(type, name);
            manager.addCategory(category);
            System.out.println("Категория создана: " + category);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка создания категории", e);
            System.out.println("Ошибка создания категории: " + e.getMessage());
        }
    }
}
