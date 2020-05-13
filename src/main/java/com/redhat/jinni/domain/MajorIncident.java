package com.redhat.jinni.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * Major Incident details
 */
@ApiModel(description = "Major Incident details")
@Entity
@Table(name = "major_incident")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "majorincident")
public class MajorIncident implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "majorincidentsourceid")
    private Integer majorincidentsourceid;

    @Column(name = "starttime")
    private LocalDate starttime;

    @Column(name = "endtime")
    private LocalDate endtime;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "details")
    private String details;

    @OneToOne
    @JoinColumn(unique = true)
    private MajorIncidentSource majorincidentsourceid;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMajorincidentsourceid() {
        return majorincidentsourceid;
    }

    public MajorIncident majorincidentsourceid(Integer majorincidentsourceid) {
        this.majorincidentsourceid = majorincidentsourceid;
        return this;
    }

    public void setMajorincidentsourceid(Integer majorincidentsourceid) {
        this.majorincidentsourceid = majorincidentsourceid;
    }

    public LocalDate getStarttime() {
        return starttime;
    }

    public MajorIncident starttime(LocalDate starttime) {
        this.starttime = starttime;
        return this;
    }

    public void setStarttime(LocalDate starttime) {
        this.starttime = starttime;
    }

    public LocalDate getEndtime() {
        return endtime;
    }

    public MajorIncident endtime(LocalDate endtime) {
        this.endtime = endtime;
        return this;
    }

    public void setEndtime(LocalDate endtime) {
        this.endtime = endtime;
    }

    public LocalDate getDate() {
        return date;
    }

    public MajorIncident date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public MajorIncident details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public MajorIncidentSource getMajorincidentsourceid() {
        return majorincidentsourceid;
    }

    public MajorIncident majorincidentsourceid(MajorIncidentSource majorIncidentSource) {
        this.majorincidentsourceid = majorIncidentSource;
        return this;
    }

    public void setMajorincidentsourceid(MajorIncidentSource majorIncidentSource) {
        this.majorincidentsourceid = majorIncidentSource;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MajorIncident)) {
            return false;
        }
        return id != null && id.equals(((MajorIncident) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MajorIncident{" +
            "id=" + getId() +
            ", majorincidentsourceid=" + getMajorincidentsourceid() +
            ", starttime='" + getStarttime() + "'" +
            ", endtime='" + getEndtime() + "'" +
            ", date='" + getDate() + "'" +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
