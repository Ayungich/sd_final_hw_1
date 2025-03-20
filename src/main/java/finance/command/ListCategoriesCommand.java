package finance.command;

import finance.domain.Category;
import finance.service.FinanceManager;

import java.util.Collection;

public class ListCategoriesCommand implements Command {

    private final FinanceManager manager;

    public ListCategoriesCommand(FinanceManager manager) { this.manager = manager; }

    @Override
    public void execute() {
        Collection<Category> categories = manager.getAllCategories();
        if (categories.isEmpty()) {
            System.out.println("Нет созданных категорий");
        } else {
            System.out.println("Категории:");
            categories.forEach(System.out::println);
        }
    }
}
