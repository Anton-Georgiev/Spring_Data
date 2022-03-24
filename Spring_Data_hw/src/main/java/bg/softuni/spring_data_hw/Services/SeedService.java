package bg.softuni.spring_data_hw.Services;

import java.io.IOException;

public interface SeedService {
    void seedAuthors() throws IOException;

    void seedCategories() throws IOException;

    void seedBooks() throws IOException;

    void seedAll() throws IOException;
}
