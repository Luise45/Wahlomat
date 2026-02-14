package htw.gruppe.backend.repository;

import htw.gruppe.backend.entity.AdminActivity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class AdminActivityRepositoryTest {

    @Autowired
    private AdminActivityRepository repository;

    @Test
    void findAllByOrderByCreatedAtDesc_ShouldReturnSortedActivities() throws InterruptedException {

        // GIVEN
        AdminActivity older = new AdminActivity("Ältere Aktivität");
        repository.save(older);

        // kleine Pause damit createdAt unterschiedlich ist
        Thread.sleep(10);

        AdminActivity newer = new AdminActivity("Neuere Aktivität");
        repository.save(newer);

        // WHEN
        List<AdminActivity> result =
                repository.findAllByOrderByCreatedAtDesc();

        // THEN
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getDescription())
                .isEqualTo("Neuere Aktivität");
        assertThat(result.get(1).getDescription())
                .isEqualTo("Ältere Aktivität");
    }
}

