package finance.command;

import finance.domain.Category;
import finance.service.FinanceManager;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditCategoryCommand implements Command {

    private final FinanceManager manager;
    private final Scanner scanner;
    private final Logger logger = Logger.getLogger(EditCategoryCommand.class.getName());

    public EditCategoryCommand(FinanceManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Введите ID категории для редактирования: ");
            String id = scanner.nextLine();
            Category target = null;
            for (Category cat : manager.getAllCategories()) {
                if (cat.getId().equals(id)) {
                    target = cat;
                    break;
                }
            }
            if (target == null) {
                System.out.println("Категория не найдена");
                return;
            }
            System.out.print("Введите новое название категории: ");
            String newName = scanner.nextLine();
            if (newName == null || newName.trim().isEmpty())
                throw new IllegalArgumentException("Название не может быть пустым");
            target.setName(newName);
            manager.updateCategory(target);
            System.out.println("Категория обновлена: " + target);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ошибка редактирования категории", e);
            System.out.println("Ошибка редактирования категории: " + e.getMessage());
        }
    }
}