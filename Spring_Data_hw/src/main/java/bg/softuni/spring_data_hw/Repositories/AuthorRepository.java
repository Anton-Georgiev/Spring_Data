package bg.softuni.spring_data_hw.Repositories;

import bg.softuni.spring_data_hw.Entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    List<Author> findDistinctByBooksReleaseDateLessThan(LocalDate date);

}
