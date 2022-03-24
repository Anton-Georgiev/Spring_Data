package bg.softuni;

import bg.softuni.Entities.Author;
import bg.softuni.Entities.Book;
import bg.softuni.Repositories.AuthorRepository;
import bg.softuni.Repositories.BookRepository;
import bg.softuni.Services.SeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final SeedService seedService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public ConsoleRunner(SeedService seedService, BookRepository bookRepository, AuthorRepository authorRepository){
        this.seedService = seedService;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
//        seedService.seedBooks();

//      findALlAfter2000();
//        findALlAuthorsWithAtLeastOneBookBefore1990();
        findAllOrderedByBookCount();
    }


    private void findALlAfter2000(){
        LocalDate date = LocalDate.of(2000, 1,1);
        List<Book> booksAfter2000 = this.bookRepository.findByReleaseDateGreaterThan(date);
        booksAfter2000.forEach(b -> System.out.println(b.getTitle()));

    }


    private void findALlAuthorsWithAtLeastOneBookBefore1990(){
        LocalDate date = LocalDate.of(1990, 1,1);
        List<Author> list = this.authorRepository.findDistinctByBooksReleaseDateLessThan(date);
        list.forEach(e -> System.out.println(e.getFirstName()+ " " + e.getLastName()));
    }

    private void findAllOrderedByBookCount(){
        List<Author> list = this.authorRepository.findAll();
        list.stream()
                .sorted((l, r) -> r.getBooks().size() - l.getBooks().size())
                .forEach(author ->
                        System.out.printf("%s %s -> %d%n",
                                author.getFirstName(),
                                author.getLastName(),
                                author.getBooks().size()));

        List<Author> authors = this.authorRepository.findAll();

    }
}
