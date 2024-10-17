package hu.cubix.hr.zoltan_sipeki.model;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Position {

    public enum  Qualification {
        NONE,
        HIGH_SCHOOL,
        COLLEGE,
        UNIVERSITY
    }

    @Id
    @GeneratedValue
    private long id;

    @NaturalId
    private String name;

    @Enumerated(EnumType.STRING)
    private Qualification minQualification;

    public Position() {
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Qualification getMinQualification() {
        return minQualification;
    }

    public void setMinQualification(Qualification minQualification) {
        this.minQualification = minQualification;
    }

    @Override
    public String toString() {
        return name;
    }
}
