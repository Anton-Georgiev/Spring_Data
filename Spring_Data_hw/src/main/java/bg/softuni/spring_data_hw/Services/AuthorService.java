package bg.softuni.spring_data_hw.Services;

import bg.softuni.spring_data_hw.Entities.Author;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService {
    Author getRandomAuthor();
}
