package bg.softuni.Repositories;

import bg.softuni.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByReleaseDateGreaterThan(LocalDate date);
    List<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitleAsc(String firstName, String lastName);
}
