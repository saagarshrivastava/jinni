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
 * Support Instance details
 */
@ApiModel(description = "Support Instance details")
@Entity
@Table(name = "support_instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "supportinstance")
public class SupportInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "starttime")
    private LocalDate starttime;

    @Column(name = "endtime")
    private LocalDate endtime;

    @Column(name = "chatlogs")
    private String chatlogs;

    @Column(name = "sessionid")
    private Integer sessionid;

    @Column(name = "supportpersonid")
    private Integer supportpersonid;

    @OneToOne
    @JoinColumn(unique = true)
    private SupportPerson supportpersonid;

    @ManyToOne
    @JsonIgnoreProperties("supportInstances")
    private Session sessionid;

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

    public SupportInstance starttime(LocalDate starttime) {
        this.starttime = starttime;
        return this;
    }

    public void setStarttime(LocalDate starttime) {
        this.starttime = starttime;
    }

    public LocalDate getEndtime() {
        return endtime;
    }

    public SupportInstance endtime(LocalDate endtime) {
        this.endtime = endtime;
        return this;
    }

    public void setEndtime(LocalDate endtime) {
        this.endtime = endtime;
    }

    public String getChatlogs() {
        return chatlogs;
    }

    public SupportInstance chatlogs(String chatlogs) {
        this.chatlogs = chatlogs;
        return this;
    }

    public void setChatlogs(String chatlogs) {
        this.chatlogs = chatlogs;
    }

    public Integer getSessionid() {
        return sessionid;
    }

    public SupportInstance sessionid(Integer sessionid) {
        this.sessionid = sessionid;
        return this;
    }

    public void setSessionid(Integer sessionid) {
        this.sessionid = sessionid;
    }

    public Integer getSupportpersonid() {
        return supportpersonid;
    }

    public SupportInstance supportpersonid(Integer supportpersonid) {
        this.supportpersonid = supportpersonid;
        return this;
    }

    public void setSupportpersonid(Integer supportpersonid) {
        this.supportpersonid = supportpersonid;
    }

    public SupportPerson getSupportpersonid() {
        return supportpersonid;
    }

    public SupportInstance supportpersonid(SupportPerson supportPerson) {
        this.supportpersonid = supportPerson;
        return this;
    }

    public void setSupportpersonid(SupportPerson supportPerson) {
        this.supportpersonid = supportPerson;
    }

    public Session getSessionid() {
        return sessionid;
    }

    public SupportInstance sessionid(Session session) {
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
        if (!(o instanceof SupportInstance)) {
            return false;
        }
        return id != null && id.equals(((SupportInstance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SupportInstance{" +
            "id=" + getId() +
            ", starttime='" + getStarttime() + "'" +
            ", endtime='" + getEndtime() + "'" +
            ", chatlogs='" + getChatlogs() + "'" +
            ", sessionid=" + getSessionid() +
            ", supportpersonid=" + getSupportpersonid() +
            "}";
    }
}
