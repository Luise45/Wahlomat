package htw.gruppe.backend.entity;

import jakarta.persistence.*;
import java.time.Instant;
import org.hibernate.annotations.CreationTimestamp;


/**
 * Entity für den Aktivitätsverlauf des Admins.
 * Jeder Eintrag beschreibt eine durchgeführte Aktion
 * (z.B. Kandidat entfernt) mit Zeitstempel.
 *
 * @author Nguemezi
 */
@Entity
@Table(name = "admin_activity")
public class AdminActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Beschreibung der Aktion (z.B. "Du hast den Kandidaten Anna Buch entfernt")
     */
    @Column(nullable = false, length = 400)
    private String description;

    /**
     * Zeitpunkt, zu dem die Aktion durchgeführt wurde
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt; //Ein exakter Zeitpunkt


    public AdminActivity() {}

    public AdminActivity(String description) {
        this.description = description;
    }


    public Long getId() {

        return id;
    }

    public String getDescription() {

        return description;
    }

    public Instant getCreatedAt() {

        return createdAt;
    }

}
