package htw.gruppe.backend.service;

import org.springframework.stereotype.Service;
import java.util.List;


import htw.gruppe.backend.entity.Kandidat;
import htw.gruppe.backend.entity.KandidatAufWahlliste;
import htw.gruppe.backend.entity.Wahlliste;
import htw.gruppe.backend.repository.KandidatAufWahllisteRepository;
import htw.gruppe.backend.repository.KandidatenRepository;
import org.springframework.transaction.annotation.Transactional;
import htw.gruppe.backend.repository.WahllisteRepository;

/**
 * Service-Klasse zur Verwaltung der Zuordnung von Kandidaten zu Wahllisten.
 * <p>
 * Diese Klasse enthält die Geschäftslogik für:
 * <ul>
 *   <li>das Abrufen aller Kandidat–Wahllisten-Zuordnungen</li>
 *   <li>das Hinzufügen eines Kandidaten zu einer Wahlliste</li>
 *   <li>das Entfernen eines Kandidaten von einer Wahlliste</li>
 * </ul>
 *
 * Die Validierungen (z. B. nur eine Wahlliste pro Gremium,
 * mindestens drei Kandidaten pro Wahlliste) werden hier zentral umgesetzt.
 *
 * @author Erdogan
 */

@Service
@Transactional
public class KandidatAufWahllisteService {

    /**
     * Repository für die Zuordnungen zwischen Kandidaten und Wahllisten.
     */

        private final KandidatAufWahllisteRepository kandidatAufWahllisteRepository;

    /**
     * Repository für Kandidaten.
     */

        private final KandidatenRepository kandidatenRepository;
    
    /**
     * Repository für Wahllisten.
     */

        private final WahllisteRepository wahllisteRepository;

    /**
     * Konstruktor zur Übergabe der benötigten Repositories.
     *
     * @param kandidatAufWahllisteRepository Repository für Kandidat–Wahllisten-Zuordnungen
     * @param kandidatenRepository Repository für Kandidaten
     * @param wahllisteRepository Repository für Wahllisten
     */

        public KandidatAufWahllisteService(
                        KandidatAufWahllisteRepository kandidatAufWahllisteRepository,
                        KandidatenRepository kandidatenRepository,
                        WahllisteRepository wahllisteRepository) {
                this.kandidatAufWahllisteRepository = kandidatAufWahllisteRepository;
                this.kandidatenRepository = kandidatenRepository;
                this.wahllisteRepository = wahllisteRepository;
        }

    /**
     * Liefert alle vorhandenen Zuordnungen von Kandidaten zu Wahllisten.
     *
     * @return Liste aller KandidatAufWahlliste-Zuordnungen
     */

       public List<KandidatAufWahlliste> getAllMappings() {
        return kandidatAufWahllisteRepository.findAll();


   }

    /**
     * Fügt einen Kandidaten zu einer Wahlliste hinzu.
     * <p>
     * Dabei gelten folgende Regeln:
     * <ul>
     *   <li>Ein Kandidat darf nicht zweimal auf derselben Wahlliste stehen.</li>
     *   <li>Ein Kandidat darf pro Gremium nur auf einer Wahlliste stehen.</li>
     * </ul>
     *
     * @param kandidatId ID des Kandidaten
     * @param wahllisteId ID der Wahlliste
     * @throws RuntimeException wenn Kandidat oder Wahlliste nicht existieren
     *                          oder gegen die Regeln verstoßen wird
     */

        public void addKandidatToWahlliste(Long kandidatId, Long wahllisteId) {

                Kandidat kandidat = kandidatenRepository.findById(kandidatId)
                                .orElseThrow(() -> new RuntimeException("Kandidat nicht gefunden"));

                Wahlliste wahlliste = wahllisteRepository.findById(wahllisteId)
                                .orElseThrow(() -> new RuntimeException("Wahlliste nicht gefunden"));

                if (kandidatAufWahllisteRepository
                                .existsByKandidatAndWahlliste(kandidat, wahlliste)) {
                        throw new RuntimeException("Kandidat ist bereits auf dieser Wahlliste");
                }

                if (kandidatAufWahllisteRepository
                                .existsByKandidatIdAndWahllisteGremiumId(
                                                kandidatId,
                                                wahlliste.getGremium().getId())) {
                        throw new RuntimeException(
                                        "Kandidat darf pro Gremium nur auf einer Wahlliste stehen");
                }

                KandidatAufWahlliste mapping = new KandidatAufWahlliste(kandidat, wahlliste);

                kandidatAufWahllisteRepository.save(mapping);
        }

    /**
     * Entfernt einen Kandidaten von einer Wahlliste.
     * <p>
     * Dabei gelten folgende Regeln:
     * <ul>
     *   <li>Ein Kandidat muss mindestens auf einer Wahlliste bleiben.</li>
     *   <li>Eine Wahlliste muss mindestens drei Kandidaten enthalten.</li>
     * </ul>
     *
     * @param kandidatId ID des Kandidaten
     * @param wahllisteId ID der Wahlliste
     * @throws RuntimeException wenn die Mindestanzahl verletzt wird
     */       


        public void removeKandidatFromWahlliste(Long kandidatId, Long wahllisteId) {

                long listenCount = kandidatAufWahllisteRepository.countByKandidatId(kandidatId);

                if (listenCount <= 1) {
                        throw new RuntimeException(
                                        "Ein Kandidat muss mindestens auf einer Wahlliste stehen");
                }

                long kandidatenAufListe = kandidatAufWahllisteRepository.countByWahllisteId(wahllisteId);

                if (kandidatenAufListe <= 3) {
                        throw new RuntimeException(
                                        "Wahlliste muss mindestens 3 Kandidaten enthalten");
                }

                kandidatAufWahllisteRepository
                                .deleteByKandidatIdAndWahllisteId(kandidatId, wahllisteId);
        }
}
