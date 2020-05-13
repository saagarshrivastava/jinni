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
 * Offer details
 */
@ApiModel(description = "Offer details")
@Entity
@Table(name = "offer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "offer")
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "incidentid")
    private Integer incidentid;

    @Column(name = "offertypeid")
    private Integer offertypeid;

    @Column(name = "examid")
    private Integer examid;

    @Column(name = "discountpercentage")
    private Integer discountpercentage;

    @OneToOne
    @JoinColumn(unique = true)
    private OfferType offertypeid;

    @ManyToOne
    @JsonIgnoreProperties("offers")
    private Incident incidentid;

    @ManyToOne
    @JsonIgnoreProperties("offers")
    private Exam examid;

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

    public Offer incidentid(Integer incidentid) {
        this.incidentid = incidentid;
        return this;
    }

    public void setIncidentid(Integer incidentid) {
        this.incidentid = incidentid;
    }

    public Integer getOffertypeid() {
        return offertypeid;
    }

    public Offer offertypeid(Integer offertypeid) {
        this.offertypeid = offertypeid;
        return this;
    }

    public void setOffertypeid(Integer offertypeid) {
        this.offertypeid = offertypeid;
    }

    public Integer getExamid() {
        return examid;
    }

    public Offer examid(Integer examid) {
        this.examid = examid;
        return this;
    }

    public void setExamid(Integer examid) {
        this.examid = examid;
    }

    public Integer getDiscountpercentage() {
        return discountpercentage;
    }

    public Offer discountpercentage(Integer discountpercentage) {
        this.discountpercentage = discountpercentage;
        return this;
    }

    public void setDiscountpercentage(Integer discountpercentage) {
        this.discountpercentage = discountpercentage;
    }

    public OfferType getOffertypeid() {
        return offertypeid;
    }

    public Offer offertypeid(OfferType offerType) {
        this.offertypeid = offerType;
        return this;
    }

    public void setOffertypeid(OfferType offerType) {
        this.offertypeid = offerType;
    }

    public Incident getIncidentid() {
        return incidentid;
    }

    public Offer incidentid(Incident incident) {
        this.incidentid = incident;
        return this;
    }

    public void setIncidentid(Incident incident) {
        this.incidentid = incident;
    }

    public Exam getExamid() {
        return examid;
    }

    public Offer examid(Exam exam) {
        this.examid = exam;
        return this;
    }

    public void setExamid(Exam exam) {
        this.examid = exam;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offer)) {
            return false;
        }
        return id != null && id.equals(((Offer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Offer{" +
            "id=" + getId() +
            ", incidentid=" + getIncidentid() +
            ", offertypeid=" + getOffertypeid() +
            ", examid=" + getExamid() +
            ", discountpercentage=" + getDiscountpercentage() +
            "}";
    }
}
