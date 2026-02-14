package htw.gruppe.backend.repository;

import htw.gruppe.backend.entity.Gremium;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ErgebnisseiteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ErgebnisseiteRepository ergebnisseiteRepository;

    @Autowired
    private GremiumRepository gremiumRepository;

    private Gremium gremium1;
    private Gremium gremium2;

    @BeforeEach
    void setUp() {

        ergebnisseiteRepository.deleteAll();
        gremiumRepository.deleteAll();


        gremium1 = new Gremium("Gremium A");
        gremium2 = new Gremium("Gremium B");

        entityManager.persist(gremium1);
        entityManager.persist(gremium2);
        entityManager.flush();
    }

    @Test
    void findAllWithRelations_ShouldReturnAllGremien() {
        // Act
        List<Gremium> result = ergebnisseiteRepository.findAllWithRelations();

        // Assert
        assertThat(result)
                .hasSize(2)
                .extracting(Gremium::getName)
                .containsExactlyInAnyOrder("Gremium A", "Gremium B");
    }

    @Test
    void findAllWithRelations_ShouldReturnEmptyIfNoGremien() {

        gremiumRepository.deleteAll();
        entityManager.flush();

        // Act
        List<Gremium> result = ergebnisseiteRepository.findAllWithRelations();

        // Assert
        assertThat(result).isEmpty();
    }


}
