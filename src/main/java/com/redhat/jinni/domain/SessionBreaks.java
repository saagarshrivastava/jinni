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
 * Session Breaks details
 */
@ApiModel(description = "Session Breaks details")
@Entity
@Table(name = "session_breaks")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "sessionbreaks")
public class SessionBreaks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "starttime")
    private LocalDate starttime;

    @Column(name = "endtime")
    private LocalDate endtime;

    @Column(name = "proctoringinstanceid")
    private Integer proctoringinstanceid;

    @ManyToOne
    @JsonIgnoreProperties("sessionBreaks")
    private ProctoringInstance proctoringinstanceid;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStarttime() {
        return starttime;
    }

    public SessionBreaks starttime(LocalDate starttime) {
        this.starttime = starttime;
        return this;
    }

    public void setStarttime(LocalDate starttime) {
        this.starttime = starttime;
    }

    public LocalDate getEndtime() {
        return endtime;
    }

    public SessionBreaks endtime(LocalDate endtime) {
        this.endtime = endtime;
        return this;
    }

    public void setEndtime(LocalDate endtime) {
        this.endtime = endtime;
    }

    public Integer getProctoringinstanceid() {
        return proctoringinstanceid;
    }

    public SessionBreaks proctoringinstanceid(Integer proctoringinstanceid) {
        this.proctoringinstanceid = proctoringinstanceid;
        return this;
    }

    public void setProctoringinstanceid(Integer proctoringinstanceid) {
        this.proctoringinstanceid = proctoringinstanceid;
    }

    public ProctoringInstance getProctoringinstanceid() {
        return proctoringinstanceid;
    }

    public SessionBreaks proctoringinstanceid(ProctoringInstance proctoringInstance) {
        this.proctoringinstanceid = proctoringInstance;
        return this;
    }

    public void setProctoringinstanceid(ProctoringInstance proctoringInstance) {
        this.proctoringinstanceid = proctoringInstance;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionBreaks)) {
            return false;
        }
        return id != null && id.equals(((SessionBreaks) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SessionBreaks{" +
            "id=" + getId() +
            ", starttime='" + getStarttime() + "'" +
            ", endtime='" + getEndtime() + "'" +
            ", proctoringinstanceid=" + getProctoringinstanceid() +
            "}";
    }
}
