/**
 * Service zur Verwaltung von Kandidaten.
 *
 * Dieser Service kapselt die Geschäftslogik für das Abrufen
 * und Erstellen von Kandidaten und stellt sicher, dass
 * Geschäftsregeln (z. B. eindeutige Matrikelnummern)
 * eingehalten werden.
 *
 * @author Schmidt
 * @version 1.0
 */

package htw.gruppe.backend.service;

import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.repository.KandidatenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;



@Service
@Transactional
public class KandidatenService {

    private final KandidatenRepository kandidatenRepository;

    /**
     * Konstruktor für den KandidatenService.
     *
     * @param kandidatenRepository Repository für den Zugriff auf Kandidaten
     */
    public KandidatenService(KandidatenRepository kandidatenRepository) {
        this.kandidatenRepository = kandidatenRepository;
    }

    /**
     * Ruft einen Kandidaten anhand der Matrikelnummer auf.
     *
     * @param matrikelnummer Matrikelnummer des Kandidaten
     * @return Optional mit Kandidat, falls gefunden
     */
    public Optional<Kandidat> getKandidatByMatrikelnummer(String matrikelnummer) {
        return kandidatenRepository.findByMatrikelnummer(matrikelnummer);
    }

    /**
     * Erstellt einen neuen Kandidaten.
     *
     * @param kandidat Kandidat mit allen erforderlichen Daten
     * @return der gespeicherte Kandidat
     * @throws IllegalArgumentException wenn die Matrikelnummer bereits existiert
     */
    public Kandidat addKandidat(Kandidat kandidat){
        if(kandidatenRepository.existsByMatrikelnummer(kandidat.getMatrikelnummer())){
            throw new IllegalArgumentException("Kandidat mit dieser Matrikelnummer existiert bereits");
        }
        return kandidatenRepository.save(kandidat);
    
    }

 public List<Kandidat> getAlleKandidaten() {
    return kandidatenRepository.findAllByRole("USER");


    }
}
