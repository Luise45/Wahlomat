/**
 * Entitaet für Gremium
 *
 * @author Nguemezi
 */

package htw.gruppe.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "gremium")
public class Gremium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gremium_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "gremium")
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnore //Dieses Feld nicht an das Frontend mitschicken nur id und name, damit das Frontend nicht zu viele daten bekommt
    private List<Wahlliste> wahllisten;

    @Column(name = "requires_fachbereich", nullable = false)
    private boolean requiresFachbereich = false;

    public boolean isRequiresFachbereich() {
        return requiresFachbereich;
    }

    public void setRequiresFachbereich(boolean requiresFachbereich) {
        this.requiresFachbereich = requiresFachbereich;
    }

    public Gremium() {
    }

    public Gremium(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Wahlliste> getWahllisten() {
        return wahllisten;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWahllisten(List<Wahlliste> wahllisten) {
        this.wahllisten = wahllisten;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Gremium other))
            return false;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
