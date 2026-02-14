package htw.gruppe.backend.service;

import htw.gruppe.backend.record.DashboardSummaryDto;
import htw.gruppe.backend.repository.*;
import org.springframework.stereotype.Service;

/**
 * Service für das Admin-Dashboard.
 *
 * <p>
 * Diese Klasse sammelt und Daten aus verschiedenen Repositories,
 * um eine Übersicht für das Admin-Dashboard bereitzustellen.
 * Dazu gehören zum Beispiel die Anzahl von Kandidaten, Gremien, Wahllisten,
 * Zuordnungen und Aussagen.
 * </p>
 *
 * @author Nguemezi
 */
@Service
public class AdminDashboardService {

    private final KandidatenRepository kandidatRepo;
    private final GremiumRepository gremiumRepo;
    private final WahllisteRepository wahllisteRepo;
    private final KandidatAufWahllisteRepository mappingRepo;
    private final AussagenRepository aussagenRepo;

    public AdminDashboardService(
            KandidatenRepository kandidatRepo,
            GremiumRepository gremiumRepo,
            WahllisteRepository wahllisteRepo,
            KandidatAufWahllisteRepository mappingRepo,
            AussagenRepository aussagenRepo
    ) {
        this.kandidatRepo = kandidatRepo;
        this.gremiumRepo = gremiumRepo;
        this.wahllisteRepo = wahllisteRepo;
        this.mappingRepo = mappingRepo;
        this.aussagenRepo = aussagenRepo;
    }

    public DashboardSummaryDto getSummary() {
        DashboardSummaryDto dto = new DashboardSummaryDto();
        dto.kandidaten = kandidatRepo.count();
        dto.gremien = gremiumRepo.count();
        dto.wahllisten = wahllisteRepo.count();
        dto.zuordnungen = mappingRepo.count();
        dto.aussagen = aussagenRepo.countByAktivTrue();
        return dto;
    }
}

