/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.entities;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * @author Patrick KENNE
 * @Project Pline Pay
 * @Since 08/08/2023 - 08:45
 */
@MappedSuperclass
public class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id; // UUID

    @Column(name = "date_creation")
    private Long dateCreation;

    @Column(name = "last_date_update")
    private Long lastDateUpdate;

    @Column(name = "user_creation", length = 190)
    private String userCreation;

    @Column(name = "last_user_update", length = 190)
    private String lastUserUpdate;

    @Column(name = "entity_state")
    private Boolean entityState;

    @Column(name = "flag", nullable = false)
    private Boolean flag;

    public AbstractEntity() {
    }

    public AbstractEntity(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Long dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Long getLastDateUpdate() {
        return lastDateUpdate;
    }

    public void setLastDateUpdate(Long lastDateUpdate) {
        this.lastDateUpdate = lastDateUpdate;
    }

    public String getUserCreation() {
        return userCreation;
    }

    public void setUserCreation(String userCreation) {
        this.userCreation = userCreation;
    }

    public String getLastUserUpdate() {
        return lastUserUpdate;
    }

    public void setLastUserUpdate(String lastUserUpdate) {
        this.lastUserUpdate = lastUserUpdate;
    }

    public Boolean getEntityState() {
        return entityState;
    }

    public void setEntityState(Boolean entityState) {
        this.entityState = entityState;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

}
