package com.redhat.jinni.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * Category Instance details
 */
@ApiModel(description = "Category Instance details")
@Entity
@Table(name = "category_instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "categoryinstance")
public class CategoryInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "incidentid")
    private Integer incidentid;

    @Column(name = "categoryid")
    private Integer categoryid;

    @Column(name = "rank")
    private Integer rank;

    @OneToOne
    @JoinColumn(unique = true)
    private Category categoryid;

    @ManyToOne
    @JsonIgnoreProperties("categoryInstances")
    private Incident incidentid;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIncidentid() {
        return incidentid;
    }

    public CategoryInstance incidentid(Integer incidentid) {
        this.incidentid = incidentid;
        return this;
    }

    public void setIncidentid(Integer incidentid) {
        this.incidentid = incidentid;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public CategoryInstance categoryid(Integer categoryid) {
        this.categoryid = categoryid;
        return this;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public Integer getRank() {
        return rank;
    }

    public CategoryInstance rank(Integer rank) {
        this.rank = rank;
        return this;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Category getCategoryid() {
        return categoryid;
    }

    public CategoryInstance categoryid(Category category) {
        this.categoryid = category;
        return this;
    }

    public void setCategoryid(Category category) {
        this.categoryid = category;
    }

    public Incident getIncidentid() {
        return incidentid;
    }

    public CategoryInstance incidentid(Incident incident) {
        this.incidentid = incident;
        return this;
    }

    public void setIncidentid(Incident incident) {
        this.incidentid = incident;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryInstance)) {
            return false;
        }
        return id != null && id.equals(((CategoryInstance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CategoryInstance{" +
            "id=" + getId() +
            ", incidentid=" + getIncidentid() +
            ", categoryid=" + getCategoryid() +
            ", rank=" + getRank() +
            "}";
    }
}
