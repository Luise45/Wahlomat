/**
 * Service zur Verwaltung von Aussagen.
 *
 * Dieser Service stellt Funktionen bereit, um aktive Aussagen aus der
 * Datenbank abzurufen und als {@link AussageDto} für die Weiterverarbeitung
 * oder Ausgabe (z. B. über REST-Controller) bereitzustellen.
 *
 * Es werden ausschließlich aktive Aussagen berücksichtigt.
 *
 * @author Schmidt
 * @version 1.0
 */

package htw.gruppe.backend.service;

import htw.gruppe.backend.record.AussageDto;
import htw.gruppe.backend.repository.AussagenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AussagenService {

    private final AussagenRepository aussagenRepository;

    /**
     * Konstruktor für den AussagenService.
     *
     * @param aussagenRepository Repository für den Zugriff auf Aussagen
     */
    public AussagenService(AussagenRepository aussagenRepository) {

        this.aussagenRepository = aussagenRepository;
    }

    /**
     * Gibt alle aktiven Aussagen zurück.
     *
     * @return Liste von {@link AussageDto} mit allen aktiven Aussagen
     */
    public List<AussageDto> getAktiveAussagen() {
        return aussagenRepository.findByAktivTrue().stream()
                .map(e -> new AussageDto(e.getId(), e.getAussage_text(), e.getAktiv()))
                .collect(Collectors.toList());
    }

}
