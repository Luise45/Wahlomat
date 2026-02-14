
package htw.gruppe.backend.service;

import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.record.ProfilRequest;
import htw.gruppe.backend.record.ProfilResponse;
import htw.gruppe.backend.repository.KandidatenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service zur Verwaltung von Kandidatenprofilen.
 *
 * Dieser Service ermöglicht das Abrufen und Aktualisieren von Profilinformationen
 * für Kandidaten. Nicht bearbeitbare Felder wie Vorname und Nachname werden nicht verändert.
 *
 * @author Schmidt
 * @version 1.0
 */
@Service
@Transactional
public class ProfilService {

    private final KandidatenRepository kandidatenRepository;
    private final Logger log = LoggerFactory.getLogger(ProfilService.class);

    public ProfilService(KandidatenRepository kandidatenRepository) {
        this.kandidatenRepository = kandidatenRepository;
    }

    /**
     * Holt das Profil eines Kandidaten anhand der Matrikelnummer
     *
     * @param matrikelnummer Matrikelnummer des Kandidaten
     * @return Optional mit ProfilResponse, falls Kandidat existiert; sonst Optional.empty()
     */
    public Optional<ProfilResponse> getProfilByMatrikelnummer(String matrikelnummer) {
        log.info("Abruf Profil für Matrikelnummer: {}", matrikelnummer);
        return kandidatenRepository.findByMatrikelnummer(matrikelnummer)
                .map(k -> new ProfilResponse(
                        k.getVorname(),
                        k.getNachname(),
                        k.getFachbereich(),
                        k.getStudiengang(),
                        k.getMatrikelnummer(),
                        k.getBeschreibung()
                ));
    }

    /**
     * Aktualisiert das Profil eines Kandidaten.
     * Vorname und Nachname werden nicht geändert.
     *
     * @param matrikelnummer Matrikelnummer des Kandidaten
     * @param request        ProfilRequest mit neuen Profildaten
     * @return Optional mit aktualisiertem ProfilResponse; Optional.empty(), wenn Kandidat nicht existiert
     */
    public Optional<ProfilResponse> updateProfil(String matrikelnummer, ProfilRequest request) {
        log.info("Update Profil für Matrikelnummer: {}", matrikelnummer);

        Optional<Kandidat> kandidatOpt = kandidatenRepository.findByMatrikelnummer(matrikelnummer);
        if (kandidatOpt.isEmpty()) {
            log.error("Kandidat mit Matrikelnummer {} existiert nicht!", matrikelnummer);
            return Optional.empty();
        }

        Kandidat k = kandidatOpt.get();

        // Nur bearbeitbare Felder updaten
        k.setFachbereich(request.fachbereich());
        k.setStudiengang(request.studiengang());
        k.setBeschreibung(request.beschreibung());

        kandidatenRepository.save(k);

        return Optional.of(new ProfilResponse(
                k.getVorname(),
                k.getNachname(),
                k.getFachbereich(),
                k.getStudiengang(),
                k.getMatrikelnummer(),
                k.getBeschreibung()
        ));
    }
}
