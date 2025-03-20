package finance.domain;

import finance.importexport.DataExportVisitor;
import finance.types.CategoryType;

public class Category implements Visitable {
    private final String id;
    private final CategoryType type;
    private String name;

    public Category(String id, CategoryType type, String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    public String getId() { return id; }
    public CategoryType getType() { return type; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public void accept(DataExportVisitor visitor) { visitor.visit(this); }

    @Override
    public String toString() {
        return "finance.domain.Category{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
