package com.pu.georgidinov.pupracticumvoltwo.domain;

import com.pu.georgidinov.pupracticumvoltwo.baseentity.BaseEntity;
import com.pu.georgidinov.pupracticumvoltwo.baseentity.BaseNamedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UnitOfMeasure implements BaseEntity, BaseNamedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UnitOfMeasure that = (UnitOfMeasure) o;
        if (!id.equals(that.id)) {
            return false;
        }
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return 11 + id.hashCode() + description.hashCode();
    }

    @Override
    public String toString() {
        return "UnitOfMeasure{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    //== builder methods ==
    public UnitOfMeasure id(Long id) {
        this.id = id;
        return this;
    }

    public UnitOfMeasure description(String description) {
        this.description = description;
        return this;
    }
}