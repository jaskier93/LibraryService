package library.services.modelservices;

import library.repositories.BookStateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookStateService {

    private BookStateRepository bookStateRepository;

}
