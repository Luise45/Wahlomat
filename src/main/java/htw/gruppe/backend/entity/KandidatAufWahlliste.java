

package htw.gruppe.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Entität zur Abbildung der Zuordnung zwischen einem Kandidaten und einer Wahlliste.
 * <p>
 * Diese Klasse repräsentiert die Many-to-Many-Beziehung zwischen {@link Kandidat}
 * und {@link Wahlliste} als eigene Tabelle, da zusätzliche Geschäftsregeln gelten
 * (z. B. ein Kandidat darf pro Gremium nur auf einer Wahlliste stehen).
 * <p>
 * In der Datenbank wird diese Beziehung über die Tabelle {@code kandidataufwahlliste}
 * mit einer Unique-Constraint auf {@code (kandidat_id, wahlliste_id)} abgebildet.
 *
 * @author Erdogan
 */

@Entity

@Table(name = "kandidataufwahlliste", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "kandidat_id", "wahlliste_id" })
})

public class KandidatAufWahlliste {

    /**
     * Primärschlüssel der Zuordnung.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kandidat_auf_wahlliste_id")
    private Long id;

    /**
     * Referenz auf den Kandidaten.
     */

    @ManyToOne(optional = false)
    @JoinColumn(name = "kandidat_id", nullable = false)
    @JsonIgnoreProperties({ "password", "kandidatenAntworten", "wahllisten" })
    private Kandidat kandidat;

    /**
     * Referenz auf die Wahlliste.
     */

    @ManyToOne(optional = false)
    @JoinColumn(name = "wahlliste_id", nullable = false)
    @JsonIgnoreProperties({ "kandidaten", "gremium" })
    private Wahlliste wahlliste;

    /**
     * Leerer Konstruktor für JPA.
     */

    public KandidatAufWahlliste() {

    /**
     * Konstruktor zur Erstellung einer neuen Zuordnung zwischen Kandidat und Wahlliste.
     *
     * @param kandidat  der Kandidat
     * @param wahlliste die Wahlliste
     */

    }

    public KandidatAufWahlliste(Kandidat kandidat, Wahlliste wahlliste) {
        this.kandidat = kandidat;
        this.wahlliste = wahlliste;
    }

    /**
     * @return ID der Zuordnung
     */

    public Long getId() {
        return id;
    }

    /**
     * @return der zugeordnete Kandidat
     */

    public Kandidat getKandidat() {
        return kandidat;
    }

    /**
     * @return die zugeordnete Wahlliste
     */


    public Wahlliste getWahlliste() {
        return wahlliste;
    }

    /**
     * Setzt den Kandidaten der Zuordnung.
     *
     * @param kandidat der neue Kandidat
     */

    public void setKandidat(Kandidat kandidat1) {
        
    }

    public void setWahlliste(Wahlliste liste) {

    }

}
