package bg.softuni.spring_data_hw.Services;

import bg.softuni.spring_data_hw.Entities.Category;
import bg.softuni.spring_data_hw.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<Category> getRandomCategories() {
        long count = categoryRepository.count();

        Random random = new Random();
        int categoriesCount = random.nextInt((int) count) + 1;

        Set<Integer> categoriesIds = new HashSet<>();

        for (int i = 0; i < categoriesCount; i++) {
            int nextId = random.nextInt((int) count) + 1;
            categoriesIds.add(nextId);
        }

         List<Category> set = this.categoryRepository.findAllById(categoriesIds);
        return new HashSet<>(set);
    }
}
