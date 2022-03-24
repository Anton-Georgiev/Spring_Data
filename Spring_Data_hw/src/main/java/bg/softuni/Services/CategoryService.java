package bg.softuni.Services;

import bg.softuni.Entities.Category;

import java.util.Set;

public interface CategoryService {
    Set<Category> getRandomCategories();
}
