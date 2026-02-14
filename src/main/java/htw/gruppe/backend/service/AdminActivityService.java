package htw.gruppe.backend.service;

import htw.gruppe.backend.entity.AdminActivity;
import htw.gruppe.backend.record.AdminActivityDto;
import htw.gruppe.backend.repository.AdminActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service für den Admin-Aktivitätsverlauf.
 * Enthält die Logik zum Speichern und Abrufen von Aktivitäten.
 * @author Nguemezi
 */
@Service
public class AdminActivityService {

    private final AdminActivityRepository repository;

    public AdminActivityService(AdminActivityRepository repository) {
        this.repository = repository;
    }

    /**
     * Speichert eine neue Admin-Aktivität mit aktuellem Zeitpunkt.
     *
     * @param description Beschreibung der Aktion
     */
    public void log(String description) {
        AdminActivity activity = new AdminActivity(description);
        repository.save(activity);
    }


    /**
     * Liefert alle Admin-Aktivitäten als DTOs,
     * sortiert nach Zeitpunkt (neueste zuerst).
     *
     * @return Liste der Aktivitäten
     */
    public List<AdminActivityDto> getAll() {
        return repository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(a -> new AdminActivityDto(
                        a.getDescription(),
                        a.getCreatedAt()
                ))
                .toList();
    }
}

