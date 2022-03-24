package bg.softuni.spring_data_hw.Services;

import bg.softuni.spring_data_hw.Entities.Author;
import bg.softuni.spring_data_hw.Repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getRandomAuthor() {
        long count = authorRepository.count();
        int authorId = new Random().nextInt((int) count) + 1;
        return this.authorRepository.findById(authorId).get();

    }
}
