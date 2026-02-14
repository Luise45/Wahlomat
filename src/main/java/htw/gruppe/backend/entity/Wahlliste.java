

package htw.gruppe.backend.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entität zur Darstellung einer Wahlliste innerhalb eines Gremiums.
 * <p>
 * Eine Wahlliste gehört genau zu einem {@link Gremium} und enthält mehrere
 * {@link KandidatAufWahlliste}-Einträge, über die die Kandidaten zugeordnet werden.
 * <p>
 * Zusätzlich besitzt eine Wahlliste einen Validierungsstatus, der angibt,
 * ob sie zur Wahl zugelassen ist.
 *
 * @author Erdogan
 */

@Entity
@Table(name = "wahlliste")
public class Wahlliste {
    
    /**
     * Primärschlüssel der Wahlliste.
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wahlliste_id")
    private Long id;

    /**
     * Name der Wahlliste (z. B. "Liste A").
     */

    @Column(nullable = false)
    private String name;

    /**
     * Gibt an, ob die Wahlliste validiert wurde.
     */

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Column(nullable = false)
    private boolean valid = false;

    /**
     * Gremium, zu dem die Wahlliste gehört.
     */

    public boolean isValid() {
        return valid;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "gremium_id", nullable = false)
    @JsonIgnoreProperties({"wahllisten"})
    private Gremium gremium;


    /**
     * Kandidaten-Zuordnungen dieser Wahlliste.
     */


    @OneToMany(mappedBy = "wahlliste", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("wahlliste")
    private List<KandidatAufWahlliste> kandidaten = new ArrayList<>();

    public Wahlliste() {
    }

    /**
     * Erstellt eine neue Wahlliste für ein bestimmtes Gremium.
     *
     * @param name    Name der Wahlliste
     * @param gremium zugehöriges Gremium
     */

    public Wahlliste(String name, Gremium gremium) {
        this.name = name;
        this.gremium = gremium;
    }

    /**
     * @return ID der Wahlliste
     */

    public Long getId() {
        return id;
    }

    /**
     * @return Name der Wahlliste
     */


    public String getName() {
        return name;
    }

    public Gremium getGremium() {
        return gremium;
    }

    public List<KandidatAufWahlliste> getKandidaten() {
        return kandidaten;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGremium(Gremium gremium) {
        this.gremium = gremium;
    }

    /**
     * Zwei Wahllisten sind gleich, wenn sie die gleiche ID haben.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Wahlliste other))
            return false;
        return id != null && id.equals(other.id);
    }

   /**
     * Hashcode basiert auf der ID.
     */


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
