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
 * Incident details
 */
@ApiModel(description = "Incident details")
@Entity
@Table(name = "incident")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "incident")
public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "sessionid")
    private Integer sessionid;

    @Column(name = "majorincidentid")
    private Integer majorincidentid;

    @Column(name = "failurestageid")
    private Integer failurestageid;

    @Column(name = "summary")
    private String summary;

    @Column(name = "investigationreport")
    private String investigationreport;

    @Column(name = "servicenowticketid")
    private String servicenowticketid;

    @OneToOne
    @JoinColumn(unique = true)
    private Session sessionid;

    @OneToOne
    @JoinColumn(unique = true)
    private FailureStage failurestageid;

    @ManyToOne
    @JsonIgnoreProperties("incidents")
    private MajorIncident majorincidentid;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSessionid() {
        return sessionid;
    }

    public Incident sessionid(Integer sessionid) {
        this.sessionid = sessionid;
        return this;
    }

    public void setSessionid(Integer sessionid) {
        this.sessionid = sessionid;
    }

    public Integer getMajorincidentid() {
        return majorincidentid;
    }

    public Incident majorincidentid(Integer majorincidentid) {
        this.majorincidentid = majorincidentid;
        return this;
    }

    public void setMajorincidentid(Integer majorincidentid) {
        this.majorincidentid = majorincidentid;
    }

    public Integer getFailurestageid() {
        return failurestageid;
    }

    public Incident failurestageid(Integer failurestageid) {
        this.failurestageid = failurestageid;
        return this;
    }

    public void setFailurestageid(Integer failurestageid) {
        this.failurestageid = failurestageid;
    }

    public String getSummary() {
        return summary;
    }

    public Incident summary(String summary) {
        this.summary = summary;
        return this;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getInvestigationreport() {
        return investigationreport;
    }

    public Incident investigationreport(String investigationreport) {
        this.investigationreport = investigationreport;
        return this;
    }

    public void setInvestigationreport(String investigationreport) {
        this.investigationreport = investigationreport;
    }

    public String getServicenowticketid() {
        return servicenowticketid;
    }

    public Incident servicenowticketid(String servicenowticketid) {
        this.servicenowticketid = servicenowticketid;
        return this;
    }

    public void setServicenowticketid(String servicenowticketid) {
        this.servicenowticketid = servicenowticketid;
    }

    public Session getSessionid() {
        return sessionid;
    }

    public Incident sessionid(Session session) {
        this.sessionid = session;
        return this;
    }

    public void setSessionid(Session session) {
        this.sessionid = session;
    }

    public FailureStage getFailurestageid() {
        return failurestageid;
    }

    public Incident failurestageid(FailureStage failureStage) {
        this.failurestageid = failureStage;
        return this;
    }

    public void setFailurestageid(FailureStage failureStage) {
        this.failurestageid = failureStage;
    }

    public MajorIncident getMajorincidentid() {
        return majorincidentid;
    }

    public Incident majorincidentid(MajorIncident majorIncident) {
        this.majorincidentid = majorIncident;
        return this;
    }

    public void setMajorincidentid(MajorIncident majorIncident) {
        this.majorincidentid = majorIncident;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Incident)) {
            return false;
        }
        return id != null && id.equals(((Incident) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Incident{" +
            "id=" + getId() +
            ", sessionid=" + getSessionid() +
            ", majorincidentid=" + getMajorincidentid() +
            ", failurestageid=" + getFailurestageid() +
            ", summary='" + getSummary() + "'" +
            ", investigationreport='" + getInvestigationreport() + "'" +
            ", servicenowticketid='" + getServicenowticketid() + "'" +
            "}";
    }
}
