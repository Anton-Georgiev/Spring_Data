package bg.softuni.spring_data_hw.Services;

import bg.softuni.spring_data_hw.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface CategoryService {
    Set<Category> getRandomCategories();
}
