package br.edu.ifpb.mestrado.openplanner.api.domain.shared;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

@MappedSuperclass
@Where(clause = "excluded = FALSE")
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -1064151572841012966L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotNull
    @Column(name = "excluded", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    protected Boolean excluded;

    @Column(name = "excluded_at", nullable = true)
    protected LocalDate excludedAt;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    protected Integer version;

    public BaseEntity() {
        super();
        excluded = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getExcluded() {
        return excluded;
    }

    public void setExcluded(Boolean excluded) {
        this.excluded = excluded;
    }

    public LocalDate getExcludedAt() {
        return excludedAt;
    }

    public void setExcludedAt(LocalDate excludedAt) {
        this.excludedAt = excludedAt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BaseEntity other = (BaseEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("BaseEntity [id=%s]", id);
    }
}
