package htw.gruppe.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import htw.gruppe.backend.entity.Gremium;
import htw.gruppe.backend.entity.Wahlliste;
import htw.gruppe.backend.repository.GremiumRepository;
import htw.gruppe.backend.repository.KandidatAufWahllisteRepository;
import htw.gruppe.backend.repository.WahllisteRepository;

import java.util.List;

/**
 * Service-Klasse zur Verwaltung von Wahllisten.
 * <p>
 * Diese Klasse enthält die Geschäftslogik für:
 * <ul>
 *   <li>das Abrufen aller Wahllisten</li>
 *   <li>das Abrufen einer einzelnen Wahlliste</li>
 *   <li>das Anlegen neuer Wahllisten</li>
 *   <li>das Validieren von Wahllisten</li>
 *   <li>das Löschen von Wahllisten</li>
 * </ul>
 *
 * Die Validierungsregeln (z. B. mindestens 3 Kandidaten pro Wahlliste)
 * werden hier zentral geprüft.
 *
 * @author Erdogan
 */

@Service
@Transactional
public class WahllisteService {

    /**
     * Repository für den Zugriff auf Wahllisten.
     */

private final WahllisteRepository wahllisteRepository;

    /**
     * Repository für die Zuordnungen zwischen Kandidaten und Wahllisten.
     */

    private final KandidatAufWahllisteRepository kandidatAufWahllisteRepository;

    /**
     * Repository für den Zugriff auf Gremien.
     */

    private final GremiumRepository gremiumRepository;

    /**
     * Konstruktor zur Übergabe der benötigten Repositories.
     *
     * @param wahllisteRepository Repository für Wahllisten
     * @param kandidatAufWahllisteRepository Repository für Kandidat–Wahllisten-Zuordnungen
     * @param gremiumRepository Repository für Gremien
     */

    public WahllisteService(
            WahllisteRepository wahllisteRepository,
            KandidatAufWahllisteRepository kandidatAufWahllisteRepository,
            GremiumRepository gremiumRepository
    ) {
        this.wahllisteRepository = wahllisteRepository;
        this.kandidatAufWahllisteRepository = kandidatAufWahllisteRepository;
        this.gremiumRepository = gremiumRepository;
    }

    /**
     * Liefert alle vorhandenen Wahllisten.
     *
     * @return Liste aller Wahllisten
     */

    public List<Wahlliste> getAllWahllisten() {
        return wahllisteRepository.findAll();
    }

    /**
     * Liefert eine einzelne Wahlliste anhand ihrer ID.
     *
     * @param wahllisteId ID der Wahlliste
     * @return gefundene Wahlliste
     * @throws RuntimeException wenn keine Wahlliste mit der ID existiert
     */

    public Wahlliste getWahlliste(Long wahllisteId) {
        return wahllisteRepository.findById(wahllisteId)
                .orElseThrow(() -> new RuntimeException("Wahlliste nicht gefunden"));
    }

    /**
     * Legt eine neue Wahlliste für ein bestimmtes Gremium an.
     * <p>
     * Der Name darf nicht leer sein und das angegebene Gremium muss existieren.
     * Neue Wahllisten werden standardmäßig als nicht validiert gespeichert.
     *
     * @param name Name der Wahlliste
     * @param gremiumId ID des zugehörigen Gremiums
     * @return die neu angelegte Wahlliste
     * @throws RuntimeException wenn Name leer ist oder das Gremium nicht existiert
     */

public Wahlliste createWahlliste(String name, Long gremiumId) {
        if (name == null || name.isBlank()) {
            throw new RuntimeException("Name der Wahlliste darf nicht leer sein");
        }

        Gremium gremium = gremiumRepository.findById(gremiumId)
                .orElseThrow(() -> new RuntimeException("Gremium nicht gefunden"));

        Wahlliste wahlliste = new Wahlliste(name, gremium);
        wahlliste.setValid(false); 

        return wahllisteRepository.save(wahlliste);
    }

    /**
     * Validiert eine Wahlliste.
     * <p>
     * Eine Wahlliste kann nur validiert werden, wenn sie mindestens
     * drei zugeordnete Kandidaten enthält.
     *
     * @param wahllisteId ID der zu validierenden Wahlliste
     * @throws RuntimeException wenn die Wahlliste weniger als 3 Kandidaten enthält
     */

    public void validateWahlliste(Long wahllisteId) {

        long kandidatenCount =
                kandidatAufWahllisteRepository.countByWahllisteId(wahllisteId);

        if (kandidatenCount < 3) {
            throw new RuntimeException("Eine Wahlliste muss mindestens 3 Kandidaten enthalten");
        }
 Wahlliste wahlliste = getWahlliste(wahllisteId);
        wahlliste.setValid(true);
        wahllisteRepository.save(wahlliste);
    }

    /**
     * Löscht eine Wahlliste inklusive aller zugehörigen Kandidaten-Zuordnungen.
     *
     * @param wahllisteId ID der zu löschenden Wahlliste
     * @throws RuntimeException wenn die Wahlliste nicht existiert
     */

    public void deleteWahlliste(Long wahllisteId) {

        
        Wahlliste wahlliste = getWahlliste(wahllisteId);


        kandidatAufWahllisteRepository.deleteByWahllisteId(wahllisteId);

        
        wahllisteRepository.deleteById(wahllisteId);


    }
}
