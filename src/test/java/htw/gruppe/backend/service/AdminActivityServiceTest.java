package htw.gruppe.backend.service;

import htw.gruppe.backend.entity.AdminActivity;
import htw.gruppe.backend.record.AdminActivityDto;
import htw.gruppe.backend.repository.AdminActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AdminActivityServiceTest {

    @Mock
    private AdminActivityRepository repository;

    private AdminActivityService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new AdminActivityService(repository);
    }

    @Test
    void log_ShouldSaveNewActivity() {


        service.log("Test Aktivität");


        ArgumentCaptor<AdminActivity> captor =
                ArgumentCaptor.forClass(AdminActivity.class);

        verify(repository, times(1)).save(captor.capture());

        AdminActivity saved = captor.getValue();
        assertThat(saved.getDescription()).isEqualTo("Test Aktivität");
    }

    @Test
    void getAll_ShouldReturnMappedDtos() {

        // GIVEN
        AdminActivity a1 = new AdminActivity("A");
        AdminActivity a2 = new AdminActivity("B");

        // createdAt manuell simulieren (Mock!)
        when(repository.findAllByOrderByCreatedAtDesc())
                .thenReturn(List.of(a1, a2));

        // Wir mocken createdAt via Spy nicht nötig,
        // weil getCreatedAt() null sein darf im Test.


        List<AdminActivityDto> result = service.getAll();


        assertThat(result).hasSize(2);
        assertThat(result.get(0).description()).isEqualTo("A");
        assertThat(result.get(1).description()).isEqualTo("B");
    }
}
