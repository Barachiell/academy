package com.ctw.workstation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractCreateModify extends AbstractEntity{

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at")
    private LocalDateTime created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="modified_at")
    private LocalDateTime modified_at;


    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getModified_at() {
        return modified_at;
    }

    public void setModified_at(LocalDateTime modified_at) {
        this.modified_at = modified_at;
    }


    @PrePersist
    public void setCreationDateBeforePersist(){
        this.setCreated_at(LocalDateTime.now());
    }

    @PreUpdate
    public void setModifyWhenUpdated(){
        this.setModified_at(LocalDateTime.now());
    }

}
