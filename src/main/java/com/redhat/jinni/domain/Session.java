package com.redhat.jinni.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * An exam session related information.\nThis includes a number of duplicate (read only) properties\nfrom other entities which are there to make querying easier.
 */
@ApiModel(description = "An exam session related information.\nThis includes a number of duplicate (read only) properties\nfrom other entities which are there to make querying easier.")
@Entity
@Table(name = "session")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "session")
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "scheduleid")
    private Integer scheduleid;

    @Column(name = "candidateid")
    private Integer candidateid;

    @Column(name = "locationid")
    private Integer locationid;

    @Column(name = "examtypeid")
    private Integer examtypeid;

    @Column(name = "deliverytypeid")
    private Integer deliverytypeid;

    @Column(name = "deliverystatusid")
    private Integer deliverystatusid;

    @Column(name = "examid")
    private Integer examid;

    @Column(name = "exambackendid")
    private Integer exambackendid;

    @Column(name = "reservationid")
    private String reservationid;

    @OneToOne
    @JoinColumn(unique = true)
    private Schedule scheduleid;

    @OneToOne
    @JoinColumn(unique = true)
    private Candidate candidateid;

    @OneToOne
    @JoinColumn(unique = true)
    private Location locationid;

    @OneToOne
    @JoinColumn(unique = true)
    private ExamType examtypeid;

    @OneToOne
    @JoinColumn(unique = true)
    private DeliveryType deliverytypeid;

    @OneToOne
    @JoinColumn(unique = true)
    private DeliveryStatus deliverystatusid;

    @OneToOne
    @JoinColumn(unique = true)
    private Exam examid;

    @OneToOne
    @JoinColumn(unique = true)
    private ExamBackend exambackendid;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScheduleid() {
        return scheduleid;
    }

    public Session scheduleid(Integer scheduleid) {
        this.scheduleid = scheduleid;
        return this;
    }

    public void setScheduleid(Integer scheduleid) {
        this.scheduleid = scheduleid;
    }

    public Integer getCandidateid() {
        return candidateid;
    }

    public Session candidateid(Integer candidateid) {
        this.candidateid = candidateid;
        return this;
    }

    public void setCandidateid(Integer candidateid) {
        this.candidateid = candidateid;
    }

    public Integer getLocationid() {
        return locationid;
    }

    public Session locationid(Integer locationid) {
        this.locationid = locationid;
        return this;
    }

    public void setLocationid(Integer locationid) {
        this.locationid = locationid;
    }

    public Integer getExamtypeid() {
        return examtypeid;
    }

    public Session examtypeid(Integer examtypeid) {
        this.examtypeid = examtypeid;
        return this;
    }

    public void setExamtypeid(Integer examtypeid) {
        this.examtypeid = examtypeid;
    }

    public Integer getDeliverytypeid() {
        return deliverytypeid;
    }

    public Session deliverytypeid(Integer deliverytypeid) {
        this.deliverytypeid = deliverytypeid;
        return this;
    }

    public void setDeliverytypeid(Integer deliverytypeid) {
        this.deliverytypeid = deliverytypeid;
    }

    public Integer getDeliverystatusid() {
        return deliverystatusid;
    }

    public Session deliverystatusid(Integer deliverystatusid) {
        this.deliverystatusid = deliverystatusid;
        return this;
    }

    public void setDeliverystatusid(Integer deliverystatusid) {
        this.deliverystatusid = deliverystatusid;
    }

    public Integer getExamid() {
        return examid;
    }

    public Session examid(Integer examid) {
        this.examid = examid;
        return this;
    }

    public void setExamid(Integer examid) {
        this.examid = examid;
    }

    public Integer getExambackendid() {
        return exambackendid;
    }

    public Session exambackendid(Integer exambackendid) {
        this.exambackendid = exambackendid;
        return this;
    }

    public void setExambackendid(Integer exambackendid) {
        this.exambackendid = exambackendid;
    }

    public String getReservationid() {
        return reservationid;
    }

    public Session reservationid(String reservationid) {
        this.reservationid = reservationid;
        return this;
    }

    public void setReservationid(String reservationid) {
        this.reservationid = reservationid;
    }

    public Schedule getScheduleid() {
        return scheduleid;
    }

    public Session scheduleid(Schedule schedule) {
        this.scheduleid = schedule;
        return this;
    }

    public void setScheduleid(Schedule schedule) {
        this.scheduleid = schedule;
    }

    public Candidate getCandidateid() {
        return candidateid;
    }

    public Session candidateid(Candidate candidate) {
        this.candidateid = candidate;
        return this;
    }

    public void setCandidateid(Candidate candidate) {
        this.candidateid = candidate;
    }

    public Location getLocationid() {
        return locationid;
    }

    public Session locationid(Location location) {
        this.locationid = location;
        return this;
    }

    public void setLocationid(Location location) {
        this.locationid = location;
    }

    public ExamType getExamtypeid() {
        return examtypeid;
    }

    public Session examtypeid(ExamType examType) {
        this.examtypeid = examType;
        return this;
    }

    public void setExamtypeid(ExamType examType) {
        this.examtypeid = examType;
    }

    public DeliveryType getDeliverytypeid() {
        return deliverytypeid;
    }

    public Session deliverytypeid(DeliveryType deliveryType) {
        this.deliverytypeid = deliveryType;
        return this;
    }

    public void setDeliverytypeid(DeliveryType deliveryType) {
        this.deliverytypeid = deliveryType;
    }

    public DeliveryStatus getDeliverystatusid() {
        return deliverystatusid;
    }

    public Session deliverystatusid(DeliveryStatus deliveryStatus) {
        this.deliverystatusid = deliveryStatus;
        return this;
    }

    public void setDeliverystatusid(DeliveryStatus deliveryStatus) {
        this.deliverystatusid = deliveryStatus;
    }

    public Exam getExamid() {
        return examid;
    }

    public Session examid(Exam exam) {
        this.examid = exam;
        return this;
    }

    public void setExamid(Exam exam) {
        this.examid = exam;
    }

    public ExamBackend getExambackendid() {
        return exambackendid;
    }

    public Session exambackendid(ExamBackend examBackend) {
        this.exambackendid = examBackend;
        return this;
    }

    public void setExambackendid(ExamBackend examBackend) {
        this.exambackendid = examBackend;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Session)) {
            return false;
        }
        return id != null && id.equals(((Session) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Session{" +
            "id=" + getId() +
            ", scheduleid=" + getScheduleid() +
            ", candidateid=" + getCandidateid() +
            ", locationid=" + getLocationid() +
            ", examtypeid=" + getExamtypeid() +
            ", deliverytypeid=" + getDeliverytypeid() +
            ", deliverystatusid=" + getDeliverystatusid() +
            ", examid=" + getExamid() +
            ", exambackendid=" + getExambackendid() +
            ", reservationid='" + getReservationid() + "'" +
            "}";
    }
}
