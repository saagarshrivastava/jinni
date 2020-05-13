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
 * Location details from Gumtree
 */
@ApiModel(description = "Location details from Gumtree")
@Entity
@Table(name = "location")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rhieid")
    private Integer rhieid;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "partner")
    private String partner;

    @Column(name = "regionid")
    private Integer regionid;

    @ManyToOne
    @JsonIgnoreProperties("locations")
    private Region regionid;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRhieid() {
        return rhieid;
    }

    public Location rhieid(Integer rhieid) {
        this.rhieid = rhieid;
        return this;
    }

    public void setRhieid(Integer rhieid) {
        this.rhieid = rhieid;
    }

    public String getCity() {
        return city;
    }

    public Location city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public Location country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPartner() {
        return partner;
    }

    public Location partner(String partner) {
        this.partner = partner;
        return this;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public Integer getRegionid() {
        return regionid;
    }

    public Location regionid(Integer regionid) {
        this.regionid = regionid;
        return this;
    }

    public void setRegionid(Integer regionid) {
        this.regionid = regionid;
    }

    public Region getRegionid() {
        return regionid;
    }

    public Location regionid(Region region) {
        this.regionid = region;
        return this;
    }

    public void setRegionid(Region region) {
        this.regionid = region;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return id != null && id.equals(((Location) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", rhieid=" + getRhieid() +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            ", partner='" + getPartner() + "'" +
            ", regionid=" + getRegionid() +
            "}";
    }
}
