package hu.cubix.hr.zoltan_sipeki.model;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class CompanyForm {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "companyFormSeq")
    @SequenceGenerator(name = "companyFormSeq", sequenceName = "company_form_seq", initialValue = 0, allocationSize = 1)
    private long id;

    @NaturalId
    private String name;

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
}
