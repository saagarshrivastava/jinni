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
 * Subcategory Instance details
 */
@ApiModel(description = "Subcategory Instance details")
@Entity
@Table(name = "subcategory_instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "subcategoryinstance")
public class SubcategoryInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "categoryinstanceid")
    private Integer categoryinstanceid;

    @Column(name = "subcategoryid")
    private Integer subcategoryid;

    @Column(name = "rank")
    private Integer rank;

    @OneToOne
    @JoinColumn(unique = true)
    private Subcategory subcategoryid;

    @ManyToOne
    @JsonIgnoreProperties("subcategoryInstances")
    private CategoryInstance categoryinstanceid;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCategoryinstanceid() {
        return categoryinstanceid;
    }

    public SubcategoryInstance categoryinstanceid(Integer categoryinstanceid) {
        this.categoryinstanceid = categoryinstanceid;
        return this;
    }

    public void setCategoryinstanceid(Integer categoryinstanceid) {
        this.categoryinstanceid = categoryinstanceid;
    }

    public Integer getSubcategoryid() {
        return subcategoryid;
    }

    public SubcategoryInstance subcategoryid(Integer subcategoryid) {
        this.subcategoryid = subcategoryid;
        return this;
    }

    public void setSubcategoryid(Integer subcategoryid) {
        this.subcategoryid = subcategoryid;
    }

    public Integer getRank() {
        return rank;
    }

    public SubcategoryInstance rank(Integer rank) {
        this.rank = rank;
        return this;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Subcategory getSubcategoryid() {
        return subcategoryid;
    }

    public SubcategoryInstance subcategoryid(Subcategory subcategory) {
        this.subcategoryid = subcategory;
        return this;
    }

    public void setSubcategoryid(Subcategory subcategory) {
        this.subcategoryid = subcategory;
    }

    public CategoryInstance getCategoryinstanceid() {
        return categoryinstanceid;
    }

    public SubcategoryInstance categoryinstanceid(CategoryInstance categoryInstance) {
        this.categoryinstanceid = categoryInstance;
        return this;
    }

    public void setCategoryinstanceid(CategoryInstance categoryInstance) {
        this.categoryinstanceid = categoryInstance;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubcategoryInstance)) {
            return false;
        }
        return id != null && id.equals(((SubcategoryInstance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SubcategoryInstance{" +
            "id=" + getId() +
            ", categoryinstanceid=" + getCategoryinstanceid() +
            ", subcategoryid=" + getSubcategoryid() +
            ", rank=" + getRank() +
            "}";
    }
}
