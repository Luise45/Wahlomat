package htw.gruppe.backend.service;
import htw.gruppe.backend.record.AussageDto;
import htw.gruppe.backend.entity.Aussage;
import htw.gruppe.backend.repository.AussagenRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Transactional

public class AussagenServiceTest {


        @Autowired
        private AussagenRepository repository;

        @Autowired
        private AussagenService service;

        @BeforeEach
        void setUp() {
            repository.deleteAll();
            repository.save(new Aussage(null, "Text 1", true));
            repository.save(new Aussage(null, "Text 2", true));
            repository.save(new Aussage(null, "Text 3", false));
        }

    // Tested Aussagen der Liste: Laenge der Liste, und get Aussage x

        @Test
        void getAktiveAussagen() {
            List<AussageDto> result = service.getAktiveAussagen();

            assertEquals(2, result.size());
            assertEquals("Text 1", result.get(0).aussageText());
            assertEquals("Text 2", result.get(1).aussageText());
        }
    }


