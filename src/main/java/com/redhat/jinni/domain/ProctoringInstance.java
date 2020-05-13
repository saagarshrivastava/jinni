package com.redhat.jinni.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * Proctoring Instance details
 */
@ApiModel(description = "Proctoring Instance details")
@Entity
@Table(name = "proctoring_instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "proctoringinstance")
public class ProctoringInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "proctorstarttime")
    private LocalDate proctorstarttime;

    @Column(name = "proctorendtime")
    private LocalDate proctorendtime;

    @Column(name = "proctorid")
    private Integer proctorid;

    @Column(name = "sessionid")
    private Integer sessionid;

    @Column(name = "sessionnotes")
    private String sessionnotes;

    @Column(name = "proctorchat")
    private String proctorchat;

    @Column(name = "suspended")
    private Boolean suspended;

    @Column(name = "terminated")
    private Boolean terminated;

    @Column(name = "numberofbreaks")
    private Integer numberofbreaks;

    @ManyToOne
    @JsonIgnoreProperties("proctoringInstances")
    private Proctor proctorid;

    @ManyToOne
    @JsonIgnoreProperties("proctoringInstances")
    private Session sessionid;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getProctorstarttime() {
        return proctorstarttime;
    }

    public ProctoringInstance proctorstarttime(LocalDate proctorstarttime) {
        this.proctorstarttime = proctorstarttime;
        return this;
    }

    public void setProctorstarttime(LocalDate proctorstarttime) {
        this.proctorstarttime = proctorstarttime;
    }

    public LocalDate getProctorendtime() {
        return proctorendtime;
    }

    public ProctoringInstance proctorendtime(LocalDate proctorendtime) {
        this.proctorendtime = proctorendtime;
        return this;
    }

    public void setProctorendtime(LocalDate proctorendtime) {
        this.proctorendtime = proctorendtime;
    }

    public Integer getProctorid() {
        return proctorid;
    }

    public ProctoringInstance proctorid(Integer proctorid) {
        this.proctorid = proctorid;
        return this;
    }

    public void setProctorid(Integer proctorid) {
        this.proctorid = proctorid;
    }

    public Integer getSessionid() {
        return sessionid;
    }

    public ProctoringInstance sessionid(Integer sessionid) {
        this.sessionid = sessionid;
        return this;
    }

    public void setSessionid(Integer sessionid) {
        this.sessionid = sessionid;
    }

    public String getSessionnotes() {
        return sessionnotes;
    }

    public ProctoringInstance sessionnotes(String sessionnotes) {
        this.sessionnotes = sessionnotes;
        return this;
    }

    public void setSessionnotes(String sessionnotes) {
        this.sessionnotes = sessionnotes;
    }

    public String getProctorchat() {
        return proctorchat;
    }

    public ProctoringInstance proctorchat(String proctorchat) {
        this.proctorchat = proctorchat;
        return this;
    }

    public void setProctorchat(String proctorchat) {
        this.proctorchat = proctorchat;
    }

    public Boolean isSuspended() {
        return suspended;
    }

    public ProctoringInstance suspended(Boolean suspended) {
        this.suspended = suspended;
        return this;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
    }

    public Boolean isTerminated() {
        return terminated;
    }

    public ProctoringInstance terminated(Boolean terminated) {
        this.terminated = terminated;
        return this;
    }

    public void setTerminated(Boolean terminated) {
        this.terminated = terminated;
    }

    public Integer getNumberofbreaks() {
        return numberofbreaks;
    }

    public ProctoringInstance numberofbreaks(Integer numberofbreaks) {
        this.numberofbreaks = numberofbreaks;
        return this;
    }

    public void setNumberofbreaks(Integer numberofbreaks) {
        this.numberofbreaks = numberofbreaks;
    }

    public Proctor getProctorid() {
        return proctorid;
    }

    public ProctoringInstance proctorid(Proctor proctor) {
        this.proctorid = proctor;
        return this;
    }

    public void setProctorid(Proctor proctor) {
        this.proctorid = proctor;
    }

    public Session getSessionid() {
        return sessionid;
    }

    public ProctoringInstance sessionid(Session session) {
        this.sessionid = session;
        return this;
    }

    public void setSessionid(Session session) {
        this.sessionid = session;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProctoringInstance)) {
            return false;
        }
        return id != null && id.equals(((ProctoringInstance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProctoringInstance{" +
            "id=" + getId() +
            ", proctorstarttime='" + getProctorstarttime() + "'" +
            ", proctorendtime='" + getProctorendtime() + "'" +
            ", proctorid=" + getProctorid() +
            ", sessionid=" + getSessionid() +
            ", sessionnotes='" + getSessionnotes() + "'" +
            ", proctorchat='" + getProctorchat() + "'" +
            ", suspended='" + isSuspended() + "'" +
            ", terminated='" + isTerminated() + "'" +
            ", numberofbreaks=" + getNumberofbreaks() +
            "}";
    }
}
