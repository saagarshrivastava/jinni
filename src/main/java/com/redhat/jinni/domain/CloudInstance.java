package com.redhat.jinni.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * Cloud Instance details
 */
@ApiModel(description = "Cloud Instance details")
@Entity
@Table(name = "cloud_instance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "cloudinstance")
public class CloudInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "cloudregionid")
    private Integer cloudregionid;

    @Column(name = "exambackendid")
    private Integer exambackendid;

    @OneToOne
    @JoinColumn(unique = true)
    private CloudRegion cloudregionid;

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

    public String getCode() {
        return code;
    }

    public CloudInstance code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public CloudInstance description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCloudregionid() {
        return cloudregionid;
    }

    public CloudInstance cloudregionid(Integer cloudregionid) {
        this.cloudregionid = cloudregionid;
        return this;
    }

    public void setCloudregionid(Integer cloudregionid) {
        this.cloudregionid = cloudregionid;
    }

    public Integer getExambackendid() {
        return exambackendid;
    }

    public CloudInstance exambackendid(Integer exambackendid) {
        this.exambackendid = exambackendid;
        return this;
    }

    public void setExambackendid(Integer exambackendid) {
        this.exambackendid = exambackendid;
    }

    public CloudRegion getCloudregionid() {
        return cloudregionid;
    }

    public CloudInstance cloudregionid(CloudRegion cloudRegion) {
        this.cloudregionid = cloudRegion;
        return this;
    }

    public void setCloudregionid(CloudRegion cloudRegion) {
        this.cloudregionid = cloudRegion;
    }

    public ExamBackend getExambackendid() {
        return exambackendid;
    }

    public CloudInstance exambackendid(ExamBackend examBackend) {
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
        if (!(o instanceof CloudInstance)) {
            return false;
        }
        return id != null && id.equals(((CloudInstance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CloudInstance{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", cloudregionid=" + getCloudregionid() +
            ", exambackendid=" + getExambackendid() +
            "}";
    }
}
