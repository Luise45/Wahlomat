package htw.gruppe.backend.service;

import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.record.ProfilRequest;
import htw.gruppe.backend.record.ProfilResponse;
import htw.gruppe.backend.repository.KandidatenRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProfilServiceTest {

    @Autowired
    private ProfilService profilService;

    @Autowired
    private KandidatenRepository kandidatenRepository;

    @Test
    void getProfilByMatrikelnummer() {

        Kandidat kandidat = new Kandidat();
        kandidat.setVorname("Anna");
        kandidat.setNachname("Karen");
        kandidat.setFachbereich("Informatik");
        kandidat.setStudiengang("Software Engineering");
        kandidat.setMatrikelnummer("123456");
        kandidat.setBeschreibung("Initiale Beschreibung");

        kandidatenRepository.save(kandidat);

        Optional<ProfilResponse> result =
                profilService.getProfilByMatrikelnummer("123456");
        assertThat(result).isPresent();

        ProfilResponse response = result.get();
        assertThat(response.vorname()).isEqualTo("Anna");
        assertThat(response.nachname()).isEqualTo("Karen");
        assertThat(response.fachbereich()).isEqualTo("Informatik");
        assertThat(response.studiengang()).isEqualTo("Software Engineering");
        assertThat(response.matrikelnummer()).isEqualTo("123456");
        assertThat(response.beschreibung()).isEqualTo("Initiale Beschreibung");
    }

    @Test
    void getProfilByMatrikelnummerEmpty() {
        Optional<ProfilResponse> result = profilService.getProfilByMatrikelnummer("999999");
        assertThat(result).isEmpty();
    }

    @Test
    void updateProfil() {

        Kandidat kandidat = new Kandidat();
        kandidat.setVorname("Anna");
        kandidat.setNachname("Schmidt");
        kandidat.setFachbereich("Mathematik");
        kandidat.setStudiengang("Lehramt");
        kandidat.setMatrikelnummer("654321");
        kandidat.setBeschreibung("Alte Beschreibung");

        kandidatenRepository.save(kandidat);

        // Record wird über Konstruktor erstellt
        ProfilRequest request = new ProfilRequest(
                "Physik",
                "Astrophysik",
                "Neue Beschreibung"
        );

        Optional<ProfilResponse> result =
                profilService.updateProfil("654321", request);

        assertThat(result).isPresent();

        ProfilResponse response = result.get();

        assertThat(response.vorname()).isEqualTo("Anna");
        assertThat(response.nachname()).isEqualTo("Schmidt");
        assertThat(response.matrikelnummer()).isEqualTo("654321");

        assertThat(response.fachbereich()).isEqualTo("Physik");
        assertThat(response.studiengang()).isEqualTo("Astrophysik");
        assertThat(response.beschreibung()).isEqualTo("Neue Beschreibung");

        Kandidat persisted = kandidatenRepository.findByMatrikelnummer("654321").orElseThrow();

        assertThat(persisted.getFachbereich()).isEqualTo("Physik");
        assertThat(persisted.getStudiengang()).isEqualTo("Astrophysik");
        assertThat(persisted.getBeschreibung()).isEqualTo("Neue Beschreibung");
    }

    @Test
    void updateProfilEmpty() {

        ProfilRequest request = new ProfilRequest(
                "Chemie",
                "Biochemie",
                "Beschreibung"
        );

        Optional<ProfilResponse> result = profilService.updateProfil("000000", request);
        assertThat(result).isEmpty();
    }
}
