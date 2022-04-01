package bg.softuni.Repositories;

import bg.softuni.Entities.Author;
import bg.softuni.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    List<Author> findDistinctByBooksReleaseDateLessThan(LocalDate date);

}
