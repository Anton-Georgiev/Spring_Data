package bg.softuni.spring_data_hw.Repositories;

import bg.softuni.spring_data_hw.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByReleaseDateGreaterThan(LocalDate date);
}
