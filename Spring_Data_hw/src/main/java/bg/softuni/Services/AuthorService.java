package bg.softuni.Services;

import bg.softuni.Entities.Author;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService {
    Author getRandomAuthor();
}
