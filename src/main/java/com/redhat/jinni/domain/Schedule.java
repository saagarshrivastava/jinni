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
 * Exam Schedule details mostly pulled from Gumtree.
 */
@ApiModel(description = "Exam Schedule details mostly pulled from Gumtree.")
@Entity
@Table(name = "schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "schedule")
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "scheduledsetupstarttime")
    private LocalDate scheduledsetupstarttime;

    @Column(name = "actualsetupstarttime")
    private LocalDate actualsetupstarttime;

    @Column(name = "scheduledsetupendtime")
    private LocalDate scheduledsetupendtime;

    @Column(name = "actualsetupendtime")
    private LocalDate actualsetupendtime;

    @Column(name = "scheduledcandidatearrivaltime")
    private LocalDate scheduledcandidatearrivaltime;

    @Column(name = "actualcandidatearrivaltime")
    private LocalDate actualcandidatearrivaltime;

    @Column(name = "scheduledproctorarrivaltime")
    private LocalDate scheduledproctorarrivaltime;

    @Column(name = "actualproctorarrivaltime")
    private LocalDate actualproctorarrivaltime;

    @Column(name = "scheduledonboardingstarttime")
    private LocalDate scheduledonboardingstarttime;

    @Column(name = "actualonboardingstarttime")
    private LocalDate actualonboardingstarttime;

    @Column(name = "scheduledonboardingendtime")
    private LocalDate scheduledonboardingendtime;

    @Column(name = "actualonboardingendtime")
    private LocalDate actualonboardingendtime;

    @Column(name = "scheduledexamstarttime")
    private LocalDate scheduledexamstarttime;

    @Column(name = "actualexamstarttime")
    private LocalDate actualexamstarttime;

    @Column(name = "scheduledexamendtime")
    private LocalDate scheduledexamendtime;

    @Column(name = "actualexamendtime")
    private LocalDate actualexamendtime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getScheduledsetupstarttime() {
        return scheduledsetupstarttime;
    }

    public Schedule scheduledsetupstarttime(LocalDate scheduledsetupstarttime) {
        this.scheduledsetupstarttime = scheduledsetupstarttime;
        return this;
    }

    public void setScheduledsetupstarttime(LocalDate scheduledsetupstarttime) {
        this.scheduledsetupstarttime = scheduledsetupstarttime;
    }

    public LocalDate getActualsetupstarttime() {
        return actualsetupstarttime;
    }

    public Schedule actualsetupstarttime(LocalDate actualsetupstarttime) {
        this.actualsetupstarttime = actualsetupstarttime;
        return this;
    }

    public void setActualsetupstarttime(LocalDate actualsetupstarttime) {
        this.actualsetupstarttime = actualsetupstarttime;
    }

    public LocalDate getScheduledsetupendtime() {
        return scheduledsetupendtime;
    }

    public Schedule scheduledsetupendtime(LocalDate scheduledsetupendtime) {
        this.scheduledsetupendtime = scheduledsetupendtime;
        return this;
    }

    public void setScheduledsetupendtime(LocalDate scheduledsetupendtime) {
        this.scheduledsetupendtime = scheduledsetupendtime;
    }

    public LocalDate getActualsetupendtime() {
        return actualsetupendtime;
    }

    public Schedule actualsetupendtime(LocalDate actualsetupendtime) {
        this.actualsetupendtime = actualsetupendtime;
        return this;
    }

    public void setActualsetupendtime(LocalDate actualsetupendtime) {
        this.actualsetupendtime = actualsetupendtime;
    }

    public LocalDate getScheduledcandidatearrivaltime() {
        return scheduledcandidatearrivaltime;
    }

    public Schedule scheduledcandidatearrivaltime(LocalDate scheduledcandidatearrivaltime) {
        this.scheduledcandidatearrivaltime = scheduledcandidatearrivaltime;
        return this;
    }

    public void setScheduledcandidatearrivaltime(LocalDate scheduledcandidatearrivaltime) {
        this.scheduledcandidatearrivaltime = scheduledcandidatearrivaltime;
    }

    public LocalDate getActualcandidatearrivaltime() {
        return actualcandidatearrivaltime;
    }

    public Schedule actualcandidatearrivaltime(LocalDate actualcandidatearrivaltime) {
        this.actualcandidatearrivaltime = actualcandidatearrivaltime;
        return this;
    }

    public void setActualcandidatearrivaltime(LocalDate actualcandidatearrivaltime) {
        this.actualcandidatearrivaltime = actualcandidatearrivaltime;
    }

    public LocalDate getScheduledproctorarrivaltime() {
        return scheduledproctorarrivaltime;
    }

    public Schedule scheduledproctorarrivaltime(LocalDate scheduledproctorarrivaltime) {
        this.scheduledproctorarrivaltime = scheduledproctorarrivaltime;
        return this;
    }

    public void setScheduledproctorarrivaltime(LocalDate scheduledproctorarrivaltime) {
        this.scheduledproctorarrivaltime = scheduledproctorarrivaltime;
    }

    public LocalDate getActualproctorarrivaltime() {
        return actualproctorarrivaltime;
    }

    public Schedule actualproctorarrivaltime(LocalDate actualproctorarrivaltime) {
        this.actualproctorarrivaltime = actualproctorarrivaltime;
        return this;
    }

    public void setActualproctorarrivaltime(LocalDate actualproctorarrivaltime) {
        this.actualproctorarrivaltime = actualproctorarrivaltime;
    }

    public LocalDate getScheduledonboardingstarttime() {
        return scheduledonboardingstarttime;
    }

    public Schedule scheduledonboardingstarttime(LocalDate scheduledonboardingstarttime) {
        this.scheduledonboardingstarttime = scheduledonboardingstarttime;
        return this;
    }

    public void setScheduledonboardingstarttime(LocalDate scheduledonboardingstarttime) {
        this.scheduledonboardingstarttime = scheduledonboardingstarttime;
    }

    public LocalDate getActualonboardingstarttime() {
        return actualonboardingstarttime;
    }

    public Schedule actualonboardingstarttime(LocalDate actualonboardingstarttime) {
        this.actualonboardingstarttime = actualonboardingstarttime;
        return this;
    }

    public void setActualonboardingstarttime(LocalDate actualonboardingstarttime) {
        this.actualonboardingstarttime = actualonboardingstarttime;
    }

    public LocalDate getScheduledonboardingendtime() {
        return scheduledonboardingendtime;
    }

    public Schedule scheduledonboardingendtime(LocalDate scheduledonboardingendtime) {
        this.scheduledonboardingendtime = scheduledonboardingendtime;
        return this;
    }

    public void setScheduledonboardingendtime(LocalDate scheduledonboardingendtime) {
        this.scheduledonboardingendtime = scheduledonboardingendtime;
    }

    public LocalDate getActualonboardingendtime() {
        return actualonboardingendtime;
    }

    public Schedule actualonboardingendtime(LocalDate actualonboardingendtime) {
        this.actualonboardingendtime = actualonboardingendtime;
        return this;
    }

    public void setActualonboardingendtime(LocalDate actualonboardingendtime) {
        this.actualonboardingendtime = actualonboardingendtime;
    }

    public LocalDate getScheduledexamstarttime() {
        return scheduledexamstarttime;
    }

    public Schedule scheduledexamstarttime(LocalDate scheduledexamstarttime) {
        this.scheduledexamstarttime = scheduledexamstarttime;
        return this;
    }

    public void setScheduledexamstarttime(LocalDate scheduledexamstarttime) {
        this.scheduledexamstarttime = scheduledexamstarttime;
    }

    public LocalDate getActualexamstarttime() {
        return actualexamstarttime;
    }

    public Schedule actualexamstarttime(LocalDate actualexamstarttime) {
        this.actualexamstarttime = actualexamstarttime;
        return this;
    }

    public void setActualexamstarttime(LocalDate actualexamstarttime) {
        this.actualexamstarttime = actualexamstarttime;
    }

    public LocalDate getScheduledexamendtime() {
        return scheduledexamendtime;
    }

    public Schedule scheduledexamendtime(LocalDate scheduledexamendtime) {
        this.scheduledexamendtime = scheduledexamendtime;
        return this;
    }

    public void setScheduledexamendtime(LocalDate scheduledexamendtime) {
        this.scheduledexamendtime = scheduledexamendtime;
    }

    public LocalDate getActualexamendtime() {
        return actualexamendtime;
    }

    public Schedule actualexamendtime(LocalDate actualexamendtime) {
        this.actualexamendtime = actualexamendtime;
        return this;
    }

    public void setActualexamendtime(LocalDate actualexamendtime) {
        this.actualexamendtime = actualexamendtime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Schedule)) {
            return false;
        }
        return id != null && id.equals(((Schedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Schedule{" +
            "id=" + getId() +
            ", scheduledsetupstarttime='" + getScheduledsetupstarttime() + "'" +
            ", actualsetupstarttime='" + getActualsetupstarttime() + "'" +
            ", scheduledsetupendtime='" + getScheduledsetupendtime() + "'" +
            ", actualsetupendtime='" + getActualsetupendtime() + "'" +
            ", scheduledcandidatearrivaltime='" + getScheduledcandidatearrivaltime() + "'" +
            ", actualcandidatearrivaltime='" + getActualcandidatearrivaltime() + "'" +
            ", scheduledproctorarrivaltime='" + getScheduledproctorarrivaltime() + "'" +
            ", actualproctorarrivaltime='" + getActualproctorarrivaltime() + "'" +
            ", scheduledonboardingstarttime='" + getScheduledonboardingstarttime() + "'" +
            ", actualonboardingstarttime='" + getActualonboardingstarttime() + "'" +
            ", scheduledonboardingendtime='" + getScheduledonboardingendtime() + "'" +
            ", actualonboardingendtime='" + getActualonboardingendtime() + "'" +
            ", scheduledexamstarttime='" + getScheduledexamstarttime() + "'" +
            ", actualexamstarttime='" + getActualexamstarttime() + "'" +
            ", scheduledexamendtime='" + getScheduledexamendtime() + "'" +
            ", actualexamendtime='" + getActualexamendtime() + "'" +
            "}";
    }
}
