package bg.softuni.spring_data_hw.Repositories;

import bg.softuni.spring_data_hw.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
